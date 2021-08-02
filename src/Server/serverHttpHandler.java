package Server;

import Server.Utilities.FileUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class serverHttpHandler extends Server implements HttpHandler {
    private String serverHome;
    private static FileUtils FU = new FileUtils();

    public serverHttpHandler( String rootDir ) {
        this.serverHome = rootDir;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        print( "\n\nClient: " + exchange.getLocalAddress() );
        print( "Method: " + exchange.getRequestMethod() );
        print( "URI: " + exchange.getRequestURI().toString() );

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

    public void handleGet( HttpExchange exchange ) throws IOException {
        switch( exchange.getRequestURI().toString().replace("%20", " ") ) {
            case "/":
                print("Do NOTHING");
                //
                break;
            default:
                //checks if user is logged in

                //if is logged in then home page

                //if not send log in page

                print("Sending Log In Page");
                print(serverHome);
                String directory = getReqDir( exchange ); // dont need to do the / replace as no space
                File page = new File( directory );
                print("Exists / Readable: " + page.exists() + " / " + page.canRead() );

                sendResponse(exchange, FU.readFromFile( directory ), 200);
                break;
        }
    }

    public void handlePut( HttpExchange exchange ) throws IOException {

    }

    public void handleDel( HttpExchange exchange ) throws IOException {

    }

    public void handlePost( HttpExchange exchange ) throws IOException {

    }

    /**
     * This method is to send the response to the client
     * @param exchange
     * @param file
     * @param code
     * @throws IOException
     */
    public void sendResponse( HttpExchange exchange, byte[] file, int code ) throws IOException {
        if( file == null ){
            exchange.sendResponseHeaders(code, -1);
        } else {
            OutputStream out = exchange.getResponseBody();
            exchange.sendResponseHeaders(code, file.length);
            out.write(file);
            out.flush();
            out.close();
        }
    }

    /**
     * This method is to get the directory that was requested as a string
     * @param exchange
     * @return
     */
    public String getReqDir(HttpExchange exchange) {
        //need to improve for efficiency
        String requestDir = exchange.getRequestURI().toString();
        if ( requestDir == "/" ) {
            requestDir += "login.html";
        }
        print("Dir: " + requestDir);
        requestDir = serverHome + requestDir;
        return requestDir;
    }

    /**
     * This method builds html code to be used with the responses of error messages and such
     * @param title
     * @param header
     * @return
     */
    public String htmlBuilder(String title, String header) {
        StringBuilder body = new StringBuilder("<HTML>\r\n");
        body.append("<HEAD><TITLE>" + title + "</TITLE>\r\n");
        body.append("</HEAD>\r\n");
        body.append("<BODY>");
        body.append("<H1>" + header + "</H1>\r\n");
        body.append("</BODY></HTML>\r\n");
        return body.toString();
    }
}

