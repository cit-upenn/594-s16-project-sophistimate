package MVC;
import java.util.ArrayList;

import GUIPage.*;
import House.*;

/**
 * This is the view class which is used to display information of the houses
 * in the panel, it stores houses to be displayed.
 * @author mlj
 *
 */
public class View {
	private ResultListingPage resultListingPage;//the page which shows the result of search page.
	ArrayList<HouseType> result;//stores houses houses information to be shown 
	
	/**
	 * this constructor set up the original view data and create result page here
	 * while show the results on the panel.
	 * @param originalList
	 */
	public View(ArrayList<HouseType> originalList){
		this.result = originalList;
		resultListingPage = new ResultListingPage(); 
		update(result);
	}
	
	/**
	 * this method is used to get houses stored in the view
	 * @return the houses to be displayed
	 */
	public ArrayList<HouseType> getResult(){
		return result;
	}
	
	/**
	 * this is method is used to set the houses which will be 
	 * displayed in the view
	 * @param result gives the houses to be stored in the view.
	 */
	public void setResult(ArrayList<HouseType> result){
		this.result = result;
	}
	
	/**
	 * this method is used to update the view with the new version of
	 * houses to be displayed
	 * @param result gives the houses to be updated
	 */
	public void update(ArrayList<HouseType> result){
		this.setResult(result);
		resultListingPage.changeTable(result);
	}
}
