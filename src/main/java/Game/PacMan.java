package Game;

import java.awt.*;
import java.util.HashSet;
import javax.swing.*;

public class PacMan extends JPanel{

    class Block{
        Image image;
        int x;
        int y;
        int width;
        int height;

        int startX;
        int startY;

        Block(Image image, int x, int y, int width, int height){
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }
    }

    private int rowCount = 21;
    private int columnCount = 19;
    private int tilesize = 32;
    private int boardWidth = columnCount * tilesize;
    private int boardHeight = rowCount * tilesize;

    private Image wallImage;
    private Image blueGhostImage;
    private Image redGhostImage;
    private Image pinkGhostImage;
    private Image orangeGhostImage;

    private Image pacmanUpImage;
    private Image pacmanDownImage;
    private Image pacmanLeftImage;
    private Image pacmanRightImage;

    HashSet<Block> walls;
    HashSet<Block> foods;
    HashSet<Block> ghosts;
    Block pacman;

    private String[] tileMap = {
            "XXXXXXXXXXXXXXXXXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X                 X",
            "X XX X XXXXX X XX X",
            "X    X       X    X",
            "XXXX XXXX XXXX XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXrXX X XXXX",
            "O       bpo       O",
            "XXXX X XXXXX X XXXX",
            "OOOX X       X XOOO",
            "XXXX X XXXXX X XXXX",
            "X        X        X",
            "X XX XXX X XXX XX X",
            "X  X     P     X  X",
            "XX X X XXXXX X X XX",
            "X    X   X   X    X",
            "X XXXXXX X XXXXXX X",
            "X                 X",
            "XXXXXXXXXXXXXXXXXXX"
    };

    public PacMan(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.BLACK);

        wallImage = new ImageIcon(getClass().getResource("/PacMan/wall.png")).getImage();
        blueGhostImage = new ImageIcon(getClass().getResource("/PacMan/blueGhost.png")).getImage();
        orangeGhostImage = new ImageIcon(getClass().getResource("/PacMan/orangeGhost.png")).getImage();
        redGhostImage = new ImageIcon(getClass().getResource("/PacMan/redGhost.png")).getImage();
        pinkGhostImage = new ImageIcon(getClass().getResource("/PacMan/pinkGhost.png")).getImage();

        pacmanDownImage = new ImageIcon(getClass().getResource("/PacMan/pacmanDown.png")).getImage();
        pacmanUpImage = new ImageIcon(getClass().getResource("/PacMan/pacmanUp.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/PacMan/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/PacMan/pacmanRight.png")).getImage();


        loadMap();
    }

    private void loadMap(){
        walls = new HashSet<Block>();
        foods = new HashSet<Block>();
        ghosts = new HashSet<Block>();

        for(int i = 0; i < rowCount; i++){
            for(int j = 0; j < columnCount; j++){
                String row = tileMap[i];
                char tileMapChar = row.charAt(j);

                int x = j * tilesize;
                int y = i * tilesize;

                if(tileMapChar == 'X'){
                    Block wall = new Block(wallImage, x, y, tilesize, tilesize);
                    walls.add(wall);
                }
                else if(tileMapChar == 'b'){
                    Block ghost = new Block(blueGhostImage, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'r'){
                    Block ghost = new Block(redGhostImage, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'p'){
                    Block ghost = new Block(pinkGhostImage, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'o'){
                    Block ghost = new Block(orangeGhostImage, x, y, tilesize, tilesize);
                    ghosts.add(ghost);
                }
                else if(tileMapChar == 'P'){
                    pacman = new Block(pacmanRightImage, x, y, tilesize, tilesize);
                }
                else if(tileMapChar == ' '){
                    Block food = new Block(null, x+14, y+14, 4, 4);
                    foods.add(food);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) {
        g.drawImage(pacman.image, pacman.x, pacman.y, pacman.width, pacman.height, null);

        for(Block ghost: ghosts){
            g.drawImage(ghost.image, ghost.x, ghost.y, ghost.width, ghost.height, null);
        }

        for(Block wall: walls){
            g.drawImage(wall.image, wall.x, wall.y, wall.width, wall.height, null);
        }

        g.setColor(Color.WHITE);
        for(Block food: foods){
            g.fillRect(food.x, food.y, food.width, food.height);
        }
    }
}
