import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Vector2;

public class Projectile {
    private static final int PROJECTILE_SPEED = 6;
    public static final int COLLISION_DIST = 62;
    private final Image image;
    public Vector2 position;
    public Vector2 velocity;
    public Enemy target;
    public Vector2 targetPos;


    /**
     * Constructor for projectile that assigns the target enemy, direction and velocity of projectile
     */
    public Projectile() {
        image = new Image("res/arrow.png");
        double minDistance = Double.MAX_VALUE;

        for (Enemy e : LevelThree.spawnedEnemies) {
            // Calculate the distance between guardianPosition and enemy.getPosition()
            double dx = Guardian.guardianPos.x - e.getEnemyPos().x;
            double dy = Guardian.guardianPos.y - e.getEnemyPos().y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance < minDistance) {
                minDistance = distance;
                target = e;

                // Set the initial position at the guardian's location
                position = new Vector2(Guardian.guardianPos.x, Guardian.guardianPos.y);

                targetPos = target.getEnemyPos();

                // Calculate the direction vector towards the enemy
                Vector2 direction = targetPos.sub(position);

                // Normalize the direction vector
                direction = direction.div(direction.length());

                // Calculate the initial velocity based on speed
                velocity = direction.mul(PROJECTILE_SPEED);
            }
        }
    }

    /**
     * Method that draws the projectile in the direction of the target enemy
     */
    public void drawProjectile() {
        position = position.add(velocity);

        if (target != null) {
            Vector2 direction = target.getEnemyPos().sub(Guardian.guardianPos);
            double angle = Math.atan2(direction.y, direction.x);

            // create DrawOptions and set rotation of projectile
            DrawOptions options = new DrawOptions();
            options.setRotation(angle);

            // draw the image
            image.draw(position.x, position.y, options);
        }
    }
}
