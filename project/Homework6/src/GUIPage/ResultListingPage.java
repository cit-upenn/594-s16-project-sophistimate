package GUIPage;
import MVC.*;
import House.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import GUIPage.BuyHomePage.HouseOption;

public class ResultListingPage extends JFrame{    /* How to resolove conflicts */
	private JSpinner livingAreaMin;//lower bound for living area filtering
	private JSpinner livingAreaMax;//upper bound for living area filtering
	private JSpinner buildYearMin;//lower bound for build year 
	private JSpinner buildYearMax;//upper bound for build year
	private JSpinner salePriceMin;//lower bound for sale price
	private JSpinner salePriceMax;//upper bound for sale price
	private JSpinner marketValueMin;//lower bound for market value
	private JSpinner marketValueMax;//upper bound for market value
	private JComboBox parkingLot;//indicator for parking lot
	private JLabel resultDetail;//detail button
	private JButton submitFilter;//submit button for filtering
	private Table table;//display table
	private JPanel resultShow;//map display
	
	private ArrayList<HouseType> retHouse;//house list after database searching and filtering
	private HouseType currentHouse;//the selected house
	private int lastRow;//the row selected in the table
	private String path;//file path of map.html 
	
	private Model model;//model for the program
	private View view;//the view panel
	
	private Browser browser;//browser for opening google map
	private BrowserView browserView;
	
	private JComboBox sorting;//gives sorting attribute
	private JComboBox sortingType;//indicate ascending or descending
	private JButton sortingSubmit;//sorting submit
	
	public ResultListingPage(){
		panelCreate();
		init();
	}
	
	/**
	 * this is the constructor for setting the model, get house list and create panels
	 * @param e gives the model of this frame.
	 */
	public ResultListingPage(Model e){
		setModel(e);
		retHouse = model.getHouseList();
		panelCreate();
        init();

	}
	
	/**
	 * this method is used to reset the house list after filtering or searching the database
	 * from the main page.
	 * @param house defines the house list for this frame. 
	 */
	public void resetInfo(ArrayList<HouseType> house){
		retHouse = house;
		currentHouse = null;
	}
	
	/**
	 * this method is used to create the panel and set layout
	 */
	public void panelCreate() {
		JPanel mainPanel = (JPanel) getContentPane();
		JPanel leftPane = new JPanel();
		JPanel rightPane = new JPanel();
		
		resultShow = new JPanel();
		resultShow.setLayout(new BorderLayout());
		resultDetail = new JLabel("The label used for displaying details");
		
		table = new Table();
		resultShow.add(resultDetail, BorderLayout.NORTH);
		resultShow.add(table.addTableComp(), BorderLayout.CENTER);
		resultShow.add(new Button("View Details"), BorderLayout.SOUTH);
		
		JSplitPane splitRightPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, setPreference(), resultShow);
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
	
	public void googleMapPane(JPanel leftPane){
		browser = new Browser();		
		browserView = new BrowserView(browser);
		leftPane.setLayout(new BorderLayout());
		leftPane.setSize(new Dimension(550, 500));
		browserView.setSize(new Dimension(550, 500));
		leftPane.add(browserView.getComponent(0), BorderLayout.CENTER);
		File file = new File("");
		path = file.getAbsolutePath();		
		browser.loadURL("file://"+path+"/map.html");//open the path of html file
		
		try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
           
          e.printStackTrace();
        }	
		markMap();//mark the google map
		addActionListeners();
	}
	
	/**
	 * this method is used to mark location of the houses in the google map
	 */
	public void markMap(){
		int countRow = retHouse.size();
		for(int i = 0; i < 30;i++){/**/
		    if(countRow <= i){
		    	continue;
		    }
	        browser.executeJavaScript("var myLatLng = new google.maps.LatLng(" + retHouse.get(i).getLocation() + ");\n"
	            + "var marker = new google.maps.Marker({\n"+
	            "position: myLatLng,\n"+
	            "map: map\n,"+	            
	          "});\n"+
	          "marker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');\n"+
	          "markers.push(marker);\n");		  
		}
	}
	
	public void clearMap(){
		browser.executeJavaScript("for(var i = 0; i < markers.length; i++) {\n" +
				"markers[i].setMap(null);\n"+
				"}\n"+"markers = [];\n");
		
	}
	
	public JPanel setPreference() {
		JPanel pane = new JPanel();
		livingAreaMin = setSpinner(0, 20, 2, 0);
		livingAreaMax = setSpinner(1, 20000, 100 , 2000);
		buildYearMin = setSpinner(2, 20, 2 , 2);
		buildYearMax = setSpinner(3, 20, 2 , 3);
		salePriceMin = setSpinner(4, 20, 2 , 4);
		salePriceMax = setSpinner(5, 20, 2 , 5);
		marketValueMin = setSpinner(6, 20, 2 , 6);
		marketValueMax = setSpinner(7, 20, 2 , 7);
		pane.add(new JLabel("     Area (ft²):"));
		pane.add(new JLabel("from"));
		pane.add(livingAreaMin);
		pane.add(new JLabel("to"));
		pane.add(livingAreaMax);		
		
		pane.add(new JLabel("           Sale Price($):"));
		pane.add(new JLabel("min"));
		pane.add(salePriceMin);
		pane.add(new JLabel("max"));
		pane.add(salePriceMax);
		
		pane.add(new JLabel("    Built Year:"));
		pane.add(new JLabel("from"));
		pane.add(buildYearMin);
		pane.add(new JLabel("to"));
		pane.add(buildYearMax);

		pane.add(new JLabel("     Market Value($):"));
		pane.add(new JLabel("min"));
		pane.add(marketValueMin);
		pane.add(new JLabel("max"));
		pane.add(marketValueMax);
		
		pane.add(new JLabel("Do you want a parking lot? "));
		parkingLot = new JComboBox<String> (new String[] {"Yes", "No"});
		pane.add(parkingLot);
		pane.add(new JLabel("                                      "));
		
		submitFilter = new JButton("Search");
		pane.add(submitFilter);
		
		sorting = new JComboBox<String> (new String[] {"Built Year", "Outdoor Area", "Living Area", "Market Price", "Sales Price"});
		pane.add(sorting);
		
		sortingType = new JComboBox<String> (new String[] { "Descending", "Ascending" });
		pane.add(sortingType);
		
		sortingSubmit = new JButton("Sorting");
		pane.add(sortingSubmit);
		
		return pane;
	}
	
	public void changeTable(ArrayList<HouseType> result){
		String[] columnNames = {"No.", "Address", "Zip", "Area", "Year", "Sale", "Market", "Outdoor"};
		Object[][] data = new Object[30][8];
		if(result.size() != 0){
	 		for ( Integer i = 0 ; i < 30 ; i++ ){
				data[i] = new Object[] { (i + 1), result.get(i).getHouseNumber() + " " + result.get(i).getStreetName(), 
						result.get(i).getZipCode(), result.get(i).getLivingArea(), result.get(i).getBuildYear(), result.get(i).getSalePrice(),
						result.get(i).getMarketValue(), result.get(i).getOutdoorArea() };
			}
		}
 		table.setData(columnNames, data);
 		
	}
	
	public Table getTable(){
		return table;
	}
	
	public JSpinner setSpinner(int min, int max, int step, int initValue){
		SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
		return new JSpinner(model);
	}

	public void setModel(Model m){
		model = m;
	}
	
	public void init(){
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1100, 700);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		
	}
	
	public void addActionListeners(){
		table.table.addMouseListener(new java.awt.event.MouseAdapter(){
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt){
				int row = table.table.rowAtPoint(evt.getPoint());
				if(currentHouse != null){
			        browser.executeJavaScript("markers["+ lastRow +"].setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png')");
				}
				lastRow = row;
				if(row >= 0 && row < retHouse.size()){
					currentHouse = retHouse.get(row);
					browser.executeJavaScript("markers["+ lastRow +"].setIcon('https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png')");
					
				}
				if(row >= retHouse.size()){
					currentHouse = null;
				}
				if (evt.getClickCount() == 2) {     /*  Show related details page */
					if( currentHouse != null ) {
						DetailPage dp = new DetailPage(currentHouse);
					}
	            }
				
			}
		});
		
	    submitFilter.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		int la_low = Integer.parseInt(livingAreaMin.getValue().toString().trim());
	    		int la_high = Integer.parseInt(livingAreaMax.getValue().toString().trim());
	    		int by_low = Integer.parseInt(buildYearMin.getValue().toString().trim());
	    		int by_high = Integer.parseInt(buildYearMax.getValue().toString().trim());
	    		int sp_low = Integer.parseInt(salePriceMin.getValue().toString().trim());
	    		int sp_high = Integer.parseInt(salePriceMax.getValue().toString().trim());
	    		int mv_low = Integer.parseInt(marketValueMin.getValue().toString().trim());
	    		int mv_high = Integer.parseInt(marketValueMax.getValue().toString().trim());
	    		String park = parkingLot.getSelectedItem().toString();
	    		int parking;
	    		if(park.equals("Yes")){
	    			parking = 1;
	    		}else{
	    			parking = 0;
	    		}
	    		retHouse = model.filtering(la_low, la_high, by_low, by_high, sp_low, sp_high, mv_low, mv_high, parking);
	    		currentHouse = null;
	    		changeTable(retHouse);
	    		clearMap();
	    		markMap();
	    		
	    		
	    	}
	    });
	    
	    sortingSubmit.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		String selectionType = sortingType.getSelectedItem().toString();
	    		String selection = sorting.getSelectedItem().toString();
	    		
	    		if(selectionType.equals("Descending")){
	    			if(model.isAscendent()){
	    				model.setIsAscendent(false);	    				
	    			}
	    		}else{
	    			if(!model.isAscendent()){
	    				model.setIsAscendent(true);
	    			}
	    		}
	    		
	    		if(selection.equals("Built Year")){
	    			retHouse = model.sorting(retHouse, "buildYear");
	    		}else if(selection.equals("Outdoor Area")){
	    			retHouse = model.sorting(retHouse, "outdoorArea");
	    		}else if(selection.equals("Living Area")){
	    			retHouse = model.sorting(retHouse, "livingArea");
	    		}else if(selection.equals("Market Price")){
	    			retHouse = model.sorting(retHouse, "marketValue");
	    		}else if(selection.equals("Sales Price")){
	    			retHouse = model.sorting(retHouse, "salePrice");
	    		}
	    		currentHouse = null;
	    		clearMap();
	    		changeTable(retHouse);
	    		markMap();
	    	}
	    });
	}
	
//	public static void main(String[] args) {
//	  JPanel abc = new JPanel();
//	  googleMapPane(abc);
//	}
//	public static void main(String[] args) {
//		ResultListingPage rl = new ResultListingPage();
//		try{
//			Thread.sleep(5000);
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		rl.getTable().setData(new String[]{"a", "b" , "c"}, new Object[][]{{10, 20, 30}, {40, 50, 60}});
//		
//
//	}

}
