package GUIPage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

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
	
	private JRadioButton radioButton1 = new JRadioButton("Residential", false);
	private JRadioButton radioButton2 = new JRadioButton("Commercial", false);
	private JRadioButton radioButton3 = new JRadioButton("Industrial", false);
	private ButtonGroup myButtonGroup = new ButtonGroup();
	
	public BuyHomePage() {   /* Page switch Panel */
		try{
			img = ImageIO.read(new File("Nice-Green-Home-Wallpaper-HD.jpg"));
			img2 = ImageIO.read(new File("Buildings.jpg"));
			img3 = ImageIO.read(new File("Commercial.jpg"));
			img4 = ImageIO.read(new File("GreenHouse.jpg"));
			
//			BufferedImage img = ImageIO.read(new File("industrial.jpg"));
			background = new JLabel(new ImageIcon(img));			// Residential, Commercial, Industrial
			this.setContentPane(background);
//            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());
//            gbc.gridwidth = GridBagConstraints.REMAINDER;
            // Add stuff...
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//        JLabel firstLabel = new JLabel("firstLabel");
//		firstPanel.add(firstLabel);
	   
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
	
	public static void main(String[] args){
			BuyHomePage tp = new BuyHomePage();
			tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			tp.setSize(1100, 700);
			tp.setResizable(false);
			tp.setVisible(true);
			tp.setLocationRelativeTo(null);
	}
	
	public void addActionListeners(){
	    radioButton1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img4));
				hint.setForeground(Color.white);
			}
	    });
	    
	    radioButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img3));
				hint.setForeground(Color.black);
			}
	    });
	    
	    radioButton3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				background.setIcon(new ImageIcon(img2));
			}
	    });
	}

}
