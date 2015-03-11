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

    dataFactory.insertCustomer = function (cust) {
        return $http.post(urlBase, cust);
    };

    dataFactory.updateCustomer = function (cust) {
        return $http.put(urlBase + '/' + cust.ID, cust)
    };

    dataFactory.deleteGame = function (id) {
        return $http.delete(urlBase + '/' + id);
    };

    return dataFactory;
}]);