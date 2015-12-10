package itp341.webb.jerry.greenbeatmachine.model;

import java.io.Serializable;

import itp341.webb.jerry.greenbeatmachine.MainActivity;

/**
 * Created by jerrywebb on 11/26/15.
 */
public class Sound implements Serializable {
    private static final String TAG = MainActivity.class.getSimpleName();

    //Instance variables
    private long _id;
    private String name;
    private String type;

    //constructor used for testing
    public Sound() {
        super();
    }

    /**
     * Overload constructor
     * @param long _id
     * @param String name
     * @param String type
     */
    public Sound(String name, long id, String type){
        super();
        this._id = id;
        this.name = name;
        this.type = type;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
