// Student.java
// This class represents a Student with attributes such as studentId, studentName, major, gpa, type, lecturer, and unit.
// It includes methods to get and set these attributes, as well as a method to display the student's information in a JComboBox.

// It implements the Person interface, which requires methods to get the student ID and name.
public class Student implements Person
{
    private String studentId;
    private String studentName;
    private String major;
    private double gpa;

    private String type;
    private Lecturer lecturer;
    private Unit unit;


    // Constructor
    public Student(String studentId, String studentName, String major, double gpa, String type, Lecturer lecturer, Unit unit)
    {
        this.studentId = studentId;
        this.studentName = studentName;
        this.major = major;
        this.gpa = gpa;
        this.type = type;
        this.lecturer = lecturer;
        this.unit = unit;
    }

    // Superclass for InternationalStudent and UndergraduateStudent
    public Student(String studentId, String studentName, String major, double gpa)
    {
        this.studentId = studentId;
        this.studentName = studentName;
        this.major = major;
        this.gpa = gpa;
    }


    // Getters
    public String getStudentId()
    {
        return studentId;
    }

    public String getStudentName()
    {
        return studentName;
    }

    public String getMajor()
    {
        return major;
    }

    public double getGpa()
    {
        return gpa;
    }

    public String getType()
    {
        return type;
    }

    public Lecturer getLecturer()
    {
        return lecturer;
    }

    public Unit getUnit()
    {
        return unit;
    }

    // Setters
    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public void setGpa(double gpa)
    {
        this.gpa = gpa;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setLecturer(Lecturer lecturer)
    {
        this.lecturer = lecturer;
    }

    public void setUnit(Unit unit)
    {
        this.unit = unit;
    }

    // For ComboBox display
    @Override
    public String toString()
    {
        return "ID: " + studentId + ", Name: " + studentName + ", Major: " + major + ", GPA: " + gpa + ", Type: " + type
        + ", Lecturer: " + (lecturer != null ? lecturer.getLecturerName() : "None")
        + ", Unit: " + (unit != null ? unit.getUnitName() : "None");
    }

    // Implement Person interface
    @Override
    public String getId()
    {
        return studentId;
    }

    @Override
    public String getName()
    {
        return studentName;
    }
}
