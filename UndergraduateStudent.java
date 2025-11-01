public class UndergraduateStudent extends Student
{
    // Constructor
    public UndergraduateStudent(String studentId, String studentName, String major, double gpa)
    {
        super(studentId, studentName, major, gpa);
        setType("Undergraduate");
    }

    // Constructor with additional parameters
    // This constructor allows you to set the lecturer and unit for the undergraduate student
    public UndergraduateStudent(String studentId, String studentName, String major, double gpa, Lecturer lecturer, Unit unit)
    {
        super(studentId, studentName, major, gpa);
        setType("Undergraduate");
        setLecturer(lecturer);
        setUnit(unit);
    }
}
