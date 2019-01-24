package edu.uclm.esi.web.ws;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.json.JSONObject; //new import
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage; //new import
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper; //new import

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.mongolabels.dao.MongoBroker;

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
		sessionsByPlayer.put(userName, session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
	}
	
	//new
	@Override
	protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) {
		Player player = (Player) session.getAttributes().get("player");
		byte[] bytes=message.getPayload().array();
		player.setFoto(bytes);
		BsonDocument criterion=new BsonDocument();
		criterion.append("userName", new BsonString(player.getUserName()));
		MongoBroker.get().delete("Player", criterion);
		
		try {
			MongoBroker.get().insert(player);
		}catch(Exception e) {
			
		}
		
	}
	
	//new
	public static void send(Vector<Player> players, Match match) {
		ObjectMapper mapper=new ObjectMapper();
		//String jso;
		JSONObject jso;
		try {
			//jso=mapper.writeValueAsString(match);
			jso = new JSONObject(mapper.writeValueAsString(match));
			jso.put("TYPE", "MATCH");
			for(Player player : players) {
				WebSocketSession session=sessionsByPlayer.get(player.getUserName());
				//WebSocketMessage<?> message=new TextMessage(jso);
				WebSocketMessage<?> message=new TextMessage(jso.toString());
				session.sendMessage(message);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
