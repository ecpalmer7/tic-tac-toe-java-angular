package org.tcd.game.tictactoe.store;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tcd.game.tictactoe.domain.Game;
import org.tcd.game.tictactoe.domain.Level;
import org.tcd.game.tictactoe.domain.Move;
import org.tcd.game.tictactoe.domain.Player;
import org.tcd.game.tictactoe.domain.Status;
import org.tcd.game.tictactoe.service.GameFactory;

@Repository
public class GameStore {
	
	Map<Long, Game> map = new ConcurrentHashMap<Long, Game>();
	
	@Autowired
	public GameStore(GameFactory factory) {
		
		Game game1 =factory.createGame(Level.HARD, Player.X);
		Game game2 =factory.createGame(Level.EASY, Player.O);
		
		Move move1 = new Move(1,1,Player.X);
		Move move2 = new Move(2,1,Player.O);
		Move move3 = new Move(1,2,Player.X);
		Move move4 = new Move(2,2,Player.O);
		Move move5 = new Move(1,3,Player.X);
		
		game1.addMove(move1);
		game1.addMove(move2);
		game1.addMove(move3);
		game1.addMove(move4);
		game1.addMove(move5);
		
		game1.setStatus(Status.WIN);
		game1.setWinner(Player.X);
		map.put(game1.getId(), game1);
		
		map.put(game2.getId(), game2);
	}
	
	// TODO - use NOSQL or mysql database for persistence
	
	public void save(Game game) {
		map.put(game.getId(), game);
	};
	
	public Optional<Game> get(Long id){
		Game game = map.get(id);
		return Optional.ofNullable(game);
	}
	
	public void delete(Long id) {
		map.remove(id);
	}

	public List<Game> getGames() {
		return new ArrayList<Game>(map.values());
	};
	
	
}
