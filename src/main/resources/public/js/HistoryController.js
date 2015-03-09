
angular.module('gameApp.controllers').controller("HistoryController", function History($scope, $http) {
		
	$scope.getGames = function() {
	    $http.get('/api/games').
	        success(function(data) {
	            $scope.games = data;
	        });
		};
	
	$scope.getGames();

	$scope.$on("gamesRefresh",function () {
		$scope.getGames();
	});
});