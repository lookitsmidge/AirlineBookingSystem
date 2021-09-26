package Server;

import Server.Utilities.FileUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    public void handleGet( HttpExchange exchange ) throws IOException {
        switch( exchange.getRequestURI().toString().replace("%20", " ") ) {
            case "/":
                print("Do NOTHING");
                //
                break;
            case "/home.html":

                break;
            case "/book.html":

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

    /**
     * This method is to handle the post request from the web client
     * @param exchange
     * @throws IOException
     */
    public void handlePost( HttpExchange exchange ) throws IOException {
        switch( exchange.getRequestURI().toString().replace("%20", " ") ) {
            case "/log-in":
                print("Login form");
                // using body of form to retrieve username and password

                String body = getBody(exchange);
                print("BODY: \n" + body);
                if ( body.equals("-1") == true) {
                    sendResponse(exchange, new byte[0], 204);
                } else {

                    String[] content = body.split("&");
                    //pos 0 : username=<content>
                    //pos 1 : password=<content>
                    //pos 2 : <submit type>

                    String username = content[0].split("=")[1];
                    String password = content[1].split("=")[1];

                    //check database
                    if( username.equals("james") && password.equals("123")) {
                        print("Username and Password Match");
                        sendResponse(exchange, new byte[0], 200);

//                    sendResponse(exchange, getPanel(exchange, "home.html"), 200);
                    } else {
                        sendResponse(exchange, new byte[0], 406);
                    }
                }

                break;
            case "/home":

            default:
                print("Body:");
                print( getBody(exchange) );
                sendResponse(exchange, new byte[0], 501);
                break;
        }
        // got body response .. check form sending to - if form is login then get username and password combo
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

    /**
     * This method is to print the headers of the http exchange
     * @param e
     */
    public void printHeaders(HttpExchange e ) {
        print("Headers:");
        Headers headers = e.getRequestHeaders();
        Object[] head = headers.values().toArray();
        for( Object i : head ) {
            print( i.toString() );
        }
    }


    public String getBody(HttpExchange e ) {
        InputStream is = e.getRequestBody();
        StringBuilder s = new StringBuilder();
        try (Reader reader = new BufferedReader( new InputStreamReader( is, Charset.forName(StandardCharsets.UTF_8.name())))){
            int c = 0;
            while((c = reader.read()) != -1 ) {
                s.append((char) c);
            }
            return s.toString();
        } catch (IOException ioException) {
            print("NO_DATA_IN_BODY");
//            ioException.printStackTrace();
            //body is empty
        }
        return "-1";
    }

    public byte[] getPanel(HttpExchange exchange, String pageToSend) throws IOException{
        String directory = serverHome + "/" + pageToSend; // dont need to do the / replace as no space
        File page = new File( directory );
        print("Exists / Readable: " + page.exists() + " / " + page.canRead() );
        return FU.readFromFile(directory);
    }
}

