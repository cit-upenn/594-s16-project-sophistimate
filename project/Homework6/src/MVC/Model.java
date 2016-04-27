package MVC;


import java.util.*;
import House.*;

/**
 * This is Model class part of MVC
 * @author 
 *
 */
public class Model{
	
	private ArrayList<HouseType> houseList;//house list filtered from the main page
	private String houseType;//house type selected from main page
	private int isAscendent;//flag that indicate the order of sorting
	
	public enum Service {SELL, BUY};
	private DataBaseEngine db;
	private Service service;



	/**
	 * This is constructor of model, initialize the database variables, and property variables
	 */
	public Model(){
		houseList = null;
		houseType = null;
		isAscendent = 1;
		db = new DataBaseEngine();
	}
	
	
	/**
	 * set service to sell
	 * @return true if normal
	 */
	public boolean sell(String location) {
	    service = Service.SELL;
	    return true;
//	    service = SELL;
	  }
	  
	/**
	 * set service to buy
	 * @return true if normal
	 */
	public boolean buy(String location) {
	  service = Service.BUY;
	  List<HouseType> list = db.getResultByLocation(location);
	  if(list.isEmpty()) return false;	  
	  //debug
//	  for(HouseType house: list) {
//	    if(house instanceof Residential) {
//	      house = (Residential) house;
//	      System.out.println(house.getStreetName()+" "+house.getHouseNumber()+" "+house.getUnit());
//	    }	    
//	  }  	  
	  return true;
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
