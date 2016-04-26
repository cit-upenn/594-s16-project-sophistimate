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

	public Model(){
		houseList = null;
		houseType = null;
		isAscendent = 1;
		mongoClient = new MongoClient( "localhost" , 27017 );
	    db = mongoClient.getDatabase("cit594");
	}
	
	
	/**
	 * set service to sell
	 * @return
	 */
	public boolean sell() {
	    service = Service.SELL;
	    return true;
//	    service = SELL;
	  }
	  
	public boolean buy() {
	  service = Service.BUY;
	  return true;
	}
	
	public void getResultByStreetName(String location) {
	    
	  //and(eq("Street Name", "WALNUT"), eq("Street Designation", "ST"), eq("Census Tract",7))
	  iterable = db.getCollection("proj").find(and(eq("Street Name", "WALNUT"), eq("Street Designation", "ST"), eq("Census Tract",7)));
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
