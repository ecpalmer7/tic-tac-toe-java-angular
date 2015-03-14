package org.tcd.game.tictactoe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Status;
import org.tcd.game.tictactoe.store.GameStore;

@Service
public class GameService  {

	@Autowired
	GameStore store;
	
	@Autowired
	GameFactory factory;
	
	public GameService() {}
	
	public Game addMove(Game state, Move move) {
		Game game = gameLogic(state).addMove(move);
		
		// check for draw/win
		if (gameLogic(game).isDraw()) {
			game.setStatus(Status.DRAW);
		} else if (gameLogic(game).isWinner(Player.X)) {
			game.setStatus(Status.WIN);
			game.setWinner(Player.X);
		} else if (gameLogic(game).isWinner(Player.O)) {
			game.setStatus(Status.WIN);
			game.setWinner(Player.O);
		}
		store.save(game);
		return game;
	}

	public Move nextMove(Game game) {
		return gameLogic(game).nextMove();		
	}

	public Game newGame(Level level, Player computerPlaysAs) {
		Game game = factory.createGame(level, computerPlaysAs);
		store.save(game);
		return game;
	}
	
	public List<Game> getGames() {
		return store.getGames();
	}

	public Optional<Game> find(Long id) {
		return store.get(id);
	}
	
	public void delete(Long id) {
		store.delete(id);
	}

	protected GameLogic gameLogic (Game game) {
		return new GameLogic(game);
	}

}
