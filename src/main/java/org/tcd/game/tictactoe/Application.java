package org.tcd.game.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.GameRepository;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	GameRepository repository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {

    	logger.debug("Deleting existing games");
		repository.deleteAll();

		// save a couple of games
		Game game1 = repository.save(new Game(Level.EASY, Player.X));
		game1.addMove(new Move(3, 3, Player.X));
		repository.save(game1);
		
		Game game2 = repository.save(new Game(Level.HARD, Player.O));
		game2.addMove(new Move(1, 3, Player.X));
		game2.addMove(new Move(2, 2, Player.O));
		repository.save(game2);
		
		// fetch all games
		logger.debug("Games found with findAll():");
		logger.debug("-------------------------------");
		for (Game game : repository.findAll()) {
			logger.debug(game.toString());
		}
		logger.debug("init done");
    }
}
