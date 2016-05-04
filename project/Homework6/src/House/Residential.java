package House;

import java.util.HashMap;

/**
 * @author Sophisitimate
 * This class is sub class Residential to extend abstract class HouseType
 * 
 *
 */
public class Residential extends HouseType{
    private int parking;
    private int exterior;
    private int heatType;
    private boolean centralAir;
    private int interior;
    private int bedroom;
    private int bathroom;
    private int basement;
    
    /**
     * This is residential class used to convert map kay value pair to Residential instance
     * @param map source of data used to generate object
     */
    public Residential(HashMap<String, String> map){
        key = map.get("Parcel Number");
        type = map.get("Category Code Description");
        
        owner = map.get("Owner 1");
        streetName = map.get("Street Name");
        houseNumber = map.get("House Number");
        unit = map.get("Unit");
        livingArea = Double.parseDouble(map.get("Total Livable Area"));
        zipCode = map.get("Zip Code");
        buildYear = Integer.parseInt(map.get("Year Built"));
        
        salePrice = Double.parseDouble(map.get("Sale Price"));
        saleDate = map.get("Sale Date");
        marketValue = Double.parseDouble(map.get("Market Value"));
        marketDate = map.get("Market Value Date");
        
        location = new Coordinate(map.get("Coordinates"));
        parking = Integer.parseInt(map.get("parking"));
        exterior = Integer.parseInt(map.get("Exterior Condition"));
        heatType = Integer.parseInt(map.get("heatType"));
        centralAir = map.get("Central Air").equals("Y") ? true:false;
        interior = Integer.parseInt(map.get("Interior Condition"));
        bedroom = Integer.parseInt(map.get("Number of Bedrooms"));
        bathroom = Integer.parseInt(map.get("Number of Bathrooms"));
        basement = Integer.parseInt(map.get("Basements"));

    }
    

    /**
     * this method gets the parking coefficient
     */
    public int getParking(){
        return parking;
    }

    /**
     * this method gets the exterior quality
     */
    public int getExterior(){
        return exterior;
    }

    /**
     * this method gets the heat type
     */
    public int getHeatType(){
        return heatType;
    }

    /**
     * this method gets whether there is central air condition
     */
    public boolean getCentralAir(){
        return centralAir;
    }

    /**
     * this method gets interior quality
     */
    public int getInterior(){
        return interior;
    }
    
    /**
     * this method gets number of bedroom
     */
    public int getBedroom(){
        return bedroom;
    }
    
    /**
     * this method gets number of bedroom
     */
    public int getBathroom(){
        return bathroom;
    }
    
    /**
     * this method gets whether there is basement
     */
    public int getBasement(){
        return basement;
    }


    @Override
    public double getOutdoorArea() {
        return 0;
    }
}
