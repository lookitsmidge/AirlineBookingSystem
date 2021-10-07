package Server;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import Server.Utilities.FileUtils;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

/**
 * This class is at the top of the hierarchy and anything that is extending it can access all of its methods
 */
public class Server {

    private static String hostname = "localhost";
    private static int port = 8080;
    private static HttpServer server;
    protected static String serverHome = System.getProperty("user.dir").replaceAll("\\\\", "/");
    protected static FileUtils FU = new FileUtils();


    /**
     * This method starts the http server including its thread pool
     * @param threadCount
     */
    public void startServer(int threadCount) {
        print(System.getProperty("user.dir").replaceAll("\\\\", "/")); // /httpserver
        try {
            server = null;
            server = HttpServer.create( new InetSocketAddress(this.hostname, this.port), 0);
            server.createContext("/", new serverHttpHandler( serverHome ) );
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadCount);
            server.setExecutor( threadPool );
            server.start();

            print( "Server has been started on: " + getPort() );
        } catch( IOException e ) {

        }
    }

    /**
     * This method gets the port number for the server
     * @return
     */
    public int getPort() {
        return this.port;
    }

    /**
     * This method is to print to the command line, this will make it much easier to print and debug
     * @param text
     */
    public void print( String text ){
        System.out.println( text);
    }

    /**
     * This method is to send the response to the client
     * @param exchange
     * @param file
     * @param code
     * @throws IOException
     */
    public void sendResponse(HttpExchange exchange, byte[] file, int code ) throws IOException {
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
        if ( requestDir.equals("/") ) {
            requestDir += "MainPage.html";
            print("Redirect to MainPage.html");
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
     * @param exchange
     */
    public void printHeaders(HttpExchange exchange ) {
        print("Headers:");
        Headers headers = exchange.getRequestHeaders();
        Object[] head = headers.values().toArray();
        for( Object i : head ) {
            print( i.toString() );
        }
    }

    /**
     * This method is to retrieve the body of the HTTPExchange
     * @param exchange
     * @return
     */
    public String getBody(HttpExchange exchange ) {
        InputStream is = exchange.getRequestBody();
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

    /**
     * This Method is to get the html page as a byte array to send to the client
     * @param pageToSend
     * @return
     * @throws IOException
     */
    public byte[] getHTMLPage(String pageToSend) throws IOException{
        String directory = serverHome + "/" + pageToSend; // dont need to do the / replace as no space
        File page = new File( directory );
        print("Exists / Readable: " + page.exists() + " / " + page.canRead() );
        // TO TEST THIS SECTION
        if( page.exists() && page.canRead() ) {
            return FU.readFromFile(directory);
        } else {
            return htmlBuilder("Error 404", "HTTP Response code 404: requested directory doesnt exist or couldnt be read").getBytes();
        }
    }
}
