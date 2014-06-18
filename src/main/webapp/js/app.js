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
      this.resource('search', {path: '/search/:departureAirport/:arrivalAirport/:departureDate/:arrivalDate'});
    });

    //Ember Controllers
    App.ApplicationController = Ember.Controller.extend({});
    App.SearchController = Ember.ObjectController.extend({
      schedule: null,
      actions: {
        search: function(){
          var search = this.get('model');
          this.updateSchedule(search);
          this.transitionToRoute('search', search);
        }
      },
      updateSchedule: function(search){
        var schedule = DS.PromiseObject.create({
          promise: $.getJSON('/schedules', $.param(search))
        });
        this.set('schedule', schedule);
      }
    });
    App.SearchRoute = Ember.Route.extend({
      setupController: function(controller, search) {
        controller.set('model', search);
        controller.updateSchedule(search);
      }
    });
}());