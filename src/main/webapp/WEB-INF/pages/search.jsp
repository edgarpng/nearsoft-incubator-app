<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Your reliable source for flight information">
    <title>FlightFinder Search</title>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,800" rel="stylesheet" type="text/css">
    <link href="/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <link href="/css/select2.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <div id="header">
      <h1 class="logo">FlightFinder</h1>
    </div>
    <div id="content">
      <div id="sidebar">
        <form action="search">
          <input type="hidden" id="from-airport" name="fromAirport" placeholder="From city">
          <input type="hidden" id="to-airport" name="toAirport" placeholder="To city">
          <input type="text" id="leaving-date" name="leavingDate" placeholder="Leaving on">
          <input type="text" id="return-date" name="returnDate" placeholder="Returning on">
          <input type="submit" value="Find!">
        </form>
      </div>
      <div id="results">
        <c:choose>
          <c:when test="${not empty flights}">
            <ul>
              <c:forEach var="flight" items="${flights}">
                <li>Flight number ${flight.flightNumber} by ${flight.carrierFsCode}. ${flight.stops} stops. Arriving at terminal ${flight.arrivalTerminal}</li>
              </c:forEach>
            </ul>
          </c:when>
          <c:otherwise>
            <span class="empty-results">No flights found. Try a different set of dates.</span>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
    <script src="/js/select2.min.js"></script>
    <script src="/js/app.js"></script>
  </body>
</html>