package Game;

import javax.swing.JFrame;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int rowCount = 21;
        int columnCount = 19;
        int tilesize = 32;
        int boardWidth = columnCount * tilesize;
        int boardHeight = rowCount * tilesize;

        JFrame frame = new JFrame("Game.PacMan");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame);
        frame.pack();
        frame.setVisible(true);
    }
}