package edu.uclm.esi.games.ppt;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.games.Result;
import edu.uclm.esi.mongolabels.dao.MongoBroker;

public class PPTMatch extends Match {

	public PPTMatch() {
		super();
		this.board=new PPTBoard(this);
	}
	
	@Override
	public void calculateFirstPlayer() {
		
	}
	
	@Override
	public boolean tieneElTurno(Player player) throws Exception {
		return true;
	}

	
	@Override
	protected void save() throws Exception{
		Result result;
		//guardamos el resultado de la partida en la tabla RESULT de la base de datos 
		//no hay winner
		if(this.winner==null) {
			result=new Result(this.getPlayers().get(0).getUserName(), this.getPlayers().get(1).getUserName(), null);
		}
		//hay winner
		else {
			result = new Result(this.getPlayers().get(0).getUserName(), this.getPlayers().get(1).getUserName(),
				this.winner.getUserName());
		}
			
		MongoBroker.get().insert(result);
	}

}
