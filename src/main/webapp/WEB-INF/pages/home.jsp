<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Your reliable source for flight information">
    <title>FlightFinder Search</title>
    <link href="//fonts.googleapis.com/css?family=Open+Sans:400,800" rel="stylesheet" type="text/css">
    <link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/themes/flick/jquery-ui.css" rel="stylesheet"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="/css/select2.css" rel="stylesheet" type="text/css">
    <link href="/css/styles.css" rel="stylesheet" type="text/css">
  </head>
  <body>
    <script type="text/x-handlebars" data-template-name="application">
      <header id="main-header">
        <h1 class="logo">FlightFinder</h1>
        <h3>Your reliable source for flight information.</h3>
      </header>
      <form id="main-form" class="form-inline">
        {{partial "form"}}
      </form>
      <section id="content">
        {{outlet}}
      </section>
    </script>
    <script type="text/x-handlebars" data-template-name="_form">
      {{input type="hidden" class="airport-picker" placeholder="From city" value=departureAirport}}
      {{input type="hidden" class="airport-picker" placeholder="To city" value=arrivalAirport}}
      {{input type="text" class="date-picker form-control" name="departureDate" placeholder="Departure date" value=departureDate}}
      {{input type="text" class="date-picker form-control" name="arrivalDate" placeholder="Arrival date" value=arrivalDate}}
      <input type="submit" value="Find!" class="btn btn-primary" {{action 'search'}}>
    </script>
    <script type="text/x-handlebars" data-template-name="search">
      <section id="results">
        {{#if schedule}}
          <div class="results-header departure-header">
            <h3>Departure</h3>
            <h4>{{departureAirport}} to {{arrivalAirport}}</h4>
          </div>
          <hr>
          <table class="table table-hover">
            {{partial "table-head"}}
            {{#each flight in schedule.departureFlights}}
              {{render "flight" flight}}
            {{else}}
              {{partial "no-results"}}
            {{/each}}
          </table>
          <div class="results-header arrival-header">
            <h3>Return</h3>
            <h4>{{arrivalAirport}} to {{departureAirport}}</h4>
          </div>
          <hr>
          <table class="table table-hover">
            {{partial "table-head"}}
            {{#each flight in schedule.arrivalFlights}}
              {{render "flight" flight}}
            {{else}}
              {{partial "no-results"}}
            {{/each}}
          </table>
        {{else}}
          {{partial "loading"}}
        {{/if}}
      </section>
    </script>
    <script type="text/x-handlebars" data-template-name="flight">
      <tr>
        <td>{{flight.id}}</td>
        <td>{{flight.airline.name}}</td>
        <td>{{formatTime flight.departureTime}}</td>
        <td>
          {{formatTime flight.arrivalTime}}
          {{#if flight.arrivalTerminal}}
             at Terminal {{flight.arrivalTerminal}}
          {{/if}}
        </td>
        <td>
          {{#if flight.stops}}
            {{flight.stops}}
          {{else}}
            Nonstop
          {{/if}}
        </td>
      </tr>
    </script>
    <script type="text/x-handlebars" data-template-name="_table-head">
      <thead>
        <tr>
          <th>Flight #</th>
          <th>Carrier</th>
          <th>Departure</th>
          <th>Arrival</th>
          <th>Stops</th>
        </tr>
      </thead>
    </script>
    <script type="text/x-handlebars" data-template-name="_no-results">
      <tr>
        <td colspan="5">Could not find flights on that date.</td>
      </tr>
    </script>
    <script type="text/x-handlebars" data-template-name="_loading">
      <div class="loading">
        <div>Finding your flights...</div>
        <img src="/images/large-spinner.gif" />
      </div>
    </script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.0.0/handlebars.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/ember.js/1.3.0/ember.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/ember-data.js/1.0.0-beta.7/ember-data.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.6.0/moment.min.js"></script>
    <script src="/js/select2.min.js"></script>
    <script src="/js/app.js"></script>
  </body>
</html>