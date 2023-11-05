/*
 Introduction: 
The Calendar Application is a user-friendly desktop application built using Java Swing that allows users to manage their events and appointments efficiently. The application provides an interactive graphical interface for adding, editing, and deleting events, along with a clear display of scheduled events in a list. With its intuitive design, the Calendar Application simplifies event management and scheduling for users. 
 
Key Features: 
Event Management: Users can easily create, edit, and delete events through a user-friendly interface. The application provides dialogs for entering event names and dates, making it simple to schedule appointments, meetings, or reminders. 
 
Visual Representation: The application uses a graphical interface to display events in a list format. Users can quickly scan their scheduled events and identify important dates. 
 
Date Format Validation: The application ensures that dates entered by users adhere to the specified format (yyyy-MM-dd). It displays error messages if the date format is incorrect, enhancing the user experience. 
 
Single Selection: The application uses a JList component to allow users to select a single event at a time for editing or deletion. This prevents confusion and provides a clear focus on the selected event. 
 
Data Persistence: Although not included in the provided code, you can extend the application to save events to a file or database for data persistence between sessions. 
 
User Interaction: The application provides buttons for adding, editing, and deleting events, making it easy for users to perform common actions. Dialogs guide users through the event creation and editing process. 
 
Error Handling: The application includes error handling mechanisms to provide feedback to users when incorrect data formats are entered or when errors occur during event creation or editing. 
 
Simple User Interface: The application’s straightforward user interface enables users to quickly understand and interact with the features. It is designed to minimize clutter and provide a clean experience. 
 
Flexibility: Users can customize event names and dates according to their needs, allowing them to manage a variety of appointments, tasks, and engagements. 
 
Easy Deployment: The application is built using Java Swing, a standard Java library, ensuring that it can be easily compiled and run on various platforms without requiring extensive setup.

 
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CalendarApplication extends JFrame {
    private List<Event> events = new ArrayList<>();
    private JList<Event> eventList;
    private DefaultListModel<Event> listModel;
    private JButton addButton, editButton, deleteButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalendarApplication().setVisible(true));
    }

    public CalendarApplication() {
        setTitle("Calendar Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initComponents();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        eventList = new JList<>(listModel);
        addButton = new JButton("Add Event");
        editButton = new JButton("Edit Event");
        deleteButton = new JButton("Delete Event");

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);

        add(new JScrollPane(eventList), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Event newEvent = createNewEvent();
                if (newEvent != null) {
                    events.add(newEvent);
                    updateEventList();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Event selectedEvent = eventList.getSelectedValue();
                if (selectedEvent != null) {
                    editEvent(selectedEvent);
                    updateEventList();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Event selectedEvent = eventList.getSelectedValue();
                if (selectedEvent != null) {
                    events.remove(selectedEvent);
                    updateEventList();
                }
            }
        });
    }

    private Event createNewEvent() {
        String name = JOptionPane.showInputDialog(this, "Enter event name:");
        if (name != null && !name.trim().isEmpty()) {
            String dateStr = JOptionPane.showInputDialog(this, "Enter event date (yyyy-MM-dd):");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = dateFormat.parse(dateStr);
                return new Event(name, date);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Event not added.");
            }
        }
        return null;
    }

    private void editEvent(Event event) {
        String newName = JOptionPane.showInputDialog(this, "Enter new event name:", event.getName());
        if (newName != null && !newName.trim().isEmpty()) {
            String newDateStr = JOptionPane.showInputDialog(this, "Enter new event date (yyyy-MM-dd):",
                    event.getDateStr());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date newDate = dateFormat.parse(newDateStr);
                event.setName(newName);
                event.setDate(newDate);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Event not edited.");
            }
        }
    }

    private void updateEventList() {
        listModel.clear();
        for (Event event : events) {
            listModel.addElement(event);
        }
    }

}

class Event {
    private String name;
    private Date date;

    public Event(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    @Override
    public String toString() {
        return "Event: " + name + "Date:" + getDateStr();
    }

}