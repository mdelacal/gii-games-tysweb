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
			pos=rellenar(tiradas1, coordinates[0]);
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
	
	//new
	@Override
	public Player getWinner() {
		//new
		for(int i=0; i<tiradas0.length; i++) {
			if(tiradas0[i]==-1 || tiradas1[i]==-1)
				//return false;
				return null;
		}//fin new
		
		/*if(this.match.getPlayers().get(0)==player)
			return gana(tiradas0, tiradas1);*/
		
		return gana(tiradas1, tiradas0);
	}
	
	//new
	private Player gana(int[] a, int[] b) { //ahora aqui no devuelves boolean sino el player ganador
		int victoriasA=0, victoriasB=0;
		for(int i=0; i<a.length; i++) {
			if(gana(a[i], b[i]))
				victoriasA++;
			else
				victoriasB++;
		}
		return victoriasA>victoriasB ? this.match.getPlayers().get(0) : this.match.getPlayers().get(1);
	}
	
	//new
	private boolean gana(int a, int b) {
		if(a==PIEDRA && b==TIJERA)
			return true;
		if(a==TIJERA && b==PAPEL)
			return true;
		if(a==PAPEL && b==PIEDRA)
			return true;
		return false;
	}

	//ya creo que no lo usamos
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
	
	//new
	@Override
	public boolean end() {
		if(this.getWinner()!=null)
			return true;
		for(int i=0; i<tiradas0.length; i++) {
			if(tiradas0[i]==-1 || tiradas1[i]==-1)
				return false;
		}
		return true;
		
	}

}
