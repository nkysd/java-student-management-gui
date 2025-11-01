public class InternationalStudent extends Student
{
    private String country = "Unknown";     // Default country
    private String visaStatus = "Valid";    // Default visa status

    // Constructor
    public InternationalStudent(String studentId, String studentName, String major, double gpa)
    {
        super(studentId, studentName, major, gpa); // Call the constructor of the superclass (Student)
        setType("International"); // Set the type to "International"
    }

    // Constructor with additional parameters
    // This constructor allows you to set the lecturer and unit for the international student
    public InternationalStudent(String studentId, String studentName, String major, double gpa, Lecturer lecturer, Unit unit)
    {
        super(studentId, studentName, major, gpa);
        setType("International");
        setLecturer(lecturer);
        setUnit(unit);
    }

    // --- Getter and Setter for Country ---
    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    // --- Getter and Setter for Visa Status ---
    public String getVisaStatus()
    {
        return visaStatus;
    }

    public void setVisaStatus(String visaStatus)
    {
        this.visaStatus = visaStatus;
    }

    // For comboBox display
    @Override
    public String toString()
    {
        return super.toString() + ", Country: " + country + ", Visa Status: " + visaStatus;
    }
}
