// MainGUI.java

import javax.swing.*; // For GUI components
import java.awt.*; // For layout and component positioning
import javax.swing.table.DefaultTableModel; // For table model
import javax.swing.table.TableColumn; // For table column width
import javax.swing.table.DefaultTableCellRenderer; // For table cell rendering
import java.text.NumberFormat; // For number formatting (comma separated)

import java.awt.event.ActionEvent; // For ActionEvent
import java.awt.event.ActionListener; // For ActionListener


public class MainGUI {

    // --- Managers ---
    private StudentManager studentManager = new StudentManager();
    private LecturerManager lecturerManager = new LecturerManager();
    private UnitManager unitManager = new UnitManager();

    // --- GUI Components ---
    // Main frame
    private JFrame frame;

    // Tables
    private JTable studentTable;
    private JTable lecturerTable;
    private JTable unitTable;

    // Table models
    private DefaultTableModel studentTableModel;
    private DefaultTableModel lecturerTableModel;
    private DefaultTableModel unitTableModel;

    // --- Input fields ---
    // Student input fields
    private JTextField studentIdField, studentNameField, majorField, gpaField;
    private JComboBox<String> typeCombo;
    private JComboBox<Lecturer> studentLecturerCombo;

    // International student input fields (from Child class)
    private JTextField countryField, visaStatusField;

    // Lecturer input fields
    private JTextField staffIdField, lecturerNameField, departmentField;
    
    // Unit input fields
    private JTextField unitCodeField, unitNameField, creditPointField;
    private JComboBox<Unit> unitCombo;
    private JComboBox<Lecturer> unitLecturerCombo;

    // Search fields
    private JComboBox<String> searchCriteriaCombo;
    private JLabel searchValueLabel;
    private JTextField searchValueField;

    // Lecturer search fields
    private JComboBox<String> lecturerSearchCriteriaCombo;
    private JLabel lecturerSearchValueLabel;
    private JTextField lecturerSearchValueField;

    // Unit search fields
    private JComboBox<String> unitSearchCriteriaCombo;
    private JLabel unitSearchValueLabel;
    private JTextField unitSearchValueField;


    // Constructor
    public MainGUI()
    {
        // ----------------------------
        // GUI
        // ----------------------------
        // Create the main frame
        frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);

        // Create the tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Students", createStudentPanel());
        tabbedPane.addTab("Lecturers", createLecturerPanel());
        tabbedPane.addTab("Units", createUnitPanel());
        frame.add(tabbedPane);

        frame.setVisible(true);

         // Populate sample data for testing
        sampleData();

    }
    

    // Method
    // ----------------------
    // Create the student panel
    // ----------------------
    private JPanel createStudentPanel()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // --- Form Panel (Left) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font inputFont = new Font("Arial", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(150, 20);

        // Student ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        studentIdField = new JTextField();
        studentIdField.setPreferredSize(fieldSize);
        studentIdField.setFont(inputFont);
        formPanel.add(studentIdField, gbc);

        // Student Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        studentNameField = new JTextField();
        studentNameField.setPreferredSize(fieldSize);
        studentNameField.setFont(inputFont);
        formPanel.add(studentNameField, gbc);

        // Major
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Major:"), gbc);
        gbc.gridx = 1;
        majorField = new JTextField();
        majorField.setPreferredSize(fieldSize);
        majorField.setFont(inputFont);
        formPanel.add(majorField, gbc);

        // GPA
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("GPA:"), gbc);
        gbc.gridx = 1;
        gpaField = new JTextField();
        gpaField.setPreferredSize(fieldSize);
        gpaField.setFont(inputFont);
        formPanel.add(gpaField, gbc);

        // Lecturer
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Lecturer:"), gbc);
        gbc.gridx = 1;
        studentLecturerCombo = new JComboBox<>();
        studentLecturerCombo.setPreferredSize(fieldSize);
        studentLecturerCombo.setFont(inputFont);
        // Add lecturers from LecturerManager
        for (Lecturer l : lecturerManager.getAllLecturers())
        {
            studentLecturerCombo.addItem(l);
        }
        formPanel.add(studentLecturerCombo, gbc);

        // Unit
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Unit:"), gbc);
        gbc.gridx = 1;
        unitCombo = new JComboBox<>();
        unitCombo.setPreferredSize(fieldSize);
        unitCombo.setFont(inputFont);
        // Add units from UnitManager
        for (Unit u : unitManager.getAllUnits())
        {
            unitCombo.addItem(u);
        }
        formPanel.add(unitCombo, gbc);

        // Student Type
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Student Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"Undergraduate", "International"});
        typeCombo.setPreferredSize(fieldSize);
        typeCombo.setFont(inputFont);
        formPanel.add(typeCombo, gbc);

        // Country Field
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel countryLabel = new JLabel("Country:");
        formPanel.add(countryLabel, gbc);
        gbc.gridx = 1;
        countryField = new JTextField();
        countryField.setPreferredSize(fieldSize);
        countryField.setFont(inputFont);
        formPanel.add(countryField, gbc);

        // Visa Status Field
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel visaLabel = new JLabel("Visa Status:");
        formPanel.add(visaLabel, gbc);
        gbc.gridx = 1;
        visaStatusField = new JTextField();
        visaStatusField.setPreferredSize(fieldSize);
        visaStatusField.setFont(inputFont);
        formPanel.add(visaStatusField, gbc);


        // ActionListener for typeCombo
        // Show/Hide country and visa status fields based on selected type
        typeCombo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                boolean isInternational = typeCombo.getSelectedItem().equals("International");
                countryLabel.setVisible(isInternational);
                countryField.setVisible(isInternational);
                visaLabel.setVisible(isInternational);
                visaStatusField.setVisible(isInternational);
            }
        });

        // Initially hide country and visa status fields
        // based on the default selection
        boolean isInternational = typeCombo.getSelectedItem().equals("International");
        countryLabel.setVisible(isInternational);
        countryField.setVisible(isInternational);
        visaLabel.setVisible(isInternational);
        visaStatusField.setVisible(isInternational);


        // --- Table Panel (Right) ---
        String[] columnNames = {"ID", "Name", "Major", "GPA", "Type", "Cost", "Lecturer", "Country", "Visa Status"};
        studentTableModel = new DefaultTableModel(columnNames, 0);
        studentTable = new JTable(studentTableModel);
        JScrollPane tableScroll = new JScrollPane(studentTable);

        // Adjust column widths
        int[] columnWidths = {60, 120, 100, 50, 100, 80, 100};
        for (int i = 0; i < columnWidths.length; i++)
        {
            TableColumn column = studentTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Center renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Right renderer for numbers
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // Apply renderers
        for (int i = 0; i < studentTable.getColumnCount(); i++)
        {
            if (i == 3 || i == 5) { // GPA and Cost
                studentTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            } else {
                studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }


        // --- Button Panel (ottom) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        // Add buttons with implemented in StudentActionListener class
        JButton addButton = new JButton("Add");
        addButton.setBackground(Color.GREEN);
        addButton.setOpaque(true);
        addButton.addActionListener(new StudentActionListener(this, "Add"));
        buttonPanel.add(addButton);


        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new StudentActionListener(this, "Update"));
        updateButton.setBackground(Color.ORANGE);
        updateButton.setOpaque(true);
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new StudentActionListener(this, "Delete"));
        deleteButton.setBackground(Color.RED);
        deleteButton.setOpaque(true);
        buttonPanel.add(deleteButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new StudentActionListener(this, "Search"));
        buttonPanel.add(searchButton);

        JButton listButton = new JButton("List All");
        listButton.addActionListener(new StudentActionListener(this, "List All"));
        buttonPanel.add(listButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new StudentActionListener(this, "Refresh"));
        refreshButton.setBackground(Color.CYAN);
        refreshButton.setOpaque(true);
        buttonPanel.add(refreshButton);


        // --- Search Panel (Top right) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createSearchPanel(), BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);


        // --- Layout ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.WEST);

        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        

        return mainPanel;
    }
    // -----------------------
    // End of createStudentPanel
    // -----------------------


    // -----------------------
    // Create the lecturer panel
    // -----------------------
    private JPanel createLecturerPanel()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // --- Form Panel (Left) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font inputFont = new Font("Arial", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(150, 20);


        // Staff ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Staff ID:"), gbc);
        gbc.gridx = 1;
        staffIdField = new JTextField();
        staffIdField.setPreferredSize(fieldSize);
        staffIdField.setFont(inputFont);
        formPanel.add(staffIdField, gbc);

        // Lecturer Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        lecturerNameField = new JTextField();
        lecturerNameField.setPreferredSize(fieldSize);
        lecturerNameField.setFont(inputFont);
        formPanel.add(lecturerNameField, gbc);

        // Department
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        departmentField = new JTextField();
        departmentField.setPreferredSize(fieldSize);
        departmentField.setFont(inputFont);
        formPanel.add(departmentField, gbc);


        // --- Table Panel (Right) ---
        String[] columnNames = {"Staff ID", "Name", "Department"};
        lecturerTableModel = new DefaultTableModel(columnNames, 0);
        lecturerTable = new JTable(lecturerTableModel);
        JScrollPane tableScroll = new JScrollPane(lecturerTable);

        // Adjust column widths
        int[] columnWidths = {60, 120, 100};
        for (int i = 0; i < columnWidths.length; i++)
        {
            TableColumn column = lecturerTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Center renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Apply center renderer to all columns
        for (int i = 0; i < lecturerTable.getColumnCount(); i++)
        {
            lecturerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // --- Button Panel (Bottom) ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));

        // Add buttons with implemented in LecturerActionListener class
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new LecturerActionListener(this, "Add"));
        addButton.setBackground(Color.GREEN);
        addButton.setOpaque(true);
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new LecturerActionListener(this, "Update"));
        updateButton.setBackground(Color.ORANGE);
        updateButton.setOpaque(true);
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new LecturerActionListener(this, "Delete"));
        deleteButton.setBackground(Color.RED);
        deleteButton.setOpaque(true);
        buttonPanel.add(deleteButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new LecturerActionListener(this, "Search"));
        buttonPanel.add(searchButton);

        JButton listButton = new JButton("List All");
        listButton.addActionListener(new LecturerActionListener(this, "List All"));
        buttonPanel.add(listButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new LecturerActionListener(this, "Refresh"));
        refreshButton.setBackground(Color.CYAN);
        refreshButton.setOpaque(true);
        buttonPanel.add(refreshButton);


        // --- Search Panel (Top right) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createLecturerSearchPanel(), BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);


        // --- Layout ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.WEST);

        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    // -----------------------
    // End of createLecturerPanel
    // -----------------------


    // -----------------------
    // Create the unit panel
    // -----------------------
    private JPanel createUnitPanel()
    {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // --- Form Panel (Left) ---
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font inputFont = new Font("Arial", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(150, 20);

        // Unit Code
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Unit Code:"), gbc);
        gbc.gridx = 1;
        unitCodeField = new JTextField();
        unitCodeField.setPreferredSize(fieldSize);
        unitCodeField.setFont(inputFont);
        formPanel.add(unitCodeField, gbc);

        // Unit Name
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Unit Name:"), gbc);
        gbc.gridx = 1;
        unitNameField = new JTextField();
        unitNameField.setPreferredSize(fieldSize);
        unitNameField.setFont(inputFont);
        formPanel.add(unitNameField, gbc);

        // Credit Points
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Credit Points:"), gbc);
        gbc.gridx = 1;
        creditPointField = new JTextField();
        creditPointField.setPreferredSize(fieldSize);
        creditPointField.setFont(inputFont);
        formPanel.add(creditPointField, gbc);

        // Lecturer
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Lecturer:"), gbc);
        gbc.gridx = 1;
        unitLecturerCombo = new JComboBox<>();
        unitLecturerCombo.setPreferredSize(fieldSize);
        unitLecturerCombo.setFont(inputFont);
        formPanel.add(unitLecturerCombo, gbc);


        // --- Table Panel (Right) ---
        String[] columnNames = {"Unit Code", "Unit Name", "Credit Points", "Lecturer"};
        unitTableModel = new DefaultTableModel(columnNames, 0);
        unitTable = new JTable(unitTableModel);
        JScrollPane tableScroll = new JScrollPane(unitTable);

        // Adjust column widths
        int[] columnWidths = {60, 100, 100};
        for (int i = 0; i < columnWidths.length; i++)
        {
            TableColumn column = unitTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        // Center renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        // Right renderer for numbers
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        // Apply renderers
        for (int i = 0; i < unitTable.getColumnCount(); i++)
        {
            if (i == 2) { // Credit Points
                unitTable.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
            } else {
                unitTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        
        // Add buttons with implemented in UnitActionListener class
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new UnitActionListener(this, "Add"));
        addButton.setBackground(Color.GREEN);
        addButton.setOpaque(true);
        buttonPanel.add(addButton);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new UnitActionListener(this, "Update"));
        updateButton.setBackground(Color.ORANGE);
        updateButton.setOpaque(true);
        buttonPanel.add(updateButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new UnitActionListener(this, "Delete"));
        deleteButton.setBackground(Color.RED);
        deleteButton.setOpaque(true);
        buttonPanel.add(deleteButton);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new UnitActionListener(this, "Search"));
        buttonPanel.add(searchButton);

        JButton listButton = new JButton("List All");
        listButton.addActionListener(new UnitActionListener(this, "List All"));
        buttonPanel.add(listButton);

        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(new UnitActionListener(this, "Refresh"));
        refreshButton.setBackground(Color.CYAN);
        refreshButton.setOpaque(true);
        buttonPanel.add(refreshButton);


        // --- Search Panel (Top right) ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(createUnitSearchPanel(), BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);


        // --- Layout ---
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(wrapperPanel, BorderLayout.WEST);

        mainPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }
    // -----------------------
    // End of createUnitPanel
    // -----------------------

    // ----------------------------
    // Sample data for testing
    // ----------------------------
    private void sampleData()
    {
        // Lecturers
        Lecturer l1 = new Lecturer("L001", "Lisa Tira", "IT");
        Lecturer l2 = new Lecturer("L002", "John Doe", "Math");
        lecturerManager.addLecturer(l1);
        lecturerManager.addLecturer(l2);
        lecturerTableModel.addRow(new Object[]{l1.getStaffId(), l1.getLecturerName(), l1.getDepartment()});
        lecturerTableModel.addRow(new Object[]{l2.getStaffId(), l2.getLecturerName(), l2.getDepartment()});
        studentLecturerCombo.addItem(l1);
        studentLecturerCombo.addItem(l2);
        unitLecturerCombo.addItem(l1);
        unitLecturerCombo.addItem(l2);

        // Units
        Unit u1 = new Unit("U101", "Programming", 6, l1);
        Unit u2 = new Unit("U102", "Networking", 6, l2);
        unitManager.addUnit(u1);
        unitManager.addUnit(u2);
        unitTableModel.addRow(new Object[]{u1.getUnitCode(), u1.getUnitName(), u1.getCreditPoints(), u1.getLecturer()});
        unitTableModel.addRow(new Object[]{u2.getUnitCode(), u2.getUnitName(), u2.getCreditPoints(), u2.getLecturer()});
        unitCombo.addItem(u1);
        unitCombo.addItem(u2);

        // Students
        Student s1 = new UndergraduateStudent("S001", "Alice", "IT", 5.8, l1, u1);
        Student s2 = new InternationalStudent("S002", "Bob", "CS", 6.5, l2, u2);
        ((InternationalStudent) s2).setCountry("Japan");
        ((InternationalStudent) s2).setVisaStatus("Student Visa");

        studentManager.addStudent(s1);
        studentManager.addStudent(s2);


        // Format the cost for display (3-digit grouping)
        java.text.NumberFormat nf = NumberFormat.getInstance();

        // -- Additional functionality --
        // country and visa status for undergraduate students
        String country1 = ""; // No country for undergraduate students
        String visaStatus1 = ""; // No visa status for undergraduate students

        // Get country and visa status for international students
        String country2 = ((InternationalStudent) s2).getCountry();
        String visaStatus2 = ((InternationalStudent) s2).getVisaStatus();

        // Add students to the table model
        studentTableModel.addRow(new Object[]
        {
            s1.getStudentId(), s1.getStudentName(), s1.getMajor(), s1.getGpa(),
            s1.getType(), nf.format(calculateCost(s1.getType())), s1.getLecturer().getLecturerName(),
            country1, visaStatus1
        });

        studentTableModel.addRow(new Object[]
        {
            s2.getStudentId(), s2.getStudentName(), s2.getMajor(), s2.getGpa(),
            s2.getType(), nf.format(calculateCost(s2.getType())), s2.getLecturer().getLecturerName(),
            country2, visaStatus2
        });
    }
    // ----------------------------
    // End of sample data
    // ----------------------------


    // -----------------------
    // Getters for input fields and managers
    // -----------------------

    // Getters for Student Panel
    public JTextField getStudentIdField()
    {
        return studentIdField;
    }

    public JTextField getStudentNameField()
    {
        return studentNameField;
    }

    public JTextField getMajorField()
    {
        return majorField;
    }

    public JTextField getGpaField()
    {
        return gpaField;
    }

    public JComboBox<String> getTypeCombo()
    {
        return typeCombo;
    }

    public JComboBox<Lecturer> getLecturerCombo()
    {
        return studentLecturerCombo;
    }

    public JComboBox<Unit> getUnitCombo()
    {
        return unitCombo;
    }

    public StudentManager getStudentManager()
    {
        return studentManager;
    }

    public DefaultTableModel getStudentTableModel()
    {
        return studentTableModel;
    }

    public JTable getStudentTable()
    {
    return studentTable;
    }

    public int calculateCost(String type)
    {
        if (type.equals("International"))
        {
            return 12000;
        }
        else
        {
            return 8000;
        }
    }

    public JTextField getCountryField()
    {
    return countryField;
    }

    public JTextField getVisaStatusField()
    {
        return visaStatusField;
    }

    // Getters for Lecturer Panel
    public JTextField getStaffIdField()
    {
        return staffIdField;
    }

    public JTextField getLecturerNameField()
    {
        return lecturerNameField;
    }

    public JTextField getDepartmentField()
    {
        return departmentField;
    }

    public LecturerManager getLecturerManager()
    {
        return lecturerManager;
    }

    public DefaultTableModel getLecturerTableModel()
    {
        return lecturerTableModel;
    }

    // Getters for Unit Panel
    public JTextField getUnitCodeField()
    {
        return unitCodeField;
    }

    public JTextField getUnitNameField()
    {
        return unitNameField;
    }

    public JTextField getCreditPointField()
    {
        return creditPointField;
    }

    public JComboBox<Lecturer> getUnitLecturerCombo()
    {
        return unitLecturerCombo;
    }

    public UnitManager getUnitManager()
    {
        return unitManager;
    }

    public DefaultTableModel getUnitTableModel()
    {
        return unitTableModel;
    }  
    // -----------------------
    // End of getters
    // -----------------------
    

    // -----------------------
    //Main method to run the GUI
    // -----------------------
    public static void main(String[] args)
    {
        new MainGUI();
    }

    // -----------------------
    // Optional / Advanced Functionalities Implemented
    // -----------------------
    // Advanced Filtering and Searching
    // ------------------------
    // --- for Student ---
    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Search criteria dropdown
        JLabel criteriaLabel = new JLabel("Search by:");
        String[] criteria = {"ID", "Name", "Major", "Unit", "Lecturer"};
        searchCriteriaCombo = new JComboBox<>(criteria);

        // Search value field
        searchValueLabel = new JLabel("ID:");
        searchValueField = new JTextField(15);

        // Search button
        JButton quickSearchButton = new JButton("Quick Search");
        quickSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentActionListener(MainGUI.this, "Search").actionPerformed(e);
            }
        });

        // Add components to panel
        searchPanel.add(criteriaLabel);
        searchPanel.add(searchCriteriaCombo);
        searchPanel.add(searchValueLabel);
        searchPanel.add(searchValueField);
        searchPanel.add(quickSearchButton);

        // Update label when search criteria changes
        searchCriteriaCombo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) searchCriteriaCombo.getSelectedItem();
                searchValueLabel.setText(selected + ":");
            }
        });
        
        return searchPanel;
    }


    // --- for Lecturer ---
    private JPanel createLecturerSearchPanel()
    {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Search criteria dropdown
        JLabel criteriaLabel = new JLabel("Search by:");
        String[] criteria = {"ID", "Name", "Department"};
        lecturerSearchCriteriaCombo = new JComboBox<>(criteria);

        // Search value field
        lecturerSearchValueLabel = new JLabel("ID:");
        lecturerSearchValueField = new JTextField(15);

        // Search button
        JButton quickSearchButton = new JButton("Quick Search");
        quickSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LecturerActionListener(MainGUI.this, "Search").actionPerformed(e);
            }
        });

        // Add components to panel
        searchPanel.add(criteriaLabel);
        searchPanel.add(lecturerSearchCriteriaCombo);
        searchPanel.add(lecturerSearchValueLabel);
        searchPanel.add(lecturerSearchValueField);
        searchPanel.add(quickSearchButton);

        // Update label when search criteria changes
        lecturerSearchCriteriaCombo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) lecturerSearchCriteriaCombo.getSelectedItem();
                lecturerSearchValueLabel.setText(selected + ":");
            }
        });
        
        return searchPanel;
    }


    // --- for Unit ---
    private JPanel createUnitSearchPanel()
    {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        // Search criteria dropdown
        JLabel criteriaLabel = new JLabel("Search by:");
        String[] criteria = {"Code", "Name", "Credit Points", "Lecturer"};
        unitSearchCriteriaCombo = new JComboBox<>(criteria);

        // Search value field
        unitSearchValueLabel = new JLabel("Code:");
        unitSearchValueField = new JTextField(15);

        // Search button
        JButton quickSearchButton = new JButton("Quick Search");
        quickSearchButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UnitActionListener(MainGUI.this, "Search").actionPerformed(e);
            }
        });

        // Add components to panel
        searchPanel.add(criteriaLabel);
        searchPanel.add(unitSearchCriteriaCombo);
        searchPanel.add(unitSearchValueLabel);
        searchPanel.add(unitSearchValueField);
        searchPanel.add(quickSearchButton);

        // Update label when search criteria changes
        unitSearchCriteriaCombo.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) unitSearchCriteriaCombo.getSelectedItem();
                unitSearchValueLabel.setText(selected + ":");
            }
        });
        
        return searchPanel;
    }


    // Getters for search fields
    public JComboBox<String> getSearchCriteriaCombo()
    {
        return searchCriteriaCombo;
    }

    public JTextField getSearchValueField()
    {
        return searchValueField;
    }

    public JComboBox<String> getLecturerSearchCriteriaCombo()
    {
        return lecturerSearchCriteriaCombo;
    }

    public JTextField getLecturerSearchValueField()
    {
        return lecturerSearchValueField;
    }

    public JComboBox<String> getUnitSearchCriteriaCombo()
    {
        return unitSearchCriteriaCombo;
    }

    public JTextField getUnitSearchValueField()
    {
        return unitSearchValueField;
    }

    // ------------------------
    // End advanced filtering and searching
    // ------------------------
}
