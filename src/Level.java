import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Level {
    public final List<Lane> lanes = new ArrayList<>();
    public static final List<Note> notes = new ArrayList<>();
    public static List<Note> activeNotes = new ArrayList<>();
    public static List<Note> specialNotes = new ArrayList<>();
    public static List<Note> specialActiveNotes = new ArrayList<>();
    public static int noteSpeed = 4;
    public static final int COL_YCOORDS = 384;
    public static final int NOTE_YCOORDS = 100;

    /**
     * Constructor for level
     * @param CSV is which CSV it will read out of the three
     */
    public Level(String CSV) {readCSV(CSV);}

    /**
     * Method that displays the notes onto the screen
     */
    public void displayNotes() {
        updateActiveNotes();
        updateActiveSpecialNotes();
        for (Note n : activeNotes) {
            n.drawNotes();
            n.setyCoords(n.getyCoords() + noteSpeed);
        }
        for (Note n : specialActiveNotes) {
            n.drawNotes();
            n.setyCoords(n.getyCoords() + noteSpeed);
        }
    }

    /**
     * Method that displays the lanes onto the screen
     */
    public void displayLanes() {
        for (Lane l : lanes) {
            l.drawLanes();
        }
    }

    /**
     * Method that assigns corresponding message to scores
     * @param notes is the first list of notes that are checked if active
     * @param activeNotes is the list of notes that are active
     */
    private void updateActiveNotes(List<Note> notes, List<Note> activeNotes) {
        List<Note> notesToRemove = new ArrayList<>();
        for (Note n : notes) {
            if(ShadowDance.frameCount == n.getEntryFrame() && n.getyCoords() <= ShadowDance.WINDOW_HEIGHT) {
                activeNotes.add(n);
                if (n.getyCoords() >= ShadowDance.WINDOW_HEIGHT) {
                    notesToRemove.add(n);
                }
            }
        }
        for (Note n : notesToRemove) {
            notes.remove(n);
        }
    }

    /**
     * Method that adds active notes onto the list of active notes
     */
    public void updateActiveNotes() {
        updateActiveNotes(notes, activeNotes);
    }

    /**
     * Method that adds special active notes onto the list of special active notes
     */
    public void updateActiveSpecialNotes() {
        updateActiveNotes(specialNotes, specialActiveNotes);
    }

    /**
     * Method that reads the CSV and creates new notes, special notes and lanes based on CSV data
     * @param CSV is the CSV that the data is taken from
     */
    public void readCSV(String CSV) {
        // use try catch exception when reading CSV to avoid errors while reading
        try (BufferedReader br = new BufferedReader(new FileReader(CSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cells = line.split(",");

                if (cells.length == 3) {
                    // assign each column and trim unnecessary white space
                    String col1 = cells[0].trim();
                    String col2 = cells[1].trim();
                    String col3 = cells[2].trim();

                    if (col1.equals("Lane")) {
                        Lane lane = new Lane(Integer.parseInt(col3), Direction.fromString(col2), COL_YCOORDS);
                        lanes.add(lane);
                    } else if (col1.equals("Special")){
                        Note specialNote = new Note(NOTE_YCOORDS, Direction.fromString(col1), NoteType.fromString(col2), Integer.parseInt(col3));
                        specialNotes.add(specialNote);
                    } else {
                        Note note = new Note(NOTE_YCOORDS, Direction.fromString(col1), NoteType.fromString(col2), Integer.parseInt(col3));
                        notes.add(note);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
