package edu.uclm.esi.web;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;

public class InvalidMatch extends Match{
	private String message;
	public InvalidMatch(String message) {
		this.message=message;
	}

	public String getMessage() {
		return message;
	}

	@Override
	protected void save() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean tieneElTurno(Player player) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void calculateFirstPlayer() {
		// TODO Auto-generated method stub
		
	}
}
