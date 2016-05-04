package MVC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import House.*;

/**
 * This class is the model class which is used to store house data from search engine
 * of mongodb, and the returned houses information are stored as an array list here.
 * Also, there are some calculation method in this class which is used to operate on
 * returned houses to filter or sorting a more customized results from user input.
 * @author mlj
 *
 */
public class Model {
    
    private ArrayList<HouseType> houseList;//house list filtered from the main page
    private static String houseType;//house type selected from main page
    private static int isAscendent;//flag that indicate the order of sorting
    private View view = null;
    
    public Model(){
//        houseList = null;
    	houseList = new ArrayList<HouseType>();
        houseType = null;
        isAscendent = 1;
    }
    
    /**
     * this method is used to update the houses in the view
     * @param result gives the houses to be updated
     */
    public void update(ArrayList<HouseType> result){
        this.houseList = result;
        if ( view == null) {
            view = new View(houseList);
        } 
        view.update(houseList);
    }
    
    /**
     * this method is used to set house type of the search
     * @param type gives the house type
     */
    public void setHouseType(String type){
        houseType = type;
    }
    
    /**
     * this method is used to get the house type
     * @return the house type
     */
    public String getHouseType(){
        return houseType;
    }

    /**
     * this method is used to set the house list of the model
     * @param house gives the houses to be stored in the model
     */
    public void setHouseList(ArrayList<HouseType> house){
        houseList = house;
    }
    
    
    /**
     * this method is used to get the house lists of the model
     * @return the house list
     */
    public ArrayList<HouseType> getHouseList(){
        return houseList;
    }
    
    /**
     * this method set the ascendent information for sorting order
     */
    public void setIsAscendent(boolean flag){
        if(flag == true){
            isAscendent = 1;
        }else{
            isAscendent = 0;
        }
    }
    
    /**
     * this method is used to indicate whether the sorting order is ascendent or not
     * @return true if ascendent, false otherwise
     */
    public boolean isAscendent(){
        if(isAscendent == 1){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * This method is used to sort the houses with the given sorting field
     * @param houses gives the array list to be sorted
     * @param sortType tells the field of houses to be sorted
     * @return the sorted house arrayList
     */
    public ArrayList<HouseType> sorting(ArrayList<HouseType> houses, String sortType){
        ArrayList<HouseType> newList = new ArrayList<HouseType>();
        if(sortType.equals("salePrice")){
            Collections.sort(houses, new SalePriceComparator());
        }else if(sortType.equals("marketValue")){
            Collections.sort(houses, new MarketPriceComparator());
        }else if(sortType.equals("livingArea")){
            Collections.sort(houses, new LivingAreaComparator());
        }else if(sortType.equals("outdoorArea")){
            Collections.sort(houses, new OutdoorAreaComparator());
        }else if(sortType.equals("buildYear")){
            Collections.sort(houses, new BuildYearComparator());
        }
        
        for(int i = 0; i < houses.size(); i++){
            newList.add(houses.get(i));
        }
        
        if(isAscendent == 0){
            newList = sortingReverse(newList);
        }
        
        return newList;
    }
    
    /**
     * This method is used to reverse the order of array-list when ascendent/descendent button
     * is clicked to reverse the order
     * @param houses the array-list to reverse the order
     * @return the reversed array-list
     */
    public ArrayList<HouseType> sortingReverse(ArrayList<HouseType> houses){
        ArrayList<HouseType> newList = new ArrayList<HouseType>();
        for(int i = houses.size() - 1; i >= 0; i--){
            newList.add(houses.get(i));
        }
        return newList;
    }
    
    /**
     * This method is used to return all houses satisfying the filtering conditions from the array-list
     * got from the main page search
     * @param la_low gives living area lower bound
     * @param la_high gives living area upper bound
     * @param by_low gives built year lower bound
     * @param by_high gives built year upper bound
     * @param sp_low gives salePrice lower bound
     * @param sp_high gives salePrice upper bound
     * @param mv_low gives marketPrice lower bound
     * @param mv_high gives marketPrice upper bound
     * @param parking gives whether parking lot is needed
     * @return an array list satisfying all of the conditions
     */
    public ArrayList<HouseType> filtering(int la_low, int la_high, int by_low, int by_high, int sp_low, int sp_high, int mv_low, int mv_high, int parking){
        ArrayList<HouseType> newList = new ArrayList<HouseType>();
        for(int i = 0; i < houseList.size(); i++){
            HouseType temp = houseList.get(i);
            double intToDouble = (double)la_low;
            if(la_low != -1 && temp.getLivingArea() < intToDouble){
                continue;
            }
            
            intToDouble = (double)la_high;
            if(la_high != -1 && temp.getLivingArea() > intToDouble){
                continue;
            }
            
            if(by_low != -1 && temp.getBuildYear() < by_low){
                continue;
            }
            
            if(by_high != -1 && temp.getBuildYear() > by_high){
                continue;
            }

            intToDouble = (double)sp_low;
            if(sp_low != -1 && temp.getSalePrice() < intToDouble){
                continue;
            }
            
            intToDouble = (double)sp_high;
            if(sp_high != -1 && temp.getSalePrice() > intToDouble){
                continue;
            }
            
            intToDouble = (double)mv_low;
            if(mv_low != -1 && temp.getMarketValue() < intToDouble){
                continue;
            }
            
            intToDouble = (double)mv_high;
            if(mv_high != -1 && temp.getMarketValue() > intToDouble){
                continue;
            }
            
            if((parking == 0 && temp.getParking() != 0) ||(parking == 1 && temp.getParking() == 0)){
                continue;
            }
            newList.add(temp);
        }
        
        return newList;     
    }


    /**
     * This method is used to get houses with certain ranking in a sorted array-list
     * @param houses gives the sorted array-list with object type HouseType
     * @param start gives the start rank of the house in the array-list
     * @param end gives the end rank of the house in the array-list
     * @return the houses within a certain rank
     */
    public ArrayList<HouseType> getSpecifiedHouses(ArrayList<HouseType> houses, int start, int end){
        ArrayList<HouseType> newList = new ArrayList<HouseType>();
        for(int i = start; i <= end; i++){
            if(i >= 0 && i < houses.size()){
                newList.add(houses.get(i));
            }
        }
        return newList;
    }
    
    /**
     * this method is used to get the most similar houses with given build year and living area
     * @param buildYear gives the target build year of the house
     * @param livingArea gives the target living area of the house
     * @return an array list of 30 most similar houses, if not enough, just return these similar houses
     */
    public ArrayList<HouseType> filteringSellHouse(int buildYear, double livingArea){
        ArrayList<HouseType> retHouses = sorting(houseList, "livingArea");
        ArrayList<HouseType> newHouses = new ArrayList<HouseType>();
        if(livingArea <= retHouses.get(0).getLivingArea()){
        	if(retHouses.size() < 30){
        		newHouses = getSpecifiedHouses(retHouses, 0, retHouses.size() - 1);
        	}else{
        		newHouses = getSpecifiedHouses(retHouses, 0, 29);
        	}
        }else if(livingArea >= retHouses.get(retHouses.size() - 1).getLivingArea()){
        	if(retHouses.size() < 30){
        		newHouses = getSpecifiedHouses(retHouses, 0, retHouses.size() - 1);
        	}else{
        		newHouses = getSpecifiedHouses(retHouses, retHouses.size() - 30, retHouses.size() - 1);
        	}
        }else{
        	for(int i = 0; i < retHouses.size(); i++){
        		HouseType temp = retHouses.get(i);
        		if(temp.getLivingArea() > livingArea){
        			newHouses.add(temp);        			
        		}
        		if(newHouses.size() >= 30){
        			break;
        		}
        	}
        }

        newHouses = sorting(newHouses,"salePrice");
        return newHouses;
    }
    
    /**
     * this method is used to get the estimated price of the house to be sold from similar houses
     * @param similarHouses gives an array list of houses which is similar to the sold house
     * @return the estimated price
     */
    public double getPrice(ArrayList<HouseType> similarHouses){
        double totalPrice = 0;
        int number = similarHouses.size();
        if(number < 10){
            for(int i = 0; i < number; i++){
                totalPrice = totalPrice + similarHouses.get(i).getSalePrice();
            }
            if(totalPrice == 0){
                return 0;
            }else{
                return totalPrice/number;
            }           
        }else{
            int median = number/2;
            for(int i = median - 5; i < median + 5; i++){
                totalPrice = totalPrice + similarHouses.get(i).getSalePrice();
            }
        }
        return totalPrice/10;
    }
    
    
    
    
    /**
     * This method is used to get the ranking of a certain house in a
     * sorted array-list of the houses
     * @param houses gives an array-list with object type HouseType
     * @param h gives the house which we want to search its rank
     * @return the rank of the target house
     */
    public int getHouseRank(ArrayList<HouseType> houses, HouseType h){
        for(int i = 0; i < houses.size(); i++){
            if(houses.get(i) == h){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * this is static inner class which implements Comparator and used
     * for sorting houses according to its sale price
     * @author mlj
     *
     */
    static class SalePriceComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            HouseType p1 = (HouseType) object1; 
            HouseType p2 = (HouseType) object2;
            return new Double(p1.getSalePrice()).compareTo(new Double(p2.getSalePrice())); 
        }  
    }
    
    
    /**
     * this is static inner class which implements Comparator and used
     * for sorting houses according to its market value
     * @author mlj
     *
     */
    static class MarketPriceComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            HouseType p1 = (HouseType) object1; 
            HouseType p2 = (HouseType) object2;
            return new Double(p1.getMarketValue()).compareTo(new Double(p2.getMarketValue())); 
        }  
    }

    /**
     * this is static inner class which implements Comparator and used
     * for sorting houses according to its living area
     * @author mlj
     *
     */
    static class LivingAreaComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            HouseType p1 = (HouseType) object1; 
            HouseType p2 = (HouseType) object2;
            return new Double(p1.getLivingArea()).compareTo(new Double(p2.getLivingArea())); 
        }  
    }
    
    /**
     * this is static inner class which implements Comparator and used
     * for sorting houses according to its outdoor area
     * @author mlj
     *
     */
    static class OutdoorAreaComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            HouseType p1 = (HouseType) object1; 
            HouseType p2 = (HouseType) object2;
            return new Double(p1.getOutdoorArea()).compareTo(new Double(p2.getOutdoorArea())); 
        }  
    }
    
    /**
     * this is static inner class which implements Comparator and used
     * for sorting houses according to its build year
     * @author mlj
     *
     */
    static class BuildYearComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            HouseType p1 = (HouseType) object1; 
            HouseType p2 = (HouseType) object2;
            return new Integer(p1.getBuildYear()).compareTo(new Integer(p2.getBuildYear())); 
        }  
    }   
    
    
}
