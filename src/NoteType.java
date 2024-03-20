public enum NoteType {
    NORMAL("Normal"),
    HOLD("Hold"),
    DOUBLESCORE("DoubleScore"),
    SPEEDUP("SpeedUp"),
    SLOWDOWN("SlowDown"),
    BOMB("Bomb");

    private final String col2;

    NoteType(String col2) {
        this.col2 = col2;
    }

    /**
     * Method that checks string input and converts it to a NoteType object
     */
    public static NoteType fromString(String col2) {
        for (NoteType i: NoteType.values()) {
            if (i.col2.equalsIgnoreCase(col2)) {
                return i;
            }
        }
        return null;
    }
}
