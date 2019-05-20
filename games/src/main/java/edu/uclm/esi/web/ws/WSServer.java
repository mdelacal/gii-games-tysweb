package edu.uclm.esi.web.ws;

import java.io.IOException;
import java.util.Collection;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.internal.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; //new import
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage; //new import
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper; //new import

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.mongolabels.dao.MongoBroker;
import edu.uclm.esi.web.Manager;

@Component
public class WSServer extends TextWebSocketHandler {
	private ConcurrentHashMap<String, WebSocketSession> sessionsById=new ConcurrentHashMap<>();
	private static ConcurrentHashMap<String, WebSocketSession> sessionsByPlayer=new ConcurrentHashMap<>(); //new-> lo pongo static
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessionsById.put(session.getId(), session);
		Player player = (Player) session.getAttributes().get("player");
		String userName=player.getUserName();
		if (sessionsByPlayer.get(userName)!=null) {
			sessionsByPlayer.remove(userName);
		}
		
		//cargar foto
		byte[] foto=player.loadFoto();
		if(foto!=null)
			sendBinary(session, foto);
		sessionsByPlayer.put(userName, session);
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
		//si el usuario se va le quitamos de la lista de sesiones
		Player player = (Player) session.getAttributes().get("player");
		String userName=player.getUserName();
		if (sessionsByPlayer.get(userName)!=null) {
			sessionsByPlayer.remove(userName);
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {		
		JSONObject jso = new JSONObject(message.getPayload());

		//si llega un mensaje del chat
		if(jso.get("TYPE").equals("MENSAJE")) {
			try {
				jso.put("TYPE", jso.get("TYPE"));
				jso.put("player", jso.get("player"));
				jso.put("mensaje", jso.get("mensaje"));
					
				WebSocketMessage<?> msgchat=new TextMessage(jso.toString());
				Collection<WebSocketSession> wss = sessionsByPlayer.values();
				
				//se envia a todas las sesiones
				for(WebSocketSession ws : wss) {
					ws.sendMessage(msgchat);
				}
				
			}catch(JSONException e) {
				e.printStackTrace();
			}
			
		//si se hace un movimiento en el PPT
		}else if(jso.get("TYPE").equals("MOVIMIENTO")) {
			Player player = (Player) session.getAttributes().get("player");
			JSONArray coordinates = jso.getJSONArray("coordinate");
			Match match = Manager.get().move(player, coordinates);
			send(match.getPlayers(), match);
		
		//si se cambia una celda en el sudoku
		}else if(jso.get("TYPE").equals("SUDOKU")) {
			Player player = (Player) session.getAttributes().get("player");
			JSONArray celda = jso.getJSONArray("coordinate");
			//int valor = jso.getInt("value");
			jso.put("player", jso.get("player"));	
			Match match = Manager.get().move(player, celda);
			sendMoveSudoku(player, celda, match);		
		}	
	}
	

	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message){
		Player player = (Player) session.getAttributes().get("player");
		byte[] bytes=message.getPayload().array();
		try {
			MongoBroker.get().insertBinary("Fotos", player.getUserName(), bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * Metodo que envia la foto
	 * @param session: sesion que solicita la foto
	 * @param foto: recurso solicitado en bytes
	 * @throws JSONException
	 * @throws IOException
	 */
	private void sendBinary(WebSocketSession session, byte[] foto) throws JSONException, IOException {
		//enviar la foto
		String imagen = Base64.encode(foto);
		JSONObject jso = new JSONObject();
		try {
			jso.put("TYPE", "FOTO");
			jso.put("foto", imagen);
			WebSocketMessage<?> message=new TextMessage(jso.toString());
			session.sendMessage(message);
		}catch(JSONException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Metodo que envia el MOVIMIENTO de una partida PPT a todos los players
	 * @param players: jugadores que estan jugando al PPT
	 * @param match: partida PPT donde estan los players
	 */
	public static void send(Vector<Player> players, Match match) {
		//enviar mensaje a los players de ese match
		ObjectMapper mapper=new ObjectMapper();
		JSONObject jso;
		try {
			jso = new JSONObject(mapper.writeValueAsString(match));
			jso.put("TYPE", "MATCH");
			for(Player player : players) {
				Thread.sleep(1000);
				WebSocketSession session=sessionsByPlayer.get(player.getUserName());
				WebSocketMessage<?> message=new TextMessage(jso.toString());
				session.sendMessage(message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Metodo que envia el MOVIMIENTO en una partida de SUDOKU
	 * @param player: jugador que cambia una celda del sudoku
	 * @param celda: casilla del sudoku que cambia
	 * @param valor: valor nuevo en la casilla
	 * @param match: partida donde esta el player
	 */
	private void sendMoveSudoku(Player player, JSONArray celda, Match match) {
		//enviar movimiento de ese jugador en su partida
		ObjectMapper mapper=new ObjectMapper();
		JSONObject jso;
		try {
			jso = new JSONObject(mapper.writeValueAsString(match));
			jso.put("TYPE", "MOVIMIENTOSUDOKU");
			jso.put("match", match);	
			int[] iC=new int[celda.length()];
			iC[0]=celda.getInt(0);
			iC[1]=celda.getInt(1);
			jso.put("celda", iC[0]);
			jso.put("valor", iC[1]);
			
			//si hay ganador le mandamos un mensaje a los 2 players
			if(match.getWinner()!=null) {
				Vector<Player> players = match.getPlayers();
				for(Player p : players) {
					WebSocketSession session=sessionsByPlayer.get(p.getUserName());
					WebSocketMessage<?> message=new TextMessage(jso.toString());
					session.sendMessage(message);
				}
			//si no hay ganador se envia el movimiento de ese player
			}else {	
				WebSocketSession session=sessionsByPlayer.get(player.getUserName());
				WebSocketMessage<?> message=new TextMessage(jso.toString());
				session.sendMessage(message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
