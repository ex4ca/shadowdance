import bagel.*;
import bagel.util.Point;

/**
 * SWEN20003 Project 2, Semester 2, 2023
 * @author EricaGurung
 */
public class ShadowDance extends AbstractGame {
    public final static int WINDOW_WIDTH = 1024;
    public final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private final static Point TITLE_POSITION = new Point(220, 250);
    public static int frameCount = 0;
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private final Font DEFAULT_FONT = new Font(FONT_FILE, 64);
    private static boolean gameEnded = false;
    private boolean level1started = false;
    private boolean level2started = false;
    private boolean level3started = false;

    private boolean gameStarted = false;
    private boolean csv1Loaded = false;
    private boolean csv2Loaded = false;
    private boolean csv3Loaded = false;

    // initialise levels
    private LevelOne level1;
    private LevelTwo level2;
    private LevelThree level3;

    protected static int targetScore;
    protected static int renderFrames = 30;
    protected static int doubleScoreFrames = 480;

    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    /**
     * Display start screen before game starts
     */
    public void displayStartScreen() {
        DEFAULT_FONT.drawString(GAME_TITLE, TITLE_POSITION.x, TITLE_POSITION.y);
        INSTRUCTION_FONT.drawString("SELECT LEVELS WITH", TITLE_POSITION.x + 100, TITLE_POSITION.y + 190);
        INSTRUCTION_FONT.drawString("NUMBER KEYS", TITLE_POSITION.x + 170, TITLE_POSITION.y + 230);
        INSTRUCTION_FONT.drawString("1  2  3", TITLE_POSITION.x + 230, TITLE_POSITION.y + 280);
    }

    /**
     * Display end screen after game ends
     */
    public void displayEndScreen() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        String finishMessage;
        if (Score.totalScore >= targetScore) {
            finishMessage = "CLEAR!";
        } else {
            finishMessage = "TRY AGAIN";
        }
        Point messagePosition = new Point((WINDOW_WIDTH - DEFAULT_FONT.getWidth(finishMessage))/2, (double) (WINDOW_HEIGHT + 64) /2);
        DEFAULT_FONT.drawString(finishMessage, messagePosition.x, messagePosition.y);
        SCORE_FONT.drawString("Score " + Score.totalScore, messagePosition.x + 50, messagePosition.y + 60);

        String spaceMessage = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
        Point spacePosition = new Point(150, messagePosition.y + 100);
        INSTRUCTION_FONT.drawString(spaceMessage, spacePosition.x, spacePosition.y);
    }

    /**
     * Resets the game and all values to 0
     */
    private void resetGame() {
        // reset all values
        csv1Loaded = false;
        csv2Loaded = false;
        csv3Loaded = false;

        gameStarted = false;
        gameEnded = false;

        level1started = false;
        level2started = false;
        level3started = false;

        frameCount = 0;
        Score.totalScore = 0;
        Score.message = " ";

        if (level3started) {
            level3.clearEnemies();
        }
    }

    /**
     * Initialises level 1 and creates new level 1 object
     */
    private void initialiseLevel1() {
        if (level1started) {
            level1 = new LevelOne();
            csv1Loaded = true;
        }
    }

    /**
     * Initialises level 2 and creates new level 2 object
     */
    private void initialiseLevel2() {
        if (level2started) {
            level2 = new LevelTwo();
            csv2Loaded = true;
        }
    }

    /**
     * Initialises level 3 and creates new level 3 object
     */
    private void initialiseLevel3() {
        if (level3started) {
            level3 = new LevelThree();
            csv3Loaded = true;
        }
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);

        // allows the game to exit when the escape key is pressed.
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }

        if (!gameStarted && !level1started && !level2started && !level3started && !gameEnded) {
            displayStartScreen();

            if (input.wasPressed(Keys.NUM_1)) {
                level1started = true;
                gameStarted = true;

                if (!csv1Loaded) {
                    initialiseLevel1();
                }
            }

            if (input.wasPressed(Keys.NUM_2)) {
                level2started = true;
                gameStarted = true;

                if (!csv2Loaded) {
                    initialiseLevel2();
                }
            }

            if (input.wasPressed(Keys.NUM_3)) {
                level3started = true;
                gameStarted = true;

                if (!csv3Loaded) {
                    initialiseLevel3();
                }
            }

        } else {
            //increments frame count with each update once game starts
            frameCount++;
            renderFrames--;
            doubleScoreFrames--;

            if (level1started) {
                // draws lanes and notes
                level1.displayLanes();
                level1.displayNotes();

                targetScore = LevelOne.TARGET_SCORE;
            }

            if (level2started) {
                // draws lanes and notes
                level2.displayLanes();
                level2.displayNotes();

                targetScore = LevelTwo.TARGET_SCORE;
            }

            if (level3started) {
                // draws lanes and notes
                level3.displayLanes();
                level3.displayNotes();

                // spawn characters
                level3.spawnGuardian();
                level3.spawnEnemy();

                level3.fireProjectile();

                targetScore = LevelThree.TARGET_SCORE;
            }

            // checks user input
            if (input.wasPressed(Keys.LEFT)) {
                Score.checkNoteHit(Direction.LEFT);
            }
            if (input.wasPressed(Keys.RIGHT)) {
                Score.checkNoteHit(Direction.RIGHT);
            }
            if (input.wasPressed(Keys.UP)) {
                Score.checkNoteHit(Direction.UP);
            }
            if (input.wasPressed(Keys.DOWN)) {
                Score.checkNoteHit(Direction.DOWN);
            }
            // special note input
            if (input.wasPressed(Keys.SPACE)) {
                Score.checkSpecialNotes(Direction.SPECIAL);
            }

            // guardian input
            if (input.wasPressed(Keys.LEFT_SHIFT)) {
                if (!LevelThree.spawnedEnemies.isEmpty()) {
                    level3.createProjectile();
                }
            }

            //checks user input for hold notes
            if (input.wasReleased(Keys.LEFT)) {
                Score.checkHoldNotes(Direction.LEFT);
            }
            if (input.wasReleased(Keys.RIGHT)) {
                Score.checkHoldNotes(Direction.RIGHT);
            }
            if (input.wasReleased(Keys.UP)) {
                Score.checkHoldNotes(Direction.UP);
            }
            if (input.wasReleased(Keys.DOWN)) {
                Score.checkHoldNotes(Direction.DOWN);
            }

            // print messages on screen
            Score.printMessage(Score.message);

            // checks for missed notes
            Score.checkMissedNotes();

            // draws score
            SCORE_FONT.drawString("Score " + Score.totalScore, 35, 35);

            // checks when arrows list is empty, so we can end the game
            if (Level.notes.isEmpty()) {
                gameEnded = true;
            }
        }


        // once all arrows have been processed, displays the finish screen
        if (gameEnded) {
            displayEndScreen();

            if (input.wasPressed(Keys.SPACE)) {
                displayStartScreen();
                resetGame();
            }

        }
    }
}
