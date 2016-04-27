package GUIPage;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;

import javax.swing.*;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

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
	
	
	public ResultListingPage(){
		panelCreate();
	}
	
	public void panelCreate() {
		JPanel mainPanel = (JPanel) getContentPane();
		JPanel leftPane = new JPanel();
		JPanel rightPane = new JPanel();
		
		JPanel resultShow = new JPanel();
		resultShow.setLayout(new BorderLayout());
		resultDetail = new JLabel("The label used for displaying details");
		
		resultShow.add(resultDetail, BorderLayout.NORTH);
		resultShow.add(new Table().addTableComp(), BorderLayout.CENTER);
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
		final Browser browser = new Browser();		
		BrowserView browserView = new BrowserView(browser);
		leftPane.setLayout(new BorderLayout());
		leftPane.setSize(new Dimension(550, 500));
		browserView.setSize(new Dimension(550, 500));
		leftPane.add(browserView.getComponent(0), BorderLayout.CENTER);
		browser.loadURL("http://maps.google.com");
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
	
	public JSpinner setSpinner(int min, int max, int step, int initValue){
		SpinnerModel model = new SpinnerNumberModel(initValue, min, max, step);
		return new JSpinner(model);
	}
	
	public static void main(String[] args) {
		ResultListingPage rl = new ResultListingPage();
		rl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		rl.setSize(1100, 700);
		rl.setResizable(false);
		rl.setVisible(true);
		rl.setLocationRelativeTo(null);

	}

}
