package GUIPage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class DetailPage extends JFrame{
	private BufferedImage img;
	private JLabel detailLabel;
	
	public DetailPage(){
		this.setLayout(new BorderLayout());
		JPanel bottomPart = new JPanel();
		bottomPart.setLayout(new BorderLayout());
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, googleMapPane(), bottomPart);
		splitPane.setDividerLocation(250);
		splitPane.setEnabled( false );  /* The splitPane cannot be resized */
		this.add(splitPane, BorderLayout.CENTER);
		
		try{
			img = ImageIO.read(new File("Nice-Green-Home-Wallpaper-HD.jpg"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel picture = new JLabel(new ImageIcon(img));
		JPanel streetScape = new JPanel();
		streetScape.add(picture);
		streetScape.setPreferredSize(new Dimension(400, 600));
		
		bottomPart.add(streetScape, BorderLayout.WEST);	
		
		detailLabel = new JLabel("This is the area for detailed information");
		bottomPart.add(detailLabel, BorderLayout.CENTER);
		
	}
	
	public JPanel googleMapPane(){
		JPanel pane = new JPanel();
		final Browser browser = new Browser();		
		BrowserView browserView = new BrowserView(browser);
		pane.setLayout(new BorderLayout());
		pane.setSize(new Dimension(550, 500));
		browserView.setSize(new Dimension(550, 500));
		pane.add(browserView.getComponent(0), BorderLayout.CENTER);
		browser.loadURL("http://maps.google.com");
		
		return pane;
	}
	
	public static void main(String[] args) {
		DetailPage dp = new DetailPage();
		dp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dp.setSize(900, 700);
//		dp.setResizable(false);
		dp.setVisible(true);
		dp.setLocationRelativeTo(null);
	}

}
