package itp341.webb.jerry.assignment7.model;

import java.io.Serializable;

/**
 * Created by jerrywebb on 10/27/15.
 */
public class Note implements Serializable {

    private String title;
    private String content;
    private int date[];    //This is an array of 3 variables which stores day, month, and year in that order
    private String dateFormat;


    //constructor
    public Note (String t, String c, int d[]){
        this.title = t;
        this.content = c;
        this.date = d;
    }

    public Note (){
        this.title = "";
        this.content = "";
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int[] getDate() {
        return date;
    }

    public void setDate(int[] date) {
        this.date = date;
    }

}
