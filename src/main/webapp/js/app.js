(function($){

    'use strict';

    function init(){
        $.get('/airports', function(airports){

            function format(airport){
                return airport.name + " (" + airport.city + ")";
            }

            function getId(airport){
                return airport.iata;
            }

            //'text' is the searchable attribute of the item
            var selectData = {text: 'city', results: airports};

            $('#from-airport, #to-airport').select2({
                data: selectData,
                formatSelection: format,
                formatResult: format,
                id: getId,
                width: '300px'
            });
        });
    }

    $(init);
}(jQuery));