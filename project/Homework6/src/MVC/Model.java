package MVC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import House.*;

public class Model {
	
	private ArrayList<HouseType> houseList;//house list filtered from the main page
	private static String houseType;//house type selected from main page
	private static int isAscendent;//flag that indicate the order of sorting

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
	 * this method returns house type
	 */
	public String getHouseType(){
		return houseType;
	}

	/*
	 * this method is called only when main page receive a new search, and stores the new house list here
	 */
	public void setHouseList(ArrayList<HouseType> house){
		houseList = house;
	}
	
	/*
	 * this method returns house list.
	 */
	public ArrayList<HouseType> getHouseList(){
		return houseList;
	}
	
	/*
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
			if(sp_high != -1 && temp.getSalePrice() < intToDouble){
				continue;
			}
			
			intToDouble = (double)mv_low;
			if(mv_low != -1 && temp.getMarketValue() < intToDouble){
				continue;
			}
			
			intToDouble = (double)mv_high;
			if(mv_high != -1 && temp.getMarketValue() < intToDouble){
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
