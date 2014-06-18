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
    <link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/flick/jquery-ui.css" rel="stylesheet"/>
    <link href="/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
    <link href="/css/select2.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <script type="text/x-handlebars" data-template-name="application">
      <div id="header">
        <h1 class="logo">FlightFinder</h1>
        <h3>Your reliable source for flight information.</h3>
      </div>
      <div id="content">
        {{outlet}}
      </div>
    </script>
    <script type="text/x-handlebars" data-template-name="search">
      <div id="sidebar">
        {{input type="hidden" class="airport-picker" name="fromAirport" placeholder="From city" value=fromAirport}}
        {{input type="hidden" class="airport-picker" name="toAirport" placeholder="To city" value=toAirport}}
        {{input type="text" class="date-picker" name="departureDate" placeholder="Leaving on" value=departureDate}}
        {{input type="text" class="date-picker" name="returnDate" placeholder="Returning on" value=returnDate}}
        <input type="submit" value="Find!" {{action 'search'}}>
      </div>
      <div id="results">
          {{#each flight in results}}
            <div class="result">
              <table>
                <tr>
                  <td><b>Flight number:</b></td>
                  <td>{{flight.id}}</td>
                </tr>
                <tr>
                  <td><b>Airline:</b></td>
                  <td>{{flight.carrierFsCode}}</td>
                </tr>
                <tr>
                  <td><b>Departure:</b></td>
                  <td>{{flight.departureTime}} from {{flight.departureAirportFsCode}}</td>
                </tr>
                <tr>
                  <td><b>Arrival:</b></td>
                  <td>
                    {{flight.arrivalTime}} to {{flight.arrivalAirportFsCode}}
                    {{#if flight.arrivalTerminal}}
                       at terminal {{flight.arrivalTerminal}}
                    {{/if}}
                  </td>
                </tr>
                <tr>
                  <td><b>Stops:</b></td>
                  <td>
                    {{#if flight.stops}}
                      {{flight.stops}}
                    {{else}}
                      Nonstop.
                    {{/if}}
                  </td>
                </tr>
              </table>
            </div>
          {{else}}
            Sorry, no flights were found. Please try a different set of dates.
          {{/each}}
      </div>
    </script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.0.0/handlebars.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/ember.js/1.3.0/ember.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/ember-data.js/1.0.0-beta.7/ember-data.min.js"></script>
    <script src="/js/select2.min.js"></script>
    <script src="/js/app.js"></script>
  </body>
</html>