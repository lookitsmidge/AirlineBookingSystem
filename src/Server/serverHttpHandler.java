package Server;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class serverHttpHandler extends Server implements HttpHandler {
    private String serverHome;

    private boolean logged_in = false;

    /**
     * This method is a constructor that is used to set the root directory
     * @param rootDir
     */
    public serverHttpHandler( String rootDir ) {
        this.serverHome = rootDir;
    }

    /**
     * This method is to handle the http request that has been made from a web client
     * @param exchange
     * @throws IOException
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        print( "\n\nClient: " + exchange.getLocalAddress() );
        print( "Method: " + exchange.getRequestMethod() );
        print( "URI: " + exchange.getRequestURI().toString() );
        print( "Content Length: " + exchange.getRequestHeaders().getFirst("Content-Length"));

        switch( exchange.getRequestMethod() ) {
            case "GET":
                handleGet( exchange );
                break;
            case "PUT":
                handlePut( exchange );
                break;
            case "DELETE":
                handleDel( exchange );
                break;
            case "POST":
                handlePost( exchange );
                break;
            default:
                String body = htmlBuilder( "Not Implemented", "Http Error 501: Not Implemented");
                sendResponse(exchange, body.getBytes(), 501);
                break;
        }
    }

    /**
     * This method is to handle get requests that have been made. this method processes and calculates the response that should be sent back
     * @param exchange
     * @throws IOException
     */
    public void handleGet( HttpExchange exchange ) throws IOException {
        switch( exchange.getRequestURI().toString().replace("%20", " ") ) {
            case "/":
                // send log in page ( main page )
                sendResponse ( exchange, FU.readFromFile(getReqDir(exchange)), 200);
                //
                break;
            case "/home.html":

                break;
            case "/book.html":

                break;
            default:
                //checks if user is logged in

                //if not send log in page

                print("Sending");
                String directory = getReqDir( exchange ); // dont need to do the / replace as no space
                File page = new File( getReqDir( exchange) );

                // IMPLEMENT DIFFERENT RESPONSE CODE FOR HERE IF EXISTS IS FALSE OR CAN READ IS FALSE
                sendResponse(exchange, FU.readFromFile(directory), 200);
                break;
        }
    }

    /**
     * This method handles put requests that have been made from the web client
     * @param exchange
     * @throws IOException
     */
    public void handlePut( HttpExchange exchange ) throws IOException {

    }

    /**
     * This method handles delete requests that have been made from the web client
     * @param exchange
     * @throws IOException
     */
    public void handleDel( HttpExchange exchange ) throws IOException {

    }

    /**
     * This method is to handle the post request from the web client
     * @param exchange
     * @throws IOException
     */
    public void handlePost( HttpExchange exchange ) throws IOException {
        switch( exchange.getRequestURI().toString().replace("%20", " ") ) {
            // the web client wants to submit a log in request
            case "/log-in":
                // get the body of the HttpExchange
                String body = getBody(exchange);
                print("BODY: \n" + body);
                if ( body.equals("-1") == true) { // if the body is empty
                    sendResponse(exchange, new byte[0], 204);
                    // HTTP Response code 204 - no content
                } else {

                    String[] content = body.split("&");
                    //pos 0 : username=<content>; pos 1 : password=<content>; pos 2 : <submit type>

                    String username = content[0].split("=")[1];
                    String password = content[1].split("=")[1];

                    //check database
                    if( username.equals("james") && password.equals("123")) {
                        print("Username and Password Match");
                        sendResponse(exchange, getHTMLPage("/home.html"), 200); // this will be changed to a redirect notice
                        // set client as logged in
                        logged_in = true;

//                    sendResponse(exchange, getPanel(exchange, "home.html"), 200);
                    } else {
                        sendResponse(exchange, new byte[0], 406);
                        // send HTTP Response code 406 - Not acceptable
                    }
                }

                break;
            case "/home":

            default:
                print("Body:");
                print( getBody(exchange) );
                sendResponse(exchange, htmlBuilder( "Not Implemented", "Http Error 501: Not Implemented").getBytes(), 501);
                break;
        }
        // got body response .. check form sending to - if form is login then get username and password combo
    }

}

