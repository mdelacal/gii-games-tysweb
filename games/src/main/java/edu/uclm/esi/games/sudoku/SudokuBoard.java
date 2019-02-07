package edu.uclm.esi.games.sudoku;

import java.util.ArrayList;
import java.util.Random;

import edu.uclm.esi.games.Board;
import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;

public class SudokuBoard extends Board {
	private int[] tableroinicial0;
	private int[] tableroinicial1;
	private int[] tablerofinal;

	public SudokuBoard(SudokuMatch sudokumatch) {
		super(sudokumatch);
		this.tableroinicial0 = generar_Inicial();
		this.tableroinicial1 = generar_Inicial();
		this.tablerofinal = resolver();
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
	
	/*
	 * SUDOKU DIFICIL
	public int [] generar_Inicial(){
	    int [] sudoku = new int[81];
	    sudoku[0]=1;
	    sudoku[5]=6;
	    sudoku[6]=8;
	    sudoku[12]=5;
	    sudoku[18]=6;
	    sudoku[25]=7;
	    sudoku[28]=9;
	    sudoku[30]=4;
	    sudoku[35]=8;
	    sudoku[40]=2;
	    sudoku[47]=1;
	    sudoku[51]=9;
	    sudoku[56]=6;
	    sudoku[62]=1;
	    sudoku[64]=1;
	    sudoku[67]=9;
	    sudoku[77]=1;
	    sudoku[79]=8;
	    
	    return sudoku;
	  }
	*/
	
	//SUDOKU FACIL PARA PRUEBAS
	public int [] generar_Inicial(){
	    int [] sudoku = new int[81];
	    sudoku[0]=1;
	    sudoku[1]=0;
	    sudoku[2]=2;
	    sudoku[3]=9;
	    sudoku[4]=7;
	    sudoku[5]=6;
	    sudoku[6]=8;
	    sudoku[7]=5;
	    sudoku[8]=3;
	    
	    sudoku[9]=7;
	    sudoku[10]=8;
	    sudoku[11]=3;
	    sudoku[12]=5;
	    sudoku[13]=4;
	    sudoku[14]=2;
	    sudoku[15]=6;
	    sudoku[16]=1;
	    sudoku[17]=9;
	    
	    sudoku[18]=6;
	    sudoku[19]=5;
	    sudoku[20]=9;
	    sudoku[21]=3;
	    sudoku[22]=1;
	    sudoku[23]=8;
	    sudoku[24]=2;
	    sudoku[25]=7;
	    sudoku[26]=4;
	    
	    
	    sudoku[27]=2;
	    sudoku[28]=9;
	    sudoku[29]=7;
	    sudoku[30]=4;
	    sudoku[31]=6;
	    sudoku[32]=5;
	    sudoku[33]=1;
	    sudoku[34]=3;
	    sudoku[35]=8;
	    
	    sudoku[36]=4;
	    sudoku[37]=3;
	    sudoku[38]=8;
	    sudoku[39]=1;
	    sudoku[40]=2;
	    sudoku[41]=9;
	    sudoku[42]=7;
	    sudoku[43]=6;
	    sudoku[44]=5;
	    
	    
	    sudoku[45]=5;
	    sudoku[46]=6;
	    sudoku[47]=1;
	    sudoku[48]=8;
	    sudoku[49]=3;
	    sudoku[50]=7;
	    sudoku[51]=9;
	    sudoku[52]=4;
	    sudoku[53]=2;
	    
	    sudoku[54]=3;
	    sudoku[55]=2;
	    sudoku[56]=6;
	    sudoku[57]=7;
	    sudoku[58]=8;
	    sudoku[59]=4;
	    sudoku[60]=5;
	    sudoku[61]=9;
	    sudoku[62]=1;
	    
	    sudoku[63]=8;
	    sudoku[64]=1;
	    sudoku[65]=5;
	    sudoku[66]=6;
	    sudoku[67]=9;
	    sudoku[68]=3;
	    sudoku[69]=4;
	    sudoku[70]=2;
	    sudoku[71]=7;
	    
	    sudoku[72]=9;
	    sudoku[73]=7;
	    sudoku[74]=4;
	    sudoku[75]=2;
	    sudoku[76]=5;
	    sudoku[77]=1;
	    sudoku[78]=3;
	    sudoku[79]=8;
	    sudoku[80]=6;
	    
	    return sudoku;
	  }
	
	//SUDOKU SOLUCION
	public int [] resolver(){
	    int [] sudoku = new int[81];
	    sudoku[0]=1;
	    sudoku[1]=4;
	    sudoku[2]=2;
	    sudoku[3]=9;
	    sudoku[4]=7;
	    sudoku[5]=6;
	    sudoku[6]=8;
	    sudoku[7]=5;
	    sudoku[8]=3;
	    
	    sudoku[9]=7;
	    sudoku[10]=8;
	    sudoku[11]=3;
	    sudoku[12]=5;
	    sudoku[13]=4;
	    sudoku[14]=2;
	    sudoku[15]=6;
	    sudoku[16]=1;
	    sudoku[17]=9;
	    
	    sudoku[18]=6;
	    sudoku[19]=5;
	    sudoku[20]=9;
	    sudoku[21]=3;
	    sudoku[22]=1;
	    sudoku[23]=8;
	    sudoku[24]=2;
	    sudoku[25]=7;
	    sudoku[26]=4;
	    
	    
	    sudoku[27]=2;
	    sudoku[28]=9;
	    sudoku[29]=7;
	    sudoku[30]=4;
	    sudoku[31]=6;
	    sudoku[32]=5;
	    sudoku[33]=1;
	    sudoku[34]=3;
	    sudoku[35]=8;
	    
	    sudoku[36]=4;
	    sudoku[37]=3;
	    sudoku[38]=8;
	    sudoku[39]=1;
	    sudoku[40]=2;
	    sudoku[41]=9;
	    sudoku[42]=7;
	    sudoku[43]=6;
	    sudoku[44]=5;
	    
	    
	    sudoku[45]=5;
	    sudoku[46]=6;
	    sudoku[47]=1;
	    sudoku[48]=8;
	    sudoku[49]=3;
	    sudoku[50]=7;
	    sudoku[51]=9;
	    sudoku[52]=4;
	    sudoku[53]=2;
	    
	    sudoku[54]=3;
	    sudoku[55]=2;
	    sudoku[56]=6;
	    sudoku[57]=7;
	    sudoku[58]=8;
	    sudoku[59]=4;
	    sudoku[60]=5;
	    sudoku[61]=9;
	    sudoku[62]=1;
	    
	    sudoku[63]=8;
	    sudoku[64]=1;
	    sudoku[65]=5;
	    sudoku[66]=6;
	    sudoku[67]=9;
	    sudoku[68]=3;
	    sudoku[69]=4;
	    sudoku[70]=2;
	    sudoku[71]=7;
	    
	    sudoku[72]=9;
	    sudoku[73]=7;
	    sudoku[74]=4;
	    sudoku[75]=2;
	    sudoku[76]=5;
	    sudoku[77]=1;
	    sudoku[78]=3;
	    sudoku[79]=8;
	    sudoku[80]=6;
	    
	    return sudoku;
	  }
	
	
	@Override
	public void move(Player player, int[] coordinates) throws Exception {

	}
	
	@Override
	public void moveSudoku(Player player, int[] coordinates, int valor) throws Exception {
		//vemos que jugador ha hecho el movimiento y lo actualizamos en su sudoku
		if(this.match.getPlayers().get(0)==player) {
			tableroinicial0[coordinates[0]] = valor;
		}else if(this.match.getPlayers().get(1)==player){
			tableroinicial1[coordinates[0]] = valor;
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
