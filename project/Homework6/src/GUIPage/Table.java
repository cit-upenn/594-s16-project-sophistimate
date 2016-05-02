package GUIPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

/**
 * 
 * This class used to showing results in result page
 * @author Sophisitimate
 *
 */
public class Table extends JFrame{
	JTable table;
	String[] columnNames = {"Number", "Location", "Owner", "Price"};		/* Initialization */
	Object[][] data = {
			{"1", "4400 Walnut", "Tianxiang", "$99999"},
			{"2", "4400 Walnut", "Hanyu", "$1"},
			{"3", "4413 Spruce", "Baile", "$99999"},
			{"4", "3131 Walnut", "Tianxiang", "$99999"},
			{"5", "3913 Pine", "Hanyu", "$1"}
	};
	
	/**
	 * This is constructor for Table class
	 */
	public Table(){
		setLayout(new FlowLayout());
		table = new JTable(this.data, this.columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		table.setBackground(null);
		table.setDefaultEditor(Object.class, null);    /* Set the table into non-editable */
	}
	
	/**
	 * This is constructor takes two args
	 * @param columnNames This arg taken to show column names
	 * @param data this 2D array showing all results in table
	 */
	public Table(String[] columnNames, Object[][] data){
		setLayout(new FlowLayout());
		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500, 50));
		table.setFillsViewportHeight(true);
		table.setBackground(null);
		table.setDefaultEditor(Object.class, null);    /* Set the table into non-editable */
	}
	
	/**
	 * This is setter class takes two args
	 * @param columnNames This arg taken to show column names
	 * @param data this 2D array showing all results to fill the table
	 */
	public void setData(String[] columnNames, Object[][] data){   /* Change the content in the table */
		this.columnNames = columnNames;
		this.data = data;
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setRowCount(0);
		tableModel.setColumnIdentifiers(columnNames);
		for( int j = 0; j < data.length; j++ ){
			tableModel.addRow(data[j]);
		}
		table.setModel(tableModel);
		tableModel.fireTableDataChanged();
	}
	
	/**
	 * This is class to put table in scrollable panel
	 * @return return Scrollable panel
	 */
	public JScrollPane addTableComp(){
		return new JScrollPane(table);
	}
}
