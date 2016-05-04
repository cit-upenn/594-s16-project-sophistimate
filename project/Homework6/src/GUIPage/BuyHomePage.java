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

/**
 * This class is for home page of our GUI application which shows option for buy
 * or sell, house type
 * 
 * @author Sophisitimate
 *
 */
public class BuyHomePage extends JFrame {

  // panels and browser which make up this frame
  private JPanel firstPanel = new JPanel();
  private JPanel secondPanel = new JPanel();
  private BrowserView browserView;
  private JLabel background;
  private JTabbedPane tabbedPane = new JTabbedPane();

  // picture information used in this frame
  private int pictureFlag = 0;
  private BufferedImage img;
  private BufferedImage img2;
  private BufferedImage img3;
  private BufferedImage img4;

  // buttons and text field input which provides basic information for searching
  JButton submit;
  JButton submit2;
  JTextField locationInput;
  JTextField sellLocationInput;
  JLabel hint;
  private JRadioButton radioButton1 = new JRadioButton("Residential", true);
  private JRadioButton radioButton2 = new JRadioButton("Commercial", false);
  private JRadioButton radioButton3 = new JRadioButton("Industrial", false);
  private ButtonGroup myButtonGroup = new ButtonGroup();

  /**
   * 
   * @author Sophisitimate
   * This is enum class for 3 options
   */
  public enum HouseOption {
    RESIDENTIAL, COMMERCIAL, INDUSTRIAL
  };

  private HouseOption type;

  // the result model and listing page for the search page
  private Model model;
  private ResultListingPage resultListing = null;

  // loading bar
  private ProgressBar pbarFrame;

  /**
   * this is the constructor which set up the components and layout of this page
   */
  public BuyHomePage() { /* Page switch Panel */

    init();

    // set up images used in this frame
    try {
      img = ImageIO.read(new File("Nice-Green-Home-Wallpaper-HD.jpg"));
      img2 = ImageIO.read(new File("Buildings.jpg"));
      img3 = ImageIO.read(new File("Commercial.jpg"));
      img4 = ImageIO.read(new File("GreenHouse.jpg"));

      background = new JLabel(new ImageIcon(img)); // Residential, Commercial,
                                                   // Industrial
      this.setContentPane(background);
      this.setLayout(new GridBagLayout());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // get house type and set layout of the panels
    type = HouseOption.RESIDENTIAL;

    final Browser browser = new Browser();
    browserView = new BrowserView(browser);

    // secondPanel.setLayout(new BorderLayout());
    secondPanel.setSize(new Dimension(500, 80));
    // browserView.setSize(new Dimension(500, 500));
    // secondPanel.add(browserView.getComponent(0), BorderLayout.CENTER);

    firstPanel.setOpaque(false);
    locationInput = new JTextField("Address, Zip, Sites");
    locationInput.setOpaque(true);
    locationInput.setPreferredSize(new Dimension(400, 40));

    submit = new JButton("Submit");
    submit2 = new JButton("Submit");

    // add action listener and components to the panels
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

    tabbedPane.setFont(new Font("Dialog", Font.BOLD | Font.PLAIN, 19));

    secondPanel.setOpaque(false);
    JTextField sellLocationInput = new JTextField("Enter your street address");
    sellLocationInput.setPreferredSize(new Dimension(400, 40));
    secondPanel.add(sellLocationInput);
    secondPanel.add(submit2);

    tabbedPane.setOpaque(false);
    tabbedPane.add("Buy", firstPanel);
    tabbedPane.add("Sell", secondPanel);

    hint = new JLabel("Get Your Way Home");
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
    this.setTitle("Get Your Way Home");
  }

  /**
   * this method is used to initialize the model
   */
  public void init() {
    model = new Model();
  }

  /**
   * this method is used to show the result pages and mark the location in the
   * result listing page as well as change the result if user repeatedly search
   * the main page
   * 
   * @param result
   */
  public void showPage(ArrayList<HouseType> result) {
    if (resultListing == null) {
      resultListing = new ResultListingPage(model);
    }

    // if repeated search the engine, change the table and map
    if (!resultListing.isShowing()) {
      resultListing.changeTable(result);
      resultListing.clearMap();
      resultListing.resetInfo(result);
      resultListing.markMap();
      resultListing.show();

    } else {
      resultListing.changeTable(result);
      resultListing.clearMap();
      resultListing.resetInfo(result);
      resultListing.markMap();
    }
  }

  public static void main(String[] args) {
    BuyHomePage tp = new BuyHomePage();
    tp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    tp.setSize(1100, 700);
    tp.setResizable(false);
    tp.setVisible(true);
    tp.setLocationRelativeTo(null);

  }

  /**
   * this method is used to add action listener to the main page search buttons
   * and selection components
   */
  public void addActionListeners() {
    // the following three action listener is used to response to
    // the change of searching type in the main page
    radioButton1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        background.setIcon(new ImageIcon(img4));
        hint.setForeground(Color.white);
        type = HouseOption.RESIDENTIAL;
      }
    });

    radioButton2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        background.setIcon(new ImageIcon(img3));
        hint.setForeground(Color.black);
        type = HouseOption.COMMERCIAL;
      }
    });

    radioButton3.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        background.setIcon(new ImageIcon(img2));
        type = HouseOption.INDUSTRIAL;
      }
    });

    // this action listener response to the submit button and connect the
    // database to retrieve the houses information
    submit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        if (locationInput.getText().trim().length() == 0) { // Nothing is input!
                                                            // oops! show
                                                            // warning dialog
          JOptionPane.showMessageDialog(getContentPane(), "Please enter Address, Zip or Sites!!", "Error Message",
              JOptionPane.INFORMATION_MESSAGE);
        } else {

          if (pbarFrame != null)
            pbarFrame.dispose();

          pbarFrame = new ProgressBar();
          swingWorker();
        }

      }
    });
    
    submit2.addActionListener(new ActionListener(){
    	@Override
        public void actionPerformed(ActionEvent e) {
    		SellPage sp = new SellPage();
    	}
    });
  }

  /**
   * the swing worker is used to perform the connection to the database which is
   * time consuming, and ensures that it will not affect other operation by
   * user.
   */
  public void swingWorker() {

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() {
        // System.out.println("Here we are00");
        // List<HouseType> result = new ArrayList<HouseType>();
        DataBaseEngine db = new DataBaseEngine();// connect the database
        List<HouseType> result = db.getResultByLocation(locationInput.getText().trim(), type);// search
                                                                                              // the
                                                                                              // result
        // set the model's house list and house type
        model.setHouseList((ArrayList<HouseType>) result);
        model.setHouseType(type.toString());

        pbarFrame.stop();
        System.out.println("size is:" + result.size());
        showPage((ArrayList<HouseType>) result);

        System.out.println("size is:" + result.size());
        return null;
      }
    };

    worker.execute();

  }

}
