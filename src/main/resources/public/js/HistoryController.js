
angular
	.module('gameApp.controllers')
	.controller("HistoryController", HistoryController);
		
HistoryController.$inject = ['$scope','$http'];
		
function HistoryController($scope,$http) {
	var vm = this;
	vm.getGames = getGames;
	
	function getGames() {
		 $http.get('/api/games').
	        success(function(data) {
	            vm.games = data;
	     });
	}	
	
	vm.getGames();

	$scope.$on("gamesRefresh",function () {
		vm.getGames();
	});
};