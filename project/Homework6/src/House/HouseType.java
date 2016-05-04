package House;

/**
 * 
 * This is an abstract housetype class, which implements houses interface
 * and it gives attributes and some other methods to different houses. This
 * class should be extended by concrete house types as commercial, residential
 * and industrial.
 * @author Sophisitimate
 *
 */
public abstract class HouseType implements Houses{
    //identify fields
    protected String key;//unique key for each entry of house information 
    protected String type; //indicate the house type

    //house info
    protected String owner;//owner of the house
    protected String streetName;//street name of the house
    protected String houseNumber;//number of the house on the street
    protected String unit;//unit number for a certain house number
    protected double livingArea;//living area of the house
    protected String zipCode;//zip-code of the house
    protected int buildYear;//year of building 
    
    //price info
    protected double salePrice;//sale price
    protected String saleDate;//sale date
    protected double marketValue;//evaluate price of the house
    protected String marketDate;//evaluate date of market price
    
    //location
    protected Coordinate location;//latitude of the house(used for MAP API)

    /**
     * this method returns the key number of each house
     */
    public String getKey(){
        return key;
    }
    
    /**
     * this method returns the type of the house
     */
    public String getType(){
        return type;
    }

    /**
     *this method returns the owner 
     */
    public String getOwner(){
        return owner;
    }
    
    /**
     * this method gets the street name of the house
     */
    public String getStreetName(){
        return streetName;
    }

    /**
     * this method gets the house number on the street
     */
    public String getHouseNumber(){
        return houseNumber;
    }
    
    /**
     * this method returns the unit of the house if exist 
     */
    public String getUnit(){
        return unit;
    }

    /**
     * this method returns the living area of the house
     */
    public double getLivingArea(){
        return livingArea;
    }
    
    /**
     * this method returns the zip-code of the house
     */
    public String getZipCode(){
        return zipCode;
    }
    
    /**
     * this method returns the year of building
     */
    public int getBuildYear(){
        return buildYear;
    }
    
    /**
     * this method returns the selling price of the house
     */
    public double getSalePrice(){
        return salePrice;
    }
    
    /**
     * this method returns the sale date of the house
     */
    public String getSaleDate(){
        return saleDate;
    }
    
    /**
     * this method returns the market value of the house
     */
    public double getMarketValue(){
        return marketValue;
    }
    
    /**
     * this method returns the date for getting market value
     */
    public String getMarketDate(){
        return marketDate;
    }
    
    
    /**
     * this method returns the location of the house
     */
    public String getLocation(){
        return location.toString();
    }
    
    abstract public double getOutdoorArea();
    abstract public int getParking();
    abstract public int getExterior();
    abstract public int getHeatType();
    abstract public boolean getCentralAir();
    abstract public int getInterior();
    abstract public int getBedroom();
    abstract public int getBathroom();
    abstract public int getBasement();  
    
}
