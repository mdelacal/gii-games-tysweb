package edu.uclm.esi.games.sudoku;

import edu.uclm.esi.games.Match;
import edu.uclm.esi.games.Player;
import edu.uclm.esi.games.Result;
import edu.uclm.esi.mongolabels.dao.MongoBroker;

public class SudokuMatch extends Match {
	
	public SudokuMatch() {
		super();
		this.board=new SudokuBoard(this);
	}
	
	@Override
	protected void save() throws Exception {
		// TODO Auto-generated method stub
		Result result=new Result(this.getPlayers().get(0).getUserName(), this.getPlayers().get(1).getUserName(),
				this.winner.getUserName());
		MongoBroker.get().insert(result);
	}

	@Override
	protected boolean tieneElTurno(Player player) throws Exception {
		return true;
	}

	@Override
	public void calculateFirstPlayer() {
		// TODO Auto-generated method stub

	}

}
