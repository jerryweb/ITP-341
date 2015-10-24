package itp341.webb.jerry.assigment6.model;

import java.io.Serializable;

/**
 * Created by jerrywebb on 10/20/15.
 */
public class CoffeeOrder implements Serializable {
    //These are the instance variables for the coffee order POJO class
    //Each stores a separate descriptor for each order
    private String name;
    private int size;
    private String brew;
    private String specialInstructions;
    private boolean sugar;
    private boolean milkOrCream;
    private int rating;
    private int brewPosition;

    //Constructor
    public CoffeeOrder(String N, int Size, String Brew, boolean Sugar, boolean mORc){
        this.name =  N;
        this.size = Size;
        this.brew = Brew;
        this.sugar = Sugar;
        this.milkOrCream = mORc;
        this.rating = 0;
        this.specialInstructions = "";
        this.brewPosition = 0;
    }

    public String getSpecialInstructions() {
        return specialInstructions;
    }

    public void setSpecialInstructions(String specialInstructions) {
        this.specialInstructions = specialInstructions;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getBrew() {
        return brew;
    }

    public void setBrew(String brew) {
        this.brew = brew;
    }

    public boolean isSugar() {
        return sugar;
    }

    public void setSugar(boolean sugar) {
        this.sugar = sugar;
    }

    public boolean isMilkOrCream() {
        return milkOrCream;
    }

    public void setMilkOrCream(boolean milkOrCream) {
        this.milkOrCream = milkOrCream;
    }

    public int getBrewPosition() {
        return brewPosition;
    }

    public void setBrewPosition(int brewPosition) {
        this.brewPosition = brewPosition;
    }
    //useful for whenever you want to print the object such as in a log statement

    @Override
    public String toString() {
        return "CoffeeOrder{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", brew='" + brew + '\'' +
                ", sugar=" + sugar +
                ", milkOrCream=" + milkOrCream +
                '}';
    }
}
