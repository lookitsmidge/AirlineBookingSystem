package Client.Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

/**
 * This is the main class that runs the airline booking client
 */
public class AirlineSystem {
    private final JFrame frame = new JFrame("Airline Booking System");
    private final JPanel mainPanel = new JPanel( null );
    private final WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {

        }
    };

    // Start of Variables that are being declared


    // End of variables that are being declared

    /**
     * This is the main method
     * @param args
     */
    public static void main( String[] args ) {
        AirlineSystem as = new AirlineSystem();
        as.start();
    }

    public void start() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch ( Exception exc ) {
            //do nothing as this is purely cosmetic
        }

        print("Starting Interface");

        this.frame.setTitle("Airline Booking System");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(520, 600);
        this.frame.addWindowListener(exitListener);
        this.frame.setLayout( new GridLayout(1, 1));
        this.frame.setResizable(false);

        print( "Interface initialisation complete" );

        // connect to server
        // only build panels when need to in this ? or just repopulate everything upon load

        // build all panels

        buildAllPanels();
    }

    public void buildAllPanels() {

    }

    public void print( String text ) {
        System.out.println(text);
    }
}
