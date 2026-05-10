// Lecturer.java
// This class represents a Lecturer with attributes such as staffId, lecturerName, and department.
// It includes methods to get and set these attributes, as well as a method to display the lecturer's name in a JComboBox.

public class Lecturer implements Person
{
    private String staffId;
    private String lecturerName;
    private String department;

    // Constructor
    public Lecturer(String staffId, String lecturerName, String department)
    {
        this.staffId = staffId;
        this.lecturerName = lecturerName;
        this.department = department;
    }

    // Getters
    public String getStaffId()
    {
        return staffId;
    }

    public String getLecturerName()
    {
        return lecturerName;
    }

    public String getDepartment()
    {
        return department;
    }

    // Setters
    public void setLecturerName(String lecturerName)
    {
        this.lecturerName = lecturerName;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    // For ComboBox display
    @Override
    public String toString()
    {
        return lecturerName;  // Display lecturerName in the JComboBox
    }

    // Person interface methods
    @Override
    public String getId()
    {
        return staffId;
    }

    @Override
    public String getName()
    {
        return lecturerName;
    }
}
