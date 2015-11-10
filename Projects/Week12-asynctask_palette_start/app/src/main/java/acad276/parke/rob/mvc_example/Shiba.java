package acad276.parke.rob.mvc_example;

/**
 * Created by R on 11/8/2015.
 */
public class Shiba {
    private String name;
    private int age;
    private String color;


    public Shiba() {

    }

    @Override
    public String toString() {
        return name + " (age " + age + ")";
    }

    public Shiba(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
