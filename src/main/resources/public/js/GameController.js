

angular.module('gameApp.controllers').controller("GameController", function Hello($scope, $http, $rootScope) {
	
	// added
	$scope.gameId = null;
	$scope.game = null;
	$scope.levelOptions = [{'value' : 'HARD', 'text' : 'Hard'}, 
	                       {'value' : 'MEDIUM', 'text' : 'Medium'}, 
	                       {'value' : 'EASY', 'text' : 'Easy'}];
	$scope.level = 'HARD';
	
	// original
	$scope.userLetter = 'o';
    $scope.systemLetter = 'x';

    $scope.turn = 0;

    $scope.gameover = false;
    $scope.winmessage = '';

    $scope.startOptions = [{'value' : 'system', 'text' : 'AI'}, 
                           {'value' : 'user', 'text' : 'Me'}];
    $scope.start = 'system';

    $scope.isGameStarted = false;
	
	$scope.rows = [
	               [
	                   {'id' : '11','letter': '','class': 'box'},
	                   {'id' : '12','letter': '','class': 'box'},
	                   {'id' : '13','letter': '','class': 'box'}
	               ],
	               [
	                   {'id' : '21','letter': '','class': 'box'},
	                   {'id' : '22','letter': '','class': 'box'},
	                   {'id' : '23','letter': '','class': 'box'}
	               ],
	               [
	                   {'id' : '31','letter': '','class': 'box'},
	                   {'id' : '32','letter': '','class': 'box'},
	                   {'id' : '33','letter': '','class': 'box'}
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
		$http.put('/api/games/'+ $scope.gameId +'/turn', move).success(function(data) {
    		
			$scope.game = data;
			
			// returned data is the current game data
			if (data.status == "OPEN") {
				getNextMove();
			} else {
				// TODO - already have status - look at data
				checkGameStatus();
			}
			/*
			 var move = data.moves.pop();
			
	         var row = move.position.row;
	         var column = move.position.column;
	         var player = move.player;
	           
	         $scope.rows[row-1][column-1].letter = player.toLowerCase();   
	           
	         $scope.setUserTurn();
	         */
	          
	    });
	}
	
	$scope.markUserClick = function(column) {
		
		if ($scope.game) {
			if ($scope.game.status |= "OPEN") {
				return;
			}
		}
        if($scope.turn == 1 && column.letter == '') {
            column.letter = $scope.userLetter;
            $scope.turn = 0;
            
            var position = convertId(column.id);
            var move = {'position': position, 'player': $scope.userLetter.toUpperCase()};
            
            sendUserMove(move);           
        }
        
    };
    
    $scope.checkWin = function(letter) {
    	
    	if ($scope.game) {
    		if (($scope.game.status == "WIN") && ($scope.game.winner == letter.toUpperCase())) {
    			return true;
    		}
    	}
    	return false;
    }
    
   $scope.checkDraw = function() {
    	
    	if ($scope.game) {
    		if ($scope.game.status == "DRAW") {
    			return true;
    		}
    	}
    	return false;
    }
   
    $scope.setUserTurn = function() {
        if($scope.checkWin($scope.systemLetter)) {
            $scope.winmessage = 'I WIN!';
            $scope.gameover = true;
        } else if ($scope.checkDraw()) {
            $scope.winmessage = 'DRAW!';
            $scope.gameover = true;
        } else {
            $scope.turn = 1;
        }
        return true;
    };
    
    function checkGameStatus() {
    	$http.get('/api/games/'+ $scope.gameId ).success(function(data) {
    		$scope.game = data;
    		
    		if (data.status != "OPEN") {
    			$rootScope.$broadcast("gamesRefresh");
    		}
    		$scope.setUserTurn();
    	});
    };
    
    function getNextMove() {
    	$http.put('/api/games/'+ $scope.gameId +'/autoturn').success(function(data) {
    		
    		$scope.game = data;
    		
 	        var row = data.position.row;
	        var column = data.position.column;
	        var player = data.player;
	           
	        $scope.rows[row-1][column-1].letter = player.toLowerCase();   
	           
	        checkGameStatus();
	    });
    };
    
    function newGame( ) {
    	var postData = {level: $scope.level, computerPlaysAs: $scope.systemLetter.toUpperCase()};
    	$http.post('/api/games', postData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            params: postData
        }).success(function(data) {
	           $scope.gameId = data.id;
	           
	           // if turn is system, then get next move after start
	           if (data.computerPlaysAs == 'X') {
	        	   getNextMove();
	           }
	           
	           $rootScope.$broadcast("gamesRefresh");
	    });
    };
    
    $scope.startGame = function() {
        $scope.gameover = false;
        $scope.game = null;
        
        angular.forEach($scope.rows, function(row) {
            row[0].letter = row[1].letter = row[2].letter = '';
            row[0].class = row[1].class = row[2].class = 'box';
        });
        $scope.isGameStarted = true;
        if($scope.start == 'system') {
            $scope.turn = 0;
            $scope.userLetter = 'o';
            $scope.systemLetter = 'x';
            
        }
        else {
            $scope.turn = 1;
            $scope.userLetter = 'x';
            $scope.systemLetter = 'o';
        }
        
        // if turn is system, then get next move after start
        newGame();  
    };
    
});


