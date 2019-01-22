package edu.uclm.esi.games.tictactoe;

import java.util.Random;

import edu.uclm.esi.games.Match;

public class TictactoeMatch extends Match {
	public TictactoeMatch() {
		super();
		this.board=new TictactoeBoard(this);
	}

	@Override
	public void calculateFirstPlayer() {
		boolean dado=new Random().nextBoolean();
		this.currentPlayer=dado ? 0 : 1;
		this.currentPlayer=0;   // puesto a propósito con fines de desarrollo y test para que empiece Pepe
	}
}
