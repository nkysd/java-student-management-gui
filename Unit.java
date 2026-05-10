// Unit.java
// This class represents a Unit with attributes such as unitCode, unitName, creditPoints, and lecturer.
// It includes methods to get and set these attributes, as well as a method to display the unit's name in a JComboBox.

public class Unit
{
    private String unitCode;
    private String unitName;
    private int creditPoints;
    private Lecturer lecturer;

    public Unit(String unitCode, String unitName, int creditPoints, Lecturer lecturer)
    {
        this.unitCode = unitCode;
        this.unitName = unitName;
        this.creditPoints = creditPoints;
        this.lecturer = lecturer;
    }

    // Getters
    public String getUnitCode()
    {
        return unitCode;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public int getCreditPoints()
    {
        return creditPoints;
    }

    public Lecturer getLecturer()
    {
        return lecturer;
    }

    // Setters
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }
    
    public void setCreditPoints(int creditPoints)
    {
        this.creditPoints = creditPoints;
    }

    public void setLecturer(Lecturer lecturer)
    {
        this.lecturer = lecturer;
    }

    // For ComboBox display
    @Override
    public String toString()
    {
        return unitName;
    }
}
