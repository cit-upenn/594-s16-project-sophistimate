package GUIPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class Table extends JFrame{
	JTable table;
	
	public Table(){
		setLayout(new FlowLayout());
		
		String[] columnNames = {"Number", "Location", "Owner", "Price"};
		
		Object[][] data = {
				{"1", "4400 Walnut", "Tianxiang", "$99999"},
				{"2", "4400 Walnut", "Hanyu", "$1"},
				{"3", "4413 Spruce", "Baile", "$99999"},
				{"4", "3131 Walnut", "Tianxiang", "$99999"},
				{"5", "3913 Pine", "Hanyu", "$1"}
		};
		
		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		table.setBackground(null);
		table.setDefaultEditor(Object.class, null);    /* Set the table into non-editable */
		
	}
	
	public JScrollPane addTableComp(){
		return new JScrollPane(table);
	}
}
