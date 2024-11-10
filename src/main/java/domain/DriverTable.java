package domain;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

public class DriverTable extends JFrame{
	private Driver driver;
	private JTable	tabla;
	public DriverTable(Driver driver){
		super(driver.getUsername()+"’s	rides ");
		this.setBounds(100,	100,	700,	200);
		this.driver =	driver;
		DriverAdapter adapt = new DriverAdapter(driver);
		tabla =	new JTable(adapt);
		tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
		// Create a left-aligned cell renderer
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(DefaultTableCellRenderer.LEFT);

        // Apply the left-aligned renderer to all columns
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }
		//Creamos un JscrollPane y le agregamos la JTable
		JScrollPane	scrollPane = new JScrollPane(tabla);
		//Agregamos el JScrollPane al contenedor
		getContentPane().add(scrollPane,	BorderLayout.CENTER);
	}
}