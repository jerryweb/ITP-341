package itp341.exercises.week10_json_image_glide.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by R on 10/28/2015.
 */
public class WebPhoto {

    private static final String JSON_NAME = "name";
    private static final String JSON_URL = "url";


    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebPhoto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public WebPhoto() {
    }
    //TODO JSON - JSON Constructor

    /**
     * @param JSONobject json
     * @throws JSONException
     */
    public WebPhoto(JSONObject json) throws JSONException {
        name = json.getString(JSON_NAME);
        url = json.getString(JSON_URL);

    }

    //TODO JSON - toJSON

    @Override
    public String toString() {
        return "WebPhoto{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * Converts CoffeeShop data into JSON object
     *
     * @return JSONobject
     * @throws JSONException
     */
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_NAME, name);
        json.put(JSON_URL, url);


        return json;
    }
}
