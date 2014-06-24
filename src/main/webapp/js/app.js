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
    });
    //Datetime pretty-printer helper for Handlebars
    Ember.Handlebars.registerBoundHelper('formatTime', function(date){
      return moment(date).format('MMMM Do, h:mm a');
    });

    //Ember Routes
    App.Router.map(function(){
      this.resource('search', {path: '/search/:departureAirport/:arrivalAirport/:departureDate/:arrivalDate'});
    });
    App.ApplicationRoute = Ember.Route.extend({
      setupController: function(controller){
        controller.set('model', {});
      }
    });
    App.SearchRoute = Ember.Route.extend({
      setupController: function(controller, search) {
        var applicationController = controller.get('applicationController');
        applicationController.set('model', search);
        controller.set('model', search);
        controller.updateSchedule(search);
      }
    });

    //Ember Controllers
    App.ApplicationController = Ember.ObjectController.extend({
      needs: 'search',
      searchController: Ember.computed.alias('controllers.search'),
      actions: {
        search: function(){
          var search = this.get('model');
          var searchController = this.get('searchController');
          this.transitionToRoute('search', search);
          //TODO: This probably should not be here. Find the appropiate way to do it
          $('#main-header').addClass('short');
          searchController.updateSchedule(search);
        }
      }
    });
    App.SearchController = Ember.ObjectController.extend({
      needs: 'application',
      applicationController: Ember.computed.alias('controllers.application'),
      schedule: null,
      updateSchedule: function(search){
        var schedule = DS.PromiseObject.create({
          promise: $.getJSON('/schedules', $.param(search))
        });
        this.set('schedule', schedule);
      }
    });
}());