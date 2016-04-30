package GUIPage;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class ProgressBar extends JFrame{
	private JProgressBar pbar;
	private boolean stopFlag = false;
	
	public ProgressBar(){
		initProgressBar();
		updateBar();
	}
	
	public void initProgressBar(){
		  final int PROGRESS_MINIMUM = 0;

		  final int PROGRESS_MAXIMUM = 100;
		  // initialize Progress Bar
		  pbar = new JProgressBar();

		  pbar.setMinimum(PROGRESS_MINIMUM);
		  pbar.setMaximum(PROGRESS_MAXIMUM);		  
		  pbar.setValue(0);

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
		  p.add(hint, BorderLayout.NORTH);					// What if the ProgressBar is just in dialog, instead of JPanel?
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
	
	 public void updateBar(int newValue) {
		    pbar.setValue(newValue);
	}
	
	public void updateBar() {
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override 
					protected Void doInBackground() throws Exception {
						
						int k;
						
						for ( k = pbar.getMinimum(); k < pbar.getMaximum(); k++ ){
							final int value = k;
							pbar.setValue(value);
							pbar.setString(value + "%");
							
							if( stopFlag ) {
								pbar.setValue(100);
								pbar.setString("100" + "%");
								Thread.sleep(1000);
								dispose();
								break;
							}
							
							Thread.sleep(600);
						}
						
						while( k == pbar.getMaximum() ) {
							if( stopFlag ) {
								pbar.setValue(100);
								pbar.setString("100" + "%");
								Thread.sleep(1000);
								dispose();
								break;
							}
//							Thread.sleep(100);
						}
						
						return null;
					}
				};
				
				worker.execute();
		
	  }
	
	public void stop(){
		stopFlag = true;
	}
	
	public static void main(String[] args) {
		ProgressBar pbarFrame = new ProgressBar();
	}

}
