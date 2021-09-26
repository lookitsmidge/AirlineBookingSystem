// this starts when the page loads
$(document).ready(function() {
    // this runs upon submit of the form
    $('form').on('submit', function(event) {
        event.preventDefault();

        //get data from the form
        var form = $(this);
        var username = form.find('input[name=username]');
        var password = form.find('input[name=password]');


        $.ajax({
            type: "POST",
            url: 'http://localhost:8080/',
            data: form.serialize(),
            dataType: 'text',
            encode: false
        }).done( function(data){
            console.log("form sent");
        });
    });
});
