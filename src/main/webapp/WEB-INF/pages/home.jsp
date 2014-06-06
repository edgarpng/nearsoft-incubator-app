<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Your reliable source for flight information">
    <title>FlightFinder</title>
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:400,800" rel="stylesheet" type="text/css">
    <link href="/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <link href="/css/select2.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <div id="hero">
      <h1 class="logo">FlightFinder</h1>
      <h2>Your reliable source for flight information</h2>
    </div>
    <div>
      <form action="search">
        <input type="hidden" id="from-airport" name="fromAirport" placeholder="From airport">
        <input type="hidden" id="to-airport" name="toAirport" placeholder="To airport">
        <input type="text" id="leaving-date" name="leavingDate" placeholder="Leaving on">
        <input type="text" id="return-date" name="returnDate" placeholder="Returning on">
        <input type="submit" value="Find!">
      </form>
    </div>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
    <script src="/js/select2.min.js"></script>
    <script src="/js/app.js"></script>
  </body>
</html>