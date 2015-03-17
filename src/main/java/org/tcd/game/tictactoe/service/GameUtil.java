package org.tcd.game.tictactoe.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Position;

public class GameUtil {

	private GameUtil() {}
	
	public static List<Position> getAllPositions() {
		List<Position> positions = new ArrayList<Position>();
		for (int row = 1; row <= 3; row++) {
			for (int col = 1; col <= 3; col++) {
				positions.add(new Position(row, col));
			}
		}
		return positions;
	}
	
	public static List<List<Position>> getWinningCombos() {
		List<List<Position>> wins = new ArrayList<List<Position>>();
		
		wins.add(asList(new Position(1,1), new Position(1,2), new Position(1,3)));
		wins.add(asList(new Position(2,1), new Position(2,2), new Position(2,3)));
		wins.add(asList(new Position(3,1), new Position(3,2), new Position(3,3)));
		
		wins.add(asList(new Position(1,1), new Position(2,1), new Position(3,1)));
		wins.add(asList(new Position(1,2), new Position(2,2), new Position(3,2)));
		wins.add(asList(new Position(1,3), new Position(2,3), new Position(3,3)));
		
		wins.add(asList(new Position(1,1), new Position(2,2), new Position(3,3)));
		wins.add(asList(new Position(3,1), new Position(2,2), new Position(1,3)));
		
		return wins;	
	}
		
	public static boolean matches(Map<Position, Player> moves, Player player,  List<Position> winningCombo) {
		return winningCombo.stream()
					.allMatch(position -> moves.get(position) == player);
	}
	
	public static boolean isWinner(Map<Position, Player> moves, Player player) {
		return getWinningCombos().stream()
								.anyMatch(combo -> matches(moves, player, combo));
	}
		
	public static boolean isWinnerWithMove(Map<Position, Player> moves, Player player, Position position) {
		Map<Position, Player> alteredMoves = new HashMap<Position, Player>(moves);
		alteredMoves.put(position, player);
		return getWinningCombos().stream()
								.anyMatch(combo -> matches(alteredMoves, player, combo));
	}
	
	public static boolean isOpen(Map<Position, Player> moves, Position position) {
		return moves.get(position) == null;
	}
	
	public static List<Position> openPositions(Map<Position, Player> moves) {
		return getAllPositions()
				.stream()
				.filter(position -> isOpen(moves, position))
				.collect(toList());
	}
		
	public static List<Position> winningPositions(Player player, Map<Position, Player> moves) {
		return openPositions(moves)
				.stream()
				.filter(position -> isWinnerWithMove(moves, player, position))
				.collect(toList());
	}
	
}
