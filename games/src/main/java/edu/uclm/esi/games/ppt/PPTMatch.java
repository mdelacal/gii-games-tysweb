package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.Match;

public class PPTMatch extends Match {

	public PPTMatch() {
		super();
		this.board=new PPTBoard(this);
	}
	
	@Override
	public void calculateFirstPlayer() {
		// TODO Auto-generated method stub

	}

}
