package org.tcd.game.tictactoe.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.GameRepository;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;

@Service
public class GameService  {

	private AtomicLong sequence = new AtomicLong(1);
	
	@Autowired
	GameRepository store;
	
	public GameService() {}
	
	public Game addMove(Game game, Move move) {
		Game modifiedGame = gameLogic(game).addMove(move);
		return store.save(modifiedGame);
	}

	public Move nextMove(Game game) {
		return gameLogic(game).nextMove();		
	}

	public Game newGame(Level level, Player computerPlaysAs) {
		return store.save(new Game(sequence.getAndIncrement(), level, computerPlaysAs));
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

	public void deleteAll() {
		store.deleteAll();
	}

}
