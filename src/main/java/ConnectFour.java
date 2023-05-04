import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    public ConnectFour() {

        int rows = 6;
        int cols = 7;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("Connect Four");
        setLocationRelativeTo(null);

        setLayout(new GridLayout(rows, cols));

        char letter;

        for (int row = rows; 0 < row; row--) {

            for (int col = 0; col < cols; col++) {

                // create new cell buttons and set focus painted to false
                JButton cellButton = new JButton();
                cellButton.setFocusPainted(false);

                // increment letter by column #
                letter = (char) ('A' + col);

                // set name and text by letter and row #
                cellButton.setName("Button" + letter + row);
                cellButton.setText(letter + String.valueOf(row));

                add(cellButton);

            }

        }

        setVisible(true);

    }
}