package edu.uclm.esi.web;

import java.util.Enumeration;
import java.util.Hashtable;

import org.json.JSONArray;

import edu.uclm.esi.games.ppt.PPT;
import edu.uclm.esi.games.sudoku.Sudoku;
import edu.uclm.esi.games.Game;
import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.games.tictactoe.TictactoeGame;

public class Manager {
	private Hashtable<String, Game> games;
	
	private Manager() {
		games=new Hashtable<>();
		Game tictactoe=new TictactoeGame();
		games.put(tictactoe.getName(), tictactoe);
		
		Game sudoku=new Sudoku();
		games.put(sudoku.getName(), sudoku);
		
		Game ppt = new PPT();
		games.put(ppt.getName(), ppt);
	}
	
	private static class ManagerHolder {
		static Manager singleton=new Manager();
	}
	
	public static Manager get() {
		return ManagerHolder.singleton;
	}

	public Match joinGame(Player player, String gameName) {
		Game game=this.games.get(gameName);
		return game.getMatch(player);

	}

	public JSONArray getGames() {
		JSONArray jsa=new JSONArray();
		Enumeration<Game> eGames = games.elements();
		while (eGames.hasMoreElements())
			jsa.put(eGames.nextElement().getName());
		return jsa;
	}
	
	//movimiento del player con las coordinates para el PPT
	public Match move(Player player, JSONArray coordinates) throws Exception {
		int[] iC=new int[coordinates.length()];
		for(int i=0;i<iC.length;i++) 
			iC[i]=coordinates.getInt(i);
		return player.move(iC);
	}
	
	//movimiento del player con la celda y el valor para el Sudoku
	public Match moveSudoku(Player player, JSONArray celda, int valor) throws Exception {
		int[] iC=new int[celda.length()];
		iC[0]=celda.getInt(0);
		return player.moveSudoku(iC, valor);
	}

}
