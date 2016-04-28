package MVC;
import java.util.ArrayList;

import GUIPage.*;
import House.*;

public class View {
	private ResultListingPage resultListingPage;
	ArrayList<HouseType> result;
	
	public View(){
		resultListingPage = new ResultListingPage();     /* calling the constructor without updating it is not enough */
	}
	
	public View(ArrayList<HouseType> originalList){
		this.result = originalList;
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
