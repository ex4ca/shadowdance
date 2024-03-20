import bagel.Image;
public class Lane {
    private final int xCoords;
    private final int yCoords;
    private final Direction direction;
    public static int leftCoordinate;
    public static int rightCoordinate;
    public static int upCoordinate;
    public static int downCoordinate;
    public static int specialCoordinate;

    /**
     * Constructor for lane
     * @param xCoords is the x coordinate of the lane
     * @param direction is what direction the lane corresponds to
     * @param yCoords is the y coordinates of the lane
     */
    public Lane(int xCoords, Direction direction, int yCoords) {
        this.xCoords = xCoords;
        this.yCoords = yCoords;
        this.direction = direction;
    }

    /**
     * Method that assigns the respective images to each lane and draws them
     */
    public void drawLanes() {
        Image image = null;
        int xCoords = 0;
        switch (this.direction) {
            case LEFT:
                leftCoordinate = this.xCoords;
                xCoords = leftCoordinate;
                image = new Image("res/laneLeft.png");
                break;
            case RIGHT:
                rightCoordinate = this.xCoords;
                xCoords = rightCoordinate;
                image = new Image("res/laneRight.png");
                break;
            case UP:
                upCoordinate = this.xCoords;
                xCoords = upCoordinate;
                image = new Image("res/laneUp.png");
                break;
            case DOWN:
                downCoordinate = this.xCoords;
                xCoords = downCoordinate;
                image = new Image("res/laneDown.png");
                break;
            case SPECIAL:
                specialCoordinate = this.xCoords;
                xCoords = specialCoordinate;
                image = new Image("res/laneSpecial.png");
        }
        if (image != null) {
            image.draw(xCoords, yCoords);

        }
    }

}
