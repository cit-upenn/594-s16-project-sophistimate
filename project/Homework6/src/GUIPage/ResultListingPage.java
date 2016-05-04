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
import java.util.List;

import javax.swing.*;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import GUIPage.BuyHomePage.HouseOption;

/**
 * This is class to show result list page
 * @author Sophisimate
 *
 */
public class ResultListingPage extends JFrame{    /* How to resolove conflicts */

    protected JSpinner livingAreaMin;//lower bound for living area filtering
    protected JSpinner livingAreaMax;//upper bound for living area filtering
    protected JSpinner buildYearMin;//lower bound for build year 
    protected JSpinner buildYearMax;//upper bound for build year
    protected JSpinner salePriceMin;//lower bound for sale price
    protected JSpinner salePriceMax;//upper bound for sale price
    protected JSpinner marketValueMin;//lower bound for market value
    protected JSpinner marketValueMax;//upper bound for market value
    protected JComboBox parkingLot;//indicator for parking lot
    protected JLabel resultDetail;//detail button
    protected JButton submitFilter;//submit button for filtering
    protected Table table;//display table
    protected JPanel resultShow;//map display
    
    protected ArrayList<HouseType> retHouse;//house list after database searching and filtering
    protected HouseType currentHouse;//the selected house
    protected int lastRow;//the row selected in the table
    protected String path;//file path of map.html 
    
    protected Model model;//model for the program
    protected View view;//the view panel
    
    protected Browser browser;//browser for opening google map
    protected BrowserView browserView;
    
    protected JComboBox sorting;//gives sorting attribute
    protected JComboBox sortingType;//indicate ascending or descending
    protected JButton sortingSubmit;//sorting submit
    protected JButton viewDetails; 
    /**
     * This is ResultListingPage class constructor
     */
    public ResultListingPage(){
//        panelCreate();
//        init();
//        this.setTitle("Result from Database");
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
        this.setTitle("Result from Database");
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
        viewDetails = new JButton("View Details");
//        resultShow.add(resultDetail, BorderLayout.NORTH);
        resultShow.add(table.addTableComp(), BorderLayout.CENTER);
        resultShow.add(viewDetails, BorderLayout.SOUTH);
        
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
    
    /**
     * this method initialize the google map pane by load the map.html
     * and set mark for the first house result on the map as well as add
     * action listener to each button. 
     * @param leftPane gives the pane on which the goolge map will be shown.
     */ 
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
        addActionListeners_Submit();
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

    /**
     * this method is used to clear the map with all the markers shown on it, and
     * reset the markers to null array for the loaded html file
     */
    public void clearMap(){
        browser.executeJavaScript("for(var i = 0; i < markers.length; i++) {\n" +
                "markers[i].setMap(null);\n"+
                "}\n"+"markers = [];\n");
        
    }

    /**
     * this method is used to set up property information for
     * filtering houses.
     * @return the pane with property spinners representing lower and upper bounds
     */ 
    public JPanel setPreference() {
        JPanel pane = new JPanel();
        livingAreaMin = setSpinner(0, 2000, 200, 500);
        livingAreaMax = setSpinner(2000, 10000, 200, 2000);
        buildYearMin = setSpinner(1900, 1970, 2 , 1950);
        buildYearMax = setSpinner(1970, 2020, 2 , 2000);
        salePriceMin = setSpinner(50000, 2000000, 50000 , 100000);
        salePriceMax = setSpinner(2000000, 10000000, 100000 , 3000000);
        marketValueMin = setSpinner(50000, 2000000, 50000 , 100000);
        marketValueMax = setSpinner(2000000, 10000000, 100000 , 3000000);
		String white1 = "";
		String white2 = "";
		for( int i = 0; i < 45; i++){
			white1 += "&nbsp";
		}
		for( int i = 0; i < 12; i++){
			white2 += "&nbsp";
		}
        JLabel whiteSpace1 = new JLabel();
        JLabel whiteSpace2 = new JLabel();
        JLabel whiteSpace3 = new JLabel();
        JLabel whiteSpace4 = new JLabel();
        
        whiteSpace1.setText("<html>" + white1 + "</html>");
        whiteSpace2.setText("<html>" + white2 + "</html>");
        whiteSpace3.setText("<html>" + white1 + "</html>");
        whiteSpace4.setText("<html>" + white2 + "</html>");
        
        pane.add(new JLabel("        Area (ft²):"));
        pane.add(new JLabel("from"));
        pane.add(livingAreaMin);
        pane.add(new JLabel("to"));
        pane.add(livingAreaMax);   
        pane.add(whiteSpace1);
        
        pane.add(new JLabel("        Sale Price($):"));
        pane.add(new JLabel("min"));
        pane.add(salePriceMin);
        pane.add(new JLabel("max"));
        pane.add(salePriceMax);
        pane.add(whiteSpace2);
        
        pane.add(new JLabel("        Built Year:"));
        pane.add(new JLabel("from"));
        pane.add(buildYearMin);
        pane.add(new JLabel("to"));
        pane.add(buildYearMax);
        pane.add(whiteSpace3);

        pane.add(new JLabel("        Market Value($):"));
        pane.add(new JLabel("min"));
        pane.add(marketValueMin);
        pane.add(new JLabel("max"));
        pane.add(marketValueMax);
        pane.add(whiteSpace4);
        
        pane.add(new JLabel("      Do you want a parking lot? "));
        parkingLot = new JComboBox<String> (new String[] {"Yes", "No"});
        pane.add(parkingLot);
        pane.add(new JLabel("                "));
        
        submitFilter = new JButton("Search");
        pane.add(submitFilter);
        
        sorting = new JComboBox<String> (new String[] {"Built Year", "Outdoor Area", "Living Area", "Market Price", "Sales Price"});
        pane.add(sorting);
        
        sortingType = new JComboBox<String> (new String[] { "Descending", "Ascending" });
        pane.add(sortingType);
        
        sortingSubmit = new JButton("Sorting");
        pane.add(new JLabel("                 "));
        pane.add(sortingSubmit);
        
        return pane;
    }

    /**
     * this method is used to change the table information which shows rows of house information and the given attributes
     * of each house, user could click rows to see the exact location of the house 
     * @param result gives the input arraylist to be shown or updated on the table
     */ 
    public void changeTable(ArrayList<HouseType> result){
        String[] columnNames = {"No.", "Address", "Zip", "Area(ft²)", "Year", "Sale($)", "Market($)", "Outdoor"};
        Object[][] data = new Object[30][8];
        if(result.size() != 0){
            for ( Integer i = 0 ; i < Math.min(30, result.size()) ; i++ ){
                data[i] = new Object[] { (i + 1), result.get(i).getHouseNumber() + " " + result.get(i).getStreetName(), 
                        result.get(i).getZipCode(), result.get(i).getLivingArea(), result.get(i).getBuildYear(), result.get(i).getSalePrice(),
                        result.get(i).getMarketValue(), result.get(i).getOutdoorArea() };
            }
        }
        table.setData(columnNames, data);
        table.table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.table.getColumnModel().getColumn(3).setPreferredWidth(55);
        table.table.getColumnModel().getColumn(4).setPreferredWidth(40);
        
    }

    /**
     * this method is used to get the table of this frame
     * @return the table containing the house information
     */ 
    public Table getTable(){
        return table;
    }

    /**
     * This method is used to set the lower and upper bounds of each property
     * @param min gives the lower bounds of the property
     * @param max gives the upper bounds of the property
     * @param step gives the step value of each change
     * @param initValue gives the initial value
     * @return the set up JSpinner
     */ 
    public JSpinner setSpinner(int min, int max, int step, int initValue){
        SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
        return new JSpinner(model);
    }

    /**
     * this method is used to set the model of this page
     * @param m gives the updated model
     */
    public void setModel(Model m){
        model = m;
    }

    /**
     * this method is used to initialize the page
     */ 
    public void init(){
//      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100, 700);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        
        
    }
    
    public void addActionListeners_Submit(){
    	//click handler for submit filter, gets information form user and collects result from model
        //then mark the first 30 new results on the map         
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

        //click handler for sorting button
        //sort the houses with given property and order     
        sortingSubmit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectionType = sortingType.getSelectedItem().toString();
                String selection = sorting.getSelectedItem().toString();

                //gets the sorting order                
                if(selectionType.equals("Descending")){
                    if(model.isAscendent()){
                        model.setIsAscendent(false);                        
                    }
                }else{
                    if(!model.isAscendent()){
                        model.setIsAscendent(true);
                    }
                }

                //gets the sorting property             
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
                
                //change basic information and markers
                currentHouse = null;
                clearMap();
                changeTable(retHouse);
                markMap();
            }
        });
    }
    
    /**
     * this method is used to add action listener to certain components of this page, 
     * including filtering, sorting, show details and table rows clicking
     */ 
    public void addActionListeners(){
        table.table.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt){
                int row = table.table.rowAtPoint(evt.getPoint());
                //change the selected icon back to other houses             
                if(currentHouse != null){
                    browser.executeJavaScript("markers["+ lastRow +"].setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png')");
                }
                lastRow = row;
                //change the icon of newly selected house to a flag
                if(row >= 0 && row < retHouse.size()){
                    currentHouse = retHouse.get(row);
                    browser.executeJavaScript("markers["+ lastRow +"].setIcon('https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png')");
                    
                }
                //if select a null row, change current house to null
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
        
        viewDetails.addActionListener(new ActionListener(){    /* The same effect as double click on JTable */
            @Override
            public void actionPerformed(ActionEvent e) {               
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() {
                        if( currentHouse != null ) {
                            DetailPage dp = new DetailPage(currentHouse);
                        }
                        return null;
                    }
                  };
                  worker.execute();           
            }
        });
    }

    
//  public static void main(String[] args) {
//    JPanel abc = new JPanel();
//    googleMapPane(abc);
//  }
//  public static void main(String[] args) {
//      ResultListingPage rl = new ResultListingPage();
//      try{
//          Thread.sleep(5000);
//      } catch(Exception e){
//          e.printStackTrace();
//      }
//      rl.getTable().setData(new String[]{"a", "b" , "c"}, new Object[][]{{10, 20, 30}, {40, 50, 60}});
//      
//
//  }

}
