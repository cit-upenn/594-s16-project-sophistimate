package GUIPage;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

/**
 * This is class used to create a progress bar to show
 * 
 * @author Sophisitimate
 *
 */
public class ProgressBar extends JFrame {
  private JProgressBar pbar;
  private boolean stopFlag = false;

  /**
   * This is ProgressBar constructor used to init class
   */
  public ProgressBar() {
    initProgressBar();
    updateBar();
//    this.setTitle("Data Processing");
  }

  /**
   * This is init method used to initialize the progress
   */
  public void initProgressBar() {
    final int PROGRESS_MINIMUM = 0;
    
    pbar = new JProgressBar();
    setLayout(new BorderLayout());
    // add to JPanel
    JPanel p = new JPanel();
    p.setLayout(new BorderLayout());
    p.setPreferredSize(new Dimension(100, 80));
    p.add(pbar, BorderLayout.CENTER);
    JLabel white = new JLabel("                                      ");
    JLabel hint = new JLabel("                           Data Loading……");
    hint.setAlignmentX(CENTER_ALIGNMENT);
    p.add(white, BorderLayout.NORTH);
    p.add(hint, BorderLayout.NORTH); // What if the ProgressBar is just in
                                     // dialog, instead of JPanel?
    add(p);
    setResizable(false);
    pbar.setVisible(true);
    pbar.setIndeterminate(false);
    pbar.setPreferredSize(new Dimension(100, 100));
    pbar.setBorderPainted(true);
    pbar.setStringPainted(true);

    this.setAlwaysOnTop(true);
    setVisible(true);
    setSize(300, 80);
    setLocationRelativeTo(null);
  }

  /**
   * This is method to update progress bar to show new percentage
   * 
   * @param newValue
   *          the new value we will show in our progress bar
   */
  public void updateBar(int newValue) {
    pbar.setValue(newValue);
  }

  /**
   * This is updateBar class takes no args to update progrss by 1
   */
  public void updateBar() {

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

      @Override
      protected Void doInBackground() throws Exception {

        int k;

        for (k = pbar.getMinimum(); k < pbar.getMaximum(); k++) {
          final int value = k;
          pbar.setValue(value);
          pbar.setString(value + "%");

          if (stopFlag) {
            pbar.setValue(100);
            pbar.setString("100" + "%");
            Thread.sleep(1500);
            dispose();
            break;
          }

          Thread.sleep(80); /* Change the speed of the progress bar */
        }

        while (k == pbar.getMaximum()) {
          if (stopFlag) {
            pbar.setValue(100);
            pbar.setString("100" + "%");
            Thread.sleep(1500);
            dispose();
            break;
          }
          Thread.sleep(10);
        }

        return null;
      }
    };

    worker.execute();

  }

  /**
   * This is class to stop showing progress bar
   */
  public void stop() {
    stopFlag = true;
  }

}
