/*-------------------------
    Student ID - s8107356
    Name - Mio Mizutani
-------------------------*/

// PersonManager.java
// This class manages a collection of objects implementing the Person interface.
// It demonstrates polymorphism by handling both Student and Lecturer objects
// through their common interface.

import java.util.ArrayList;
import java.util.List;

public class PersonManager
{
    // Declare a list to store Person objects
    private List<Person> people;

    // Constructor
    public PersonManager()
    {
        people = new ArrayList<>();
    }

    // Add a Person object
    public boolean addPerson(Person person)
    {
        // Check for duplicate ID
        for (Person p : people) {
            if (p.getId().equals(person.getId()))
            {
                return false; // Duplicate ID, do not add
            }
        }
        
        people.add(person);
        return true;
    }

    // Search for a Person by ID
    public Person findById(String id)
    {
        for (Person p : people)
        {
            if (p.getId().equals(id))
            {
                return p;
            }
        }
        return null;
    }

    // Search for a Person by name (partial match)
    public List<Person> findByName(String namePattern)
    {
        List<Person> result = new ArrayList<>();
        for (Person p : people)
        {
            if (p.getName().contains(namePattern)) {
                result.add(p);
            }
        }
        return result;
    }

    // Search for a Person by type
    public List<Person> getByType(Class<?> type)
    {
        List<Person> result = new ArrayList<>();
        for (Person p : people)
        {
            if (type.isInstance(p))
            {
                result.add(p);
            }
        }
        return result;
    }

    // Get all Students
    public List<Person> getStudents()
    {
        return getByType(Student.class);
    }

    // Get all Lecturers
    public List<Person> getLecturers()
    {
        return getByType(Lecturer.class);
    }

    // Get all Persons
    public List<Person> getAllPeople()
    {
        return new ArrayList<>(people);
    }

    // Remove a Person
    public boolean removePerson(String id)
    {
        for (int i = 0; i < people.size(); i++)
        {
            if (people.get(i).getId().equals(id))
            {
                people.remove(i);
                return true;
            }
        }
        return false;
    }
}
