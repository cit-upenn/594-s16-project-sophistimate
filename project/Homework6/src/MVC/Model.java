package MVC;

import java.util.ArrayList;
import House.*;

public class Model {
	
	private ArrayList<HouseType> houseList;//house list filtered from the main page
	private String houseType;//house type selected from main page
	private int isAscendent;//flag that indicate the order of sorting


	public Model(){
		houseList = null;
		houseType = null;
		isAscendent = 1;
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
