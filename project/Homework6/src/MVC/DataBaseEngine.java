package MVC;
import org.bson.Document;
import GUIPage.BuyHomePage.HouseOption;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import House.Commercial;
import House.Coordinate;
import House.HouseType;
import House.Industrial;
import House.LocationLookUpMap;
import House.Residential;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.*;

import org.bson.Document;

/**
 * This is class used to fetch data from cloud server.
 * @author Sophisitimate
 *
 */
public class DataBaseEngine {
  private MongoClient mongoClient;
  private MongoDatabase db;
  private FindIterable<Document> iterable;
  private final String databaseName = "cit594";
  private final String collectionName = "proj";
//  private final String URIaddress = "mongodb://vagvlan536.0561.wlan.asc.upenn.edu:27000";
  private final String URIaddress = "mongodb://52.22.212.86:27017";
  
  /**
   * This is constructor for database engine
   */
  public DataBaseEngine() {
    mongoClient = new MongoClient(new MongoClientURI(URIaddress));
//    mongoClient = new MongoClient("localhost",27017);
    db = mongoClient.getDatabase(databaseName);
  }
  
  /**
   * This is method used to communicate database, to query and get result
   * @param location This is location keyword we pass into method, and get result based on location
   */
  public List<HouseType> getResultByLocation(String location, HouseOption type) {
    List<HouseType> product = new ArrayList<>();
    //and(eq("Street Name", "WALNUT"), eq("Street Designation", "ST"), eq("Census Tract",7)
    try {
      int x = Integer.parseInt(location);
      //to see how many digits in there
      int digits = (int) Math.log10(x) + 1;
      if (Double.isNaN(Math.log10(x))) {
        throw new NumberFormatException();
      }
      //create uplimit for search
      int uplimit = (int) ((x+1) * Math.pow(10,9-digits));
      //create downlimit for search
      int downlimit = (int) (x * Math.pow(10,9-digits));
      iterable = db.getCollection(collectionName).find( com.mongodb.client.model.Filters.or(
          and(com.mongodb.client.model.Filters.gte("Zip Code", downlimit), com.mongodb.client.model.Filters.lte("Zip Code", uplimit)),
          eq("Zip Code", downlimit/10000)
          ));
      
      iterable.forEach(new Block<Document>() {
        @Override
        public void apply(final Document document) {
          if(document.getString("Category Code Description").equals(type.toString())){
            product.add(HouseConstructor(AttribMap(document)));  //.get("Coordinates")    
          }            
        }
      });
      if(product.isEmpty()) {
        throw new NumberFormatException();
      }
    } catch (NumberFormatException e) {
      LocationLookUpMap localMap = LocationLookUpMap.getInstance();
      Coordinate crn = localMap.getCoordinate(location);
      //debug
      System.out.println("center:"+crn);
      
      if (crn == null) {
        return null;
      }
      double upLat = crn.getLatitude()+0.005;
      double downLat = crn.getLatitude()-0.005;
      double upLnt = crn.getLongitude()+0.005;
      double downLnt = crn.getLongitude()-0.005;
      
      //TO DO improved by indexing search
      iterable = db.getCollection(collectionName).find(and(and(com.mongodb.client.model.Filters.gte("Latitude", downLat), com.mongodb.client.model.Filters.lte("Latitude", upLat)), and(com.mongodb.client.model.Filters.gte("Longitude", downLnt), com.mongodb.client.model.Filters.lte("Longitude", upLnt))));
      PriorityQueue<HouseType> resultQueue = new PriorityQueue<HouseType>(30, new Comparator<HouseType>() {
        @Override
        public int compare(HouseType o1, HouseType o2) {
          double d1 = crn.distance(new Coordinate("("+o1.getLocation()+")"));
          double d2 = crn.distance(new Coordinate("("+o2.getLocation()+")"));
          if(d1 > d2) {
            return 1;
          }
          if(d1 < d2) {
            return -1;
          }
          return 0;
        }       
      });
      iterable.forEach(new Block<Document>() {
        @Override
        public void apply(final Document document) { 
          if(crn.distance(new Coordinate(document.getString("Coordinates"))) <= 1000 && document.getString("Category Code Description").equals(type.toString())) {        
            resultQueue.add(HouseConstructor(AttribMap(document)));
          }        
        }
      });
      int count = 0;
      while(!resultQueue.isEmpty() && count < 30) {
        product.add(resultQueue.poll());
        count ++;
      }
      if(!resultQueue.isEmpty()) {
        product.addAll(resultQueue);
      }   
    } 
    return product;
  }
  
  /**
   * This is a constructor class for a general HouseType of different classes
   * @param map para used to generate all attribs of a housetype
   * @return certain house type based on map we passing in.
   */
  private HouseType HouseConstructor(HashMap<String,String> map) {
    HouseType ret = null;
    String code = map.get("Category Code");
    if(code.equals("1")) {
      ret = new Residential(map);
    } else if (code.equals("4") || code.equals("2")) {
      ret = new Commercial(map);
    } else if (code.equals("5")) {
      ret = new Industrial(map);
    }
    return ret;
  }
  
  /**
   * This is for parsing the object got from database, since document.getString() not work for Integer or Double object.
   * @param This object is passed by Mongodb document object 
   * @return Well format String parsed by given object
   */
  private String parseObject(Object o) {
    String ret = null;
    if(o instanceof String) {
      ret = (String)o;
      if(ret.length() == 0) {
        ret = "0";
      }
      if(ret.charAt(0) == '$') {
        ret = ret.replaceAll(",", "");
        ret = ret.substring(1);
      }
      
    } else if(o instanceof Integer) {
      ret = o.toString();
    } else if(o instanceof Double) {
      ret = o.toString();
    }   
    return ret;
  }
  
  /**
   * This is for parsing the object got from database, since document.get() will return corresponding object.
   * @param o This object is passed by Mongodb document object.
   * @return Well format String parsed by given object
   */
  private String parseHeat(Object o) {
    String ret = null;
    if(o instanceof String) {
      ret = (String)o;
      if(ret.length() == 0) {
        ret = "0";
      } else {
        ret = Integer.toString('H' - ret.charAt(0));
      }
    } else {
      ret = "0";
    }
    return ret;  
  }
  
  /**
   * This is for parsing the information about garage and offstreet parking slots got from database, 
   * @param garage parking slots from garage data
   * @param offSt parking slots from offstreet parking data
   * @return well format parking slots string
   */
  private String parseParking(Object garage, Object offSt) {
    int gag = 0;
    int off = 0;
    if(garage instanceof String) {
      gag = 0;
    } else {
      gag = (Integer)garage;
    }
    if(offSt instanceof String) {
      off = 0;
    } else {
      off = (Integer)offSt;
    }
    return Integer.toString(gag+off); 
  }
  
  /**
   * This is for parsing the basement data got from database.
   * @param o object used to to be parsed to generate info about basement info
   * @return well format return message 
   */
  private String parseBasement(Object o) {
    String ret = null;
    if(o instanceof String) {
      if(((String)o).length() == 0) {
        ret = "0";
      } else {
        ret = Integer.toString('J' - ((String)o).charAt(0));
      }    
    } else if (o instanceof Integer) {
        ret = "0";
    }
    return ret;
  }
  
  /**
   * This is for parsing the central air data got from database.
   * @param o object used to to be parsed to generate info about central air info
   * @return well format info about central air info
   */
  private String parseCentralAir(Object o) {
    String ret = null;
    if(o instanceof String) {
      if(((String)o).length() == 0) {
        ret = "N";
      } else {
        ret = (String)o;
      }
    } else {
      ret = "N";
    }
    return ret;
  }
  
  /**
   * This is private method used to generate
   * @param document The mongodb document used to generate Map  
   * @return map which stores key value pair
   */
  private HashMap<String, String> AttribMap(final Document document) {
    HashMap<String,String> map = new HashMap<>();
    map.put("Parcel Number", parseObject(document.get("Parcel Number")));
    map.put("Category Code Description", document.getString("Category Code Description"));
    map.put("Owner 1", document.getString("Owner 1"));
    map.put("Street Name", document.getString("Street Name"));
    map.put("Unit", parseObject(document.get("Unit")));
    map.put("Total Livable Area", parseObject(document.get("Total Livable Area")));
    map.put("Zip Code", parseObject(document.get("Zip Code")));
    map.put("House Number", parseObject(document.get("House Number")));
    map.put("Year Built", document.getInteger("Year Built").toString());
    map.put("Sale Price", parseObject(document.get("Sale Price")));
    map.put("Sale Date", document.getString("Sale Date"));
    map.put("Market Value", parseObject(document.get("Market Value")));
    map.put("Market Value Date", document.getString("Market Value Date"));
    map.put("Coordinates", document.getString("Coordinates"));
    map.put("Total Area", parseObject(document.get("Total Area")));
    map.put("Category Code", parseObject(document.get("Category Code")));     
    map.put("Basements", parseBasement(document.get("Basements")));     
    map.put("parking", parseParking(document.get("Garage Spaces"), document.get("Off Street Open")));
    map.put("Exterior Condition", parseObject(document.getInteger("Exterior Condition")));
    map.put("heatType", parseHeat(document.get("Type Heater")));
    map.put("Central Air", parseCentralAir(document.get("Central Air")));   
    map.put("Interior Condition", parseObject(document.get("Interior Condition")));
    map.put("Number of Bedrooms", parseObject(document.get("Number of Bedrooms")));
    map.put("Number of Bathrooms", parseObject(document.get("Number of Bathrooms")));  
    map.put("Number of Rooms", parseObject(document.get("Number of Rooms")));
    
    return map;     
  }

  /**
   * This is method for selling page call that could insert data into database
   * @param map para that used to generate key value pair
   * @return ture if everything is fine
   */
  public boolean insertData(Map<String, String> map) {
    Document doc = new Document();
    for (String key:map.keySet()) {
      doc.append(key, map.get(key));
    }
    db.getCollection("test").insertOne(doc); 
    return true;
  }
  
  //debug
//  public static void main(String[] args) {
//    DataBaseEngine db = new DataBaseEngine();
//
//    List<HouseType> ret = db.getResultByLocation("WALNUT", HouseOption.RESIDENTIAL);
//    System.out.println(ret.size());
//    for(HouseType house: ret) {
//      System.out.println(house.getStreetName()+" "+house.getHouseNumber()+" "+house.getUnit()+" "+house.getZipCode());      
//    }  
//    
//  }
  
}
