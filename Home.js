// this file may not be needed anymore as all processing is to happen in login.js ( to be renamed application.js )
$(document).on('ready', function() {
    console.log('loading doc');

	// no longer using forms with home panel - using buttons instead
    $('form').on('submit', function(event) {
        event.preventDefault();
        //send button press
        var form = $(this);
        var promise = $.ajax('localhost:8080/home.html', {
            data: form.serialize(), method: 'POST'
        });
    });
});