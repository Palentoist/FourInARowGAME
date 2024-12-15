import javax.swing.*; 
import java.awt.*; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener;

public class Main
{
    private static final int ROWS = 6;
    private static final int COLS = 7;
    private static JButton[][] buttons = new JButton[ROWS][COLS];
    private static int[][] board = new int[ROWS][COLS];
    private static int currentPlayer = 1; 

    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("Four in a Row");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(ROWS, COLS));

        for (int row = 0; row < ROWS; row++) 
        {
            for (int col = 0; col < COLS; col++) 
            {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                button.setActionCommand(row + "," + col);
                button.addActionListener(new ButtonClickListener());
                buttons[row][col] = button;
                boardPanel.add(button);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static class ButtonClickListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            String[] parts = e.getActionCommand().split(",");
            int row = Integer.parseInt(parts[0]);
            int col = Integer.parseInt(parts[1]);

            
            int nextRow = getNextAvailableRow(col);
            if (nextRow != -1) 
            {
                board[nextRow][col] = currentPlayer;
                buttons[nextRow][col].setBackground(currentPlayer == 1 ? Color.RED : Color.YELLOW);
                
                if (checkWin(nextRow, col)) 
                {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                } 
                else 
                {
                    currentPlayer = 3 - currentPlayer; 
                }
            }
        }

        private int getNextAvailableRow(int col) 
        {
            for (int row = ROWS - 1; row >= 0; row--) 
            {
                if (board[row][col] == 0) 
                {
                    return row;
                }
            }
            return -1;
        }

        private boolean checkWin(int row, int col) 
        {
            return (checkDirection(row, col, 1, 0) + checkDirection(row, col, -1, 0) > 2) ||
                   (checkDirection(row, col, 0, 1) + checkDirection(row, col, 0, -1) > 2) ||
                   (checkDirection(row, col, 1, 1) + checkDirection(row, col, -1, -1) > 2) ||
                   (checkDirection(row, col, 1, -1) + checkDirection(row, col, -1, 1) > 2);
        }

        private int checkDirection(int row, int col, int dRow, int dCol) 
        {
            int count = 0;
            int player = board[row][col];
            for (int r = row + dRow, c = col + dCol; r >= 0 && r < ROWS && c >= 0 && c < COLS; r += dRow, c += dCol) 
            {
                if (board[r][c] == player) 
                {
                    count++;
                } 
                else 
                {
                    break;
                }
            }
            return count;
        }

        private void resetGame() 
        {
            for (int row = 0; row < ROWS; row++) 
            {
                for (int col = 0; col < COLS; col++) 
                {
                    board[row][col] = 0;
                    buttons[row][col].setBackground(Color.WHITE);
                }
            }
            currentPlayer = 1; 
        }
    }
}
