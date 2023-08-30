import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ToDoAppGUI {
    private JFrame frame;
    private JTextField taskNameField;
    private DefaultListModel<Task> taskListModel;
    private JList<Task> taskList;

    private int nextTaskIndex = 1;

    public ToDoAppGUI() {
        frame = new JFrame("TaskTrek: TT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("TaskTrek: TT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(titleLabel);

        JPanel centerPanel = new JPanel(new BorderLayout());
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16)); // Set a font that supports colored emojis
        taskList.setCellRenderer(new TaskListRenderer());
        JScrollPane taskScrollPane = new JScrollPane(taskList);
        centerPanel.add(taskScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        taskNameField = new JTextField(25);
        JButton addButton = new JButton("Add Task");
        addButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskName = taskNameField.getText();
                if (!taskName.isEmpty()) {
                    Task task = new Task(nextTaskIndex++, taskName);
                    taskListModel.addElement(task);
                    taskNameField.setText("");
                }
            }
        });

        JButton doneButton = new JButton("Mark as Done");
        doneButton.setFont(new Font("Arial", Font.PLAIN, 14));
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Task selectedTask = taskListModel.getElementAt(selectedIndex);
                    if (!selectedTask.isDone()) {
                        selectedTask.setDone(true);
                        taskList.repaint();
                    }
                }
            }
        });

        bottomPanel.add(taskNameField);
        bottomPanel.add(addButton);
        bottomPanel.add(doneButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ToDoAppGUI();
            }
        });
    }

    class Task {
        private final int index;
        private final String name;
        private boolean done;
        private final Date timestamp;

        public Task(int index, String name) {
            this.index = index;
            this.name = name;
            this.done = false;
            this.timestamp = new Date();
        }

        public int getIndex() {
            return index;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String timestampStr = dateFormat.format(timestamp);
            String statusSymbol = (done ? "✅" : "❌");
            String colorCode = (done ? "#008000" : "#FF0000"); // Green for done, red for not done
            return "<html><font color='" + colorCode + "'>" + statusSymbol + " " + index + ". " + name + " (" + timestampStr + ")</font></html>";
        }
    }

    class TaskListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            Task task = (Task) value;
            setText(task.toString());
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            return this;
        }
    }
}