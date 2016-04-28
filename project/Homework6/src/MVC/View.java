package MVC;
import java.util.ArrayList;

import GUIPage.*;
import House.*;

public class View {
	private ResultListingPage resultListingPage;
	ArrayList<HouseType> result;
	
	public View(ArrayList<HouseType> originalList){
		this.result = originalList;
		resultListingPage = new ResultListingPage(); 
		update(result);
	}
	
	public ArrayList<HouseType> getResult(){
		return result;
	}
	
	public void setResult(ArrayList<HouseType> result){
		this.result = result;
	}
	
	public void update(ArrayList<HouseType> result){
		this.setResult(result);
		resultListingPage.changeTable(result);
	}
}
