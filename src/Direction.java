public enum Direction {
    LEFT("Left"),
    RIGHT("Right"),
    UP("Up"),
    DOWN("Down"),
    SPECIAL("Special");

    private final String col1;

    Direction(String col1) {
        this.col1 = col1;
    }

    /**
     * Method that checks string input and converts it to a direction object
     */
    public static Direction fromString(String col1) {
        for (Direction i: Direction.values()) {
            if (i.col1.equalsIgnoreCase(col1)) {
                return i;
            }
        }
        return null;
    }
}