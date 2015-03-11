angular.module('gameApp.controllers')
    .factory('DataFactory', ['$http', function($http) {

    var urlBase = '/api/games';
    var dataFactory = {};

    dataFactory.getGames = function () {
        return $http.get(urlBase);
    };

    dataFactory.getGame = function (id) {
        return $http.get(urlBase + '/' + id);
    };

    dataFactory.deleteGame = function (id) {
        return $http.delete(urlBase + '/' + id);
    };
    
    dataFactory.getNextMove = function (id) {
    	return $http.put(urlBase + '/'+ id +'/autoturn');
    }
    
    dataFactory.sendUserMove = function (id, move) {
    	return $http.put(urlBase + '/'+ id +'/turn', move);
    }
    
    dataFactory.createNewGame = function (level, computerPlaysAs) {
    	var postData = {'level': level, 'computerPlaysAs': computerPlaysAs.toUpperCase()};
    	return $http.post(urlBase, postData, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            params: postData
        });
    }

    return dataFactory;
}]);