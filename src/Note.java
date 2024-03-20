import bagel.Image;
public class Note {
    private int yCoords;
    private Direction direction;
    private NoteType noteType;
    private int entryFrame;
    private boolean notePressed;


    /**
     * Constructor for note
     * @param yCoords assigns the y coordinate of the note
     * @param direction assigns the direction of the note
     * @param noteType assigns the type of the note
     * @param entryFrame assigns the frame that the note enters in
     */
    public Note(int yCoords, Direction direction, NoteType noteType, int entryFrame) {
        this.yCoords = yCoords;
        this.direction = direction;
        this.noteType = noteType;
        this.entryFrame = entryFrame;
        notePressed = false;
    }

    /**
     * Getter method
     * @return int returns the y coordinate of the note
     */
    public int getyCoords() {
        return yCoords;
    }

    /**
     * Setter method
     * @param yCoords is the y coordinate that the note is set to
     */
    public void setyCoords(int yCoords) {
        this.yCoords = yCoords;
    }

    /**
     * Getter method
     * @return Direction returns the direction of the note
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Setter method
     * @param direction is the direction that the note is set to
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Getter method
     * @return noteType returns the type of note it is
     */
    public NoteType getNoteType() {
        return noteType;
    }

    /**
     * Setter method
     * @param noteType is the type that the note is set to
     */
    public void setNoteType(NoteType noteType) {
        this.noteType = noteType;
    }

    /**
     * Getter method
     * @return int returns the entry frame of the note
     */
    public int getEntryFrame() {
        return entryFrame;
    }

    /**
     * Setter method
     * @param entryFrame is the frame that the note starts from
     */
    public void setEntryFrame(int entryFrame) {
        this.entryFrame = entryFrame;
    }

    /**
     * Getter method
     * @return boolean returns true if the note is pressed and false if it isn't
     */
    public boolean isNotePressed() {
        return notePressed;
    }

    /**
     * Setter method
     * @param notePressed sets a boolean for whether the note has been pressed or not
     */
    public void setNotePressed(boolean notePressed) {
        this.notePressed = notePressed;
    }

    /**
     * Method that assigns the correct image of the note depending on its direction and type and draws it
     */
    public void drawNotes() {
        Image image = null;
        int xCoord = 0;
        switch (this.direction) {
            case LEFT:
                xCoord = Lane.leftCoordinate;
                if (this.noteType == NoteType.NORMAL) {
                    image = new Image("res/noteLeft.png");
                } else if (this.noteType == NoteType.HOLD) {
                    image = new Image("res/holdNoteLeft.png");
                } else if (this.noteType == NoteType.BOMB) {
                    image = new Image("res/noteBomb.png");
                }
                break;
            case RIGHT:
                xCoord = Lane.rightCoordinate;
                if (this.noteType == NoteType.NORMAL) {
                    image = new Image("res/noteRight.png");
                } else if (this.noteType == NoteType.HOLD) {
                    image = new Image("res/holdNoteRight.png");
                } else if (this.noteType == NoteType.BOMB) {
                    image = new Image("res/noteBomb.png");
                }
                break;
            case UP:
                xCoord = Lane.upCoordinate;
                if (this.noteType == NoteType.NORMAL) {
                    image = new Image("res/noteUp.png");
                } else if (this.noteType == NoteType.HOLD) {
                    image = new Image("res/holdNoteUp.png");
                } else if (this.noteType == NoteType.BOMB) {
                    image = new Image("res/noteBomb.png");
                }
                break;
            case DOWN:
                xCoord = Lane.downCoordinate;
                if (this.noteType == NoteType.NORMAL) {
                    image = new Image("res/noteDown.png");
                } else if (this.noteType == NoteType.HOLD) {
                    image = new Image("res/holdNoteDown.png");
                } else if (this.noteType == NoteType.BOMB) {
                    image = new Image("res/noteBomb.png");
                }
                break;
            case SPECIAL:
                xCoord = Lane.specialCoordinate;
                if (this.noteType == NoteType.DOUBLESCORE) {
                    image = new Image("res/note2x.png");
                } else if (this.noteType == NoteType.SPEEDUP) {
                    image = new Image("res/noteSpeedUp.png");
                } else if (this.noteType == NoteType.SLOWDOWN) {
                    image = new Image("res/noteSlowDown.png");
                } else if (this.noteType == NoteType.BOMB) {
                    image = new Image("res/noteBomb.png");
                }
                break;
        }
        if (image != null) {
            image.draw(xCoord, yCoords);
        }
    }



}
