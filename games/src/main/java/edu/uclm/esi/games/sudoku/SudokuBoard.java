package edu.uclm.esi.games.sudoku;

import java.util.ArrayList;
import java.util.Random;

import edu.uclm.esi.games.Board;
import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;

public class SudokuBoard extends Board {
	//private static int[][] tablero;
	//private static int[][] solucion;
	private int[] tableroinicial;
	private int[] tablerofinal;

	public SudokuBoard(SudokuMatch sudokumatch) {
		super(sudokumatch);
		this.tableroinicial = generar_Inicial();
		this.tablerofinal = resolver();
		
		
		/*
		boolean generado = false;
		int[][] sudoku;
		do {
			sudoku = generarSudoku();
			generado = resolver(sudoku);
		} while (!generado);
		solucion = sudoku;
		tablero = generarInicial();
		*/
		//this.tablerofinal = solucion;
		//this.tableroinicial = tablero;
	}
	
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
	
	public int[] gettableroinicial(){
		return tableroinicial;
	}
	
	public int[] gettablerofinal(){
		return tablerofinal;
	}
	
	@Override
	public void move(Player player, int[] coordinates) throws Exception {
		//int row = coordinates[0];
		//int col = coordinates[1];
		//tablero[row][col] = coordinates[3];
		tableroinicial[coordinates[0]] = coordinates[1];
	}
/*
	@Override
	public boolean win(Player player) {
		return ((SudokuBoard) player.getCurrentMatch().getBoard()).esCorrecto();
	}

	@Override
	public Player getWinner() {
		for (Player player : this.match.getPlayers()) {
			if (player.getCurrentMatch().getBoard().end()
					&& ((SudokuBoard) player.getCurrentMatch().getBoard()).esCorrecto())
				return player;
		}
		return null;
	}

	@Override
	public boolean end() {
		if (this.getWinner() != null)
			return true;
		return sudokuEnd();
	}

	public boolean esCorrecto() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				//if (tablero[i][j] != solucion[i][j])
				if (tableroinicial[i][j] != tablerofinal[i][j])
					return false;
		return true;
	}

	private boolean sudokuEnd() {
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				//if (tablero[i][j] == 0)
				if (tableroinicial[i][j] == 0)
					return false;
		return true;
	}
*/
	public boolean esPosibleInsertar(int[][] tablero, int i, int j, int valor) {
		// Comprueba columna
		for (int a = 0; a < 9; a++) {
			if (a != i && tablero[a][j] == valor) {
				return false;
			}
		}
		// Comprueba fila
		for (int a = 0; a < 9; a++) {
			if (a != j && tablero[i][a] == valor) {
				return false;
			}
		}
		// Comprueba cuadardo
		int y = (i / 3) * 3;
		int x = (j / 3) * 3;
		for (int a = 0; a < 9 / 3; a++) {
			for (int b = 0; b < 9 / 3; b++) {
				if (a != i && b != j && tablero[y + a][x + b] == valor) {
					return false;
				}
			}
		}
		return true;
	}

	public int[][] generarSudoku() {

		int[][] sudok = new int[9][9];
		for (int i = 0; i < sudok.length; i++) {
			for (int j = 0; j < sudok[1].length; j++) {
				int ale = (int) (Math.random() * 10);
				if (ale == 5) {
					sudok[i][j] = (int) (Math.random() * 9) + 1;
				} else {
					sudok[i][j] = 0;
				}
			}

		}
		return sudok;
	}

	public boolean resolver(int[][] tablero) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (tablero[i][j] != 0) {
					continue;
				}
				for (int k = 1; k <= 9; k++) {
					if (esPosibleInsertar(tablero, i, j, k)) {
						tablero[i][j] = k;
						boolean b = resolver(tablero);
						if (b) {
							return true;
						}
						tablero[i][j] = 0;
					}
				}
				return false;
			}
		}
		return true;
	}

	public static int[][] generarInicial() {
		int[][] inicial = new int[9][9];
		Random r = new Random();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (r.nextInt(10) <= 3) {
					//inicial[i][j] = solucion[i][j];
				}
			}
		}
		return inicial;

	}

	@Override
	public boolean win(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Player getWinner() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean end() {
		// TODO Auto-generated method stub
		return false;
	}

}
