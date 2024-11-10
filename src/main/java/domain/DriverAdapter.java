package domain;

import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Date;

public class DriverAdapter implements TableModel {
    protected Driver driver;
    protected List<Ride> rList;
    
    // Define column headers
    private final String[] columnNames = {"From", "To", "Date", "Places", "Price"};
    private final Class<?>[] columnClasses = {String.class, String.class, Date.class, Integer.class, Double.class};

    public DriverAdapter(Driver d) {
        this.driver = d;
        this.rList = d.getCreatedRides();  // Populate ride list from the driver
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;  // Number of columns based on headers
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];  // Return column header
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];  // Return expected class for each column
    }

    @Override
    public int getRowCount() {
        return rList.size();  // Number of rides to display
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ride ride = rList.get(rowIndex);  // Get ride at the specified row index
        switch (columnIndex) {
            case 0: return ride.getFrom();
            case 1: return ride.getTo();
            case 2: return ride.getDate();
            case 3: return ride.getnPlaces();
            case 4: return ride.getPrice();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;  // Table cells are read-only in this example
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Not implemented because table is read-only
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        // Not needed for basic implementation
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        // Not needed for basic implementation
    }
}