package GUIPage;
import MVC.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import House.HouseType;


public class BuyHomePage extends JFrame{
	
	private JPanel firstPanel = new JPanel();
	private JPanel secondPanel = new JPanel();
	
	private BrowserView browserView;
	private JLabel background;
	private JTabbedPane tabbedPane = new JTabbedPane();
	
	private int pictureFlag = 0;
	private BufferedImage img;
	private BufferedImage img2;
	private BufferedImage img3;
	private BufferedImage img4;
	
	JButton submit;
	JButton submit2;
	JTextField locationInput;
	JTextField sellLocationInput;
	JLabel hint;
	
	private JRadioButton radioButton1 = new JRadioButton("Residential", true);
	private JRadioButton radioButton2 = new JRadioButton("Commercial", false);
	private JRadioButton radioButton3 = new JRadioButton("Industrial", false);
	private ButtonGroup myButtonGroup = new ButtonGroup();
	
	public enum HouseOption{RESIDENTIAL, COMMERCIAL, INDUSTRIAL};
	
	private HouseOption type;
	
	private Model model;
	
	public BuyHomePage() {   /* Page switch Panel */
		
		init();
		
		try{
			img = ImageIO.read(new File("Nice-Green-Home-Wallpaper-HD.jpg"));
			img2 = ImageIO.read(new File("Buildings.jpg"));
			img3 = ImageIO.read(new File("Commercial.jpg"));
			img4 = ImageIO.read(new File("GreenHouse.jpg"));
			
			background = new JLabel(new ImageIcon(img));			// Residential, Commercial, Industrial
			this.setContentPane(background);
            this.setLayout(new GridBagLayout());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		type = HouseOption.RESIDENTIAL;
	   
		final Browser browser = new Browser();
	    browserView = new BrowserView(browser);

//	    secondPanel.setLayout(new BorderLayout());
	    secondPanel.setSize(new Dimension(500, 80));
//	    browserView.setSize(new Dimension(500, 500));
//	    secondPanel.add(browserView.getComponent(0), BorderLayout.CENTER);
//	    
//	    browser.loadURL("http://maps.google.com");
		
	    firstPanel.setOpaque(false);
	    locationInput = new JTextField("Address, Zip, Sites");
	    locationInput.setOpaque(true);
	    locationInput.setPreferredSize(new Dimension(400, 40));
	    
	    submit = new JButton("Submit"); 
	    submit2 = new JButton("Submit"); 
	    
	    addActionListeners();
	    
	    firstPanel.setPreferredSize(new Dimension(500, 80));
	    firstPanel.add(locationInput);
	    firstPanel.add(submit);
	    myButtonGroup.add(radioButton1);
	    myButtonGroup.add(radioButton2);
	    myButtonGroup.add(radioButton3);
	    firstPanel.add(radioButton1);
	    firstPanel.add(radioButton2);
	    firstPanel.add(radioButton3);
	    
	    tabbedPane.setFont( new Font( "Dialog", Font.BOLD|Font.PLAIN, 19 ) );
        
	    secondPanel.setOpaque(false);
	    JTextField sellLocationInput = new JTextField("Enter your street address");
	    sellLocationInput.setPreferredSize(new Dimension(400, 40));
	    secondPanel.add(sellLocationInput);
	    secondPanel.add(submit2);
	    
	    tabbedPane.setOpaque(false);
	    tabbedPane.add("Buy", firstPanel);
		tabbedPane.add("Sell", secondPanel);
        
		hint = new JLabel("Find your way home");
		JLabel whiteSpace = new JLabel("                                                                       ");
		hint.setFont(new Font("Verdana", Font.PLAIN, 28));
		whiteSpace.setFont(new Font("Verdana", Font.PLAIN, 18));
		JPanel bag = new JPanel();
		bag.setOpaque(false);
		bag.setPreferredSize(new Dimension(600, 300));
		bag.add(hint);
		bag.add(whiteSpace);
		bag.add(tabbedPane);
		add(bag);
        this.pack();
		
	}
	
	public void init(){
		model = new Model();
	}
	
	public static void main(String[] args){
			BuyHomePage tp = new BuyHomePage();
			tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			ResultListingPage rl = new ResultListingPage();
//			rl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			rl.setSize(1100, 700);
			rl.setResizable(false);
			rl.setVisible(true);
			rl.setLocationRelativeTo(null);
			
			
			tp.setSize(1100, 700);
			tp.setResizable(false);
			tp.setVisible(true);
			tp.setLocationRelativeTo(null);
			
			try{
				Thread.sleep(5000);
			} catch(Exception e){
				e.printStackTrace();
			}
			
//			while(!rl.isShowing()) {
//				System.out.println(HouseOption.COMMERCIAL.toString());
//				rl.show();
//				
//			}
			
	}
	
	public void addActionListeners(){
	    radioButton1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img4));
				hint.setForeground(Color.white);
				type = HouseOption.RESIDENTIAL;
			}
	    });
	    
	    radioButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img3));
				hint.setForeground(Color.black);
				type = HouseOption.COMMERCIAL;
			}
	    });
	    
	    radioButton3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img2));
				type = HouseOption.INDUSTRIAL;
			}
	    });
	    
	    submit.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		if (locationInput.getText().trim().length() == 0) {    // Nothing is input! oops! show warning dialog	    			
	    			JOptionPane.showMessageDialog(getContentPane(), "Please enter Address, Zip or Sites!!", 
	    					"Error Message", JOptionPane.INFORMATION_MESSAGE);
	    		} else {
	    			DataBaseEngine db = new DataBaseEngine();
	    			List<HouseType> result = db.getResultByLocation(locationInput.getText().trim(), type);
	    			model.setHouseList( (ArrayList<HouseType>) result );
	    			model.setHouseType( type.toString() );
	    		}
	    		
	    	}
	    });
	}

}
