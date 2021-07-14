package Client;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Build Utilities Airline Booking Version 1
 * REF: https://github.com/lookitsmidge/
 * @author James Martland
 */
public class BuildUtilitiesABV1 {
    protected JPanel panel = new JPanel();

    public JPanel getPanel(){
        return panel;
    }

    /**
     * This method is to add a button to a panel
     * @param l
     * @param toAddTo
     * @param button
     * @param x
     * @param y
     * @param text
     * @param sizeX
     * @param sizeY
     * @param tooltip
     */
    public void addButton(ActionListener l, JPanel toAddTo,
                          JButton button,
                          int x, int y,
                          String text, int sizeX,
                          int sizeY, String tooltip) {
        button.setLocation(x, y);
        button.setSize(sizeX, sizeY);

        button.setOpaque(true);
        button.setVisible(true);
        if (text.equals(null) == true) {
            button.setText("");
        } else {
            button.setText(text);
        }
        button.addActionListener(l);
        button.setToolTipText(tooltip);
        System.out.println("\tButton has been created");
        toAddTo.add(button);
    }

    /**
     * This method adds a label to the panel
     * @param label
     * @param panel
     * @param x
     * @param y
     * @param text
     * @param sizeX
     * @param sizeY
     */
    public void addLabel(JLabel label, JPanel panel,
                         int x, int y, String text, int sizeX, int sizeY) {
        label.setLocation(x, y);
        label.setSize(sizeX, sizeY);
        label.setOpaque(true);
        label.setText(text);
        panel.add(label);
    }

    /**
     * This method adds a text field to the panel
     * @param txtfield
     * @param panel
     * @param x
     * @param y
     * @param sizeX
     * @param sizeY
     */
    public void addTextField(JTextField txtfield, JPanel panel,
                             int x, int y, int sizeX, int sizeY) {
        txtfield.setLocation(x, y);
        txtfield.setSize(sizeX, sizeY);
        panel.add(txtfield);
    }

    /**
     * This method adds a text Area to the panel
     * @param txtfield
     * @param panel
     * @param x
     * @param y
     * @param sizeX
     * @param sizeY
     */
    public void addTextArea(JTextArea txtfield, JPanel panel,
                            int x, int y, int sizeX, int sizeY) {
        txtfield.setLocation(x, y);
        txtfield.setSize(sizeX, sizeY);
        panel.add(txtfield);
    }

    /**
     * This method adds a table to the panel
     * @param tbl
     * @param panel
     * @param x
     * @param y
     * @param sizeX
     * @param sizeY
     */
    public void addTable(JTable tbl, JPanel panel, int x, int y, int sizeX, int sizeY) {
        tbl.setLocation(x, y);
        tbl.setSize(sizeX, sizeY);
        panel.add(tbl);
        System.out.println("Table has Been Created");
    }

    /**
     * This method is to more easily print to the command line
     * @param text
     */
    public void print(String text){
        System.out.println(text);
    }
}
