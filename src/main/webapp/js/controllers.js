'use strict';

angular.module('myApp.controllers', []).

  controller('HomeController', ['$scope', '$rootScope', '$upload', 'Restangular', function($scope, $rootScope, $upload, Restangular) {
	  
	  $scope.positions = [{lat:12.9606139,lng:77.5945543}];
//	  Restangular.all("key/maps").getList()
//	  .then(function(apiKey) {
//		  $scope.apiKey = apiKey[0].name;
//		});
	  $scope.$on('mapInitialized', function(event, map) {
	     
		  
	    });
	  

//		    var polys = [];
//		    $scope.infoWindow;
//		    Restangular.all("polygon").getList()
//			  .then(function(polygons) {
//				  for(var i=0; i < polygons.length; i++) {
//					  var name = polygons[i].name;
//					  var coords = polygons[i].coords;
//					  polys.push(new google.maps.Polygon({
//	                        paths: coords,
//	                        strokeColor: '#FF0000',
//	                        strokeOpacity: 0.3,
//	                        strokeWeight: 0.8,
//	                        fillColor: '#FF0000',
//	                        fillOpacity: 0.25
//	                      }));
//					  polys[polys.length-1].setMap(map);
//					  google.maps.event.addListener(polys[i], 'click', function(i, name) {
//					      return function(event) {
//					    	  if ($scope.infoWindow) {
//					    		  $scope.infoWindow.close();
//					    	    }
//					    	  Restangular.one("households/latlong","").get({lat: event.latLng.H, long: event.latLng.L})
//					    	  .then(function(pol) {
//					    		  var household = "<b>Constituency Name : " + name + "</b><br/>" +
//					    		  	"Name :" + pol.name + "<br/>Party :" + pol.partyName + "<br/>Contact No. : " + pol.contact +
//					    		  	"<br/>Address :" + pol.address + "<br/>Email :" + pol.email + "<br/>Education :" +pol.education + "<br/>Total Assets :" +
//					    		  	pol.liabilities + "<br/>Liabilities :" + pol.totalAssets + "";
//					    		  $scope.infoWindow = new google.maps.InfoWindow({
//						              content: household
//						          });
//						    	  $scope.infoWindow.setPosition(event.latLng);
//						    	  $scope.infoWindow.open(map, polys[i]);
//					    	  });
//					    	  
//					      }
//					    }(i, name));
//				  }
//				});
//		    
//		}
//	  
//	  
//		google.maps.event.addDomListener(window, 'load', initialize);
		
		$rootScope.$on('WorkplaceSet', function(event) {
			$scope.position = [];
			$scope.position.push({lat:parseFloat($rootScope.worklat), lng: parseFloat($rootScope.worklng)});
		});
	   
  }]).
  
   controller('MainController', ['$scope', '$rootScope', 'Restangular', function($scope, $rootScope, Restangular) {
	   $scope.changeLatLng = function() {
		   $scope.arr = $scope.latlng.split(',');
		   $rootScope.worklat = $scope.arr[0];
		   $rootScope.worklng = $scope.arr[1];
		   $rootScope.$broadcast('WorkplaceSet');
//		   var marker = new google.maps.Marker({
//			    position: myLatLng,
//			    map: map,
//			    title: 'Hello World!'
//		   });
	   }
  }]).
  
   controller('FilterController', ['$scope', 'Restangular', function($scope, Restangular) {
	   $scope.attributeTypes = Restangular.all("filter").getList().$object;
	   $scope.filterObjects = [];
	   $scope.filters = [];
	   $scope.filterObject = {};
	   $scope.status;
	   $scope.message;
	   $scope.sendReq = function() {
		   $scope.filterObjects.length = 0;
		   for(var i = 0; i < $scope.filters.length; i++) {
			   if($scope.filters[i] != null) {
				   $scope.filterObject = new filter($scope.attributeTypes[i].attributeName,$scope.filters[i]);
				   $scope.filterObjects.push($scope.filterObject);
			   }
		   }
		   Restangular.all('filter').post($scope.filterObjects).then(function() {
			   $scope.status = true;
			   $scope.message = "Successfully filtered.";
			   
			}, function(response){
				   $scope.status = false;
				   $scope.message = response;
			});
	   };
	   
	   function filter(name, category)
	   {
		   this.name = name;
		   this.category = category;
	   }
	   
  }]).
  
  controller('FakeController', ['$scope', 'Restangular', function($scope, Restangular) {
	$scope.projects = Restangular.one("import").get();
 }]);