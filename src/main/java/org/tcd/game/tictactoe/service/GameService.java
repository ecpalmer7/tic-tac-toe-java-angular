package org.tcd.game.tictactoe.service;

import java.util.List;
import java.util.Optional;

import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;

public interface GameService {
	Game newGame(Level level, Player computerPlaysAs);	
	Optional<Game> find(Long id);
	Game addMove(Game game, Move move);
	Move nextMove(Game game);
	List<Game> getGames();
	void delete(Long id);
}
