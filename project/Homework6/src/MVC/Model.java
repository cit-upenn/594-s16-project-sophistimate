package MVC;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Observable;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

import House.*;

/**
 * This is Model class part of MVC
 * @author 
 *
 */
public class Model extends Observable{
	
	private ArrayList<HouseType> houseList;//house list filtered from the main page
	private String houseType;//house type selected from main page
	private int isAscendent;//flag that indicate the order of sorting
	public enum Service {SELL, BUY};

	Service service;
	MongoClient mongoClient;
	MongoDatabase db;
	FindIterable<Document> iterable;
	
	private final String databaseName = "cit594";
	private final String collectionName = "proj";

	/**
	 * This is constructor of model, initialize the database variables, and property variables
	 */
	public Model(){
		houseList = null;
		houseType = null;
		isAscendent = 1;
		mongoClient = new MongoClient( "localhost" , 27017 );
	    db = mongoClient.getDatabase(databaseName);
	}
	
	
	/**
	 * set service to sell
	 * @return true if normal
	 */
	public boolean sell() {
	    service = Service.SELL;
	    return true;
//	    service = SELL;
	  }
	  
	/**
	 * set service to buy
	 * @return true if normal
	 */
	public boolean buy() {
	  service = Service.BUY;
	  return true;
	}
	
	/**
	 * This is method used to communicate database, to query and get result
	 * @param location This is location keyword we pass into method, and get result based on location
	 */
	public void getResultByLocation(String location) {	    
	  //and(eq("Street Name", "WALNUT"), eq("Street Designation", "ST"), eq("Census Tract",7))
	  
	  
	  iterable = db.getCollection(collectionName).find(and(eq("Street Name", "WALNUT"), eq("Street Designation", "ST"), eq("Census Tract",7)));
	  iterable.forEach(new Block<Document>() {
    	  @Override
    	  public void apply(final Document document) {
    	    System.out.println(document);//.get("Coordinates")
    	  }
	  });
	}
	
	
	/*
	 * this method is called only when the main page house type is changed
	 */
	public void setHouseType(String type){
		houseType = type;
	}

	/*
	 * this method is called only when main page receive a new search, and stores the new house list here
	 */
	public void setHouseList(ArrayList<HouseType> house){
		houseList = house;
	}
	
	public void setIsAscendent(boolean flag){
		if(flag == true){
			isAscendent = 1;
		}else{
			isAscendent = 0;
		}
	}
	
	
	
	
}
