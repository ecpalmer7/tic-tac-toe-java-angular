
angular
	.module('gameControllers')
	.controller("HistoryController", HistoryController);
		
HistoryController.$inject = ['$scope','DataFactory'];
		
function HistoryController($scope, DataFactory) {
	var vm = this;
	vm.getGames = getGames;
	vm.deleteGame = deleteGame;
	
	function getGames() {
		DataFactory.getGames().
	        success(function(data) {
	            vm.games = data;
	        });
	}	
	
	function deleteGame(id) {
		DataFactory.deleteGame(id).
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