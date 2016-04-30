package GUIPage;

import java.awt.BorderLayout;
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

public class DetailPage extends JFrame{
	private BufferedImage img;
	private JLabel detailLabel;
	private JLabel titleLabel;
	private Browser browser;
	private BrowserView browserView;
	private Browser streetBrowser;
	private BrowserView streetView;
	private HouseType currentHouse;
	
	public DetailPage(HouseType house){
		currentHouse = house;
		this.setLayout(new BorderLayout());
		JPanel bottomPart = new JPanel();
		bottomPart.setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, googleMapPane(), bottomPart);
		splitPane.setDividerLocation(550);
		splitPane.setEnabled( false );  /* The splitPane cannot be resized */
		this.add(splitPane, BorderLayout.CENTER);
//		currentHouse = house;
		
		try{
			img = ImageIO.read(new File("Nice-Green-Home-Wallpaper-HD.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel picture = new JLabel(new ImageIcon(img));
		JPanel streetScape = new JPanel();
		streetScape.add(picture);
		streetScape.setPreferredSize(new Dimension(300, 300));
		
		bottomPart.add(streetScape, BorderLayout.NORTH);	
		
		detailLabel = new JLabel("This is the area for detailed information");
		titleLabel = new JLabel("This is the area for title information");
		detailLabel.setPreferredSize(new Dimension(500, 280));
		bottomPart.add(titleLabel, BorderLayout.CENTER);
		bottomPart.add(detailLabel, BorderLayout.SOUTH);
		
		setLabelText();
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 800);
		setVisible(true);
		setLocationRelativeTo(null);
		System.out.println(currentHouse.getType());
	}

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
//		String location = currentHouse.getLocation();		
//		browser.loadURL("https://maps.googleapis.com/maps/api/streetview?size=600x300&location="
//				+ location + "&heading=151.78&pitch=-0.76&key=AIzaSyA14H3OSxJgCSUIlXcppWjb3P_2Qi6Hitc");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String location = currentHouse.getLocation();
        browser.executeJavaScript("var myLatLng = new google.maps.LatLng(" + location + ");\n"
	            + "var marker = new google.maps.Marker({\n"+
	            "position: myLatLng,\n"+
	            "map: map\n,"+	            
	          "});\n"+
	          "marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');\n");
		
		return pane;
	}
	
	public void setLabelText(){
		StringBuilder title = new StringBuilder();
		StringBuilder details = new StringBuilder();
		titleLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		title.append("<html>");
		title.append("<p style=\"line-height:50%\"><b><h2>" + currentHouse.getHouseNumber() + " " + currentHouse.getStreetName() + "</h2></b><br>");
		title.append("<h3>Philadelphia, PA " + currentHouse.getZipCode() + "</h3><br>");
		title.append("<h3>Type: " + currentHouse.getType().toString() + "</h3></p></html>");
		
		titleLabel.setText(title.toString());
		detailLabel.setText(details.toString());
		
		if ( currentHouse.getType().equals( HouseOption.RESIDENTIAL.toString() ) ){
			
		}
		
		if ( currentHouse.getType().equals( HouseOption.COMMERCIAL.toString() ) ){
			
		}
		
		if ( currentHouse.getType().equals( HouseOption.INDUSTRIAL.toString() ) ){
			
		}
	}
	
//	public static void main(String[] args) {
//		DetailPage dp = new DetailPage();
//
//	}

}
