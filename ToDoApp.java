import java.util.*;
import java.text.SimpleDateFormat;

public class ToDoApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TaskList taskList = new TaskList();

        System.out.println("Welcome TaskTrek: TT");


        System.out.println("You can do the following operations :\n");

        while (true) {
            clearScreen();
            System.out.println("1. Add a Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark a Task as Done");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    Task task = new Task(taskName);
                    taskList.addTask(task);
                    break;
                case 2:
                    taskList.displayTasks();
                    break;
                case 3:
                    System.out.print("Enter task number to be marked as done: ");
                    int index = scanner.nextInt();
                    taskList.markTaskAsDone(index-1);
                    taskList.displayTasks();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    // Clear the console screen
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

class Task {
    private final String name;
    private boolean done;
    private final Date timestamp;

    public Task(String name) {
        this.name = name;
        this.done = false;
        this.timestamp = new Date();
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
        return (done ? "✅" : " ") + " " + name + " (" + timestampStr + ")";
    }
}

class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("🔴 " + tasks.get(i));
            }
        }
        System.out.println("\n");
    }

    public void markTaskAsDone(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task task = tasks.get(index);
            if (!task.isDone()) {
                task.setDone(true);
                System.out.println("Task done: " + task.getName() + "\n");
            } else {
                System.out.println("Task is already done.");
            }
        } else {
            System.out.println("Invalid task.");
        }

    }


}