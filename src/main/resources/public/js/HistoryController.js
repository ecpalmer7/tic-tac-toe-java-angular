
angular
	.module('controllers')
	.controller("HistoryController", HistoryController);
		
HistoryController.$inject = ['$scope','DataFactory', '$rootScope'];
		
function HistoryController($scope, DataFactory, $rootScope) {
	var vm = this;
	vm.getGames = getGames;
	vm.deleteGame = deleteGame;
	vm.showGame = showGame;
	
	function showGame(e, id) {
		e.preventDefault();
		$rootScope.$broadcast('showGame', id);
	}
	
	function getGames() {
		DataFactory.getGames().
	        success(function(data) {
	            vm.games = data;
	        });
	}	
	
	function deleteGame(e, id) {
		e.preventDefault();
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