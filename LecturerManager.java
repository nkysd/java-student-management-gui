// LecturerManager.java
// This class manages a list of Lecturer objects.
// It provides methods to add, delete, update, and lookup lecturers.
// It also provides a method to retrieve all lecturers in the list.
// It includes a constructor to initialize the lecturer list.

import java.util.ArrayList; // For ArrayList

public class LecturerManager
{
    // Field
    // Declare a list to store Lecturer objects
    private ArrayList<Lecturer> lecturerList;

    // Constructor
    public LecturerManager()
    {
        lecturerList = new ArrayList<>(); // Initialize the list
    }

    // Lecturer add Methods
    public boolean addLecturer(Lecturer lecturer)
    {
        for (Lecturer l : lecturerList)
        {
            if (l.getStaffId().equals(lecturer.getStaffId()))
            {
                return false; // ID already exists
            }
        }
        lecturerList.add(lecturer); // Add the new lecturer to the list
        return true; // Successfully added
    }

    // Lecturer delete Methods
    public boolean deleteLecturer(String staffId)
    {
        for (int i = 0; i < lecturerList.size(); i++)
        {
            if (lecturerList.get(i).getStaffId().equals(staffId))
            {
                lecturerList.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // Not found
    }

    // Lecturer update Methods
    public boolean updateLecturer(String staffId, String lecturerName, String department)
    {
        for (Lecturer l : lecturerList)
        {
            if (l.getStaffId().equals(staffId))
            {
                l.setLecturerName(lecturerName);
                l.setDepartment(department);
                return true; // Successfully updated
            }
        }
        return false; // Not found
    }

    // Lecturer lookup Methods
    public Lecturer lookupLecturer(String staffId)
    {
        for (Lecturer l : lecturerList)
        {
            if (l.getStaffId().equals(staffId))
            {
                return l;
            }
        }
        return null;
    }

    // Display all lecturers Methods
    public ArrayList<Lecturer> getAllLecturers()
    {
        return lecturerList;
    }

    // Method to get the number of lecturers
    public ArrayList<Lecturer> searchByName(String name) {
        ArrayList<Lecturer> results = new ArrayList<>();
        String searchName = name.toLowerCase();
        
        for (Lecturer l : lecturerList) {
            if (l.getLecturerName().toLowerCase().contains(searchName)) {
                results.add(l);
            }
        }
        return results;
    }

    // Method to search lecturers by department (Partial match)
    public ArrayList<Lecturer> searchByDepartment(String department) {
        ArrayList<Lecturer> results = new ArrayList<>();
        String searchDept = department.toLowerCase();
        
        for (Lecturer l : lecturerList) {
            if (l.getDepartment().toLowerCase().contains(searchDept)) {
                results.add(l);
            }
        }
        return results;
    }
}
