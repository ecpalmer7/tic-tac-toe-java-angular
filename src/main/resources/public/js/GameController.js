
angular
	.module('controllers')
	.controller("GameController", GameController);
			
GameController.$inject = ['DataFactory', '$rootScope'];
	
function GameController(DataFactory, $rootScope) {

	var vm = this;
	
	vm.gameId = null;
	vm.game = null;
	vm.levelOptions = [{'value' : 'HARD', 'text' : 'Hard'}, 
	                   {'value' : 'MEDIUM', 'text' : 'Medium'}, 
	                   {'value' : 'EASY', 'text' : 'Easy'}];
	vm.level = 'HARD';
	
	vm.userLetter = 'O';
	vm.systemLetter = 'X';

	vm.turn = 0;

	vm.gameover = false;
	vm.winmessage = '';

	vm.startOptions = [{'value' : 'system', 'text' : 'AI'}, 
                       {'value' : 'user', 'text' : 'Me'}];
	vm.start = 'system';

	vm.isGameStarted = false;
	
	vm.rows = [
	               [
	                   {'id': '11', 'letter': '', 'class': 'box'},
	                   {'id': '12', 'letter': '', 'class': 'box'},
	                   {'id': '13', 'letter': '', 'class': 'box'}
	               ],
	               [
	                   {'id': '21', 'letter': '', 'class': 'box'},
	                   {'id': '22', 'letter': '', 'class': 'box'},
	                   {'id': '23', 'letter': '', 'class': 'box'}
	               ],
	               [
	                   {'id': '31', 'letter': '', 'class': 'box'},
	                   {'id': '32', 'letter': '', 'class': 'box'},
	                   {'id': '33', 'letter': '', 'class': 'box'}
	               ]
	           ];
		
	function convertId(id) {
		// incoming form 11,22,33,etc.
		var row = id.charAt(0);
		var column = id.charAt(1);
		var position = {'row': row, 'column': column};
		return position;
	}
	
	function sendUserMove(move) {
		DataFactory.sendUserMove(vm.gameId, move).success(function(data) {
    		
			vm.game = data;
			
			// returned data is the current game data
			if (data.status == "OPEN") {
				getNextMove();
			} else {
				vm.setUserTurn();
	    		$rootScope.$broadcast("gamesRefresh");
	    	}	          
	    });
	}
	
	vm.markUserClick = function(column) {
		
		if (vm.game) {
			if (vm.game.status |= "OPEN") {
				return;
			}
		}
        if(vm.turn == 1 && column.letter == '') {
            column.letter = vm.userLetter;
            vm.turn = 0;
            
            var position = convertId(column.id);
            var move = {'position': position, 'player': vm.userLetter};
            
            sendUserMove(move);           
        }
        
    };
    
    vm.checkWin = function(letter) {
    	
    	if (vm.game) {
    		if ((vm.game.status == "WIN") && (vm.game.winner == letter)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    vm.checkDraw = function() {
    	
    	if (vm.game) {
    		if (vm.game.status == "DRAW") {
    			return true;
    		}
    	}
    	return false;
    }
   
    vm.setUserTurn = function() {
        if(vm.checkWin(vm.systemLetter)) {
        	vm.winmessage = 'I WIN!';
        	vm.gameover = true;
        } else if (vm.checkDraw()) {
        	vm.winmessage = 'DRAW!';
        	vm.gameover = true;
        } else {
        	vm.turn = 1;
        }
        return true;
    };
    
    function checkGameStatus() {
    	DataFactory.getGame(vm.gameId).success(function(data) {
    		vm.game = data;
    		
    		if (data.status != "OPEN") {
    			$rootScope.$broadcast("gamesRefresh");
    		}
    		vm.setUserTurn();
    	});
    };
    
    function getNextMove() {
    	DataFactory.getNextMove(vm.gameId).success(function(data) {
    		
    		vm.game = data;
    		
 	        var row = data.position.row;
	        var column = data.position.column;
	        var player = data.player;
	           
	        vm.rows[row-1][column-1].letter = player;   
	           
	        checkGameStatus();
	    });
    };
    
    function newGame( ) {
    	DataFactory.createNewGame(vm.level, vm.systemLetter).success(function(data) {
        	vm.gameId = data.id;
	           
	           // if turn is system, then get next move after start
	           if (data.computerPlaysAs == 'X') {
	        	   getNextMove();
	           }
	           
	           $rootScope.$broadcast("gamesRefresh");
	    });
    };
    
    vm.startGame = function() {
    	vm.gameover = false;
    	vm.game = null;
        
        angular.forEach(vm.rows, function(row) {
            row[0].letter = row[1].letter = row[2].letter = '';
            row[0].class = row[1].class = row[2].class = 'box';
        });
        vm.isGameStarted = true;
        if(vm.start == 'system') {
        	vm.turn = 0;
        	vm.userLetter = 'O';
        	vm.systemLetter = 'X';
            
        }
        else {
        	vm.turn = 1;
        	vm.userLetter = 'X';
        	vm.systemLetter = 'O';
        }
        
        newGame();  
    };   
};


