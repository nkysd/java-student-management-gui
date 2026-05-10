// UnitActionListener.java
// This class handles the action events for the Unit GUI

import javax.swing.*; // For GUI components
import java.awt.event.*; // For button actions
import javax.swing.table.DefaultTableModel; // For table model
import java.util.ArrayList; // For ArrayList

public class UnitActionListener implements ActionListener
{
    private MainGUI gui;
    private String action;

    // Constructor
    // Initialize the GUI and action
    public UnitActionListener(MainGUI gui, String action)
    {
        this.gui = gui;
        this.action = action;
    }

    // Action listener method
    // Handle button clicks based on the action
    public void actionPerformed(ActionEvent e)
    {
        switch (action)
        {
            case "Add":
                handleAdd();
                break;
            case "Update":
                handleUpdate();
                break;
            case "Delete":
                handleDelete();
                break;
            case "Search":
                handleSearch();
                break;
            case "List All":
                handleListAll();
                break;
            case "Refresh":
                handleRefresh();
                break;
        }
    }

    // --- Handle the action for each button ---
    // ---------------------------------
    // Add Button
    // ---------------------------------
    private void handleAdd()
    {
        // Get input values from GUI
        String unitCode = gui.getUnitCodeField().getText().trim();
        String unitName = gui.getUnitNameField().getText().trim();
        String creditText = gui.getCreditPointField().getText().trim();
        Lecturer lecturer = (Lecturer) gui.getUnitLecturerCombo().getSelectedItem();

        // Parse credit points to integer
        int creditPoints;
        try
        {
            creditPoints = Integer.parseInt(creditText);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Credit points must be a number.");
            return;
        }

        // If any field is empty, show error message
        // and return without adding
        if (unitCode.isEmpty() || unitName.isEmpty() || creditText.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        // Create a new Unit object
        // and add it to the UnitManager
        Unit unit = new Unit(unitCode, unitName, creditPoints, lecturer);
        boolean added = gui.getUnitManager().addUnit(unit);

        if (added)
        {
            // Add the new unit to the table model
            Object[] rowData = {
                unitCode,
                unitName,
                creditPoints,
                (lecturer != null ? lecturer.getLecturerName() : "")
            };
            gui.getUnitTableModel().addRow(rowData);
            
            // Add unit to combo box
            gui.getUnitCombo().addItem(unit);    // Add unit to the combo box
    
            // Clear the input fields
            gui.getUnitCodeField().setText("");
            gui.getUnitNameField().setText("");
            gui.getCreditPointField().setText("");
            gui.getUnitLecturerCombo().setSelectedItem(null);

            // Show success popup
            JOptionPane.showMessageDialog(null, "Unit added successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Unit Code already exists."); // ID already exists
        }
    }
    // ---------------------------------
    // End Add Button
    // ---------------------------------

    // ---------------------------------
    // Update Button
    // ---------------------------------
    private void handleUpdate()
    {
        // Get Unit Code from GUI
        String unitCode = gui.getUnitCodeField().getText().trim();
        String unitName = gui.getUnitNameField().getText().trim();
        String creditText = gui.getCreditPointField().getText().trim();
        Lecturer lecturer = (Lecturer) gui.getUnitLecturerCombo().getSelectedItem();

        // If Unit Code is empty, show error message
        if (unitCode.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Unit Code is required.");
            return;
        }

        // Look up the unit by Unit Code
        Unit unit = gui.getUnitManager().lookupUnit(unitCode);

        // Update Credit Point if provided
        if (!creditText.isEmpty())
        {
            try
            {
                int creditPoints = Integer.parseInt(creditText);
                unit.setCreditPoints(creditPoints); // Update credit points
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Credit points must be an integer.");
                return;
            }
        }

        // Update other fields if provided
        // If the field is empty, it won't be updated
        if (!unitName.isEmpty())  unit.setUnitName(unitName);
        unit.setLecturer(lecturer);

        // Find and update the corresponding row in the Unit table
        int rowCount = gui.getUnitTableModel().getRowCount();
        for (int i = 0; i < rowCount; i++)
        {
            String tableCode = (String) gui.getUnitTableModel().getValueAt(i, 0);

            if (tableCode.equals(unitCode)) // If matched
            {
                gui.getUnitTableModel().setValueAt(unit.getUnitName(), i, 1);
                gui.getUnitTableModel().setValueAt(unit.getCreditPoints(), i, 2);
                gui.getUnitTableModel().setValueAt(
                    (lecturer != null) ? lecturer.getLecturerName() : "", i, 3);
                break;
            }
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "Unit updated successfully.");

    }
    // ---------------------------------
    // End Update Button
    // ---------------------------------

    // ---------------------------------
    // Delete Button
    // ---------------------------------
    private void handleDelete()
    {
        // Get Unit Code from GUI
        String unitCode = gui.getUnitCodeField().getText().trim();

        // If Unit Code is empty, show error message
        if (unitCode.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please enter Unit Code to delete.");
            return;
        }

        // Delete the unit from the UnitManager
        boolean deleted = gui.getUnitManager().deleteUnit(unitCode);

        if (deleted)
        {
            // Remove the unit from the table model
            DefaultTableModel model = gui.getUnitTableModel();

            // Find the row with the matching Unit Code
            for (int i = 0; i < model.getRowCount(); i++)
            {
                if (model.getValueAt(i, 0).equals(unitCode))
                {
                    model.removeRow(i);
                    break;
                }
            }

            // Clear input fields
            gui.getUnitCodeField().setText("");
            gui.getUnitNameField().setText("");
            gui.getCreditPointField().setText("");
            if (gui.getLecturerCombo().getItemCount() > 0) gui.getLecturerCombo().setSelectedIndex(0);

            // Show success popup
            JOptionPane.showMessageDialog(null, "Unit deleted successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Unit Code not found.");
        }
    }
    // ---------------------------------
    // End Delete Button
    // ---------------------------------

    // ---------------------------------
    // Search Button
    // ---------------------------------
    private void handleSearch()
    {
        // Get the selected search criteria and value
        String criteria = "Code"; // Default is Code
        String searchValue = "";

        // Check the search panel for values
        if (gui.getUnitSearchValueField() != null && !gui.getUnitSearchValueField().getText().trim().isEmpty()) {
            criteria = (String) gui.getUnitSearchCriteriaCombo().getSelectedItem();
            searchValue = gui.getUnitSearchValueField().getText().trim();
        } else {
            // If not found, search by code from input field
            // It's used for searching by code
            searchValue = gui.getUnitCodeField().getText().trim();
        }

        // If the search value is empty, show error message
        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search value.");
            return;
        }

        // Clear the table
        DefaultTableModel model = gui.getUnitTableModel();
        model.setRowCount(0);

        // Store search results
        ArrayList<Unit> results = new ArrayList<>();

        // Execute search based on selected criteria
        switch (criteria)
        {
            case "Code":
                Unit unit = gui.getUnitManager().lookupUnit(searchValue);
                if (unit != null)
                {
                    results.add(unit);
                    // Update form fields
                    gui.getUnitCodeField().setText(unit.getUnitCode());
                    gui.getUnitNameField().setText(unit.getUnitName());
                    gui.getCreditPointField().setText(String.valueOf(unit.getCreditPoints()));
                    gui.getUnitLecturerCombo().setSelectedItem(unit.getLecturer());
                }
                break;
                
            case "Name":
                results = gui.getUnitManager().searchByName(searchValue);
                break;
                
            case "Credit Points":
                try
                {
                    int creditPoints = Integer.parseInt(searchValue);
                    results = gui.getUnitManager().searchByCreditPoints(creditPoints);
                } catch (NumberFormatException ex)
                {
                    JOptionPane.showMessageDialog(null, "Credit points must be a number.");
                    return;
                }
                break;
                
            case "Lecturer":
                // Search by lecturer name (partial match)
                for (Lecturer lecturer : gui.getLecturerManager().getAllLecturers())
                {
                    if (lecturer.getLecturerName().toLowerCase().contains(searchValue.toLowerCase()))
                    {
                        results.addAll(gui.getUnitManager().searchByLecturer(lecturer));
                    }
                }
                break;
        }

        // Display search results in the table
        if (results.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "No units found matching your search criteria.");
        } else {
            // Add each unit to the table
            for (Unit u : results)
            {
                model.addRow(new Object[]
                {
                    u.getUnitCode(),
                    u.getUnitName(),
                    u.getCreditPoints(),
                    (u.getLecturer() != null) ? u.getLecturer().getLecturerName() : ""
                });
            }
            
            JOptionPane.showMessageDialog(null, results.size() + " unit(s) found.");
        }
    }
    // ---------------------------------
    // End Search Button
    // ---------------------------------

    // ---------------------------------
    // List All Button
    // ---------------------------------
    private void handleListAll()
    {
        // Get the table model from the GUI
        DefaultTableModel model = gui.getUnitTableModel();

        // Clear the table before adding new data
        model.setRowCount(0); // clear

        // Get all registered units from the UnitManager
        for (Unit unit : gui.getUnitManager().getAllUnits())
        {
            Object[] rowData =
            {
                unit.getUnitCode(),
                unit.getUnitName(),
                unit.getCreditPoints(),
                (unit.getLecturer() != null ? unit.getLecturer().getLecturerName() : "")
            };
            // Add the unit information to the table
            model.addRow(rowData);
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "All units displayed.");
    }
    // ---------------------------------
    // End List All Button
    // ---------------------------------

    // ---------------------------------
    // Refresh Button
    // ---------------------------------
    private void handleRefresh()
    {
        // Clear all input fields
        gui.getUnitCodeField().setText("");
        gui.getUnitNameField().setText("");
        gui.getCreditPointField().setText("");

        if (gui.getUnitLecturerCombo().getItemCount() > 0)
        {
            gui.getUnitLecturerCombo().setSelectedIndex(0);
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "Unit form cleared.");
    }
    // ---------------------------------
    // End Refresh Button
    // ---------------------------------
}
