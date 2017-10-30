<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>

<link rel="stylesheet"
	href="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.css">
<script src="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/angular.js/1.4.2/angular.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<head>
<title>Home</title>
</head>
<body ng-app="aplikacja">
	<div ng-controller="kontrolertransakcji">

		<h1>Hello world!</h1>


<button id="btn"> Nacisnij</button>
<table>
 <div id="animal-info"> </div>
</table>


		<form action="query" method="post">
			<input name="value">
			<button>Ok</button>
		</form>


		<button class="btn btn-success">czesc</button>


		<h1>Wynik zapytania SQL:</h1>

		<div class="input-group">

			<span class="input-group-addon">Szukaj</span> <input type="text"
				class="form-control" ng-model="wyszukiwarka">

		</div>
		<div class=table>
			<div class="table-responsive">



				<table ng-table="vm.tableParams" class="table" show-filter="true">
					<tr ng-repeat="user in lista|filter:wyszukiwarka">
						<td title="'Name'" filter="{ name: 'text'}" sortable="'name'">
							{{user.name}}</td>
						<td title="'Age'" filter="{ age: 'number'}" sortable="'id'">
							{{user.id}}</td>
						<td> <button class="btn btn-danger">Delete</button></td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</body>

<script>
	var app = angular.module('aplikacja', []);

	app.controller('kontrolertransakcji', [ '$scope', function($scope) {

		$scope.lista = ${userlist};
	} ]);
</script>

<script>
var pageCounter = 1;
var animalContainer = document.getElementById("animal-info");
var btn = document.getElementById("btn");

btn.addEventListener("click", function() {
  var ourRequest = new XMLHttpRequest();
  ourRequest.open('GET', 'https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(523920)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys');
  ourRequest.onload = function() {
  
      var ourData = JSON.parse(ourRequest.responseText);
      renderHTML(ourData);
  
    
  };

 

  ourRequest.send();
 
});

function renderHTML(data){

	
	
   var htmlrender =  ' ' +  data.query.count + ' ' + data.query.created + ' ' + data.query.results.channel.units.distance + '</br>';

	
	 animalContainer.insertAdjacentHTML('beforeend', htmlrender);
}



</script>
</html>
