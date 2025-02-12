package Game;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {
        int rowCount = 21;
        int columnCount = 19;
        int tilesize = 32;
        int boardWidth = columnCount * tilesize;
        int boardHeight = rowCount * tilesize;

        JFrame frame = new JFrame("PacMan");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        pacmanGame.requestFocus(); /// legat de key press focus
        frame.setVisible(true);
    }
}

/*
TODO:
  add cerry powerUP
 */