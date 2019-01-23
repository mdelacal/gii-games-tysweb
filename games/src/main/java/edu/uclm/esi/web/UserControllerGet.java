package edu.uclm.esi.web;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;

@RestController
public class UserControllerGet {

	//CAMBIO: Devolvemos un JSONObject en vez de un Player
	@RequestMapping("/register")
	public JSONObject register(@RequestParam(value="email") String email, @RequestParam(value="userName") String userName, @RequestParam(value="pwd1") String pwd1, @RequestParam(value="pwd2") String pwd2) throws Exception {
		if (!pwd1.equals(pwd2))
			return new JSONObject().put("mensaje","Error: las contraseñas no coinciden");
		Player player=Player.register(email, userName, pwd1);
		return new JSONObject().put("mensaje", "OK");
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public Player login(HttpSession session, @RequestParam(value="userName") String userName, @RequestParam(value="pwd") String pwd) throws Exception {
		Player player=Player.identify(userName, pwd);
		session.setAttribute("player", player);
		return player;
	}
	
	@RequestMapping(value="/joinGame", method=RequestMethod.GET)
	public Match joinGame(HttpSession session, @RequestParam(value="gameName") String gameName) throws Exception {
		Player player=(Player) session.getAttribute("player");
		if (player==null)
			throw new Exception("You need to be logged");
		Match match=Manager.get().joinGame(player, gameName);
		return match;
	}
		
	@RequestMapping("/games")
	public JSONArray games() throws Exception {
		return Manager.get().getGames();
	}
}
