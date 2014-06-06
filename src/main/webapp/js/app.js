(function($){
    function init(){
        $('#from-airport, #to-airport').select2({
            allowClear: true,
            data: function(){
                return $.get('/airports', function(airports){
                    return {text: 'name', results: airports};
                });
            }
        });
    }

    $(init);
}(jQuery));