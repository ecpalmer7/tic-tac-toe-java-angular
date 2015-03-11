
angular
	.module('gameApp.controllers')
	.controller("HistoryController", HistoryController);
		
HistoryController.$inject = ['$scope','$http'];
		
function HistoryController($scope,$http) {
	var vm = this;
	vm.getGames = getGames;
	vm.deleteGame = deleteGame;
	
	function getGames() {
		 $http.get('/api/games').
	        success(function(data) {
	            vm.games = data;
	     });
	}	
	
	function deleteGame(id) {
		$http.delete('/api/games/'+ id ).
        success(function(data) {
        	// refresh
            getGames();
        });
	}
	
	vm.getGames();

	$scope.$on("gamesRefresh",function () {
		vm.getGames();
	});
};