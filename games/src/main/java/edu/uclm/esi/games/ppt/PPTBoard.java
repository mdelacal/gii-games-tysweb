package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.Board;
import edu.uclm.esi.games.Player;

public class PPTBoard extends Board {

	private final static int PIEDRA = 0;
	private final static int PAPEL = 1;
	private final static int TIJERA = 2;
	private int[] tiradas0, tiradas1;
	
	
	public PPTBoard(PPTMatch pptMatch) {
		super(pptMatch);
		this.tiradas0 = new int[] { -1, -1, -1}; //los jugadores todavía no han tirado
		this.tiradas1 = new int[] { -1, -1, -1};
	}
	
	@Override
	public void move(Player player, int[] coordinates) throws Exception {
		int pos;
		if(this.match.getPlayers().get(0)==player) {
			pos=rellenar(tiradas0, coordinates[0]);
		}else {
			pos=rellenar(tiradas1, coordinates[1]);
		}
		if(tiradas0[pos]!=-1 && tiradas1[pos]!=-1) { //la ronda ya ha terminado
			System.out.println(tiradas0 + "\n" + tiradas1);
		}
	}

	private int rellenar(int[] tiradas, int valor) {
		for(int i=0; i<tiradas.length; i++) {
			if(tiradas[i]==-1) {
				tiradas[i]=valor;
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean win(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int[] getTiradas0(){
		return tiradas0;
	}
	
	public int[] getTiradas1(){
		return tiradas1;
	}

}
