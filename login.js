/**
 * This function should update the webpage with html data that has been sent via the server
 * @param data
 */
function switch_content(data) {
    $('#main_content').html(data); // this didnt work before as the tag was for an id
    console.log("setting up event listener");
    $('#options').on('click', on_options_submit );
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

function on_options_submit(event) {
    console.log("log_out_submit");
    event.preventDefault();
    alert(event.target.id);

}
/**
 * This function starts when the page loads
 */
$(document).ready(function() {
    //
    $('#log_in').on('submit', on_login_submit );
});
