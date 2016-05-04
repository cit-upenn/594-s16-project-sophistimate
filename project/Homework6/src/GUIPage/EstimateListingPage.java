package GUIPage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import MVC.DataBaseEngine;
import MVC.Model;

/**
 * This is the class for displaying the related real estate for user's selling house.
 * The class extends from the ResultListing Page, which is another JFrame.
 * It overrides the filtering JPanel part in ResultListingPage, as well as some action Listener.
 * The added field: map & estimated are used for displaying the predicted value and the function
 * that this data from user can be inserted into our MongoDB.
 * @author dongtianxiang
 *
 */
public class EstimateListingPage extends ResultListingPage {
	private int estimate = 999999;
	private Map<String, String> map;
	private JButton submit = new JButton("Publish");
	
	/**
	 * The constructor that should be called in SellPage
	 * @param m	model
	 * @param map related value to insert into database	
	 * @param estimatedPricepredicted price from the method in DataBaseEngine
	 */
	public EstimateListingPage(Model m, Map<String, String> map, double estimatedPrice){
		super();
		this.estimate = (int)estimatedPrice;
        this.map = map;
        setModel(m);
        retHouse = model.getHouseList();
        this.panelCreate();
        init();
        
        this.setTitle("Result from Database");
        
        this.setSize(1100, 700);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
	}
	
	/**
	 * The set method function for map field.
	 * @param map
	 */
	public void setMap(Map<String, String> map){
		this.map = map;
	}
	
	@Override 
	/**
	 * This is the class used to create a JPanel, which is for displaying details and estimate 
	 * of the real estate input by user. There is also a JButton for submitting the information
	 * into database.
	 */
	public JPanel setPreference() {
		JPanel pane = new JPanel();
		JLabel detail = new JLabel();
		
		System.out.println(map == null);
		System.out.println(map.get("Street Name"));
		System.out.println(map.get("House Number"));
		System.out.println(map.get("Zip Code"));
		System.out.println(map.get("Category Code"));
		System.out.println(map.get("Coordinates"));
		
		String whiteSpace = "";
		for( int i = 0; i < 100; i++){
			whiteSpace += "&nbsp";
		}
		
		String whiteSpace2 = "";
		for( int i = 0; i < 40; i++){
			whiteSpace2 += "&nbsp";
		}
		
		StringBuilder title = new StringBuilder();
		title.append("<html>");
		title.append("<p><b><big>" + map.get("House Number") + " " + map.get("Street Name")  + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Predicted Value: $" + estimate + "<big></b><br>");
		title.append("Philadelphia, PA " + map.get("Zip Code") + whiteSpace2 + "<br>");
		title.append("Type: " + map.get("Category Code") + whiteSpace2 + "<br>");
		title.append("" + whiteSpace + "<br>");
		
		title.append("&nbsp;&nbsp;&nbsp;-Owner: " + map.get("Owner 1") + "&#9"  + (map.get("Owner 1").length() < 12?"&#9":"") + "-Build Year: " + map.get("Year Built") +"<br>");
		title.append("&nbsp;&nbsp;&nbsp;-Street: " + map.get("Street Name") + "&#9;&#9;" + "-House Number: " + map.get("House Number") + "<br>");
		title.append("&nbsp;&nbsp;&nbsp;-Unit " + map.get("Unit") + "&#9;&#9;&#9;" + "-Living Area(ft²): " + map.get("Total Livable Area") + "<br>");
		title.append("&nbsp;&nbsp;&nbsp;-ParkingLots: " + map.get("parking") + "&#9;&#9;" + "-Central Air: " + map.get("Central Air") + "<br>");
		title.append("&nbsp;&nbsp;&nbsp;-Heat: " + map.get("heatType") + "<br>");
		

//		title.append(" " + whiteSpace + "&#9" + whiteSpace + "<br>");		
		title.append("</p></html>");
		
		detail.setText(title.toString());
//		detail.setBackground(Color.red);
		detail.setPreferredSize(new Dimension(400, 200));
		pane.setLayout(new BorderLayout());
		pane.add(detail, BorderLayout.CENTER);
		pane.add(detail);
		pane.add(submit, BorderLayout.SOUTH);
		return pane;	
	}
	
	/**
	 * This method is nearly the same as in its super class.
	 * The only difference is that in this class, we use the setPreference() method which has
	 * been overridden in this subclass.
	 */
	@Override
	public void panelCreate() {
		JPanel mainPanel = (JPanel) getContentPane();
        JPanel leftPane = new JPanel();
        JPanel rightPane = new JPanel();
        
        resultShow = new JPanel();
        resultShow.setLayout(new BorderLayout());
        resultDetail = new JLabel("The label used for displaying details");
        
        table = new Table();
        viewDetails = new JButton("View Details");
//        resultShow.add(resultDetail, BorderLayout.NORTH);
        resultShow.add(table.addTableComp(), BorderLayout.CENTER);
        resultShow.add(viewDetails, BorderLayout.SOUTH);
        
        JSplitPane splitRightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.setPreference(), resultShow);
        splitRightPane.setDividerLocation(200);
        splitRightPane.setOpaque(false);
        rightPane.setLayout(new BorderLayout());
        rightPane.add(splitRightPane, BorderLayout.CENTER);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
        splitPane.setDividerLocation(520);
        splitPane.setEnabled( false );  /* The splitPane cannot be resized */
        
        googleMapPane(leftPane);//set the google map in the frame as well as mark the locations
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(splitPane, BorderLayout.CENTER);
    
	}
	
	/**
	 * The submit Action listener.
	 * The super class use the submit to do filter and sorting.
	 * The method here used to show confirmation dialog and call the method to insert data into database.
	 */
	@Override
	public void addActionListeners_Submit(){
		
		 submit.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	int yesNo = JOptionPane.showConfirmDialog(getContentPane(),
	                        "Are you sure you want to publish your real estate to the Database?");
	                if (yesNo == JOptionPane.YES_OPTION) {
	                	DataBaseEngine db = new DataBaseEngine();
		            	db.insertData(map);   
		            	JOptionPane.showMessageDialog(getContentPane(), "Thank you! Your data has been collected into our Database", "Success",
		                        JOptionPane.INFORMATION_MESSAGE);
		            	}
	            	
	            	
	            }
		 });
	}

}
