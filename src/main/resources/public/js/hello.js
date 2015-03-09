
var app = angular.module("game", []);


app.controller("HistoryController", function History($scope, $http) {
    $http.get('http://localhost:9080/games').
        success(function(data) {
            $scope.games = data;
        });
});
