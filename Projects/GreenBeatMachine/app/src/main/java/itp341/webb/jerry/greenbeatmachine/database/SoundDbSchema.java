package itp341.webb.jerry.greenbeatmachine.database;

/**
 * Created by jerrywebb on 12/6/15.
 */
public class SoundDbSchema {
    public static final class TABLE_SOUNDS {
        public static final String NAME = "sounds";

        // Column Names
        public static final String KEY_ID = "_id"; // follow this convention
        public static final String KEY_NAME = "name"; //this will be stored as an int value
        public static final String KEY_SOUNDID = "sounds"; //this is the id for each sound to be played
        public static final String KEY_TYPE = "type";

        // Column indexes (good enumeration style)
        public static final int COLUMN_ID = 0;
        public static final int COLUMN_NAME = 1;
        public static final int COLUMN_SOUNDID = 2;
        public static final int COLUMN_TYPE = 3;
    }
}