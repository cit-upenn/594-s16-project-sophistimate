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

public class ResultListingPage extends JFrame{
	private JSpinner livingAreaMin;  
	private JSpinner livingAreaMax;
	private JSpinner buildYearMin;
	private JSpinner buildYearMax;
	private JSpinner salePriceMin;
	private JSpinner salePriceMax;
	private JSpinner marketValueMin;
	private JSpinner marketValueMax;
	private JComboBox parkingLot;
	private JLabel resultDetail;
	private JButton submitFilter;
	private Table table;
	private JPanel resultShow;
	
	private ArrayList<HouseType> retHouse;
	private HouseType currentHouse;
	private int lastRow;
	private Model model;
	private View view;
	private Browser browser;
	private BrowserView browserView;
	
	
	public ResultListingPage(){
		panelCreate();
		init();
	}
	
	public ResultListingPage(Model e){
		setModel(e);
		retHouse = model.getHouseList();
		panelCreate();
        init();

	}
	
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
		
		googleMapPane(leftPane);
		
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
		String path = file.getAbsolutePath();
		System.out.println(path+"/map.html");
		
		browser.loadURL("file://"+path+"/map.html");
		
		try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
           // TODO Auto-generated catch block
          e.printStackTrace();
        }
//		System.out.println(retHouse.get(0).getLocation());
//      browser.executeJavaScript("var myLatLng = new google.maps.LatLng(" + retHouse.get(0).getLocation() + ");\n"
//      + "var marker = new google.maps.Marker({\n"+
//      "position: myLatLng,\n"+
//      "map: map\n"+
//      
//    "});");
//		
		for(int i = 0; i < 30;i++){
		    System.out.println(retHouse.get(i).getLocation());
	        browser.executeJavaScript("var myLatLng = new google.maps.LatLng(" + retHouse.get(i).getLocation() + ");\n"
	            + "var marker = new google.maps.Marker({\n"+
	            "position: myLatLng,\n"+
	            "map: map\n,"+	            
	          "});\n"+
	          "marker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');\n"+
	          "markers.push(marker);\n");		  
		}
		addActionListeners();
	}
	
	public JPanel setPreference() {
		JPanel pane = new JPanel();
		livingAreaMin = setSpinner(0, 20, 2, 0);
		livingAreaMax = setSpinner(1, 20, 2 , 1);
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
		return pane;
	}
	
	public void changeTable(ArrayList<HouseType> result){
		String[] columnNames = {"No.", "Address", "Zip", "Area", "Year", "Sale", "Market", "Outdoor"};
		Object[][] data = new Object[30][8];
 		for ( Integer i = 0 ; i < 30 ; i++ ){
			data[i] = new Object[] { (i + 1), result.get(i).getHouseNumber() + " " + result.get(i).getStreetName(), 
					result.get(i).getZipCode(), result.get(i).getLivingArea(), result.get(i).getBuildYear(), result.get(i).getSalePrice(),
					result.get(i).getMarketValue(), result.get(i).getOutdoorArea() };
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
				if(row >= 0){
					currentHouse = retHouse.get(row);
					browser.executeJavaScript("markers["+ lastRow +"].setIcon('https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png')");
					
				}
			}
		});

	    
	}
	
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
