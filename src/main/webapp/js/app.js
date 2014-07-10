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
              return airport.name + ' (' + airport.city + ')';
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

    //Set the current route's name as a class attribute to body
    Ember.Route.reopen({
      activate: function() {
        var cssClass = this.toCssClass();
        Ember.$('body').addClass(cssClass);
      },
      deactivate: function() {
        Ember.$('body').removeClass(this.toCssClass());
      },
      toCssClass: function() {
        return this.routeName.replace(/\./g, '-').dasherize() + '-route';
      }
    });

    //Ember Routes
    App.Router.map(function(){
      this.resource('search', {path: '/search/:departureAirport/:arrivalAirport/:departureDate/:arrivalDate'});
    });
    App.ApplicationRoute = Ember.Route.extend({
      setupController: function(controller){
        controller.set('model', App.Search.create());
      }
    });
    App.SearchRoute = Ember.Route.extend({
      setupController: function(controller, search) {
        var applicationController = controller.get('applicationController');
        var searchObject = App.Search.create(search);
        applicationController.set('model', searchObject);
        controller.set('model', searchObject);
        controller.updateSchedule(searchObject);
      }
    });

    //Ember Controllers
    App.ApplicationController = Ember.ObjectController.extend(Ember.Evented, {
      actions: {
        search: function(){
          var search = this.get('model');
          if(search.get('isValid')){
            this.doSearch();
          }
          else{
            this.trigger('searchInvalid');
          }
        }
      },
      doSearch: function(){
        var search = this.get('model');
        this.transitionToRoute('search', search);
      }
    });
    App.SearchController = Ember.ObjectController.extend({
      needs: 'application',
      applicationController: Ember.computed.alias('controllers.application'),
      schedule: null,
      updateSchedule: function(search){
        var controller = this;
        var schedulePromise = DS.PromiseObject.create({
          promise: Ember.$.getJSON('/schedules', search.get('queryString'))
        });
        schedulePromise.then(Ember.$.noop, function(error){
          controller.get('applicationController').trigger('serviceError');
        });
        this.set('schedule', schedulePromise);
      }
    });

    //Ember Views
    App.ApplicationView = Ember.View.extend({
      didInsertElement: function(){
        //Model is invalid the moment after its creation. So we have to hide the alert on page load
        this.hideSearchInvalidAlert();
        this.get('controller').on('searchInvalid', this, this.showSearchInvalidAlert);
        this.get('controller').on('serviceError', this, this.showServiceErrorAlert);
      },
      showSearchInvalidAlert: function(){
        this.$('.alert').show();
      },
      hideSearchInvalidAlert: function(){
        this.$('.alert').hide();
      },
      showServiceErrorAlert: function(){
        alert('Something went awfully wrong with the service. Please try again.');
      }
    });

    //Ember Validation Mixins
    App.SearchValidations = Ember.Mixin.create({
      validations: {
        departureAirport: {
          presence: {message: 'Departure city cannot be blank'}
        },
        arrivalAirport: {
          presence: {message: 'Arrival city cannot be blank'}
        },
        departureDate: {
          presence: {message: 'Departure date cannot be blank'}
        },
        arrivalDate: {
          presence: {message: 'Arrival date cannot be blank'}
        },
        datesAreValid: {
          acceptance: {
            message: 'Arrival date must after or the same as the departure'
          }
        }
      },
      datesAreValid: function(){
        var departure = this.get('departureDate');
        var arrival = this.get('arrivalDate');
        var timeDifference = moment(arrival).diff(moment(departure));
        var datesArePresent = departure !== undefined && arrival !== undefined
          && departure.length > 0 && arrival.length > 0;
        var datesAreValid;
        if(!datesArePresent){
          //Only validate correctness if both dates are present
          return true;
        }
        return timeDifference >= 0;
      }.property('departureDate', 'arrivalDate'),
      errorMessages: function(){
        var errorMessages = [];
        var errors = this.get('errors'), error;
        for(error in errors){
          if(errors.hasOwnProperty(error) && errors[error].length){
            errorMessages = errorMessages.concat(errors[error]);
          }
        }
        return errorMessages;
      }.property('errors.departureDate', 'errors.arrivalDate', 'errors.departureAirport', 'errors.arrivalAirport', 'errors.datesAreValid'),
    });

    //Ember Models
    App.Search = Ember.Object.extend(Ember.Validations.Mixin, App.SearchValidations, {
      queryString: function(){
        return Ember.$.param({
          departureAirport: this.get('departureAirport'),
          arrivalAirport: this.get('arrivalAirport'),
          departureDate: this.get('departureDate'),
          arrivalDate: this.get('arrivalDate')
        });
      }.property('departureDate', 'arrivalDate', 'departureAirport', 'arrivalAirport')
    });
}());