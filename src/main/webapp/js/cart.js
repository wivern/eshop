var app = angular.module("eshop", []);

app.service("CartService", function($http){
    this.addToCart = function(id){
        return $http.post("/api/v1/cart/"+id);
    };
    this.getCart = function(){
        return $http.get("/api/v1/cart");
    };
    this.removeFromCart = function(id){
        return $http.delete("/api/v1/cart/"+id);
    };
    this.updateCart = function(id, quantity){
        return $http({
            url: "/api/v1/cart/"+id,
            method: "PUT",
            data: {quantity: quantity}
        });
    }
});

app.controller("CartController", function($scope, CartService){
    $scope.cart = [];
    getCart();
    function getCart(){
        CartService.getCart().then(function(response){
            $scope.cart = response.data;
        });
    }
    $scope.addToCart = function(id){
        CartService.addToCart(id).then(function(response){
            $scope.cart = response.data;
        });
    };
    $scope.cartContains = function(id){
        return $scope.cart.items.find(function(i){ return i.product.id == id; });
    };
    $scope.removeFromCart = function(id){
        CartService.removeFromCart(id).then(function(response){
            $scope.cart = response.data || [];
        });
    }
});