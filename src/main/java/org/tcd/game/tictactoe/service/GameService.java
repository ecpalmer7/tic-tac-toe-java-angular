package org.tcd.game.tictactoe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.GameRepository;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Status;

@Service
public class GameService  {

	@Autowired
	GameRepository store;
	
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
		Game game = new Game(level, computerPlaysAs);
		store.save(game);
		return game;
	}
	
	public List<Game> getGames() {
		return store.findAll();
	}

	public Optional<Game> find(String id) {
		return Optional.ofNullable(store.findOne(id));
	}
	
	public void delete(String id) {
		store.delete(id);
	}

	protected GameLogic gameLogic (Game game) {
		return new GameLogic(game);
	}

}
