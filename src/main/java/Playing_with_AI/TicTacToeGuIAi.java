package Playing_with_AI;

import Playing_with_each_other.Board;
import Playing_with_each_other.TicTacToeGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGuIAi {
    private JFrame frame;
    private JButton[][] buttons;
    private Board board;
    private char currentPlayer;

    public TicTacToeGuIAi() {
        frame = new JFrame("Крестики-нолики");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());

        board = new Board();
        buttons = new JButton[3][3];

        chooseStartingPlayer();

        initializeButtons();

        frame.setVisible(true);
    }

    private void chooseStartingPlayer() {
        String[] options = {"X", "O"};
        int choice = JOptionPane.showOptionDialog(
                frame,
                "Выберите, каким символом играть:",
                "Выбор игрока",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            currentPlayer = 'X';
        } else {
            currentPlayer = 'O';
        }
    }

    public void initializeButtons() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("-");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].setBackground(Color.DARK_GRAY);
                buttons[i][j].setForeground(Color.white);
                final int row = i;
                final int col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override public void actionPerformed(ActionEvent e){
                        if (board.makeMove(row, col, currentPlayer)) {
                            buttons[row][col].setText(String.valueOf(currentPlayer));
                            char winner = board.checkWinner();
                            if (winner != '-') {
                                JOptionPane.showMessageDialog(frame, "Игрок " + winner + " победа!");
                                resetGame();
                            } else if (board.isFull()) {
                                JOptionPane.showMessageDialog(frame, "Ничья!");
                                resetGame();
                            } else {
                                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                            }
                        }
                    }
                });
                gbc.gridx = j;
                gbc.gridy = i;
                frame.add(buttons[i][j], gbc);
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isValidMove(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetGame() {
        board.initializeBoard();
        chooseStartingPlayer();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("-");
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}

