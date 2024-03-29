package edu.uclm.esi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.games.Token;
import edu.uclm.esi.mongolabels.dao.MongoBroker;
import edu.uclm.esi.web.ws.WSServer;

@RestController
public class UserControllerPost {
	//new
	@RequestMapping(value="/register", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Player register(String email, String userName, String pwd1, String pwd2) throws Exception {
		if (!pwd1.equals(pwd2))
			throw new Exception("Error: las contraseņas no coinciden");
		Player player=Player.register(email, userName, pwd1);
		
		if(player==null)
			throw new Exception("Error: No se ha podido registrar al usuario");
		return player;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Player loginPost(HttpSession session, String userName, String pwd) throws Exception {
		Player player=Player.identify(userName, pwd);
		session.setAttribute("player", player);
		return player;
	}
	
	//new
	@RequestMapping(value="/registrarOLoguear", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public Player loginConGoogle(HttpSession session, String idGoogle, String nombre, String email) throws Exception {
		Player player = null;
		try {
			player=Player.identifyGoogle(idGoogle, nombre, email);
		}catch (Exception e){
			Player.registerGoogle(idGoogle, nombre, email);
			player=Player.identifyGoogle(idGoogle, nombre, email);
		}
		session.setAttribute("player", player);
		return player;
		
	}
	
	//new cambiar password
	@RequestMapping(value="/cambiarPassword", method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public void changepassword(HttpSession session, String pwd1, String pwd2, String valor) throws Exception {
		//aqui conseguimos el nombre de usuario del token
		BsonDocument criterion = new BsonDocument();
		criterion.append("valor", new BsonString(valor));
		BsonDocument tk = MongoBroker.get().loadOne("Token", criterion);
		String userName = tk.getString("userName").getValue();
		
		//caducidad
		long caducidad = tk.getInt64("caducidad").getValue();
		long tiempoactual = System.currentTimeMillis();
		
		//comprobamos si el token ha caducado
		if(tiempoactual <= caducidad) {
			//modificamos la password
			BsonDocument result = MongoBroker.getPlayer(userName);
			BsonDocument result_new = new BsonDocument();
			
			result_new.put("pwd", new BsonString(pwd1));
			result_new.put("className", new BsonString(result.getString("className").getValue()));
			result_new.put("email", new BsonString(result.getString("email").getValue()));
			result_new.put("userName", new BsonString(result.getString("userName").getValue()));
			try {
				result_new.put("tipo", new BsonString(result.getString("tipo").getValue()));		
			}catch(Exception e) {
				
			}
			try {
				result_new.put("idGoogle", new BsonString(result.getString("idGoogle").getValue()));		
			}catch(Exception e) {
				
			}	
			
			MongoBroker.get().updateBson("Player", result, result_new);	
			
		}else {
			throw new Exception("Error: Token caducado");
		}
				
	}
	
	//new OJO POR GET
	@RequestMapping(value="/solicitarToken", method=RequestMethod.GET)
	public Player solicitarToken(HttpSession session, @RequestParam(value="userName") String userName) throws Exception {
		return Player.solicitarToken(userName);			
	}
	
	@RequestMapping(value= {"/joinGame", "/post/joinGame"}, method=RequestMethod.POST, consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE) 
	public Match joinGamePost(HttpSession session, @RequestBody String gameName) throws Exception {
		Player player=(Player) session.getAttribute("player");
		if (player==null)
			throw new Exception("You need to be logged");
		Match match=Manager.get().joinGame(player, gameName.substring(0, gameName.length()-1));
		WSServer.send(match.getPlayers(), match); //new
		return match;
	}
	
	//new
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		ModelAndView result = new ModelAndView();
		result.setViewName("respuesta");
		result.addObject("exception", ex);
		result.setStatus(HttpStatus.UNAUTHORIZED);
		return result;
	}
}
