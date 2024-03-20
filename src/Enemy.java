import bagel.Image;
import bagel.util.Vector2;
import java.util.Random;

public class Enemy{
    private int enemyX;
    private final int enemyY;
    private static final int ENEMY_SPEED = 1;
    private boolean movingRight;
    private static final Random rand = new Random();

    /**
     * Constructor for enemy that sets the position and chooses randomly if enemy is moving left or right
     * @param x is the x coordinate of the enemy
     * @param y is the y coordinate of the enemy
     */
    public Enemy(int x, int y) {
         enemyX = x;
         enemyY = y;
         movingRight = rand.nextBoolean();
    }

    /**
     * Getter method
     * @return Vector2 returns the position of the enemy
     */
    public Vector2 getEnemyPos() {
        return new Vector2(enemyX, enemyY);
    }

    /**
     * Method moves the enemy and reverses direction if it hits the edges
     */
    public void move() {
         // new value for x
         int newX;
         // move enemy horizontally
         if (movingRight) {
             newX = enemyX + ENEMY_SPEED;
         } else {
             newX = enemyX - ENEMY_SPEED;
         }

         // reverse direction
         if (newX <= 100 || newX >= 900) {
             movingRight = !movingRight;
         } else {
             enemyX = newX;
         }
    }

    /**
     * Method that adds the enemy image to the enemy and draws it onto the screen
     */
    public void renderEnemy() {
         Image image = new Image("res/enemy.png");
         image.draw(enemyX, enemyY);
    }

    /**
     * Method that check collision between the enemy and the note
     * @param note is the note that the enemy interacts with
     * @return boolean returns true if the distance between the enemy and note is less than of equal to 104
     */
    public boolean checkCollision(Note note) {
         int centreNoteX = 0;
         if (note.getDirection() == Direction.LEFT) {
             centreNoteX = Lane.leftCoordinate;
         } else if (note.getDirection() == Direction.RIGHT) {
             centreNoteX = Lane.rightCoordinate;
         } else if (note.getDirection() == Direction.DOWN) {
             centreNoteX = Lane.downCoordinate;
         } else if (note.getDirection() == Direction.UP) {
             centreNoteX = Lane.upCoordinate;
         }

         int distance = (int) Math.sqrt(Math.pow(enemyX - centreNoteX, 2) + Math.pow(enemyY - note.getyCoords(), 2));

         return distance <= 104;
    }
}
