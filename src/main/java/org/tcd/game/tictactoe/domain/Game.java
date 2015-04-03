package org.tcd.game.tictactoe.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;

public class Game {
	
	@Id
	private String id;

	private List<Move> moves;
	private Level level;
	private Player computerPlaysAs;
	private Status status;
	private Player winner;
	private Long sequence;
	
	public Game() {}
	
	public Game(String id, List<Move> moves, Level level, Player computerPlaysAs, Long sequence) {
		this.moves = moves;
		this.level = level;
		this.computerPlaysAs = computerPlaysAs;
		this.id = id;
		this.setStatus(Status.OPEN);
		this.sequence = sequence;
	}
	
	public Game( Level level, Player computerPlaysAs, Long sequence) {
		this(null, new ArrayList<Move>(), level, computerPlaysAs, sequence);
	}
		
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void addMove(Move move){
		moves.add(move);
	}
	
	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long visibleId) {
		this.sequence = visibleId;
	}

	public List<Move> getMoves() {
		return moves;
	}
	
	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}

	public Player playerAt(int row, int col) {
		return playerAt(new Position(row, col));
	}
	
	public Player playerAt(Position position) {
		return movesAsMap().get(position);
	}
	
	public Map<Position, Player> movesAsMap() {
		
		Map<Position, Player> map = new HashMap<Position, Player>();
		
		for (Move move: moves) {
			map.put(move.getPosition(), move.getPlayer());
		}
		return map;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Player getComputerPlaysAs() {
		return computerPlaysAs;
	}

	public void setComputerPlaysAs(Player computerPlaysAs) {
		this.computerPlaysAs = computerPlaysAs;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", moves=" + moves + ", level=" + level
				+ ", computerPlaysAs=" + computerPlaysAs + ", status=" + status
				+ ", winner=" + winner + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerPlaysAs == null) ? 0 : computerPlaysAs.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((moves == null) ? 0 : moves.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((winner == null) ? 0 : winner.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (computerPlaysAs != other.computerPlaysAs)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (level != other.level)
			return false;
		if (moves == null) {
			if (other.moves != null)
				return false;
		} else if (!moves.equals(other.moves))
			return false;
		if (status != other.status)
			return false;
		if (winner != other.winner)
			return false;
		return true;
	}
	
}