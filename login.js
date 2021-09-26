// this starts when the page loads
$(document).ready(function() {
    // this runs upon submit of the form
    $('form').on('submit', function(event) {
        event.preventDefault();

        //get data from the form
        var form = $(this);
        var username = form.find('input[name=username]');
        var password = form.find('input[name=password]');
        var data = form.serialize();

        // this is the ajax post request
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/log-in',
            data: data,
            contentType: "text",
            encode: false,
            success: function(data){
                console.log(data)
                console.log("sent");
            },
            error: function(err, status){
                console.log(err);}
        }).done( function(data){
            console.log("form sent");
        });
    });
});
