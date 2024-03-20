import bagel.Image;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LevelThree extends Level {
    public static final int TARGET_SCORE = 350;
    public static final String CSV3 = "res/level3.csv";
    private static Random rand = new Random();
    public Enemy enemy;
    public static List<Enemy> spawnedEnemies = new ArrayList<>();
    public static List<Projectile> activeArrows = new ArrayList<>();

    /**
     * Constructor for level 3
     */
    public LevelThree() {
        super(CSV3);
    }

    /**
     * Method to draw the guardian at its default position
     */
    public void spawnGuardian() {
        Image image = new Image("res/guardian.png");
        image.draw(Guardian.GUARDIAN_X, Guardian.GUARDIAN_Y);
    }

    /**
     * Method to draw a new enemy every 600 frames
     */
    public void spawnEnemy() {
        if (ShadowDance.frameCount % 600 == 0) {
            enemy = new Enemy(rand.nextInt(800) + 100, rand.nextInt(400) + 100);
            spawnedEnemies.add(enemy);
        }
        for (Enemy e : spawnedEnemies) {
            e.renderEnemy();
            e.move();

            // check for collisions with notes and remove them
            for (Note note : Level.activeNotes) {
                NoteType type = note.getNoteType();
                if (type.equals(NoteType.fromString("Normal")) && e.checkCollision(note)) {
                    Level.activeNotes.remove(note);
                    Level.notes.remove(note);
                    break;
                }
            }
        }
    }

    /**
     * Method to clear all enemies
     * utilised in the ShadowDance class when resetting the game
     */
    public void clearEnemies() {
        spawnedEnemies.clear();
    }

    /**
     * Method to create a new projectile
     * called when the left shift button is pressed
     */
    public void createProjectile() {
        Projectile projectile = new Projectile();
        activeArrows.add(projectile);
    }

    /**
     * Method to draw the projectile
     * implements the reaction to arrow hitting an enemy
     */
    public void fireProjectile() {
        List<Projectile> arrowsToRemove = new ArrayList<>();
        List<Enemy> hitEnemies = new ArrayList<>();

        for (Projectile p : activeArrows) {
            p.drawProjectile();

            Vector2 position = p.position;

            if (position.x > ShadowDance.WINDOW_WIDTH || position.x < 0 || position.y > ShadowDance.WINDOW_HEIGHT || position.y < 0) {
                arrowsToRemove.add(p);
            }

            double dx = position.x - p.targetPos.x;
            double dy = position.y - p.targetPos.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= Projectile.COLLISION_DIST) {
                hitEnemies.add(p.target);
                arrowsToRemove.add(p);
            }
        }
        LevelThree.spawnedEnemies.removeAll(hitEnemies);
        activeArrows.removeAll(arrowsToRemove);
    }

}
