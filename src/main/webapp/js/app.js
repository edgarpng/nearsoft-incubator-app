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
          var searchQuery = this.get('model'),
          self = this;
          this.transitionToRoute('search', searchQuery);
          this.store.find('flight', searchQuery).then(function (data) {
            self.set('results', data);
          }, function (error) {
            console.error(error);
          });
        }
      },
      results: null
    });
    App.SearchRoute = Ember.Route.extend({
      setupController: function(controller, search) {
        controller.set('model', search);
        console.log('SETTING UP THE CONTROLLER');
      }
    });

    //Ember Models
    App.Flight = DS.Model.extend({
      departureTime: DS.attr('number'),
      arrivalTime: DS.attr('number'),
      carrierFsCode: DS.attr('string'),
      arrivalTerminal: DS.attr('string'),
      flightNumber: DS.attr('string'),
      stops: DS.attr('number')
    });
    App.Search = DS.Model.extend({
      fromAirport: DS.attr('string'),
      toAirport:  DS.attr('string'),
      departureTime:  DS.attr('string'),
      returnTIme:  DS.attr('string')
    });
}());