package itp341.webb.jerry.greenbeatmachine.model;

import java.io.Serializable;

import itp341.webb.jerry.greenbeatmachine.MainActivity;

/**
 * Created by jerrywebb on 11/28/15.
 * This is the Track Object template for each of the 8 tracks
 */
public class Track implements Serializable {
    public static final String TAG = "itp341.finalProject.tag";

    //Instance Variables
    private String name;
    private String type;
    private String sampleName;
    private int currentSampleId;
    private double trackVolume;
    private double trackPan;
    private Sound trackSample;
    private boolean isMuted;

    //constructor used for testing
    public Track() {
        super();
    }

    public Track(String n, double tV, double tP) {
        super();
        this.name = n;
        this.trackVolume = tV;
        this.trackPan = tP;
        this.isMuted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sound getTrackSample() {
        return trackSample;
    }

    public void setTrackSample(Sound trackSample) {
        this.trackSample = trackSample;
        this.name = trackSample.getName();
        this.type = trackSample.getType();
        this.currentSampleId = trackSample.getSoundId();
    }

    public boolean isMuted() {
        return isMuted;
    }

    public void setIsMuted(boolean isMuted) {
        this.isMuted = isMuted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public int getCurrentSampleId() {
        return currentSampleId;
    }

    public void setCurrentSampleId(int currentSampleId) {
        this.currentSampleId = currentSampleId;
    }

    public double getTrackVolume() {
        return trackVolume;
    }

    public void setTrackVolume(double trackVolume) {
        this.trackVolume = trackVolume;
    }

    public double getTrackPan() {
        return trackPan;
    }

    public void setTrackPan(double trackPan) {
        this.trackPan = trackPan;
    }
}
