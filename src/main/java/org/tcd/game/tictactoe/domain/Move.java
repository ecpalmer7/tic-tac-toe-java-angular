package org.tcd.game.tictactoe.domain;

public class Move {
	private Position position;
	private Player player;
	
	
	/*
	 * @JsonCreator
	 * public Product(@JsonProperty("name") String name) {
	 * .this.name = name;
	 * }
	 */
	
	// for marshalling, unmarshalling
	public Move() {}
	
	public Move(int row, int column, Player player) {
		this(new Position(row, column), player);
	}
	
	public Move(Position position, Player player) {
		super();
		this.position = position;
		this.player = player;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
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
		Move other = (Move) obj;
		if (player != other.player)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Move [position=" + position + ", player=" + player + "]";
	}
	
}
