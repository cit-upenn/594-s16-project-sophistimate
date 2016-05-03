package GUIPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import GUIPage.BuyHomePage.HouseOption;
import House.HouseType;

/**
 * This is class to show the detail page for each property
 * @author Sophisimate
 *
 */
public class DetailPage extends JFrame{
	private BufferedImage img;
	private JLabel detailLabel;
	private JLabel titleLabel;
	private Browser browser;
	private BrowserView browserView;
	private Browser streetBrowser;
	private BrowserView streetView;
	private HouseType currentHouse;
	private String location;
	
	/**
	 * This is constructor for detail page of homepage
	 * @param house This is parameter for house type, used to generate detail page
	 */
	public DetailPage(HouseType house){
		currentHouse = house;
        location = currentHouse.getLocation();
		this.setLayout(new BorderLayout());
		JPanel bottomPart = new JPanel();
		bottomPart.setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, googleMapPane(), bottomPart);
		splitPane.setDividerLocation(550);
		splitPane.setEnabled( false );  /* The splitPane cannot be resized */
		this.add(splitPane, BorderLayout.CENTER);

	    streetBrowser = new Browser();
	    streetView = new BrowserView(streetBrowser);		
		JPanel streetScape = new JPanel();
		streetScape.setLayout(new BorderLayout());
        streetView.setPreferredSize(new Dimension(700, 700));
		streetScape.add(streetView.getComponent(0), BorderLayout.CENTER);
		streetScape.setPreferredSize(new Dimension(300, 270));
		streetBrowser.loadURL("https://maps.googleapis.com/maps/api/streetview?size=600x300&location="
      + location + "&heading=151.78&pitch=-0.76&key=AIzaSyA14H3OSxJgCSUIlXcppWjb3P_2Qi6Hitc");
		
		bottomPart.add(streetScape, BorderLayout.NORTH);	
		
		detailLabel = new JLabel("This is the area for detailed information");
		titleLabel = new JLabel("This is the area for title information");
		detailLabel.setPreferredSize(new Dimension(500, 280));
//		detailLabel.setBackground(Color.blue);
		
		bottomPart.add(titleLabel, BorderLayout.CENTER);
		bottomPart.add(detailLabel, BorderLayout.SOUTH);
		
		bottomPart.setPreferredSize(new Dimension(500, 1200));
//		bottomPart.setBackground(Color.red);
		
		setLabelText();
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 800);
		setVisible(true);
		setLocationRelativeTo(null);
		System.out.println(currentHouse.getType());
		this.setTitle("Real Estate Details");
		this.setResizable(false);
	}

    /**
     * This method used to generate googleMap panel 
     * @return The Jpanel we want to display
     */
    public JPanel googleMapPane(){
        JPanel pane = new JPanel();
        browser = new Browser();
        browserView = new BrowserView(browser);
        pane.setLayout(new BorderLayout());
        pane.setSize(new Dimension(550, 500));
        browserView.setSize(new Dimension(550, 500));
        pane.add(browserView.getComponent(0), BorderLayout.CENTER);
        
        
        File file = new File("");
        String path = file.getAbsolutePath();
        browser.loadURL("file://" + path + "/map.html");     
//      browser.loadURL("https://maps.googleapis.com/maps/api/streetview?size=600x300&location="
//              + location + "&heading=151.78&pitch=-0.76&key=AIzaSyA14H3OSxJgCSUIlXcppWjb3P_2Qi6Hitc");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        browser.executeJavaScript("var myLatLng = new google.maps.LatLng(" + location + ");\n"
	            + "var marker = new google.maps.Marker({\n"+
	            "position: myLatLng,\n"+
	            "map: map\n,"+	            
	          "});\n"+
	          "marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');\n");
		
		return pane;
	}
	
	/**
	 * This is method used to set Label text field to show details of property.
	 */
	public void setLabelText(){
		StringBuilder title = new StringBuilder();
		StringBuilder details = new StringBuilder();
		titleLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		title.append("<html>");
		title.append("<p><b><big>" + currentHouse.getHouseNumber() + " " + currentHouse.getStreetName() + "<big></b><br>");
		title.append("Philadelphia, PA " + currentHouse.getZipCode().substring(0, 5) + "-" + currentHouse.getZipCode().substring(5) + "<br>");
		title.append("Type: " + currentHouse.getType().toString() + "</p></html>");
		
		titleLabel.setText(title.toString());
		
		details.append("<html>");
		
		String whiteSpace = "";
		for( int i = 0; i < 80; i++){
			whiteSpace += "&nbsp";
		}
		
		if ( currentHouse.getType().equals( HouseOption.RESIDENTIAL.toString() ) ){
			details.append("-Owner: " + currentHouse.getOwner() + "&#9"  + (currentHouse.getOwner().length() < 12?"&#9":"") + "-Build Year: " + currentHouse.getBuildYear() + "<br>");
			details.append("-Street: " + currentHouse.getStreetName() + "&#9;&#9;" + "-House Number: " + currentHouse.getHouseNumber() + "<br>");
			details.append("-Unit " + currentHouse.getUnit() + "&#9;&#9;&#9;" + "-Living Area(ft²): " + currentHouse.getLivingArea() + "<br>");
			details.append("-Sales Price($): " + currentHouse.getSalePrice() + "&#9" + "-Sale Date: " + currentHouse.getSaleDate() + "<br>");
			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");
			details.append("-ParkingLots: " + currentHouse.getParking() + "&#9;&#9;" + "-Central Air: " + (currentHouse.getCentralAir()?"Yes":"No") + "<br>");
			details.append("-BedRoom : " + currentHouse.getBedroom() + "&#9;&#9;" + "-BathRoom: " + currentHouse.getBathroom() + "<br>");
			details.append("-Basement: " + currentHouse.getBasement() + "&#9;&#9;" + "-Interior Rating: " + currentHouse.getInterior() + "<br>");
//			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");

			details.append(" " + whiteSpace + "&#9" + whiteSpace + "<br>");
		}
		
		if ( currentHouse.getType().equals( HouseOption.COMMERCIAL.toString() ) ){
			details.append("-Owner: " + currentHouse.getOwner() + "&#9"  + (currentHouse.getOwner().length() < 12?"&#9":"") + "-Build Year: " + currentHouse.getBuildYear() + "<br>");
			details.append("-Street: " + currentHouse.getStreetName() + "&#9;&#9;" + "-House Number: " + currentHouse.getHouseNumber() + "<br>");
			details.append("-Unit " + currentHouse.getUnit() + "&#9;&#9;&#9;" + "-Living Area(ft²): " + currentHouse.getLivingArea() + "<br>");
			details.append("-Sales Price($): " + currentHouse.getSalePrice() + "&#9" + "-Sale Date: " + currentHouse.getSaleDate() + "<br>");
			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");
			details.append("-ParkingLots: " + currentHouse.getParking() + "&#9;&#9;" + "-Central Air: " + (currentHouse.getCentralAir()?"Yes":"No") + "<br>");
			details.append("-Outdoor Area(ft²) : " + currentHouse.getOutdoorArea() + "&#9;" + "-Heat: " + (currentHouse.getHeatType() == 0?"No": "Yes") + "<br>");
			details.append("-Exterior Rating: " + currentHouse.getExterior() + "&#9;&#9;" + "-Interior Rating: " + currentHouse.getInterior() + "<br>");
//			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");

			details.append(" " + whiteSpace + "&#9" + whiteSpace + "<br>");
		}
		
		if ( currentHouse.getType().equals( HouseOption.INDUSTRIAL.toString() ) ){
			details.append("-Owner: " + currentHouse.getOwner() + "&#9"  + (currentHouse.getOwner().length() < 12?"&#9":"") + "-Build Year: " + currentHouse.getBuildYear() + "<br>");
			details.append("-Street: " + currentHouse.getStreetName() + "&#9;&#9;" + "-House Number: " + currentHouse.getHouseNumber() + "<br>");
			details.append("-Unit " + currentHouse.getUnit() + "&#9;&#9;&#9;" + "-Living Area(ft²): " + currentHouse.getLivingArea() + "<br>");
			details.append("-Sales Price($): " + currentHouse.getSalePrice() + "&#9" + "-Sale Date: " + currentHouse.getSaleDate() + "<br>");
			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");
			details.append("-ParkingLots: " + currentHouse.getParking() + "&#9;&#9;" + "-Outdoor Area(ft²) : " + currentHouse.getOutdoorArea() + "<br>");
			details.append("-Basement: " + currentHouse.getBasement() + "&#9;&#9;" + " " + "<br>");
//			details.append("-Market Value($): " + currentHouse.getMarketValue() + "&#9" + "-Market Date: " + currentHouse.getMarketDate() + "<br>");

			details.append(" " + whiteSpace + "&#9" + whiteSpace + "<br>");
		}
		details.append("</html>");
		detailLabel.setText(details.toString());
	}
	
//	public static void main(String[] args) {
//		DetailPage dp = new DetailPage(new Industrial());
//
//  }

}
