package org.tcd.game.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.service.GameService;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	GameService service;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {

    	logger.debug("Deleting existing games");
		service.deleteAll();

		// save a couple of games
		Game game1 = service.newGame(Level.EASY, Player.X);
		service.addMove(game1, new Move(3, 3, Player.X));
		
		Game game2 = service.newGame(Level.HARD, Player.O);
		service.addMove(game2, new Move(1, 3, Player.X));
		service.addMove(game2, new Move(2, 2, Player.O));
		
		logger.debug("init done");
    }
}
