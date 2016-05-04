package GUIPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import GUIPage.BuyHomePage.HouseOption;
import House.HouseType;
import House.LocationLookUpMap;
import MVC.DataBaseEngine;
import MVC.Model;

/**
 * 
 * This is class used to show sell page result page
 * @author Sophisitimate
 *
 */
public class SellPage extends JFrame{
	private BufferedImage img;
	private JLabel background;
	private Model model;
	private JTextField firstName;
	private JTextField lastName;
	
	private JTextField location;
	
	private JTextField streetName;
	private JTextField houseNumber;
	private JTextField unit;
	
	private JTextField livingArea;
	private JTextField outdoorArea;
	private JTextField zipCode;
	private JTextField buildYear;
	private JComboBox parking;
	private JComboBox basement;
	private JComboBox centralAir;
	private JComboBox heat;
	private JComboBox type;
	private JButton submit;
	private JPanel textHolder;
	
	private ProgressBar pbarFrame;
//	private ResultListingPage resultListing;
	private EstimateListingPage resultListing;
	
	private HouseOption type1;
	
	/**
	 * This is SellPage constructor which set up the layout
	 * and size of the panel
	 */
	public SellPage(){
		try{
			img = ImageIO.read(new File("niceNight.jpg"));
		} catch(Exception e){
			e.printStackTrace();
		}
		init();
		background = new JLabel(new ImageIcon(img));			// Residential, Commercial, Industrial
		this.setContentPane(background);
        this.setLayout(new GridBagLayout());
		textHolder = new JPanel();
		textHolder.setOpaque(false);
		JLabel hint = new JLabel("How Much Is My House Worth?");
		hint.setFont(new Font("Verdana", Font.PLAIN, 28));
		textHolder.add(hint);
		
		addToPane();
		
		this.add(textHolder);
		this.pack();
	
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		
		addActionListeners();
		this.setTitle("Survey Page");
	}
	
	/**
	 * This is init method used to initialize class varibles
	 */
	public void init(){
		firstName = new JTextField();
		firstName.setPreferredSize(new Dimension(150, 30));
		lastName = new JTextField();
		lastName.setPreferredSize(new Dimension(180, 30));
		location = new JTextField();
		location.setPreferredSize(new Dimension(380, 30));
		streetName = new JTextField();
		streetName.setPreferredSize(new Dimension(150, 30));
		houseNumber = new JTextField();
		houseNumber.setPreferredSize(new Dimension(150, 30));
		unit = new JTextField();
		unit.setPreferredSize(new Dimension(153, 30));
		livingArea = new JTextField();
		livingArea.setPreferredSize(new Dimension(130, 30));
		zipCode = new JTextField();
		zipCode.setPreferredSize(new Dimension(192, 30));
		outdoorArea = new JTextField();
		outdoorArea.setPreferredSize(new Dimension(140, 30));
		buildYear = new JTextField();
		buildYear.setPreferredSize(new Dimension(140, 30));
		parking = new JComboBox<String>(new String[] {"Yes", "No"});
		basement = new JComboBox<String>(new String[] {"Yes", "No"});
		centralAir = new JComboBox<String>(new String[] {"Yes", "No"});
		heat = new JComboBox<String>(new String[] {"Yes", "No"});
		type = new JComboBox<String> (new String[] {"Residential", "Commercial", "Industrial"});
		type.setPreferredSize(new Dimension(150, 30));
		submit = new JButton("Request Consultation");
		submit.setPreferredSize(new Dimension(150, 40));
	}
	
	/**
	 * This is method used to fill textholder with content
	 */
	public void addToPane(){
		textHolder.add(new JLabel("  First Name:"));
		textHolder.add(firstName);
		textHolder.add(new JLabel("Last Name:"));
		textHolder.add(lastName);
		textHolder.add(new JLabel("Street Name:"));				
		textHolder.add(streetName);
		textHolder.add(new JLabel("House Number:"));				
		textHolder.add(houseNumber);
		textHolder.add(new JLabel("           Unit:"));				
		textHolder.add(unit);
		textHolder.add(new JLabel("Zip Code:"));
		textHolder.add(zipCode);
		textHolder.add(new JLabel("Living Area(ft²):"));
		textHolder.add(livingArea);
		textHolder.add(new JLabel("Outdoor Area(ft²):"));
		textHolder.add(outdoorArea);
		textHolder.add(new JLabel("    Built Year:"));
		textHolder.add(buildYear);
		textHolder.add(new JLabel("                             Parking:"));
		textHolder.add(parking);
		textHolder.add(new JLabel("         Basement:"));
		textHolder.add(basement);
		textHolder.add(new JLabel(" Central Air:"));
		textHolder.add(centralAir);
		textHolder.add(new JLabel("   Heat:"));
		textHolder.add(heat);
		JLabel houseTypeHint = new JLabel("      House Type:");
		houseTypeHint.setForeground(Color.white);
		textHolder.add(houseTypeHint);
		textHolder.add(type);
		textHolder.add(new JLabel("                 "));
		textHolder.add(submit);
		textHolder.setPreferredSize(new Dimension(500, 350));
	}
	
	 /**
	 * This method add action listener to the submit button
	 * of the sell page. It should set the model, communicate
	 * with database and show the resulting page.  
	 */
	public void addActionListeners(){
		 submit.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	if( firstName.getText().trim().length() == 0 || lastName.getText().trim().length() == 0 ||
	            			streetName.getText().trim().length() == 0 || houseNumber.getText().trim().length() == 0 || 
	            			unit.getText().trim().length() == 0 || livingArea.getText().trim().length() == 0 ||
	            			outdoorArea.getText().trim().length() == 0 || zipCode.getText().trim().length() == 0 ||
	            			buildYear.getText().trim().length() == 0){
	            		JOptionPane.showMessageDialog(getContentPane(), "Please complete all the information!! Enter 0 for inapplicable blanks!!", 
	                            "Error Message", JOptionPane.INFORMATION_MESSAGE);
	            	} else {
	            		String[] label = {"House Number", "Unit", "Living Area", "Outdoor Area", "Built Year", "Zip Code"};
	            		int houseNum;
	            		int unitNum;
	            		double living;
	            		double outdoor;
	            		int build;
	            		int zip;
	            		int count = 0;
	            		try{
	            			houseNum = new Integer(houseNumber.getText().trim());
	            			count++;
	            			unitNum = new Integer(unit.getText().trim());
	            			count++;
	            			living = new Double(livingArea.getText().trim());
	            			count++;
	            			outdoor = new Double(outdoorArea.getText().trim());
	            			count++;
	            			build = new Integer(buildYear.getText().trim());
	            			count++;
	            			zip = new Integer(zipCode.getText().trim());
	            		} catch (NumberFormatException error){
	            			JOptionPane.showMessageDialog(getContentPane(), "Please enter only numbers for " + label[count], 
		                            "Input Format Error Message", JOptionPane.INFORMATION_MESSAGE);
	            			return ;   // finish the event when the Message Dialog has been displayed.
	            		}
	            		
	            		String typeString = type.getSelectedItem().toString();
	            		if(typeString.equals("Residential")){
	            			type1 = HouseOption.RESIDENTIAL;
	            		}else if(typeString.equals("Commercial")){
	            			type1 = HouseOption.COMMERCIAL;
	            		}else if(typeString.equals("Industrial")){
	            			type1 = HouseOption.INDUSTRIAL;
	            		}
	            		
	                    if (pbarFrame != null)
	                        pbarFrame.dispose();

	                      pbarFrame = new ProgressBar();
	                      swingWorker(typeString, build, living);
	            		
	            		
	            	}
	            }
		 });
	 }
	 
	/**
	 * This method is called when user submit the basic information of the house
	 * and communicate with database mainly in this method where swing worker is used.
	 * @param typeString gives the type of the house
	 * @param build gives the build year of the house
	 * @param living tells the living area of the house
	 */
	public void swingWorker(String typeString, int build, double living) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() {
				/* Related real estate list & estimated price */
//				String location = streetName.getText().trim() + houseNumber.getText().trim() + unit.getText().trim();
				String location = houseNumber.getText().trim() + " " + streetName.getText().trim();
				DataBaseEngine db = new DataBaseEngine();// connect the database
				List<HouseType> result = db.getResultByLocation(location, type1);
				String latLon = LocationLookUpMap.getInstance().getCoordinate(location).toString();
				model = new Model();
				model.setHouseList((ArrayList<HouseType>) result);
				model.setHouseType(typeString);
				ArrayList<HouseType> filteredHouses = model.filteringSellHouse(build, living);
				double estimatedPrice = model.getPrice(filteredHouses);
				model.setHouseList(filteredHouses);
				
				pbarFrame.stop();
				
				Map<String, String> map = mapCommunication(latLon);
				
//				showPage((ArrayList<HouseType>) result, map);
				showPage((ArrayList<HouseType>) filteredHouses, map, estimatedPrice);
			
				/* Ready to insert into database, which actually occurs in EstimateListingPage */ 
				/* The values in map are all strings. Absent values becomes empty string "" */
				
				return null;
			}
		};

		worker.execute();

	}
	
	/**
	 * this method is used to create a new house information and will
	 * later be put back into the database, which is adding a house 
	 * entry into the original database.
	 * @param coordinates gives the location of the house
	 * @return a map object which is created to initialize a house object
	 */
	private Map<String, String> mapCommunication(String coordinates){
		Map<String,String> map = new HashMap<>();
	    map.put("Parcel Number", "");
	    map.put("Category Code Description", type.getSelectedItem().toString());
	    map.put("Owner 1", firstName.getText().trim() + " " + lastName.getText().trim());
	    map.put("Street Name", streetName.getText().trim());
	    map.put("Unit", unit.getText().trim());
	    map.put("Total Livable Area", livingArea.getText().trim());
	    map.put("Zip Code", zipCode.getText().trim());
	    map.put("House Number", houseNumber.getText().trim());
	    map.put("Year Built", buildYear.getText().trim());
	    map.put("Sale Price", "");
	    map.put("Sale Date", "");
	    map.put("Market Value", "");
	    map.put("Market Value Date", "");
	    map.put("Coordinates", coordinates);
	    map.put("Total Area", "");
	    map.put("Category Code", type.getSelectedItem().toString());     
	    map.put("Basements", basement.getSelectedItem().toString());     
	    map.put("parking", parking.getSelectedItem().toString());
	    map.put("Exterior Condition", "");
	    map.put("heatType", heat.getSelectedItem().toString());
	    map.put("Central Air", centralAir.getSelectedItem().toString());   
	    map.put("Interior Condition", "");
	    map.put("Number of Bedrooms", "");
	    map.put("Number of Bathrooms", "");  
	    map.put("Number of Rooms", "");
	    
	    return map;
	}
	
	/**
	 * this method is used to show the result page with the
	 * information from user and get the similar houses from
	 * database engine.
	 * @param result gives an array list of houses to show in the resulting page
	 */
	public void showPage(ArrayList<HouseType> result, Map<String, String> map, double estimatedPrice) {
		
	    if (resultListing == null) {
	      resultListing = new EstimateListingPage(model, map, estimatedPrice);
	    }
	    
	    // if repeated search the engine, change the table and map
	    if (!resultListing.isShowing()) {
		  ((EstimateListingPage)resultListing).setMap(map);
	      resultListing.changeTable(result);
	      resultListing.clearMap();
	      resultListing.resetInfo(result);
	      resultListing.markMap();
	      resultListing.show();

	    } else {
		  ((EstimateListingPage)resultListing).setMap(map);
	      resultListing.changeTable(result);
	      resultListing.clearMap();
	      resultListing.resetInfo(result);
	      resultListing.markMap();
	    }
	  }


}
