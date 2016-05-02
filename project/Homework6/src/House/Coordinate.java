package House;

/**
 * This class used to define coordinate 
 * @author Sophisitimate
 *
 */
public class Coordinate {
      private double latitude;
      private double longitude;
      private static LocationLookUpMap localLookUp;
      
      
      /**
       * This is constructor of Coordinate 
       * @param location The location used to construct Coordinate
       * @throws NumberFormatException throw exception when format is wrong
       */
      public Coordinate(String location) throws NumberFormatException{
          
        String[] coord = location.split(",");
        //May be improved by RegEx in the future, TO DO
        if(location.indexOf('(') == 0 && location.indexOf(')') == location.length()-1 && coord.length == 2) {
            latitude = Double.parseDouble(coord[0].substring(1));
            longitude = Double.parseDouble(coord[1].substring(0,coord[1].length()-2));
            if(latitude < 39.8806293647618 || latitude > 40.1376079548888 /*|| longitude > -74.9587571741226 || longitude < -75.2748107615542*/) {
                throw new NumberFormatException("Unknown location!"+ "  " + latitude + ", " +longitude);
            }     
        } else {
            localLookUp = LocationLookUpMap.getInstance();
            Coordinate coor = localLookUp.getMap().get(location.toUpperCase());
            latitude = coor.getLatitude();
            longitude = coor.getLongitude();     
        }
      }
      
      /**
       * This getter class to return Latitude of property
       * @return Latitude
       */
      public double getLatitude() {
        return latitude;
      }
      
      /**
       * This getter class to return Longitude of property
       * @return Longitude
       */
      public double getLongitude() {
        return longitude;
      }
      
      /**
       * This is the implementation of Haversine method in Java.
       * @param location
       * @param coordinates
       * @return
       */
      public static double distance(Coordinate center, Coordinate dest) {
        //This part will be improved by using 
        //https://developers.google.com/maps/documentation/geocoding/intro
        double lat1 = center.getLatitude();
        double lat2 = dest.getLatitude();
        double lon1 = center.getLongitude();
        double lon2 = dest.getLongitude();
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
      } 
      
      /**
       * This is the implementation of Haversine method in Java.
       * @param dest gives the Coordinate object of destination 
       * @return the distance from this Coordinate to destination Coordinate
       */
      public double distance(Coordinate dest) {
        //This part will be improved by using 
        //https://developers.google.com/maps/documentation/geocoding/intro
        double lat1 = latitude;
        double lat2 = dest.getLatitude();
        double lon1 = longitude;
        double lon2 = dest.getLongitude();
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
      }   

      @Override
      public String toString() {
        return latitude+","+longitude;
      }
      
    }

