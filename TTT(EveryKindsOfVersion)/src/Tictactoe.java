import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JFrame;
public class Tictactoe extends JFrame
{
        private static class GameBoard extends Canvas
        {
                private static final int EMPTY = 0,X_PIECE = 1,O_PIECE = 2;
                private int[][] gameGrid = new int[3][3]; 
                private boolean xTurn; 
                private Random random;
                private boolean gameRunning; 
                private int gameResult;
                private class GameListener implements MouseListener
                {
                        public void mousePressed(MouseEvent e){}
                        public void mouseClicked(MouseEvent e)
                        {
                                int xPos = e.getPoint().x;
                                int yPos = e.getPoint().y;
                                if(xPos > 50 && yPos > 50 && xPos < 50+50*3 && yPos < 50+50*3)
                                {
                                        if(gameRunning == false)
                                        {
                                                wipeGrid();
                                                gameRunning = true;
                                                repaint();
                                                return;
                                        }
                                        if(gameGrid[xPos/50-1][yPos/50-1] != EMPTY)
                                                return; // Not empty, can't place piece
                                        if(xTurn)
                                        {
                                                gameGrid[xPos/50-1][yPos/50-1] = X_PIECE;
                                                xTurn = false;
                                        }
                                        else
                                        {
                                                gameGrid[xPos/50-1][yPos/50-1] = O_PIECE;
                                                xTurn = true;
                                        }
                                        gameResult = gameOver();
                                        if(gameResult != 0)
                                        {
                                                // Game has ended!
                                                gameRunning = false;
                                        }
                                        repaint();
                                }
                        }
                        public void mouseReleased(MouseEvent e){}
                        public void mouseEntered(MouseEvent e){}
                        public void mouseExited(MouseEvent e){}
                }
                public GameBoard()
                {
                        setPreferredSize(new Dimension(300,300));
                        setBackground(Color.WHITE);
                        addMouseListener(new GameListener());
                        random = new Random();
                        wipeGrid();
                        gameRunning = true;
                }
                public void wipeGrid()
                {
                        for(int y = 0;y < 3;y++)
                                for(int x = 0;x < 3;x++)
                                        gameGrid[x][y] = EMPTY;
                       if(random.nextInt(100) < 50)
                                xTurn = true;
                        else
                                xTurn = false;
                }
                int gameOver()
                {
                        for(int x = 0;x < 3;x++) // Rows
                                if(gameGrid[x][0] == gameGrid[x][1] && gameGrid[x][1] == gameGrid[x][2])
                                        return gameGrid[x][0];
                        for(int y = 0;y < 3;y++) // Columns
                                if(gameGrid[0][y] == gameGrid[1][y] && gameGrid[1][y] == gameGrid[2][y])
                                        return gameGrid[0][y];
                        // Diagonal 1
                        if(gameGrid[0][0] == gameGrid[1][1] && gameGrid[1][1] == gameGrid[2][2])
                                return gameGrid[0][0];
                        if(gameGrid[2][0] == gameGrid[1][1] && gameGrid[0][2] == gameGrid[1][1])
                                return gameGrid[2][0];
                        // Check for tie
                        for(int y = 0;y < 3;y++)
                                for(int x = 0;x < 3;x++)
                                        if(gameGrid[x][y] == 0)
                                                return 0; 
                        return 3;
                }
                public void paint(Graphics g)
                {
                        g.clearRect(0,0,getWidth(),getHeight());
                        g.setColor(Color.BLACK);
                        for(int y = 1;y < 3;y++)
                                g.drawLine(50,y*50+50,50+50*3,y*50+50);
                        for(int x = 1;x < 3;x++)
                                g.drawLine(x*50+50,50,x*50+50,50+50*3);
                       for(int y = 0;y < 3;y++)
                        {
                                for(int x = 0;x < 3;x++)
                                {
                                        if(gameGrid[x][y] == X_PIECE)
                                        {
                                                g.setColor(Color.BLUE);
                                                g.drawLine(50+x*50,50+y*50,50+x*50+50,50+y*50+50);
                                                g.drawLine(50+50+x*50,50+y*50,50+x*50,50+y*50+50);
                                        }
                                        if(gameGrid[x][y] == O_PIECE)
                                        {
                                                g.setColor(Color.RED);
                                                g.drawOval(50+x*50,50+y*50,50,50);
                                        }
                                }
                        }
                        // Check for turns
                        g.setColor(Color.RED);
                        if(gameRunning)
                        {
 if(xTurn)
                                  g.drawString("玩家X，该你了！",10,20);
                                else
                                   g.drawString("玩家O，该你了！",10,20);
                        }
                        else
                        {
                                if(gameResult == X_PIECE)
                                    g.drawString("玩家 X 赢了！",10,20);
                                if(gameResult == O_PIECE)
                                    g.drawString("玩家 O 赢了！",10,20);
                                if(gameResult == 3)
                                        g.drawString("平局！",10,20)   ;                             g.drawString("点击方格重新开始游戏！",10,40);
                        }
                }
        }
        public static void main(String[] args)
        {
                Tictactoe ticTacToe = new Tictactoe();
                ticTacToe.setTitle("Tic Tac Toe");
                ticTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                GameBoard gameBoard = new GameBoard();
                ticTacToe.add(gameBoard);
                ticTacToe.pack();
                ticTacToe.setLocationRelativeTo(null);
                ticTacToe.setVisible(true);
        }
}
