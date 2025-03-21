package Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;

public class PacMan extends JPanel implements ActionListener, KeyListener {
///----------------------------------------------------------- Key Listeners
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(gameOver){
            restart();
        }
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            pacman.updateDirection('U');
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            pacman.updateDirection('D');
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            pacman.updateDirection('L');
        }
        else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            pacman.updateDirection('R');
        }

        if(pacman.direction == 'U'){
            pacman.image = pacmanUpImage;
        }
        else if(pacman.direction == 'D'){
            pacman.image = pacmanDownImage;
        }
        else if(pacman.direction == 'L'){
            pacman.image = pacmanLeftImage;
        }
        else if(pacman.direction == 'R'){
            pacman.image = pacmanRightImage;
        }
    }
/// ---------------------------------------------------------- Metode class PacMan
    public void restart(){
        loadMap();
        reset();
        lives = 3;
        score = 0;
        gameOver = false;
        gameLoop.start();
    }

    char[] Directions = {'U', 'D', 'L', 'R'};
    Random rand = new Random();

    public void move(){
        pacman.x += pacman.velocityX;
        pacman.y += pacman.velocityY;

        for(Block wall: walls){
            if(collision(pacman, wall)){
                pacman.x -= pacman.velocityX;
                pacman.y -= pacman.velocityY;
                break;
            }
        }
        Block foodEaten = null;
        for(Block food: foods){
            if(collision(pacman,food)){
                foodEaten = food;
                score += 10;
                break;
            }
        }
        foods.remove(foodEaten);
        if(foods.isEmpty()){
            loadMap();
            reset();
        }
        checkAndWorp(pacman);
        for(Block ghost: ghosts){
            if(collision(pacman,ghost)) {
                lives -= 1;
                if(lives == 0){
                    gameOver = true;
                    return;
                }
                reset();
            }
            if(ghost.y == 9*tilesize && ghost.direction != 'U' && ghost.direction != 'D'){
                ghost.updateDirection('U');
            }
            ghost.x += ghost.velocityX;
            ghost.y += ghost.velocityY;
            for(Block wall: walls){
                if(collision(ghost, wall)){
                    ghost.x -= ghost.velocityX;
                    ghost.y -= ghost.velocityY;
                    char newDirection = Directions[rand.nextInt(4)];
                    ghost.updateDirection(newDirection);
                }
            }
            checkAndWorp(ghost);
        }
    }

    public void reset(){
        pacman.resetPosition();
        pacman.velocityX = 0;
        pacman.velocityY = 0;
        for(Block ghost: ghosts){
            ghost.resetPosition();
            char newDirection = Directions[rand.nextInt(4)];
            ghost.updateDirection(newDirection);
        }
    }

    public void checkAndWorp(Block block){
        if(block.x < -tilesize){
            block.x = 19*tilesize;
        }
        else if(block.x > 19*tilesize){
            block.x = -tilesize;
        }
    }

    public boolean collision(Block a, Block b){
        return a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }

/// ---------------------------------------------------------- nested class Block
    class Block{
        Image image;
        int x;
        int y;
        int width;
        int height;

        int startX;
        int startY;

        char direction = 'U'; /// U:up  D:down  L:left  R:right
        int velocityX;
        int velocityY;

        Block(Image image, int x, int y, int width, int height){
            this.image = image;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.startX = x;
            this.startY = y;
        }

        void updateDirection(char direction){
            char prevDirection = this.direction;
            this.direction = direction;
            updateVelocity();
            this.x += this.velocityX;
            this.y += this.velocityY;
            for(Block wall: walls){
                if(collision(this, wall)){
                    this.x -= this.velocityX;
                    this.y -= this.velocityY;
                    this.direction = prevDirection;
                    updateVelocity();
                    break;
                }
            }
            updateVelocity();
        }

        void resetPosition(){
            this.x = startX;
            this.y = startY;
        }

        void updateVelocity(){
            if(direction == 'U'){
                this.velocityX = 0;
                this.velocityY = -tilesize/4;
            }
            else if(direction == 'D'){
                this.velocityX = 0;
                this.velocityY = tilesize/4;
            }
            else if(direction == 'L'){
                this.velocityX = -tilesize/4;
                this.velocityY = 0;
            }
            else if(direction == 'R'){
                this.velocityX = tilesize/4;
                this.velocityY = 0;
            }
        }
    }

/// ---------------------------------------------------------- atribut class PacMan
    int score = 0;
    int lives = 3;
    boolean gameOver = false;

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

    Timer gameLoop;

/// -------------------------------------------------------------------- Constructor PacMan
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

        for(Block ghost: ghosts){
            char newDirection = Directions[rand.nextInt(4)];
            ghost.updateDirection(newDirection);
        }

        gameLoop = new Timer(50, this);
        gameLoop.start();
        addKeyListener(this);
        setFocusable(true);  /// asigura ca programul care asculta pentru key press e pacman
    }

/// -------------------------------------------------------------------- Load Map
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

/// -------------------------------------------------------------------Paint Component
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

        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if(gameOver){
            g.drawString("Game Over: " + String.valueOf(score), tilesize/2, tilesize/2);
        }
        else{
            g.drawString('X' + String.valueOf(lives) + " Score: " + String.valueOf(score), tilesize/2, tilesize/2);
        }
    }
}
