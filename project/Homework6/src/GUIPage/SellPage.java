package GUIPage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SellPage extends JFrame{
	private BufferedImage img;
	private JLabel background;
	JTextField firstName;
	JTextField lastName;
	
	JTextField location;
	
	JTextField StreetName;
	JTextField houseNumber;
	JTextField unit;
	
	JTextField livingArea;
	JTextField zipCode;
	JTextField buildYear;
	JComboBox parking;
	JComboBox type;
	JButton submit;
	
	public SellPage(){
		try{
			img = ImageIO.read(new File("niceNight.jpg"));
		} catch(Exception e){
			e.printStackTrace();
		}
		background = new JLabel(new ImageIcon(img));			// Residential, Commercial, Industrial
		this.setContentPane(background);
        this.setLayout(new GridBagLayout());
		JPanel textHolder = new JPanel();
		textHolder.setOpaque(false);
		firstName = new JTextField();
		firstName.setPreferredSize(new Dimension(150, 30));
		lastName = new JTextField();
		lastName.setPreferredSize(new Dimension(150, 30));
		location = new JTextField();
		location.setPreferredSize(new Dimension(380, 30));
		livingArea = new JTextField();
		livingArea.setPreferredSize(new Dimension(140, 30));
		zipCode = new JTextField();
		zipCode.setPreferredSize(new Dimension(140, 30));
		type = new JComboBox<String> (new String[] {"Residential", "Commercial", "Industrial"});
		type.setPreferredSize(new Dimension(150, 30));
		submit = new JButton("Request Consultation");
		submit.setPreferredSize(new Dimension(150, 40));
		JLabel hint = new JLabel("How Much Is My House Worth?");
		hint.setFont(new Font("Verdana", Font.PLAIN, 28));
		textHolder.add(hint);
		textHolder.add(new JLabel("First Name:"));
		textHolder.add(firstName);
		textHolder.add(new JLabel("Last Name:"));
		textHolder.add(lastName);
		textHolder.add(new JLabel("Zip Code:"));
		textHolder.add(zipCode);
		textHolder.add(new JLabel("Living Area(ft²):"));
		textHolder.add(livingArea);
		textHolder.add(new JLabel("Address:"));
		textHolder.add(location);
		textHolder.add(new JLabel("House Type:"));
		textHolder.add(type);
		textHolder.add(submit);
		textHolder.setPreferredSize(new Dimension(500, 200));
		this.add(textHolder);
		this.pack();
	}
	
	public static void main(String[] args) {
		SellPage sp = new SellPage();
		sp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sp.setSize(1100, 700);
		sp.setResizable(false);
		sp.setVisible(true);
		sp.setLocationRelativeTo(null);

	}

}
