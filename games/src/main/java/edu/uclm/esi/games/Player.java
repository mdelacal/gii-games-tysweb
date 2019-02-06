package edu.uclm.esi.games;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

import org.bson.BsonDateTime;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonString;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import edu.uclm.esi.mongolabels.dao.MongoBroker;
import edu.uclm.esi.mongolabels.labels.Bsonable;

public class Player {
	@Bsonable
	private String userName;
	@Bsonable
	private String email;
	@Bsonable
	private String pwd;
	@JsonIgnore
	private Match currentMatch;
	@Bsonable
	private String idGoogle;
	@Bsonable
	private String tipo;
	@Bsonable
	private byte[] foto;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public static Player identify(String userName, String pwd) throws Exception {
		BsonDocument criterion = new BsonDocument();
		criterion.append("userName", new BsonString(userName)).put("pwd", new BsonString(pwd));
		Player player = (Player) MongoBroker.get().loadOne(Player.class, criterion);
		return player;
	}

	// new
	public static Player identifyGoogle(String id, String nombre, String email) throws Exception {
		BsonDocument criterion = new BsonDocument();
		criterion.append("idGoogle", new BsonString(id)).put("userName", new BsonString(nombre));
		criterion.append("email", new BsonString(email));
		criterion.append("tipo", new BsonString("Google"));
		Player player = (Player) MongoBroker.get().loadOne(Player.class, criterion);
		return player;
	}
	
	public static Player register(String email, String userName, String pwd) throws Exception {
		Player player = new Player();
		player.setEmail(email);
		player.setUserName(userName);
		player.setPwd(pwd);
		if (usuarioRegistrado(player) == false) //comprobamos si el usuario esta registrado
			MongoBroker.get().insert(player);
		else
			return null;
		return player;
	}

	// comprobar usuario registrado
	public static boolean usuarioRegistrado(Player player) throws Exception {
		// comprobar username
		BsonDocument criterionUsername = new BsonDocument();
		criterionUsername.append("userName", new BsonString(player.userName));
		boolean existeUsername = MongoBroker.get().comprobarUsuario(Player.class, criterionUsername);

		// comprobar email
		BsonDocument criterionEmail = new BsonDocument();
		criterionEmail.append("email", new BsonString(player.email));
		boolean existeEmail = MongoBroker.get().comprobarUsuario(Player.class, criterionEmail);

		return existeUsername || existeEmail;
	}

	// new
	public static Player registerGoogle(String id, String nombre, String email) throws Exception {
		Player player = new Player();
		player.setEmail(email);
		player.setUserName(nombre);
		player.setIdGoogle(id);
		MongoBroker.get().insert(player);
		return player;
	}

	private void setIdGoogle(String id) {
		this.idGoogle = id;
		this.tipo = "Google";
	}

	public void setCurrentMatch(Match match) {
		this.currentMatch = match;
	}

	public Match getCurrentMatch() {
		return currentMatch;
	}

	public Match move(int[] coordinates) throws Exception {
		return this.currentMatch.move(this, coordinates);
	}
	
	public Match moveSudoku(int[] celda, int valor) throws Exception {
		return this.currentMatch.moveSudoku(this, celda, valor);
	}

	public static Player solicitarToken(String userName) {
		Player player = null;
		try {
			BsonDocument criterion = new BsonDocument();
			criterion.append("userName", new BsonString(userName));
			player = (Player) MongoBroker.get().loadOne(Player.class, criterion);
			player.createToken();
		} catch (Exception e) {

		}
		return player;
	}

	private void createToken() throws Exception {
		Token token = new Token(this.userName);
		MongoBroker.get().insert(token);
		EMailSenderService email = new EMailSenderService();
		email.enviarPorGmail(this.email, token.getValor());
	}

	public void setFoto(byte[] bytes) {
		this.foto = bytes;
	}

	public byte[] loadFoto() {
		try {
			BsonDocument criterion = new BsonDocument();
			criterion.append("userName", new BsonString(this.userName));
			BsonDocument result = MongoBroker.get().loadBinary("Fotos", criterion);
			return result.getBinary("bytes").getData();
		} catch (Exception e) {
			return null;
		}
	}
	
}
