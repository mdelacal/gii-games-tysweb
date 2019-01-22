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
		// TODO Auto-generated method stub
		
	}
	
	//new
	@Override
	public boolean tieneElTurno(Player player) throws Exception {
		return true;
	}

	//new
	@Override
	protected void save() throws Exception{
		// TODO Auto-generated method stub
		Result result=new Result(this.getPlayers().get(0).getUserName(), this.getPlayers().get(1).getUserName(),
				this.winner.getUserName());
		MongoBroker.get().insert(result);
	}
		
	/* ALTERNATIVA
	@Override
	protected void save() throws Exception{
		// TODO Auto-generated method stub
		Result result=new Result(this.getPlayers());
		MongoBroker.get().insert(result);
	}*/
}
