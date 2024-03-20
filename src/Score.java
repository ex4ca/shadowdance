import bagel.Font;
import bagel.util.Point;

import java.util.ArrayList;
import java.util.List;

public class Score {
    private static final int STATIONARY_Y = 657;
    private static final Font MESSAGE_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static int perfectScore = 10;
    private static int goodScore = 5;
    private static int badScore = 1;
    private static int missScore = 5;
    protected static int totalScore = 0;
    public static String message = "";

    /**
     * Method that calculates the score given the distance of the user's input
     * @return int returns the score
     */
    public static int calculateScore(int distance) {
        if (distance <= 15) {
            // PERFECT score
            return 10;
        } else if (distance <= 50) {
            // GOOD score
            return 5;
        } else if (distance <= 100) {
            // BAD score
            return -1;
        } else if (distance <= 200) {
            // MISS
            return -5;
        } else {
            return 0;
        }
    }

    /**
     * Method that assigns corresponding message to scores
     */
    public static void updateMessages(int score) {
        if (score == 10) {
            totalScore += perfectScore;
            message = "PERFECT";
        } else if (score == 5) {
            totalScore += goodScore;
            message = "GOOD";
        } else if (score == -1) {
            totalScore -= badScore;
            message = "BAD";
        } else if (score == -5 || score == 0) {
            totalScore -= missScore;
            message = "MISS";
        }
    }

    /**
     * Method that checks if correct arrow was hit
     */
    public static void checkNoteHit(Direction direction) {
        // initialise message render time
        ShadowDance.renderFrames = 30;

        // find lowest note to apply user input to
        Note lowest = null;
        for (Note n : Level.activeNotes) {
            if (direction == n.getDirection()) {
                if (lowest == null || n.getyCoords() > lowest.getyCoords()) {
                    lowest = n;
                }
            }
        }
        // apply user input to lowest note on the screen first
        if (lowest != null) {
            lowest.setNotePressed(true);
        }
        if (lowest != null && lowest.getNoteType() == NoteType.NORMAL) {
            int noteYcoord = lowest.getyCoords();
            int distance = Math.abs(noteYcoord - STATIONARY_Y);
            int score = calculateScore(distance);

            updateMessages(score);

            // remove normal notes from active notes and notes list
            Level.activeNotes.remove(lowest);
            Level.notes.remove(lowest);
        }
        if (lowest != null && lowest.getNoteType() == NoteType.HOLD) {
            int noteYcoord = lowest.getyCoords();
            int distance = Math.abs((noteYcoord + 82) - STATIONARY_Y);
            int score = calculateScore(distance);

            updateMessages(score);
        }
        // bomb note implementation
        if (lowest != null && lowest.getNoteType() == NoteType.BOMB) {
            message = "LANE CLEAR";
            List<Note> notesToRemove = new ArrayList<>();
            for (Note n : Level.notes) {
                if (n.getDirection() == lowest.getDirection()) {
                    notesToRemove.add(n);
                }
            }
            for (Note n : notesToRemove) {
                Level.notes.remove(n);
                Level.activeNotes.remove(n);
            }
        }
    }

    /**
     * Method for scoring implementation for hold notes
     */
    public static void checkHoldNotes(Direction direction) {
        // initialise message render time
        ShadowDance.renderFrames = 30;

        // find lowest arrow to apply user input to
        Note lowest = null;
        for (Note n : Level.activeNotes) {
            if (direction == n.getDirection()) {
                if (lowest == null || n.getyCoords() > lowest.getyCoords()) {
                    lowest = n;
                }
            }
        }
        //only check release if arrow has already been pressed
        if (lowest != null && lowest.getNoteType() == NoteType.HOLD && lowest.isNotePressed()) {
            int noteYcoord = lowest.getyCoords();
            int distance = Math.abs((noteYcoord - 82) - STATIONARY_Y);
            int score = calculateScore(distance);

            updateMessages(score);

            // remove hold notes after release
            Level.activeNotes.remove(lowest);
            Level.notes.remove(lowest);
        }
    }

    /**
     * Method that checks if notes have left the screen without being pressed
     * score is decremented by 15 if missed
     */
    public static void checkMissedNotes() {
        List<Note> missedNotes = new ArrayList<>();
        for (Note n : Level.activeNotes) {
            if (n.getyCoords() >= ShadowDance.WINDOW_HEIGHT) {
                if (n.getNoteType() != NoteType.BOMB) {
                    ShadowDance.renderFrames = 30;
                    totalScore -= 5;
                    message = "MISS";
                }
                missedNotes.add(n);
            }
        }
        // remove missed notes from the lists
        Level.activeNotes.removeAll(missedNotes);
        Level.notes.removeAll(missedNotes);
    }

    /**
     * Method that checks the scoring implementation of special notes
     * @param direction the direction of the note determines which notes to apply user input to
     */
    public static void checkSpecialNotes(Direction direction) {
        // initialise score frames
        ShadowDance.renderFrames = 30;
        ShadowDance.doubleScoreFrames = 480;
        // find lowest note to apply user input to
        Note lowest = null;
        for (Note n : Level.specialActiveNotes) {
            if (direction == n.getDirection()) {
                if (lowest == null || n.getyCoords() > lowest.getyCoords()) {
                    lowest = n;
                }
            }
        }
        // notes speed up implementation
        if (lowest != null && lowest.getNoteType() == NoteType.SPEEDUP) {
            int noteYcoord = lowest.getyCoords();
            int distance = Math.abs(noteYcoord - STATIONARY_Y);
            if (distance <= 50) {
                totalScore += 15;
                message = "SPEED UP";

                Level.noteSpeed += 1;

                Level.specialActiveNotes.remove(lowest);
                Level.specialNotes.remove(lowest);
            }
        }

        // notes slow down implementation
        if (lowest != null && lowest.getNoteType() == NoteType.SLOWDOWN) {
            int noteYcoord = lowest.getyCoords();
            int distance = Math.abs(noteYcoord - STATIONARY_Y);
            if (distance <= 50) {
                totalScore += 15;
                message = "SLOW DOWN";

                Level.noteSpeed -= 1;

                Level.specialActiveNotes.remove(lowest);
                Level.specialNotes.remove(lowest);
            }
        }

        // double score implementation
        if (lowest != null && lowest.getNoteType() == NoteType.DOUBLESCORE) {
            message = "DOUBLE SCORE";
            if (ShadowDance.doubleScoreFrames > 0) {
                perfectScore *= 2;
                goodScore *= 2;
                badScore *= 2;
                missScore *= 2;
            }
            Level.specialActiveNotes.remove(lowest);
            Level.specialNotes.remove(lowest);
        }

        // bomb note implementation
        if (lowest != null && lowest.getNoteType() == NoteType.BOMB) {
            message = "LANE CLEAR";
            List<Note> notesToRemove = new ArrayList<>();
            for (Note n : Level.specialNotes) {
                if (n.getDirection() == lowest.getDirection()) {
                    notesToRemove.add(n);
                }
            }
            for (Note n : notesToRemove) {
                Level.specialNotes.remove(n);
                Level.specialActiveNotes.remove(n);
            }
            Level.specialActiveNotes.remove(lowest);
            Level.specialNotes.remove(lowest);
        }
    }

    /**
     * Method that prints the corresponding messages onto the screen
     */
    public static void printMessage(String message) {
        Point messagePosition = new Point((ShadowDance.WINDOW_WIDTH - MESSAGE_FONT.getWidth(message)/2)/2, (double) (ShadowDance.WINDOW_HEIGHT + 40) /2);
        if (ShadowDance.renderFrames > 0) {
            MESSAGE_FONT.drawString(message, messagePosition.x, messagePosition.y);
        }
    }

}
