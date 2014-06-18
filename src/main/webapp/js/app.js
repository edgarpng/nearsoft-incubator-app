(function(){

    'use strict';

    //Ember Application
    window.App = Ember.Application.create({LOG_TRANSITIONS: true});
    //Ember Initializers for plugins
    Ember.Application.initializer({
      name: 'airportsInitializer',
      initialize: function(container, application){
        Ember.$(function(){
          Ember.$.get('/airports', function(airports){
            function format(airport){
              return airport.name + " (" + airport.city + ")";
            }

            function getId(airport){
              return airport.iata;
            }

            //'text' is the searchable attribute of the item
            var selectData = {text: 'city', results: airports};

            Ember.$('.airport-picker').select2({
              minimumInputLength: 3,
              data: selectData,
              formatSelection: format,
              formatResult: format,
              id: getId,
              width: '300px'
            });
          });
        });
      }
    });
    Ember.Application.initializer({
      name: 'datepickerInitializer',
      initialize: function(){
        Ember.$(function(){
          Ember.$('.date-picker').datepicker({
            dateFormat: 'yy-mm-dd'
          });
        })
      }
    })

    //Ember Routes
    App.Router.map(function(){
      this.resource('search', {path: '/search/:fromAirport/:toAirport/:departureDate/:returnDate'});
    });

    //Ember Controllers
    App.ApplicationController = Ember.Controller.extend({});
    App.SearchController = Ember.ObjectController.extend({
      results: null,
      actions: {
        search: function(){
          var search = this.get('model');
          this.updateResults(search);
          this.transitionToRoute('search', search);
        }
      },
      updateResults: function(search){
        var results = this.store.find('flight', search);
        this.set('results', results);
      }
    });
    App.SearchRoute = Ember.Route.extend({
      setupController: function(controller, search) {
        controller.set('model', search);
        controller.updateResults(search);
      }
    });

    //Ember Models
    App.Flight = DS.Model.extend({
      departureTime: DS.attr('date'),
      arrivalTime: DS.attr('date'),
      departureAirportFsCode: DS.attr('string'),
      arrivalAirportFsCode: DS.attr('string'),
      carrierFsCode: DS.attr('string'),
      arrivalTerminal: DS.attr('string'),
      flightNumber: DS.attr('string'),
      stops: DS.attr('number')
    });
    App.Search = DS.Model.extend({
      fromAirport: DS.attr('string'),
      toAirport:  DS.attr('string'),
      departureDate:  DS.attr('string'),
      returnDate:  DS.attr('string')
    });
}());