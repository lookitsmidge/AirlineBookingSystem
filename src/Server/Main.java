package Server;

/**
 * this class is to start the server, this runs the Server class.
 * this might be changed out for a gui interface for easy server management later on
 * @author James Martland
 */
public class Main {
	
	/**
	 * This is the static method that is to be ran to start the server
	 * @param args
	 */
    public static void main(String[] args) {
        Server s = new Server();
        s.startServer(4);
    }
}
