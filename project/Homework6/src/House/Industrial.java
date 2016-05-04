package House;

import java.util.HashMap;

/**
 * This class is Industrial type property extends HouseType
 * @author Sophisitimate 
 *
 */
public class Industrial extends HouseType{
    private double outdoorArea;
    private int basement;
    private double parking;

    /**
     * This is Industrial class used to convert map kay value pair to Industrial instance
     * @param map source of data used to generate object
     */
    public Industrial(HashMap<String, String> map){
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
        outdoorArea = Double.parseDouble(map.get("Total Area"));
        basement = Integer.parseInt(map.get("Basements"));
        parking = Double.parseDouble(map.get("parking"));
    }

    /**
     * this method gets the outdoor area
     */
    public double getOutdoorArea(){
        return outdoorArea;
    }
    
    /**
     * this method gets the basement information
     */
    public int getBasement(){
        return basement;
    }

    /**
     * this method gets the parking coefficient
     */
    public double parking(){
        return parking;
    }

    @Override
    public int getParking() {
        return 0;
    }

    @Override
    public int getExterior() {
        return 0;
    }

    @Override
    public int getHeatType() {
        return 0;
    }

    @Override
    public boolean getCentralAir() {
        return false;
    }

    @Override
    public int getInterior() {
        return 0;
    }

    @Override
    public int getBedroom() {
        return 0;
    }

    @Override
    public int getBathroom() {
        return 0;
    }
}
