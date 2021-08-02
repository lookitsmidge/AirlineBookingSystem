package Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import com.sun.net.httpserver.HttpServer;

public class Server {

    private static String hostname = "localhost";
    private static int port = 8080;
    private static HttpServer server;
    protected static String serverHome = System.getProperty("user.dir").replaceAll("\\\\", "/");

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

    public int getPort() {
        return this.port;
    }

    public void print( String text ){
        System.out.println( text);
    }
}
