package vaxapp_package;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class VaxApp extends JFrame {

    // Jaydenn
    // Frame setting
    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 500;

    // Jaydenn
    // Fields for user data entry
    private String date;
    private String city;
    private String numPfizer; // get string from user, then parse into Integer type (Recorder.java))
    private String numModerna; // get string from user, then parse into Integer type (Recorder.java))
    private String numAZ; // get string from user, then parse into Integer type (Recorder.java))

    private String vaccineType; // radio button selection result

    // Jaydenn
    // Internal frames for Add and Search popups
    private final JDesktopPane desktopPane;
    private JInternalFrame intFrameAddRecord;
    private JInternalFrame intFrameSearchRecord;

    // Jaydenn
    // JPanels for Record Adding
    private JPanel addRecordBasePanel; // the base panel for text and button
    private JPanel addRecordInputPanel; // the upper panel for data input

    // Jaydenn
    // JPanels for Record Searching
    private JPanel searchRecordBasePanel; // the base panel for text and button
    private JPanel searchInputPanel; // the upper panel for data search

    // Jaydenn
    // Label to identify the fields for Record Adding
    private JLabel instructionAddLabel;
    private JLabel dateAddLabel;
    private JLabel cityAddLabel;
    private JLabel numPfizerAddLabel;
    private JLabel numModernaAddLabel;
    private JLabel numAZAddLabel;


    // Jaydenn
    // Label to identify the fields for Record Searching
    private JLabel instructionSearchLabel;
    private JLabel dateSearchLabel;
    private JLabel citySearchLabel;
    private JLabel vaccineSearchLabel; // for radio buttons

    // Jaydenn
    // Fields for data input for Recording Adding
    JTextField dateAddTextField;
    JTextField cityAddTextField;
    JTextField numPfizerInput;
    JTextField numModernaInput;
    JTextField numAZInput;

    // Jaydenn
    // Fields for data input for Recording Searching
    JTextField dateSearchTextField;
    JTextField citySearchTextField;

    // Jaydenn
    // Radio Buttons for Record Searching
    ButtonGroup group;
    private JRadioButton allButton;
    private JRadioButton pfizerButton;
    private JRadioButton modernaButton;
    private JRadioButton azButton;

    // Jaydenn
    // Submit Button for Record Adding
    private JButton submitButton;

    // Jaydenn
    // Submit Button for Record Searching
    private JButton searchButton;

    //Kaiyan
    //labels & panels for displayResult
    private JLabel searchResultLabel;
    private JLabel cityLabel;
    private JLabel cityResultLabel;

    private JLabel dateLabel;
    private JLabel dateResultLabel;

    private JLabel pfizerLabel;
    private JLabel pfizerResultLabel;

    private JLabel modernaLabel;
    private JLabel modernaResultLabel;

    private JLabel azLabel;
    private JLabel azResultLabel;

    private JPanel displayPanel;
    private JPanel mainPanel;

    // Jaydenn
    // add a success or error message to the result panel on east
    // the components for success or error message
    private JPanel errorMsgPanel;
    private String listString = "Welcome!";
    private ArrayList<String> executionResultMsg;
    JLabel executionResultMsgLabel;


    // Jaydenn & Kaiyan
    public VaxApp() {

        // setup the frame
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle("VaxApp");

        // put together menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuFile = new JMenu("Option");
        menuFile.add(createAddRecordMenuItem()); // JMenuItem (create record) add to JMenuBar
        menuFile.add(createSearchRecordMenuItem());// JMenuItm (search data) add to JMenuBar
        menuBar.add(menuFile);

        // put together the result panel to the frame
        displayResult();

        // create desktop frame and add to Frame
        desktopPane = new JDesktopPane();
        add(desktopPane, BorderLayout.CENTER);
    }



    //Kaiyan
    public void displayResult() {
        //create search result labels
        searchResultLabel = new JLabel("Search Result");
        cityLabel = new JLabel("City:");
        cityResultLabel = new JLabel();

        dateLabel = new JLabel("Date:");
        dateResultLabel = new JLabel();

        pfizerLabel = new JLabel("Pfizer: ");
        pfizerResultLabel = new JLabel();

        modernaLabel = new JLabel("Moderna: ");
        modernaResultLabel = new JLabel();

        azLabel = new JLabel("AstraZeneca: ");
        azResultLabel = new JLabel();

        //create search result panels
        displayPanel = new JPanel();
        mainPanel = new JPanel();

        //set the display panel (show the result) with grid layout
        displayPanel.setLayout(new GridLayout(5,2));
        //add labels in display panel
        displayPanel.add(cityLabel);
        displayPanel.add(cityResultLabel);
        displayPanel.add(dateLabel);
        displayPanel.add(dateResultLabel);
        displayPanel.add(pfizerLabel);
        displayPanel.add(pfizerResultLabel);
        displayPanel.add(modernaLabel);
        displayPanel.add(modernaResultLabel);
        displayPanel.add(azLabel);
        displayPanel.add(azResultLabel);

        // error/success message
        executionResultMsgLabel = new JLabel(listString, JLabel.CENTER);

        errorMsgPanel = new JPanel();
        errorMsgPanel.add(executionResultMsgLabel);


        //set main panel to border layout, add title and results in it
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(searchResultLabel,BorderLayout.NORTH);
        mainPanel.add(displayPanel,BorderLayout.CENTER);
        mainPanel.add(errorMsgPanel, BorderLayout.EAST);

        //resize the main panel
        mainPanel.setPreferredSize(new Dimension(600, 120));

        //add main panel to the north of frame
        add(mainPanel,BorderLayout.NORTH);

    }



    // Kaiyan
    // create the menu option to Create Record
    public JMenuItem createAddRecordMenuItem() {
        JMenuItem createRecord = new JMenuItem("Create Record");

        // add eventListener to trigger the internal frame
        ActionListener menuButtonClick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createIntFrameAddRecord();
            }
        };

        // add eventListener to the menu item
        createRecord.addActionListener(menuButtonClick);

        return createRecord;
    }

    // Kaiyan
    public JMenuItem createSearchRecordMenuItem() {
        JMenuItem searchRecord = new JMenuItem("Search Record");

        // add eventListener to trigger the internal frame

        ActionListener menuSearchClick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                createIntFrameSearchRecord();
            }
        };

        // add eventListener to the menu item
        searchRecord.addActionListener(menuSearchClick);

        return searchRecord;
    }




    // Jaydenn
    // create internal frame for add record feature
    public void createIntFrameAddRecord() {
        intFrameAddRecord = new JInternalFrame("Add/Update a Record", true, true, true, true);
        desktopPane.add(intFrameAddRecord);
        intFrameAddRecord.setBounds(50, 30, 450, 250);
        intFrameAddRecord.add(createAddRecordPanel(), BorderLayout.CENTER);
        intFrameAddRecord.setVisible(true);
    }

    // create internal frame for search record feature
    public void createIntFrameSearchRecord() {
        intFrameSearchRecord = new JInternalFrame("Search a Record", true, true, true, true);
        desktopPane.add(intFrameSearchRecord);
        intFrameSearchRecord.setBounds(50, 30, 450, 250);
        intFrameSearchRecord.add(createSearchRecordPanel(), BorderLayout.CENTER);
        intFrameSearchRecord.setVisible(true);
    }


    // Jaydenn
    // create a panel for record input
    // put input contents into the panel
    public JPanel createAddRecordPanel() {

        // create the base panel
        addRecordBasePanel = new JPanel();
        addRecordBasePanel.setLayout(new BorderLayout());

        // create an upper panel for input boxes
        addRecordInputPanel = new JPanel();
        addRecordInputPanel.setLayout(new GridLayout(5, 2));

        // create components for base panel
        instructionAddLabel = new JLabel("Enter Data Below", JLabel.CENTER);
        submitButton = new JButton("Submit");

        // create components for upper panel
        // create the text part
        dateAddLabel = new JLabel("Date of vaccination:* ");
        cityAddLabel = new JLabel("Name of city:* ");
        numPfizerAddLabel = new JLabel("Number of Pfizer:* ");
        numModernaAddLabel = new JLabel("Number of Moderna:* ");
        numAZAddLabel = new JLabel("Number of AstraZeneca:* ");

        // create the input box part
        dateAddTextField = new JTextField("mm/dd/yyyy (e.g. 12/01/2022)", 10);
//        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//        JFormattedTextField dateInput = new JFormattedTextField(format);

        cityAddTextField = new JTextField(10);
        numPfizerInput = new JTextField(10);
        numModernaInput = new JTextField(10);
        numAZInput = new JTextField(10);

        // put together the upper panel
        addRecordInputPanel.add(dateAddLabel);
//        addRecordPanel.add(dateInput);
        addRecordInputPanel.add(dateAddTextField);
        addRecordInputPanel.add(cityAddLabel);
        addRecordInputPanel.add(cityAddTextField);
        addRecordInputPanel.add(numPfizerAddLabel);
        addRecordInputPanel.add(numPfizerInput);
        addRecordInputPanel.add(numModernaAddLabel);
        addRecordInputPanel.add(numModernaInput);
        addRecordInputPanel.add(numAZAddLabel);
        addRecordInputPanel.add(numAZInput);

        // put together the base panel
        addRecordBasePanel.add(instructionAddLabel, BorderLayout.NORTH);
        addRecordBasePanel.add(addRecordInputPanel, BorderLayout.CENTER);
        addRecordBasePanel.add(submitButton, BorderLayout.SOUTH);

        // add actionListener to submit button
        class createRecordListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // test use
//                System.out.println("submit button hit");

                // record user input data and save to variables
                createRecordDataCollector();

                // call write function and send stored user input value to it
                try {
                    executionResultMsg = Recorder.submitRecord(date, city, numPfizer, numModerna, numAZ);
                    displayAddResult(executionResultMsg);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        }

        ActionListener listener = new createRecordListener();
        submitButton.addActionListener(listener);

        // return panel
        return addRecordBasePanel;
    }

    // Jaydenn
    // get user data from create record GUI fields and save to variable
    public void createRecordDataCollector() {
        date = dateAddTextField.getText();
        city = cityAddTextField.getText();
        numPfizer = numPfizerInput.getText();
        numModerna = numModernaInput.getText();
        numAZ = numAZInput.getText();

        // test use
//        System.out.println(date + city + numPfizer + numModerna + numAZ);
    }


    // Jaydenn
    // create a panel for search input
    // put input contents into the panel
    public JPanel createSearchRecordPanel() {
        // create base panel
        searchRecordBasePanel = new JPanel();
        searchRecordBasePanel.setLayout(new BorderLayout());

        // create upper panel
        searchInputPanel = new JPanel();
        searchInputPanel.setLayout(new GridLayout(3, 2));

        // create components for base panel
        // title text
        instructionSearchLabel = new JLabel("Enter Search Criteria", JLabel.CENTER);

        // search button
        searchButton = new JButton("Search");

        // create components for upper panel
        // text labels
        dateSearchLabel = new JLabel("Date:* ");
        citySearchLabel = new JLabel("City: *");
        vaccineSearchLabel = new JLabel("Vaccine Type:* ");

        // text fields
        dateSearchTextField = new JTextField("mm/dd/yyyy (e.g. 12/01/2022)", 10);
        citySearchTextField = new JTextField(10);

        // radio button
        allButton = new JRadioButton("All");
        pfizerButton = new JRadioButton("Pfizer");
        modernaButton = new JRadioButton("Moderna");
        azButton = new JRadioButton("AstraZeneca");

        group = new ButtonGroup();
        group.add(allButton);
        group.add(pfizerButton);
        group.add(modernaButton);
        group.add(azButton);

        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setLayout(new GridLayout(2, 2));
        radioButtonPanel.add(allButton);
        radioButtonPanel.add(pfizerButton);
        radioButtonPanel.add(modernaButton);
        radioButtonPanel.add(azButton);

        allButton.setSelected(true);

        // put together the upper panel
        searchInputPanel.add(citySearchLabel);
        searchInputPanel.add(citySearchTextField);
        searchInputPanel.add(dateSearchLabel);
        searchInputPanel.add(dateSearchTextField);
        searchInputPanel.add(vaccineSearchLabel);
        searchInputPanel.add(radioButtonPanel);

        // putting panel together into the base panel
        searchRecordBasePanel.add(instructionSearchLabel, BorderLayout.NORTH);
        searchRecordBasePanel.add(searchInputPanel, BorderLayout.CENTER);
        searchRecordBasePanel.add(searchButton, BorderLayout.SOUTH);


        // create actionListener for search button
        class searchRecordListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                // grab date from input field
                searchRecordDataCollector();

                // call search function
                try {
                    executionResultMsg = Recorder.searchData(city, date);
                    displaySearchResult(executionResultMsg);
                } catch (FileNotFoundException ex) {
                    System.out.println(ex);
                }
            }
        }

        // add actionListener to the search button
        ActionListener listener = new searchRecordListener();
        searchButton.addActionListener(listener);

        // return panel
        return searchRecordBasePanel;

    }

    // Jaydenn
    // get user data from search record GUI fields and save to variable
    public void searchRecordDataCollector() {
        date = dateSearchTextField.getText();
        city = citySearchTextField.getText();
        if (allButton.isSelected())
            vaccineType = "allSelected";
        else if (pfizerButton.isSelected())
            vaccineType = "pfizerSelected";
        else if (modernaButton.isSelected())
            vaccineType = "modernaSelected";
        else if (azButton.isSelected())
            vaccineType = "azSelected";
    }


    // Kaiyan & Jaydenn
    // set labels // to be called when record add and search buttons are hit
    public void displaySearchResult(ArrayList<String> searchResult) {

        //user select All button
        if (searchResult.size() == 5) {
            System.out.println(searchResult.size());
            executionResultMsgLabel.setText("Your result is ready.");
            if (vaccineType == "allSelected") {
                dateResultLabel.setText(searchResult.get(0));
                cityResultLabel.setText(searchResult.get(1));
                pfizerResultLabel.setText(searchResult.get(2));
                modernaResultLabel.setText(searchResult.get(3));
                azResultLabel.setText(searchResult.get(4));
            }
            //user select pfizer button
            else if (vaccineType == "pfizerSelected") {
                dateResultLabel.setText(searchResult.get(0));
                cityResultLabel.setText(searchResult.get(1));
                pfizerResultLabel.setText(searchResult.get(2));
                modernaResultLabel.setText("--");
                azResultLabel.setText("--");
            }
            //user select moderna button
            else if (vaccineType == "modernaSelected") {
                dateResultLabel.setText(searchResult.get(0));
                cityResultLabel.setText(searchResult.get(1));
                pfizerResultLabel.setText("--");
                modernaResultLabel.setText(searchResult.get(3));
                azResultLabel.setText("--");
            }
            //user select az button
            else if (vaccineType == "azSelected") {
                dateResultLabel.setText(searchResult.get(0));
                cityResultLabel.setText(searchResult.get(1));
                pfizerResultLabel.setText("--");
                modernaResultLabel.setText("--");
                azResultLabel.setText(searchResult.get(4));
            }
        }
        else {
            System.out.println(searchResult);
            listString = String.join("<br/>", searchResult);
            // test use
//            System.out.println(listString);

            executionResultMsgLabel.setText("<html>"+listString+"</html>");
        }
    }

    // Kaiyan & Jaydenn
    public void displayAddResult(ArrayList<String> searchResult) {
        listString = String.join("<br/>", searchResult);
        // test use
//        System.out.println(listString);

        executionResultMsgLabel.setText("<html>"+listString+"</html>");
    }

}
