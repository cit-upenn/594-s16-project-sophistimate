package House;

/**
 * this interface define the houses, which should be 
 * implemented by housetypes. The following method provides
 * basic get method for certain attributes and will be
 * implemented by housetype abstract class
 * @author mlj
 *
 */
public interface Houses {
	
	public String getKey();
	public String getType();
	public String getOwner();
	public String getStreetName();
	public String getHouseNumber();
	public String getUnit();
	public double getLivingArea();
	public String getZipCode();
	public int getBuildYear();
	public double getSalePrice();
	public String getSaleDate();
	public double getMarketValue();
	public String getMarketDate();
	public String getLocation();
}
