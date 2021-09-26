$(document).on('ready', function() {
    console.log('loading doc');

    $('form').on('submit', function(event) {
        event.preventDefault();
        //send button press
        var form = $(this);
        var promise = $.ajax('localhost:8080/home.html', {
            data: form.serialize(), method: 'POST'
        });
    });
});