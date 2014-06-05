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
  </head>
  <body>
    <div id="header">
      <h1 class="logo">FlightFinder</h1>
    </div>
    <div id="content">
      <div id="sidebar">
        <form action="search">
          <input type="text" id="from-airport" name="fromAirport" placeholder="From airport">
          <input type="text" id="to-airport" name="toAirport" placeholder="To airport">
          <input type="text" id="leaving-date" name="leavingDate" placeholder="Leaving on">
          <input type="text" id="return-date" name="returnDate" placeholder="Returning on">
          <input type="submit" value="Find!">
        </form>
      </div>
      <div id="results">
        <span class="empty-results">No flights found. Try a different set of dates.</span>
        <!-- TEMPLATE WITH RESULTS-->
      </div>
    </div>
  </body>
</html>