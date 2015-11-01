package itp341.exercises.week9_list_singleton.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CoffeeShop { // serializable added later
	// added later - JSON keys
	private static final String JSON_NAME = "name";
	private static final String JSON_ADDRESS = "address";
	private static final String JSON_CITY = "city";
	private static final String JSON_STATE = "state";
	private static final String JSON_ZIP = "zip";
	private static final String JSON_PHONE = "phone";
	private static final String JSON_WEBSITE = "website";
	private static final String JSON_RATING = "rating";

	//Instance variables
	private String name;
	private String address;
	private String city;
	private String state;
	private String zip;
	private String phone;
	private String website;
	private double rating;


	/**
	 * No-argument constructor
	 */
	//constructor used for testing
	public CoffeeShop() {
		super();

	}

	/**Overload constructor
	 * @param String name
	 * @param String address
	 * @param String city
	 * @param String state
	 * @param String zip
	 * @param String phone
	 * @param String website
	 * @param double rating
	 */
	public CoffeeShop(String name, String address, String city, String state,
			String zip, String phone, String website, double rating) {
		super();
		this.name = name;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.website = website;
		this.rating = rating;
	}

    public CoffeeShop(JSONObject json) throws JSONException{
        name = json.getString(JSON_NAME);
        address = json.getString(JSON_ADDRESS);
        city = json.getString(JSON_CITY);
        state = json.getString(JSON_STATE);
        zip = json.getString(JSON_ZIP);
        phone = json.getString(JSON_PHONE);
        website = json.getString(JSON_PHONE);
        rating = json.getDouble(JSON_RATING);
    }

	//JSON - toJSON
    /**converst CoffeeShop data into JSON object
     * @retrun JSONObject
     * @throws JSONException
     */
	public JSONObject toJSON() throws JSONException {	//it always has to have throws...
		JSONObject json = new JSONObject();

        json.put(JSON_NAME, name);
        json.put(JSON_ADDRESS, address);
        json.put(JSON_CITY, city);
        json.put(JSON_ZIP, zip);
        json.put(JSON_STATE, state);
        json.put(JSON_PHONE, phone);
        json.put(JSON_WEBSITE, website);
        json.put(JSON_RATING, rating);

        return json;
	}



	

	/**
	 * 
	 * @return String city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param String city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return String state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param String state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return String zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param String zip
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param String name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param String address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return String phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param String phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return String website
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param String website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return double rating
	 */
	public double getRating() {
		return rating;
	}


	/**
	 * @param double rating
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}


	//TODO toString()
	public String toString(){
		return name + " (" + city+ ")";
	}
}