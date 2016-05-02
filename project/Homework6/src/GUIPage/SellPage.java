package GUIPage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * This is class used to show sell page result page
 * @author Sophisitimate
 *
 */
public class SellPage extends JFrame{
	private BufferedImage img;
	private JLabel background;
	JTextField firstName;
	JTextField lastName;
	
	JTextField location;
	
	JTextField streetName;
	JTextField houseNumber;
	JTextField unit;
	
	JTextField livingArea;
	JTextField outdoorArea;
	JTextField zipCode;
	JTextField buildYear;
	JComboBox parking;
	JComboBox basement;
	JComboBox centralAir;
	JComboBox heat;
	JComboBox type;
	JButton submit;
	JPanel textHolder;
	
	/**
	 * This is SellPage constructor
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
	 * This 
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
	            		String[] label = {"House Number", "Unit", "Living Area", "Outdoor Area", "Built Year"};
	            		int houseNum;
	            		int unitNum;
	            		double living;
	            		double outdoor;
	            		int build;
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
	            		} catch (NumberFormatException error){
	            			JOptionPane.showMessageDialog(getContentPane(), "Please enter only numbers for " + label[count], 
		                            "Input Format Error Message", JOptionPane.INFORMATION_MESSAGE);
	            			return ;   // finish the event when the Message Dialog has been displayed.
	            		}
	            		
	            		
	            		
	            	}
	            }
		 });
	 }
	
	public static void main(String[] args) {
		SellPage sp = new SellPage();
	}

}
