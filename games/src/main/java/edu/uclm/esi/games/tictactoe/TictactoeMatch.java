package edu.uclm.esi.games.tictactoe;

import java.util.Random;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;

public class TictactoeMatch extends Match {
	public TictactoeMatch() {
		super();
		this.board=new TictactoeBoard(this);
	}

	@Override
	public void calculateFirstPlayer() {
		boolean dado=new Random().nextBoolean();
		this.currentPlayer=dado ? 0 : 1;
		this.currentPlayer=0;   // puesto a proposito con fines de desarrollo y test para que empiece Pepe
	}
	
	@Override
	protected boolean tieneElTurno(Player player) throws Exception {
		boolean turno=false;
		if(this.players.get(currentPlayer)!=player) {
			throw new Exception("You are not the current player");
		}else {
			turno=true;
		}
		return turno;
	}

	//new
	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}
}
