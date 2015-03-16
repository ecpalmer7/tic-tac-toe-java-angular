package org.tcd.game.tictactoe.service;

import org.springframework.stereotype.Component;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Player;

@Component
public class GameFactory {

	public Game createGame(Level level, Player computerPlaysAs) {
		return new Game( level, computerPlaysAs);
	}
	
}
