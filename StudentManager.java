// StudentManager.java
// This class manages a list of Student objects, allowing for adding, updating, deleting, and looking up students.
// It also provides a method to retrieve all students in the list.
// It includes a constructor to initialize the student list.
// It uses an ArrayList to store the Student objects.

import java.util.ArrayList; // For ArrayList

public class StudentManager
{
    // Declare a list to store Student objects
    private ArrayList<Student> studentList;

    // Constructor
    public StudentManager()
    {
        studentList = new ArrayList<>(); // Initialize the list
    }

    // Student add Methods
    public boolean addStudent(Student student)
    {
        for (Student s : studentList)
        {
            if (s.getStudentId().equals(student.getStudentId()))
            {
                return false; // ID already exists
            }
        }
        studentList.add(student); // Add the new student to the list
        return true; // Successfully added
    }

    // Student update Methods
    public boolean updateStudent(String studentId, String name, String major, double gpa, String type, Lecturer lecturer, Unit unit)
    {
    for (Student s : studentList)
    {
        if (s.getStudentId().equals(studentId)) // Check if the student ID matches
        {
            s.setStudentName(name);
            s.setMajor(major);
            s.setGpa(gpa);
            s.setType(type);
            s.setLecturer(lecturer);
            s.setUnit(unit);
            return true; // Successfully updated
        }
    }
    return false; // Not found
    }


    // Student delete Methods
    public boolean deleteStudent(String studentId)
    {
        for (int i = 0; i < studentList.size(); i++)
        {
            if (studentList.get(i).getStudentId().equals(studentId))
            {
                studentList.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // Not found
    }

    // Student lookup Methods
    public Student lookupStudent(String studentId)
    {
        for (Student s : studentList)
        {
            if (s.getStudentId().equals(studentId))
            {
                return s;
            }
        }
        return null;
    }

    // Display all students Methods
    public ArrayList<Student> getAllStudents()
    {
        return studentList;
    }

    // Student search Methods (partial match)
    public ArrayList<Student> searchByName(String name) {
        ArrayList<Student> results = new ArrayList<>();
        String searchName = name.toLowerCase();
        
        for (Student s : studentList) {
            if (s.getStudentName().toLowerCase().contains(searchName)) {
                results.add(s);
            }
        }
        return results;
    }

    // Student search by major Methods (partial match)
    public ArrayList<Student> searchByMajor(String major) {
        ArrayList<Student> results = new ArrayList<>();
        String searchMajor = major.toLowerCase();
        
        for (Student s : studentList) {
            if (s.getMajor().toLowerCase().contains(searchMajor)) {
                results.add(s);
            }
        }
        return results;
    }

    // Student search by unit Methods (partial match)
    public ArrayList<Student> searchByUnit(Unit unit) {
        ArrayList<Student> results = new ArrayList<>();
        
        if (unit == null) {
            return results;
        }
        
        for (Student s : studentList) {
            if (s.getUnit() != null && s.getUnit().getUnitCode().equals(unit.getUnitCode())) {
                results.add(s);
            }
        }
        return results;
    }

    // Student search by lecturer Methods (partial match)
    public ArrayList<Student> searchByLecturer(Lecturer lecturer) {
        ArrayList<Student> results = new ArrayList<>();
        
        if (lecturer == null) {
            return results;
        }
        
        for (Student s : studentList) {
            if (s.getLecturer() != null && s.getLecturer().getStaffId().equals(lecturer.getStaffId())) {
                results.add(s);
            }
        }
        return results;
    }
}
