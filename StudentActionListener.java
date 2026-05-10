// StudentActionListener.java
// This class handles the actions for the Student Management GUI

import javax.swing.*; // For GUI components
import java.awt.event.*; // For button action
import javax.swing.table.DefaultTableModel; // For table model
import java.text.NumberFormat; // For formatting numbers (comma-separated)
import java.util.ArrayList; // For using ArrayList


public class StudentActionListener implements ActionListener
{
    private MainGUI gui;
    private String action;

    // Constructor
    // Initializes the GUI and action
    public StudentActionListener(MainGUI gui, String action)
    {
        this.gui = gui;
        this.action = action;
    }

    // ActionListener method
    // Handles the action based on the button clicked
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
        String studentId = gui.getStudentIdField().getText().trim();
        String studentName = gui.getStudentNameField().getText().trim();
        String major = gui.getMajorField().getText().trim();
        String gpaText = gui.getGpaField().getText().trim();
        String type = (String) gui.getTypeCombo().getSelectedItem();
        Lecturer lecturer = (Lecturer) gui.getLecturerCombo().getSelectedItem();
        Unit unit = (Unit) gui.getUnitCombo().getSelectedItem();

        // If GPA is empty, set it to 0.0
        // If GPA is not empty, parse it to double
        double gpa = 0.0;
        if (!gpaText.isEmpty())
        {
            try
            {
                gpa = Double.parseDouble(gpaText);
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Invalid GPA. Please enter a numeric value or leave blank.");
                return;
            }
        }

        // If Student ID, Name, or Major is empty, show error message
        // and return without adding the student
        if (studentId.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Student ID cannot be empty.");
            return;
        }

        if (studentName.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Name cannot be empty.");
            return;
        }

        if (major.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Major cannot be empty.");
            return;
        }

        // Create a new Student object
        // and add it to the StudentManager

        // Check the type of student and create the appropriate object
        // Use the constructor of the appropriate subclass
        Student student;
        if (type.equals("Undergraduate"))
        {
            student = new UndergraduateStudent(studentId, studentName, major, gpa);
        } else if (type.equals("International"))
        {
            InternationalStudent intStudent = new InternationalStudent(studentId, studentName, major, gpa);
            
            // InternationalStudent specific fields
            // Get the country and visa status from the GUI
            // If the fields are empty, set them to default values
            String country = gui.getCountryField().getText().trim();
            String visaStatus = gui.getVisaStatusField().getText().trim();
            
            // Set the country and visa status if provided
            // If the field is empty, it won't be updated
            if (!country.isEmpty()) intStudent.setCountry(country);
            if (!visaStatus.isEmpty()) intStudent.setVisaStatus(visaStatus);
            
            student = intStudent;
        } else {
            student = new UndergraduateStudent(studentId, studentName, major, gpa); // fallback
        }

        // Add the lecturer and unit if provided
        // If the field is empty, it won't be updated
        if (lecturer != null) student.setLecturer(lecturer);
        if (unit != null) student.setUnit(unit);

        // Add the student to the StudentManager
        boolean added = gui.getStudentManager().addStudent(student);

        if (added)
        {
            // Format the cost for display (3-digit grouping)
            // The cost is calculated in the StudentManager
            int rawCost = gui.calculateCost(type);
            String costFormatted = NumberFormat.getInstance().format(rawCost);


            // Add the student to the table model
            String country = "";
            String visaStatus = "";
            if (student instanceof InternationalStudent) {
                InternationalStudent intStudent = (InternationalStudent) student;
                country = intStudent.getCountry();
                visaStatus = intStudent.getVisaStatus();
            }

            Object[] rowData = {
                studentId,
                studentName,
                major,
                gpa,
                type,
                costFormatted,
                (lecturer != null) ? lecturer.getLecturerName() : "",
                country,
                visaStatus
            };
            gui.getStudentTableModel().addRow(rowData);

            // Clear input fields
            gui.getStudentIdField().setText("");
            gui.getStudentNameField().setText("");
            gui.getMajorField().setText("");
            gui.getGpaField().setText("");
            gui.getTypeCombo().setSelectedIndex(0);

            /*  Used there were no sample data
            // Reset lecturer combo safely (Ignore if no items)
            if (gui.getLecturerCombo().getItemCount() > 0)
            {
                gui.getLecturerCombo().setSelectedIndex(0);
            }

            // Reset unit combo safely (Ignore if no items)
            if (gui.getUnitCombo().getItemCount() > 0)
            {
                gui.getUnitCombo().setSelectedIndex(0);
            }
            */

            // Show success popup
            JOptionPane.showMessageDialog(null, "Student added successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Student ID already exists."); // ID already exists
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
        String studentId = gui.getStudentIdField().getText().trim();
        String studentName = gui.getStudentNameField().getText().trim();
        String major = gui.getMajorField().getText().trim();
        String gpaText = gui.getGpaField().getText().trim();
        String type = (String) gui.getTypeCombo().getSelectedItem();
        Lecturer lecturer = (Lecturer) gui.getLecturerCombo().getSelectedItem();
        Unit unit = (Unit) gui.getUnitCombo().getSelectedItem();

        // If Student ID is empty, show error message
        if (studentId.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Student ID is required.");
            return;
        }

        // Look up the student by Student ID
        Student student = gui.getStudentManager().lookupStudent(studentId);

        // If student is not found, show error message
        if (student == null)
        {
            JOptionPane.showMessageDialog(null, "Student ID not found.");
            return;
        }

        // Update GPA if provided (Ensure the valiable type)
        if (!gpaText.isEmpty())
        {
            try
            {
                double gpa = Double.parseDouble(gpaText);
                student.setGpa(gpa);
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, "Invalid GPA. Please enter a numeric value.");
                return;
            }
        }

        // Update other fields if provided
        // If the field is empty, it won't be updated
        if (!studentName.isEmpty())  student.setStudentName(studentName);
        if (!major.isEmpty()) student.setMajor(major);
        student.setType(type);
        student.setLecturer(lecturer);
        student.setUnit(unit);

        // International Student specific fields
        // Check if the student is an InternationalStudent
        if (student instanceof InternationalStudent) {
            InternationalStudent intStudent = (InternationalStudent) student;
            String country = gui.getCountryField().getText().trim();
            String visaStatus = gui.getVisaStatusField().getText().trim();
            
            if (!country.isEmpty()) intStudent.setCountry(country);
            if (!visaStatus.isEmpty()) intStudent.setVisaStatus(visaStatus);
        }

        // Find and update the corresponding row in the table
        int rowCount = gui.getStudentTableModel().getRowCount();
        for (int i = 0; i < rowCount; i++)
        {
            String tableId = (String) gui.getStudentTableModel().getValueAt(i, 0);
            if (tableId.equals(studentId))
            {
                java.text.NumberFormat nf = NumberFormat.getInstance(); // Number format (comma-separated)
                gui.getStudentTableModel().setValueAt(student.getStudentName(), i, 1);
                gui.getStudentTableModel().setValueAt(student.getMajor(), i, 2);
                gui.getStudentTableModel().setValueAt(student.getGpa(), i, 3);
                gui.getStudentTableModel().setValueAt(student.getType(), i, 4);
                gui.getStudentTableModel().setValueAt(nf.format(gui.calculateCost(type)), i, 5);
                gui.getStudentTableModel().setValueAt(
                    (lecturer != null) ? lecturer.getLecturerName() : "", i, 6);

                // 以下を追加
                String country = "";
                String visaStatus = "";
                if (student instanceof InternationalStudent) {
                    InternationalStudent intStudent = (InternationalStudent) student;
                    country = intStudent.getCountry();
                    visaStatus = intStudent.getVisaStatus();
                }
                gui.getStudentTableModel().setValueAt(country, i, 7);
                gui.getStudentTableModel().setValueAt(visaStatus, i, 8);
                break;
            }
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "Student updated successfully.");
    }
    // ---------------------------------
    // End Update Button
    // ---------------------------------

    // ---------------------------------
    // Delete Button
    // ---------------------------------
    private void handleDelete()
    {
        // Get Student ID from GUI
        String studentId = gui.getStudentIdField().getText().trim();

        // If Student ID is empty, show error message
        if (studentId.isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Please enter a Student ID to delete.");
            return;
        }

        // Delete the student from the StudentManager
        boolean deleted = gui.getStudentManager().deleteStudent(studentId);

        if (deleted)
        {
            // Remove the corresponding row from the table
            DefaultTableModel model = gui.getStudentTableModel();
            int rowCount = model.getRowCount();

            // Find the row with the matching student ID and remove it
            for (int i = 0; i < rowCount; i++)
            {
                String id = (String) model.getValueAt(i, 0);
                if (id.equals(studentId))
                {
                    model.removeRow(i);
                    break;
                }
            }

            // Clear input fields
            gui.getStudentIdField().setText("");
            gui.getStudentNameField().setText("");
            gui.getMajorField().setText("");
            gui.getGpaField().setText("");
            gui.getTypeCombo().setSelectedIndex(0);
            if (gui.getLecturerCombo().getItemCount() > 0) gui.getLecturerCombo().setSelectedIndex(0);
            if (gui.getUnitCombo().getItemCount() > 0) gui.getUnitCombo().setSelectedIndex(0);
            
            // Show success popup
            JOptionPane.showMessageDialog(null, "Student deleted successfully.");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Student ID not found."); // ID not found
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
    // Get the search criteria and value from the GUI
    String criteria = "ID"; // Default is ID
    String searchValue = "";

    // Get values from the search panel
    if (gui.getSearchValueField() != null && !gui.getSearchValueField().getText().trim().isEmpty())
    {
        criteria = (String) gui.getSearchCriteriaCombo().getSelectedItem();
        searchValue = gui.getSearchValueField().getText().trim();
    } else
    {
        // If not found, searching by ID from input field
        // It's used for searching by ID
        searchValue = gui.getStudentIdField().getText().trim();
    }

    // Show an error message if the search value is empty
    if (searchValue.isEmpty())
    {
        JOptionPane.showMessageDialog(null, "Please enter a search value.");
        return;
    }

    // Clear the table
    DefaultTableModel model = gui.getStudentTableModel();
    model.setRowCount(0);

    // Cost display format
    java.text.NumberFormat nf = java.text.NumberFormat.getInstance();

    // Search results list
    ArrayList<Student> results = new ArrayList<>();

    // Execute search based on selected criteria
    switch (criteria)
    {
        case "ID":
            Student student = gui.getStudentManager().lookupStudent(searchValue);
            if (student != null)
            {
                results.add(student);
                // Update form fields as well
                updateFormFields(student);
            }
            break;
            
        case "Name":
            results = gui.getStudentManager().searchByName(searchValue);
            break;
            
        case "Major":
            results = gui.getStudentManager().searchByMajor(searchValue);
            break;
            
        case "Unit":
            // Search for a Unit by name (partial match)
            for (Unit unit : gui.getUnitManager().getAllUnits())
            {
                if (unit.getUnitName().toLowerCase().contains(searchValue.toLowerCase()))
                {
                    results.addAll(gui.getStudentManager().searchByUnit(unit));
                }
            }
            break;
            
        case "Lecturer":
            // Search for a Lecturer by name (partial match)
            for (Lecturer lecturer : gui.getLecturerManager().getAllLecturers())
            {
                if (lecturer.getLecturerName().toLowerCase().contains(searchValue.toLowerCase()))
                {
                    results.addAll(gui.getStudentManager().searchByLecturer(lecturer));
                }
            }
            break;
    }

    // Display search results in the table
    if (results.isEmpty())
    {
        JOptionPane.showMessageDialog(null, "No students found matching your search criteria.");
    }
    else
        // Add each student to the table
        for (Student s : results) {
            String country = "";
            String visaStatus = "";
            if (s instanceof InternationalStudent)
            {
                InternationalStudent intStudent = (InternationalStudent) s;
                country = intStudent.getCountry();
                visaStatus = intStudent.getVisaStatus();
            }
            
            // Add the student to the table model
            model.addRow(new Object[]
            {
                s.getStudentId(),
                s.getStudentName(),
                s.getMajor(),
                s.getGpa(),
                s.getType(),
                nf.format(gui.calculateCost(s.getType())),
                (s.getLecturer() != null) ? s.getLecturer().getLecturerName() : "",
                country,
                visaStatus
            });
        }
        
        JOptionPane.showMessageDialog(null, results.size() + " student(s) found.");
    }
    
    // Method to update form fields with student data
    // This method is called when a student is found during the search
    private void updateFormFields(Student student)
    {
        // Clear all input fields
        gui.getStudentIdField().setText(student.getStudentId());
        gui.getStudentNameField().setText(student.getStudentName());
        gui.getMajorField().setText(student.getMajor());
        gui.getGpaField().setText(String.valueOf(student.getGpa()));
        gui.getTypeCombo().setSelectedItem(student.getType());
        
        if (student.getLecturer() != null)
            gui.getLecturerCombo().setSelectedItem(student.getLecturer());
        
        if (student.getUnit() != null)
            gui.getUnitCombo().setSelectedItem(student.getUnit());
        
        // If the student is an InternationalStudent, set the country and visa status
        if (student instanceof InternationalStudent)
        {
            InternationalStudent intStudent = (InternationalStudent) student;
            gui.getCountryField().setText(intStudent.getCountry());
            gui.getVisaStatusField().setText(intStudent.getVisaStatus());
        }else{
            gui.getCountryField().setText("");
            gui.getVisaStatusField().setText("");
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
        DefaultTableModel model = gui.getStudentTableModel();

        // Clear the table before adding new data
        model.setRowCount(0);

        // Number format (comma-separated)
        java.text.NumberFormat nf = NumberFormat.getInstance();

        // Get all registered students from the StudentManager
        for (Student student : gui.getStudentManager().getAllStudents()) {
            String country = "";
            String visaStatus = "";
            if (student instanceof InternationalStudent) {
                InternationalStudent intStudent = (InternationalStudent) student;
                country = intStudent.getCountry();
                visaStatus = intStudent.getVisaStatus();
            }
            
            Object[] rowData = {
                student.getStudentId(),
                student.getStudentName(),
                student.getMajor(),
                student.getGpa(),
                student.getType(),
                nf.format(gui.calculateCost(student.getType())),
                (student.getLecturer() != null) ? student.getLecturer().getLecturerName() : "",
                country,
                visaStatus
            };
            model.addRow(rowData);
        }

        // Show success popup
        JOptionPane.showMessageDialog(null, "All student records have been displayed.");
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
        gui.getStudentIdField().setText("");
        gui.getStudentNameField().setText("");
        gui.getMajorField().setText("");
        gui.getGpaField().setText("");

        if (gui.getTypeCombo().getItemCount() > 0)
            gui.getTypeCombo().setSelectedIndex(0);

        if (gui.getLecturerCombo().getItemCount() > 0)
            gui.getLecturerCombo().setSelectedIndex(0);

        if (gui.getUnitCombo().getItemCount() > 0)
            gui.getUnitCombo().setSelectedIndex(0);

        // Clear also the country and visa status fields
        gui.getCountryField().setText("");
        gui.getVisaStatusField().setText("");

        // Show success popup
        JOptionPane.showMessageDialog(null, "Form cleared.");
    }
    // ---------------------------------
    // End Refresh Button
    // ---------------------------------
    }
