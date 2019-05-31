package edu.uclm.esi.games.sudoku;

import java.util.ArrayList;
import java.util.Random;

import org.bson.BsonDocument;
import org.bson.BsonString;

import edu.uclm.esi.games.Board;
import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.mongolabels.dao.MongoBroker;

public class SudokuBoard extends Board {
	private int[] tableroinicial0;
	private int[] tableroinicial1;
	private int[] tablerofinal;

	public SudokuBoard(SudokuMatch sudokumatch) {
		super(sudokumatch);
		Random r=new Random();
		int number=r.nextInt(3);
		this.tableroinicial0 = generar_Inicial(number);
		this.tableroinicial1 = generar_Inicial(number);
		this.tablerofinal = resolver(number);
	}
	
	public int[] gettableroinicial0(){
		return tableroinicial0;
	}
	
	public int[] gettableroinicial1(){
		return tableroinicial1;
	}
	
	public int[] gettablerofinal(){
		return tablerofinal;
	}
	
	public String loadSudokuInicial(String number) {
		try {
			BsonDocument criterion = new BsonDocument();
			criterion.append("identificador", new BsonString("3"));
			BsonDocument result = MongoBroker.get().load("Sudokus", criterion);
			return result.getString("sudokuInicial").getValue();
		} catch (Exception e) {
			return null;
		}
	}
	public String loadSudokuFinal(String number) {
		try {
			BsonDocument criterion = new BsonDocument();
			criterion.append("identificador", new BsonString("3"));
			BsonDocument result = MongoBroker.get().load("Sudokus", criterion);
			return result.getString("sudokuFinal").getValue();
		} catch (Exception e) {
			return null;
		}
	}
	
	public int [] generar_Inicial(int number){
	    int [] sudoku = new int[81];
	    String cad=loadSudokuInicial(number+"");
	    System.out.println(cad);
	    for (int i=0;i<cad.length();i++) {
	    	sudoku[i]=cad.charAt(i)-'0';
	    }    
	    return sudoku;
	  }
	
	//SUDOKU SOLUCION
	public int [] resolver(int number){
	    int [] sudoku = new int[81];
	    String cad=loadSudokuFinal(number+"");
	    for (int i=0;i<cad.length();i++) {
	    	sudoku[i]=cad.charAt(i)-'0';
	    }
	    return sudoku;
	  }
	
	
	@Override
	public void move(Player player, int[] coordinates) throws Exception {
		if(this.match.getPlayers().get(0)==player) {
			tableroinicial0[coordinates[0]] = coordinates[1];
		}else if(this.match.getPlayers().get(1)==player){
			tableroinicial1[coordinates[0]] = coordinates[1];
		} 
	}

	@Override
	public Player getWinner() {
		int index = 0;
		for (Player player : this.match.getPlayers()) {
			if (((SudokuBoard) player.getCurrentMatch().getBoard()).esCorrecto(player, index))
				return player;
			index++;
		}
		return null;
	}

	@Override
	public boolean end() {
		if (this.getWinner() != null)
			return true;
		return false;
	}

	/**
	 * Metodo para comprobar si el tablero de un usuario tras un movimiento en el sudoku es correcto
	 * @param player: jugador actual
	 * @param index: indice para comprobar un tablero u otro
	 * @return
	 */
	public boolean esCorrecto(Player player, int index) {
		if(index==0) {
			for (int j = 0; j < 81; j++)
				if (tableroinicial0[j] != tablerofinal[j])
					return false;
		}else if(index==1) {
			for (int j = 0; j < 81; j++)
				if (tableroinicial1[j] != tablerofinal[j])
					return false;
		}
		return true;
	}

	@Override
	public boolean win(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

		
}
