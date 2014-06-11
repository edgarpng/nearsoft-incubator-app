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
      this.resource('search', {path: '/search/:fromAirport/:toAirport/:departureTime/:returnTime'});
    });

    //Ember Controllers
    App.ApplicationController = Ember.Controller.extend({});
    App.SearchController = Ember.ObjectController.extend({
      actions: {
        search: function(){
          var searchQuery = this.get('model');
          this.transitionToRoute('search', searchQuery);
        }
      },
      flights: function(){
        var searchQuery = this.get('model');
        return this.store.find('flight', searchQuery);
      }.property()
    });

    //Ember Models
    App.Flight = DS.Model.extend({
      departureTime: DS.attr('date'),
      arrivalTime: DS.attr('date'),
      carrierFsCode: DS.attr('string'),
      arrivalTerminal: DS.attr('string'),
      flightNumber: DS.attr('string'),
      stops: DS.attr('number')
    });
    App.Search = Ember.Object.extend({
      fromAirport: '',
      toAirport: '',
      departureTime: '',
      returnTIme: ''
    });
}());