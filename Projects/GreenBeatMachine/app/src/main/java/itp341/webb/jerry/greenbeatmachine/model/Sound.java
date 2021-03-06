package itp341.webb.jerry.greenbeatmachine.model;

import java.io.Serializable;

import itp341.webb.jerry.greenbeatmachine.MainActivity;

/**
 * Created by jerrywebb on 11/26/15.
 */
public class Sound implements Serializable {
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";

    //Instance variables
    private long _id;
    private String name;
    private int soundId;
    private String type;
    private int resourceArrayPosition;

    //constructor used for testing
    public Sound() {
        super();
    }

    /**
     * Overload constructor
     * @param long _id
     * @param String name
     * @param int soundId
     * @param String type
     */
    public Sound(String name, long id, int sId, String type){
        super();
        this._id = id;
        this.name = name;
        this.soundId = sId;
        this.type = type;
    }

    public Sound(String Name, int sId, String Type, int rp){
        super();
        name = Name;
        this.soundId = sId;
        type = Type;
        resourceArrayPosition = rp;
    }

    public int getResourceArrayPosition() {
        return resourceArrayPosition;
    }

    public void setResourceArrayPosition(int resourceArrayPosition) {
        resourceArrayPosition = resourceArrayPosition;
    }


    public String getName() {
        return name;
    }

    public void setName(String Name) {
        name = Name;
    }

    public int getSoundId() {
        return this.soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
