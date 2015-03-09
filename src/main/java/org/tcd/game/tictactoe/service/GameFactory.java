package org.tcd.game.tictactoe.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Player;

@Component
public class GameFactory {

	private final AtomicLong counter = new AtomicLong();
	
	public Game createGame(Level level, Player computerPlaysAs) {
		Game game = new Game(nextId(), level, computerPlaysAs);
		return game;
	}
	
	// TODO - use a persistent sequence or UUID
	private Long nextId() {
		return counter.incrementAndGet();
	}
}
