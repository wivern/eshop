var app = angular.module("eshop", []);

app.controller("CartController", function($scope, $http){
    $http.get("/api/cart").success(function(data){
        $scope.cart = data;
    });

    $scope.addToCart = function(){
        $http.post("/api/cart/add").success(function(data){
            $scope.cart = data;
        });
    }
});