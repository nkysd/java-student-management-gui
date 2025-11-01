/*-------------------------
    Student ID - s8107356
    Name - Mio Mizutani
-------------------------*/

// UnitManager.java
// This class manages a list of Unit objects.
// It provides methods to add, delete, update, and lookup units.
// It also provides a method to retrieve all units in the list.
// It includes a constructor to initialize the unit list.

import java.util.ArrayList; // For ArrayList

public class UnitManager
{
    // Field
    // Declare a list to store Unit objects
    private ArrayList<Unit> unitList;

    // Constructor
    public UnitManager()
    {
        unitList = new ArrayList<>(); // Initialize the list
    }

    // Unit add Methods
    public boolean addUnit(Unit unit)
    {
        for (Unit u : unitList)
        {
            if (u.getUnitCode().equals(unit.getUnitCode()))
            {
                return false; // Code already exists
            }
        }
        unitList.add(unit); // Add the new unit to the list
        return true; // Successfully added
    }

    // Unit delete Methods
    public boolean deleteUnit(String unitCode)
    {
        for (int i = 0; i < unitList.size(); i++)
        {
            if (unitList.get(i).getUnitCode().equals(unitCode))
            {
                unitList.remove(i);
                return true; // Successfully deleted
            }
        }
        return false; // Not found
    }

    // Unit update Methods
    public boolean updateUnit(String unitCode, String unitName, int creditPoints, Lecturer lecturer)
    {
        for (Unit u : unitList)
        {
            if (u.getUnitCode().equals(unitCode))
            {
                u.setUnitName(unitName);
                u.setCreditPoints(creditPoints);
                u.setLecturer(lecturer);
                return true; // Successfully updated
            }
        }
        return false; // Not found
    }

    // Unit lookup Methods
    public Unit lookupUnit(String unitCode)
    {
        for (Unit u : unitList)
        {
            if (u.getUnitCode().equals(unitCode))
            {
                return u;
            }
        }
        return null;
    }

    // Display all units Methods
    public ArrayList<Unit> getAllUnits()
    {
        return unitList;
    }

    // Unit search Methods (partial match)
    public ArrayList<Unit> searchByName(String name) {
        ArrayList<Unit> results = new ArrayList<>();
        String searchName = name.toLowerCase();
        
        for (Unit u : unitList) {
            if (u.getUnitName().toLowerCase().contains(searchName)) {
                results.add(u);
            }
        }
        return results;
    }

    // Unit search Methods (exact match)
    public ArrayList<Unit> searchByCreditPoints(int creditPoints) {
        ArrayList<Unit> results = new ArrayList<>();
        
        for (Unit u : unitList) {
            if (u.getCreditPoints() == creditPoints) {
                results.add(u);
            }
        }
        return results;
    }

    // Lecturer search Methods (partial match)
    public ArrayList<Unit> searchByLecturer(Lecturer lecturer) {
        ArrayList<Unit> results = new ArrayList<>();
        
        if (lecturer == null) {
            return results;
        }
        
        for (Unit u : unitList) {
            if (u.getLecturer() != null && u.getLecturer().getStaffId().equals(lecturer.getStaffId())) {
                results.add(u);
            }
        }
        return results;
    }
}
