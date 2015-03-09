/**
 * Created with JetBrains PhpStorm.
 * User: ksankaran
 * Date: 4/10/13
 * Time: 12:25 PM
 * To change this template use File | Settings | File Templates.
 */
angular.module('tictactoe').controller('GameController', function($scope, $http) {

    $scope.userLetter = 'o';
    $scope.systemLetter = 'x';

    $scope.turn = 0;

    $scope.gameover = false;
    $scope.winmessage = '';

    $scope.startOptions = [{'value' : 'system', 'text' : 'AI'}, {'value' : 'user', 'text' : 'Me'}];
    $scope.start = 'system';

    $scope.isGameStarted = false;

    $scope.rows = [
        [
            {
                'id' : 'A11',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'A12',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'A13',
                'letter': '',
                'class': 'box'
            }
        ],
        [
            {
                'id' : 'B11',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'B12',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'B13',
                'letter': '',
                'class': 'box'
            }
        ],
        [
            {
                'id' : 'C11',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'C12',
                'letter': '',
                'class': 'box'
            },
            {
                'id' : 'C13',
                'letter': '',
                'class': 'box'
            }
        ]
    ];

    $scope.markUserClick = function(column) {
        if($scope.turn == 1 && column.letter == '') {
            column.letter = $scope.userLetter;
            $scope.turn = 0;
            $scope.think();
        }
    };

    // check common win here

    $scope.markTheOne = function(box1, box2, box3, letter) {
        var textToLookFor = letter + letter;
        if(box1.letter + box2.letter + box3.letter == textToLookFor) {
            if(box1.letter == '') {
                box1.letter = $scope.systemLetter;
            }
            else if(box2.letter == '') {
                box2.letter = $scope.systemLetter;
            }
            else {
                box3.letter = $scope.systemLetter;
            }
            return true;
        }
        return false;
    };

    $scope.fillTheEmptySpot = function(letter) {
        for(var count=0; count < $scope.rows.length; count++) {
            if($scope.markTheOne($scope.rows[count][0], $scope.rows[count][1], $scope.rows[count][2], letter) ||
                $scope.markTheOne($scope.rows[0][count], $scope.rows[1][count], $scope.rows[2][count], letter) ) {
                return true;
            }
        }
        if( $scope.markTheOne($scope.rows[0][0], $scope.rows[1][1], $scope.rows[2][2], letter) ||
            $scope.markTheOne($scope.rows[2][0], $scope.rows[1][1], $scope.rows[0][2], letter) ) {
            return true;
        }
        return false;
    };

    $scope.isCornerSpot = function(x, y) {
        return ( (x == 0 && y == 0) || (x == 0 && y == 2) || (x == 2 && y == 0) || (x == 2 && y == 2) );
    };

    $scope.getInFork = function(letter) {
        // brute force to see which cell creates better fork.
        var bestSpot;
        var bestScore = 0;
        for(var rCount=0; rCount < $scope.rows.length; rCount++) {
            var currentRow = $scope.rows[rCount];
            for(var cCount=0; cCount < currentRow.length; cCount++) {
                var currentSpot = currentRow[cCount];
                var currentScore = 0;
                if(currentSpot.letter == '') {
                    var presentRowString = currentRow[0].letter + currentRow[1].letter + currentRow[2].letter;
                    if(presentRowString == letter) {
                        currentScore++;
                    }
                    var presentColumnString = $scope.rows[0][cCount].letter + $scope.rows[1][cCount].letter + $scope.rows[2][cCount].letter;
                    if(presentColumnString == letter) {
                        currentScore++;
                    }
                    if($scope.isCornerSpot(rCount, cCount) && rCount != 1 && cCount != 1) {
                        var presentDiagonalString = currentSpot.letter + $scope.rows[1][1].letter + $scope.rows[((rCount + 2) > 2 ? 0 : 2)][((cCount + 2) > 2 ? 0 : 2)].letter;
                        if(presentDiagonalString == letter) {
                            currentScore++;
                        }
                    }
                    if(rCount == 1 && cCount == 1) {
                        var diagonal1String = $scope.rows[0][0].letter + $scope.rows[1][1].letter + $scope.rows[2][2].letter;
                        var diagonal2String = $scope.rows[0][2].letter + $scope.rows[1][1].letter + $scope.rows[2][0].letter;
                        if(diagonal1String == letter) {
                            currentScore++;
                        }
                        if(diagonal2String == letter) {
                            currentScore++;
                        }
                    }
                    if(currentScore > bestScore) {
                        bestScore = currentScore;
                        bestSpot = currentSpot;
                    }
                }
            }
        }
        if(bestScore > 1) {
            bestSpot.letter = $scope.systemLetter;
            return true;
        }
        return false;
    };

    $scope.targetCorner = function(oppositeToOpponentFlag) {
        if($scope.rows[0][0].letter + $scope.rows[2][2].letter == $scope.userLetter) {
            ($scope.rows[0][0].letter ==  $scope.userLetter) ? ($scope.rows[2][2].letter = $scope.systemLetter) : ($scope.rows[0][0].letter = $scope.systemLetter);
            return true;
        }
        if($scope.rows[2][0].letter + $scope.rows[0][2].letter == $scope.userLetter) {
            ($scope.rows[2][0].letter ==  $scope.userLetter) ? ($scope.rows[0][2].letter = $scope.systemLetter) : ($scope.rows[2][0].letter = $scope.systemLetter);
            return true;
        }
        if(!oppositeToOpponentFlag) {
            for(var rCount=0; rCount < $scope.rows.length; rCount++) {
                for(var cCount=0; cCount < $scope.rows[rCount].length; cCount++) {
                    if($scope.isCornerSpot(rCount, cCount) && $scope.rows[rCount][cCount].letter == '') {
                        $scope.rows[rCount][cCount].letter = $scope.systemLetter;
                        return true;
                    }
                }
            }
        }
        return false;
    };

    $scope.playAtEmptySpot = function() {
        for(var rCount=0; rCount < $scope.rows.length; rCount++) {
            for(var cCount=0; cCount < $scope.rows[rCount].length; cCount++) {
                if($scope.rows[rCount][cCount].letter == '') {
                    $scope.rows[rCount][cCount].letter = $scope.systemLetter;
                    return;
                }
            }
        }
    };

    $scope.checkWin = function(letter) {
        var textToLookFor = letter + letter + letter;
        for(var count=0; count < $scope.rows.length; count++) {
            if( $scope.rows[count][0].letter + $scope.rows[count][1].letter + $scope.rows[count][2].letter == textToLookFor ) {
                $scope.rows[count][0].class = $scope.rows[count][1].class = $scope.rows[count][2].class = 'winbox';
                return true;
            }
            if( $scope.rows[0][count].letter + $scope.rows[1][count].letter + $scope.rows[2][count].letter == textToLookFor ) {
                $scope.rows[0][count].class = $scope.rows[1][count].class = $scope.rows[2][count].class = 'winbox';
                return true;
            }
        }
        if( $scope.rows[0][0].letter + $scope.rows[1][1].letter + $scope.rows[2][2].letter == textToLookFor ) {
            $scope.rows[0][0].class = $scope.rows[1][1].class = $scope.rows[2][2].class = 'winbox';
            return true;
        }
        if( $scope.rows[2][0].letter + $scope.rows[1][1].letter + $scope.rows[0][2].letter == textToLookFor ) {
            $scope.rows[2][0].class = $scope.rows[1][1].class = $scope.rows[0][2].class = 'winbox';
            return true;
        }
        return false;
    };

    $scope.setUserTurn = function() {
        if($scope.checkWin($scope.systemLetter)) {
            $scope.winmessage = 'I WIN!';
            $scope.gameover = true;
        }
        else {
            $scope.turn = 1;
        }
        return true;
    };

    $scope.think = function() {
        // check for user win
        if($scope.checkWin($scope.userLetter)) {
            $scope.winmessage = 'YOU WIN!';
            $scope.gameover = true;
            return;
        }

        // go for win
        if($scope.fillTheEmptySpot($scope.systemLetter)) {
            return $scope.setUserTurn();
        }

        // block opponent win
        if($scope.fillTheEmptySpot($scope.userLetter)) {
            return $scope.setUserTurn();
        }

        // fork
        if($scope.getInFork($scope.systemLetter)) {
            return $scope.setUserTurn();
        }

        // block opponent fork
        if($scope.getInFork($scope.userLetter)) {
            return $scope.setUserTurn();
        }

        // block center if available
        if($scope.rows[1][1].letter == '') {
            $scope.rows[1][1].letter = $scope.systemLetter;
            return $scope.setUserTurn();
        }

        // opposite corner
        if($scope.targetCorner(true)) {
            return $scope.setUserTurn();
        }
        // play empty corner
        if($scope.targetCorner(false)) {
            return $scope.setUserTurn();
        }

        // play empty spot
        $scope.playAtEmptySpot();

        return $scope.setUserTurn();
    };

    $scope.startGame = function() {
        $scope.gameover = false;
        angular.forEach($scope.rows, function(row) {
            row[0].letter = row[1].letter = row[2].letter = '';
            row[0].class = row[1].class = row[2].class = 'box';
        });
        $scope.isGameStarted = true;
        if($scope.start == 'system') {
            $scope.turn = 0;
            $scope.think();
        }
        else {
            $scope.turn = 1;
        }
    };

});