package edu.uclm.esi.games;

import java.util.UUID;
import java.util.Vector;

public abstract class Match {
	protected UUID id;
	protected Vector<Player> players;
	protected int currentPlayer;
	protected Player winner;
	protected Board board;
	
	public Match() {
		this.id=UUID.randomUUID();
		this.players=new Vector<>();
		this.currentPlayer=-1;
	}
	
	public UUID getId() {
		return id;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
		player.setCurrentMatch(this);
	}
	
	public Vector<Player> getPlayers() {
		return players;
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public Match move(Player player, int[] coordinates) throws Exception {
		//comprobar si eres el current player
		if (!tieneElTurno(player))
			throw new Exception("You are not the current player");
		
		//comprobar si la partida ha terminado
		if(this.board.end())
			throw new Exception("The match is finished");
		
		//si antes no hay fallos hacemos el movimiento
		this.board.move(player, coordinates);
		//y cambiamos el current player
		this.currentPlayer=(this.currentPlayer+1)%this.players.size();
		
		//vemos quien ha ganado
		this.winner=this.board.getWinner();
		
		//si la partida ha terminado guardamos el resultado en la tabla RESULT de la base de datos
		if(this.board.end())
			save();
		
		return this;
	}

	protected abstract void save() throws Exception;

	protected abstract boolean tieneElTurno(Player player) throws Exception;
	
	public abstract void calculateFirstPlayer();
		
}
