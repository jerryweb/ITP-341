package itp341.webb.jerry.assignment8.Model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class Stock {

    //JSON keys
    private static final String JSON_ID = "id";
    private static final String JSON_PRICE = "price";
    private static final String JSON_STOCK = "stock";
    private static final String JSON_COLOR = "color";
    private static final String JSON_BRAND = "brand";

    //Instance Variables
    private int ID;
    private int price;
    private int stock;
    private String color;
    private String brand;



    public Stock(){
        super();
    }

    public Stock(int id, int p, int s, String c, String b){
        super();
        this.ID = id;
        this.price =  p;
        this.stock = s;
        this.color = c;
        this.brand = b;
    }

    public Stock(JSONObject json) throws JSONException {
        ID = json.getInt(JSON_ID);
        price = json.getInt(JSON_PRICE);
        stock = json.getInt(JSON_STOCK);
        color = json.getString(JSON_COLOR);
        brand = json.getString(JSON_BRAND);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();

        json.put(JSON_ID, ID);
        json.put(JSON_PRICE, price);
        json.put(JSON_STOCK, stock);
        json.put(JSON_COLOR, color);
        json.put(JSON_BRAND, brand);

        return json;

    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


}
