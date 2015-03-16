package org.tcd.game.tictactoe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.GameRepository;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Player;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	@Autowired
	GameRepository repository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Override
	public void run(String... args) throws Exception {

		//repository.deleteAll();

		// save a couple of customers
		Game game1 = repository.save(new Game(Level.EASY, Player.X));
		
		System.out.println(game1);
		
		Game game2 = repository.save(new Game(Level.HARD, Player.O));

		System.out.println(game2);
		
		// fetch all games
		System.out.println("Games found with findAll():");
		System.out.println("-------------------------------");
		for (Game game : repository.findAll()) {
			System.out.println(game);
		}
		System.out.println("init done");
    }
}
