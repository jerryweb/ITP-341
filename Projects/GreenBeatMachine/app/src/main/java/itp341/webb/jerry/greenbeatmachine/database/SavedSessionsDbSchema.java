package itp341.webb.jerry.greenbeatmachine.database;

/**
 * Created by jerrywebb on 12/14/15.
 * THIS CLASS SAVES THE USERES SESSION INFORMATION:
 * VOLUME, STEP SEQUENCE, PAN AND MUTE
 */
public class SavedSessionsDbSchema {
    public static final class TABLE_SAVED_SESSIONS {
        public static final String NAME = "sessions";

        // Column Names
        public static final String KEY_ID = "_id"; // follow this convention
        public static final String KEY_NAME = "name"; //this will be stored as an int value
        public static final String KEY_SEQUENCER_STEPS = "sequencer_steps"; //this is the id for each sound to be played
        public static final String KEY_MASTER_VOLUME = "master_volume";
        public static final String KEY_TRACK_VOLUMES = "track_volumes";
        public static final String KEY_TRACK_PANS = "track_pans";
        public static final String KEY_TRACK_MUTES = "track_mutes";


        // Column indexes
        public static final int COLUMN_ID = 0;
        public static final int COLUMN_NAME = 1;
        public static final int COLUMN_SEQUENCER_STEPS = 2;
        public static final int COLUMN_MASTER_VOLUME = 3;
        public static final int COLUMN_TRACK_VOLUMES = 4;
        public static final int COLUMN_TRACK_PANS = 5;
        public static final int COLUMN_TRACK_MUTES = 6;
    }
}
