import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame implements ActionListener {
    public static final int ROWS = 6;
    public static final int COLS = 7;
    private static final Color BASELINE_COLOR = Color.LIGHT_GRAY;
    private static final Color WINNING_COLOR = Color.YELLOW;

    private final JButton[][] boardCells;

    private boolean turn;
    private boolean gameFinished;

    public ConnectFour() {
        turn = true;
        boardCells = new JButton[ROWS][COLS];
        gameFinished = false;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setTitle("Connect Four");
        setLocationRelativeTo(null);

        JPanel boardPanel = new JPanel(new GridLayout(ROWS, COLS));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton button = new JButton();
                String name = String.valueOf((char) ('A' + j)) + (ROWS - i);

                button.setText(" ");
                button.setName("Button" + name);
                button.setFocusPainted(false);
                button.addActionListener(this);
                button.setBackground(BASELINE_COLOR);

                boardPanel.add(button);
                boardCells[ROWS - 1 - i][j] = button;
            }
        }

        JButton resetButton = new JButton("Reset");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> resetGame());
        Dimension resetButtonSize = new Dimension(150, 50);
        resetButton.setPreferredSize(resetButtonSize);

        setLayout(new BorderLayout());
        add(boardPanel, BorderLayout.CENTER);
        add(resetButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameFinished) {
            return;
        }

        int column = ((JButton) e.getSource()).getName().charAt("Button".length()) - 'A';

        for (int i = 0; i < ROWS; i++) {
            if (boardCells[i][column].getText().equals(" ")) {
                boardCells[i][column].setText(turn ? "X" : "O");
                checkWin(i, column);
                break;
            }
        }
        turn = !turn;
    }

    private void resetGame() {
        turn = true;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton cellButton = boardCells[i][j];
                cellButton.setBackground(BASELINE_COLOR);
                cellButton.setText(" ");
            }
        }
        gameFinished = false;
    }

    private void checkWin(int row, int column) {
        String currentPlayerSymbol = turn ? "X" : "O";

        // Check vertical
        int count = 0;
        int startRow = Math.max(0, row - 3);
        int endRow = Math.min(ROWS - 1, row + 3);
        for (int i = startRow; i <= endRow; i++) {
            if (boardCells[i][column].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 4) {
                    gameFinished = true;
                    highlightWinningCells(i, column, i + 3, column);
                    return;
                }
            } else {
                count = 0;
            }
        }

        // Check horizontal
        count = 0;
        int startColumn = Math.max(0, column - 3);
        int endColumn = Math.min(COLS - 1, column + 3);
        for (int j = startColumn; j <= endColumn; j++) {
            if (boardCells[row][j].getText().equals(currentPlayerSymbol)) {
                count++;
                if (count == 4) {
                    gameFinished = true;
                    highlightWinningCells(row, j - 3, row, j);
                    return;
                }
            } else {
                count = 0;
            }
        }

        // Check diagonal (top-left to bottom-right)
        int startDiagonalRow = row;
        int startDiagonalColumn = column;
        while (startDiagonalRow > 0 && startDiagonalColumn > 0 && boardCells[startDiagonalRow - 1][startDiagonalColumn - 1].getText().equals(currentPlayerSymbol)) {
            startDiagonalRow--;
            startDiagonalColumn--;
        }
        int endDiagonalRow = row;
        int endDiagonalColumn = column;
        while (endDiagonalRow < ROWS - 1 && endDiagonalColumn < COLS - 1 && boardCells[endDiagonalRow + 1][endDiagonalColumn + 1].getText().equals(currentPlayerSymbol)) {
            endDiagonalRow++;
            endDiagonalColumn++;
        }
        if (endDiagonalRow - startDiagonalRow + 1 >= 4) {
            gameFinished = true;
            highlightWinningCells(startDiagonalRow, startDiagonalColumn, endDiagonalRow, endDiagonalColumn);
            return;
        }

        // Check diagonal (top-right to bottom-left)
        startDiagonalRow = row;
        startDiagonalColumn = column;
        while (startDiagonalRow > 0 && startDiagonalColumn < COLS - 1 && boardCells[startDiagonalRow - 1][startDiagonalColumn + 1].getText().equals(currentPlayerSymbol)) {
            startDiagonalRow--;
            startDiagonalColumn++;
        }
        endDiagonalRow = row;
        endDiagonalColumn = column;
        while (endDiagonalRow < ROWS - 1 && endDiagonalColumn > 0 && boardCells[endDiagonalRow + 1][endDiagonalColumn - 1].getText().equals(currentPlayerSymbol)) {
            endDiagonalRow++;
            endDiagonalColumn--;
        }
        if (endDiagonalRow - startDiagonalRow + 1 >= 4) {
            gameFinished = true;
            highlightWinningCells(startDiagonalRow, startDiagonalColumn, endDiagonalRow, endDiagonalColumn);return;
        }
    }

    private void highlightWinningCells(int startRow, int startColumn, int endRow, int endColumn) {
        Color winningColor = WINNING_COLOR;

        if (startRow == endRow) {
            // Horizontal sequence
            for (int j = startColumn; j <= endColumn; j++) {
                boardCells[startRow][j].setBackground(winningColor);
            }
        } else if (startColumn == endColumn) {
            // Vertical sequence
            for (int i = startRow; i <= endRow; i++) {
                boardCells[i][startColumn].setBackground(winningColor);
            }
        } else if (endRow - startRow == endColumn - startColumn) {
            // Diagonal sequence (top-left to bottom-right)
            int i = startRow;
            int j = startColumn;
            while (i <= endRow && j <= endColumn) {
                boardCells[i][j].setBackground(winningColor);
                i++;
                j++;
            }
        } else if (endRow - startRow == startColumn - endColumn) {
            // Diagonal sequence (top-right to bottom-left)
            int i = startRow;
            int j = startColumn;
            while (i <= endRow && j >= endColumn) {
                boardCells[i][j].setBackground(winningColor);
                i++;
                j--;
            }
        }
    }
}