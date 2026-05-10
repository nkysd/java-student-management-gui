// LecturerActionListener.java
// This class handles the action events for the Lecturer GUI

import javax.swing.*; // For GUI components
import java.awt.event.*; // For button actions
import javax.swing.table.DefaultTableModel; // For table model
import java.util.ArrayList; // For ArrayList

public class LecturerActionListener implements ActionListener
{
    private MainGUI gui;
    private String action;

    // Constructor
    // Initialize the GUI and action
    public LecturerActionListener(MainGUI gui, String action)
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
        String staffId = gui.getStaffIdField().getText().trim();
        String lecturerName = gui.getLecturerNameField().getText().trim();
        String department = gui.getDepartmentField().getText().trim();

        // If any field is empty, show a message and return
        if (staffId.isEmpty() || lecturerName.isEmpty() || department.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please fill in all fields.");
            return;
        }

        // Create a new Lecturer object
        // and add it to the LecturerManager
        Lecturer lecturer = new Lecturer(staffId, lecturerName, department);
        boolean added = gui.getLecturerManager().addLecturer(lecturer);

        if (added)
        {
            // Add the new lecturer to the table model
            Object[] rowData =
            {
                staffId,
                lecturerName,
                department
            };
            gui.getLecturerTableModel().addRow(rowData);
            
            // Add lecturer to combo boxes
            gui.getLecturerCombo().addItem(lecturer);       // Add to the lecturer combo box
            gui.getUnitLecturerCombo().addItem(lecturer);   // Add to the unit lecturer combo box
            
            // Clear the input fields
            gui.getStaffIdField().setText("");
            gui.getLecturerNameField().setText("");
            gui.getDepartmentField().setText("");

            // Show success popup
            JOptionPane.showMessageDialog(null, "Lecturer added successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Staff ID already exists."); // ID already exists
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
        // Get input values from GUI
        String staffId = gui.getStaffIdField().getText().trim();
        String lecturerName = gui.getLecturerNameField().getText().trim();
        String department = gui.getDepartmentField().getText().trim();

        // If Staff ID is empty, show error message
        if (staffId.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Staff ID is required.");
            return;
        }

        // Lookup the Lecturer by Staff ID
        Lecturer lecturer = gui.getLecturerManager().lookupLecturer(staffId);

        // If lecturer is not found, show error message
        if (lecturer == null) // if null, it means the lecturer is not found
        {
            JOptionPane.showMessageDialog(null, "Lecturer not found.");
            return;
        }

        // Update other fields if provided
        // If the field is empty, it won't be updated
        if (!lecturerName.isEmpty()) lecturer.setLecturerName(lecturerName);
        if (!department.isEmpty()) lecturer.setDepartment(department);

        // Find and update the corresponding row in the table
        int rowCount = gui.getLecturerTableModel().getRowCount();
        for (int i = 0; i < rowCount; i++)
        {
            String tableId = (String) gui.getLecturerTableModel().getValueAt(i, 0);
            if (tableId.equals(staffId))
            {
                gui.getLecturerTableModel().setValueAt(lecturer.getLecturerName(), i, 1);
                gui.getLecturerTableModel().setValueAt(lecturer.getDepartment(), i, 2);
                break;
            }
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "Lecturer updated successfully.");
    }
    // ---------------------------------
    // End Update Button
    // ---------------------------------

    // ---------------------------------
    // Delete Button
    // ---------------------------------
    private void handleDelete()
    {
        // Get Staff ID from GUI
        String staffId = gui.getStaffIdField().getText().trim();

        // If Staff ID is empty, show error message
        if (staffId.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please enter Staff ID to delete.");
            return;
        }

        // Delete the student from the LecturerManager
        boolean deleted = gui.getLecturerManager().deleteLecturer(staffId);

        if (deleted)
        {
            // Remove the corresponding row from the table
            DefaultTableModel model = gui.getLecturerTableModel();

            // Find the row with the matching saff ID and remove it
            for (int i = 0; i < model.getRowCount(); i++)
            {
                if (model.getValueAt(i, 0).equals(staffId))
                {
                    model.removeRow(i);
                    break;
                }
            }
            JOptionPane.showMessageDialog(null, "Lecturer deleted.");

            // Clear input fields
            gui.getStaffIdField().setText("");
            gui.getLecturerNameField().setText("");
            gui.getDepartmentField().setText("");

        }
        else
        {
            // Show success popup
            JOptionPane.showMessageDialog(null, "Lecturer ID not found.");
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
        String criteria = "ID"; // Default is ID
        String searchValue = "";

        // Check the search panel for values
        if (gui.getLecturerSearchValueField() != null && !gui.getLecturerSearchValueField().getText().trim().isEmpty()) {
            criteria = (String) gui.getLecturerSearchCriteriaCombo().getSelectedItem();
            searchValue = gui.getLecturerSearchValueField().getText().trim();
        } else {
            // If not found, search by ID from input field
            // It's userd to search by ID
            searchValue = gui.getStaffIdField().getText().trim();
        }

        // If search value is empty, show error message
        if (searchValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a search value.");
            return;
        }

        // Clear the table
        DefaultTableModel model = gui.getLecturerTableModel();
        model.setRowCount(0);

        // List to store search results
        ArrayList<Lecturer> results = new ArrayList<>();

        // Execute search based on selected criteria
        switch (criteria) {
            case "ID":
                Lecturer lecturer = gui.getLecturerManager().lookupLecturer(searchValue);
                if (lecturer != null) {
                    results.add(lecturer);
                    // Update form fields as well
                    gui.getStaffIdField().setText(lecturer.getStaffId());
                    gui.getLecturerNameField().setText(lecturer.getLecturerName());
                    gui.getDepartmentField().setText(lecturer.getDepartment());
                }
                break;
                
            case "Name":
                results = gui.getLecturerManager().searchByName(searchValue);
                break;
                
            case "Department":
                results = gui.getLecturerManager().searchByDepartment(searchValue);
                break;
        }

        // Display search results in the table
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No lecturers found matching your search criteria.");
        } else {
            // Add each lecturer to the table
            for (Lecturer l : results) {
                model.addRow(new Object[]{
                    l.getStaffId(),
                    l.getLecturerName(),
                    l.getDepartment()
                });
            }
            
            JOptionPane.showMessageDialog(null, results.size() + " lecturer(s) found.");
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
        DefaultTableModel model = gui.getLecturerTableModel();

        // Clear the table before adding new data
        model.setRowCount(0);

        // Get all registed lecturers from the LecturerManager
        for (Lecturer lecturer : gui.getLecturerManager().getAllLecturers())
        {
            Object[] rowData =
            {
                lecturer.getStaffId(),
                lecturer.getLecturerName(),
                lecturer.getDepartment()
            };
            // Add the lecturer information to the table
            model.addRow(rowData);

        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "All lecturers displayed.");
    }
    // ---------------------------------
    // End List All Button
    // ---------------------------------

    // ---------------------------------
    // Refresh Button
    // ---------------------------------
    private void handleRefresh()
    {
        gui.getStaffIdField().setText("");
        gui.getLecturerNameField().setText("");
        gui.getDepartmentField().setText("");

        // Show success popup
        JOptionPane.showMessageDialog(null, "Lecturer form cleared.");
    }
    // ---------------------------------
    // End Refresh Button
    // ---------------------------------
}
