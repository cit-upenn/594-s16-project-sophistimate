package House;

import java.util.*;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

/**
 * This is a class used to lookup specific location, change it to longitude and latitude
 * This is a singelton class, since location is deterministic.
 * @author Sophisitimate 
 * @param <GeoApiContext>
 *
 */
public class LocationLookUpMap {
  //This is a map which stores the main location of phl, it will updated whenever user lookup a new location.
  private Map<String, Coordinate> localLookUp;
  private static LocationLookUpMap oneInstance = null;
//  GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyA37ATERCyi0vb1B80cXpJDavxwC7PUZ_k");;
  
  /**
   * This is singelton class used to check the longitude and latitude of a certain location
   */
  private LocationLookUpMap() {
    //input intialized map
    localLookUp = new HashMap<String, Coordinate>();
    localLookUp.put("city hall", new Coordinate("(39.9526238,-75.1634624)"));
    localLookUp.put("one liberty observation deck", new Coordinate("(39.9528022,-75.1700789)"));
    localLookUp.put("independence hall", new Coordinate("(39.948859,-75.1522245)"));
    localLookUp.put("museum of art", new Coordinate("(39.9487732,-75.1678601)"));
    localLookUp.put("liberty bell", new Coordinate("(39.9496103,-75.1524708)"));
    localLookUp.put("national constitution center", new Coordinate("(39.9538916,-75.1512543)"));
    localLookUp.put("second bank of the us", new Coordinate("(39.9485629,-75.1483565)"));
    localLookUp.put("independence visitor center", new Coordinate("(39.9510887,-75.1500623)"));
    localLookUp.put("love park", new Coordinate("(39.9542892,-75.1656568)"));
    localLookUp.put("the franklin institute", new Coordinate("(39.9582109,-75.1731347)"));
    localLookUp.put("greyhound terminal", new Coordinate("(39.9528635,-75.1570524)"));
    localLookUp.put("rocky steps", new Coordinate("(39.965343,-75.1806342)"));
    localLookUp.put("eniac", new Coordinate("(39.9520061,-75.1942719)"));
    localLookUp.put("university city", new Coordinate("(39.9502296,-75.2119406)"));
    localLookUp.put("university of pennsylvania", new Coordinate("(39.9522188,-75.1954024)"));
    localLookUp.put("drexel university", new Coordinate("(39.9566127,-75.1899441)"));
    localLookUp.put("30th street station", new Coordinate("(39.9557799,-75.1819683)"));
    localLookUp.put("philadelphia zoo", new Coordinate("(39.971463,-75.196309)"));
    localLookUp.put("eastern state penitentiary", new Coordinate("(39.9683364,-75.1726648)"));
    localLookUp.put("washington square", new Coordinate("(39.9470381,-75.1523959)"));
    localLookUp.put("chinatown", new Coordinate("(39.9553788,-75.1615154)"));
    localLookUp.put("first bank of the united states", new Coordinate("(39.948062,-75.1462587)"));
    localLookUp.put("penn's landing", new Coordinate("(39.9447677,-75.1496358)"));
    localLookUp.put("franklin field", new Coordinate("(39.9500391,-75.1899468)"));
    
  }
  
  /**
   * This is method to get singleton instance
   * @return singleton instance to search location
   */
  public synchronized static LocationLookUpMap getInstance () {
    if(oneInstance == null) {
      oneInstance = new LocationLookUpMap();
    }
    return oneInstance;
  }
  
  /**
   * Method used to return map stored in local
   * @return map which stored in local memory
   */
  public Map<String, Coordinate> getMap() {
    return localLookUp;
  }
  
  /**
   * This is method for public use
   * @param location natural language denotes certain location
   * @return Coordinate object
   */
  public Coordinate getCoordinate(String location) {
    Coordinate ret = localLookUp.get(location);
    if(ret == null) {
      String coord = getCoordinateByGoogle(location);
      if (coord != null) {
        try {
          ret = new Coordinate(coord);
        } catch (NumberFormatException e) {
          ret = null;
        }
      }       
    }
    return ret;
  }
  
  /**
   * This is method used to get the coordinates by google map.
   * @param location natural language to generate well format location info including longitude and latitude
   * @return well format location info including longitude and latitude
   */
  public String getCoordinateByGoogle(String location)
  {
    String ret = null;
    GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyA37ATERCyi0vb1B80cXpJDavxwC7PUZ_k");
    GeocodingResult[] results = null;
    try {
      results = GeocodingApi.geocode(context,location +", Philadelphia, PA").await();
      ret = "("+results[0].geometry.location.lat+","+results[0].geometry.location.lng+")";
      localLookUp.put(location, new Coordinate(ret));
    } catch (Exception e) {
      e.printStackTrace();
      ret = null;
    }   
    return ret;
  }
 
//  public static void main(String[] args) {
//    LocationLookUpMap a = LocationLookUpMap.getInstance();
//    
//    try {
//      System.out.println(a.getCoordinateByGoogle("M78 nebula"));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  } 
}
