/**
 * This function should update the webpage with html data that has been sent via the server
 * @param data
 */
function switch_content(data) {
    $('#main_content').html(data); // this didnt work before as the tag was for an id
    console.log("setting up event listener");
    // need to find a way to distinguish between what is on the html page so this doesnt run twice
    $('#options').on('click', on_options_submit );
    $('#log_in').on('submit', on_login_submit );
    // .append is also a function
}

/**
 * This function is ran when login button is pressed
 * @param event
 */
function on_login_submit(event) {
    console.log("login_submit");
    event.preventDefault();

    //get data from the form
    var form = $(this);
    var username = form.find('input[name=username]');
    var password = form.find('input[name=password]');
    var data = form.serialize();

    // this is the ajax post request
    var promise = $.ajax({
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
        switch_content(data);
    });
}

/**
 * This function is called when the options div with buttons in it are pressed
 * @param event
 */
function on_options_submit(event) {
    event.preventDefault();
    if( event.target.id == "btn_log_out"){
        //do something
        var promise = $.ajax({
            method: 'GET',
            url: 'http://localhost:8080/lif.html',// this needs to be url for the get request to not use /?lif
            data: null,
            contentType: null,
            encode: false,
            success: function(data){
                console.log(data);
                console.log("Log in page requested");
            },
            error: function(err, status){
                console.log(err);
            }
        }).done( function(data) {
            switch_content(data);
        });
        console.log("log out");
    }
    alert(event.target.id);

}
/**
 * This function starts when the page loads
 */
$(document).ready(function() {
    //
    $('#log_in').on('submit', on_login_submit );
});
