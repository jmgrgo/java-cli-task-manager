package org.jmgrgo.view;

import org.jmgrgo.controller.TaskController;
import org.jmgrgo.exception.IdNotAvailable;
import org.jmgrgo.exception.TaskNotAvailable;
import org.jmgrgo.exception.TitleIsEmptyException;
import org.jmgrgo.exception.TitleNotAvailableException;
import org.jmgrgo.model.Task;

import java.util.List;
import java.util.Scanner;

public class TaskMenu {

    private final TaskController taskController;
    private final Scanner scanner;

    public TaskMenu(TaskController taskController, Scanner scanner) {
        this.taskController = taskController;
        this.scanner = scanner;
    }

    public void showMenu() {

        byte selectedOption;

        while (true) {

            // Print menu options and ask for input
            printMenu();

            // Select a valid option
            try {
                selectedOption = Byte.parseByte(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option.");
                continue;
            }

            switch (selectedOption) {
                case 1:
                    createTaskMenu();
                    break;
                case 2:
                    printAllTasks();
                    break;
                case 3:
                    updateTaskTitleMenu();
                    break;
                case 4:
                    toggleTaskCompletionMenu();
                    break;
                case 5:
                    removeTaskMenu();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Prints main selection menu
     */
    public void printMenu() {
        System.out.println();
        System.out.println("-Task Manager-");
        System.out.println("1. Create task\n2. List tasks\n3. Update task title\n4. Toggle task completion\n5. Remove task\n0. Exit");
        System.out.println("You can also use 0 to cancel any operation.");
        System.out.println("Select an option: ");
    }

    /**
     * Prints menu to create a new task
     */
    public void createTaskMenu() {

        String taskTitle;
        Task task;

        try {

            // Ask user to enter task title
            taskTitle = readString("Enter task title: ");

            // If input is 0, return to main menu
            if (taskTitle.equals("0")){
                System.out.println("Returning to main menu...");
                return;
            }

            // Call controller to create task
            task = taskController.createTask(taskTitle);
            System.out.println("Created task: ");
            printTask(task);

        } catch (TitleIsEmptyException | TitleNotAvailableException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints all saved tasks.
     */
    public void printAllTasks() {
        List<Task> tasks = taskController.getAllTasks();

        // Check if there are tasks saved
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        // Loop through all tasks and print them
        for (Task task : tasks) {
            printTask(task);
        }

    }

    /**
     * Prints menu to update an existing task's title.
     */
    public void updateTaskTitleMenu() {

        long taskId = -1;
        String newTaskTitle;
        Task task;

        while (true) {

            // Check if taskId is not defined yet
            if (taskId == -1) {
                taskId = readId("Enter task id to change title: ");

                // If input is 0, return to main menu
                if (taskId == 0) break;

                continue;
            }

            try {

                // Ask the user to enter the new title
                newTaskTitle = readString("Enter new task title: ");

                // If input is 0, return to main menu
                if (newTaskTitle.equals("0")){
                    System.out.println("Returning to main menu...");
                    return;
                }

                // Call the controller and print updated task
                task = taskController.updateTaskTitle(taskId, newTaskTitle);
                System.out.print("Updated task title: ");
                printTask(task);
                break;

            } catch (IdNotAvailable | TitleIsEmptyException | TaskNotAvailable | TitleNotAvailableException e) {
                System.out.println(e.getMessage());
                taskId = 0;
            }
        }

    }

    /**
     * Prints menu to toggle a task's completion status.
     */
    public void toggleTaskCompletionMenu() {

        long taskId = -1;
        Task task;

        while (true) {

            // Check if taskId is not defined yet
            if (taskId == -1) {
                taskId = readId("Enter task id to toggle completion: ");

                // If input is 0, return to main menu
                if (taskId == 0) break;

                continue;
            }

            try {

                // Call controller to toggle task completion
                task = taskController.toggleTaskCompletion(taskId);
                System.out.print("Toggled task completion: ");
                printTask(task);
                break;

            } catch (IdNotAvailable | TitleIsEmptyException | TitleNotAvailableException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Prints menu to remove a task from the storage.
     */
    public void removeTaskMenu() {

        long taskId = -1;
        Task task;

        while (true) {

            // Check if taskId is not defined yet
            if (taskId == -1) {
                taskId = readId("Enter task id to remove: ");

                // If input is 0, return to main menu
                if (taskId == 0) break;

                continue;
            }

            try {
                // Call the controller to remove the task
                task = taskController.removeTask(taskId);
                System.out.println("The task [" + task.getTitle() + "] has been removed successfully.");
            } catch (TaskNotAvailable | IdNotAvailable e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Helper method to read an entered string and parse it to a long type.
     *
     * @param prompt the entered value
     * @return the parsed long
     */
    private Long readId(String prompt) {

        long id;
        String input;

        while (true) {

            // User enters an input
            System.out.print(prompt);
            input = scanner.nextLine();

            try {

                // Parse input to long
                id = Long.parseLong(input);

                // Verify input is a valid ID
                if (id < 0) {
                    System.out.println("Please introduce a valid task id.");
                    continue;
                }

                // If user enters 0, return to main menu
                if (id == 0) {
                    System.out.println("Returning to main menu...");
                    return 0L;
                }

                // Check if task exists with the specified id
                if (!taskController.existsById(id)){
                    throw new TaskNotAvailable("Task not found.");
                }

                // Return valid task id
                return id;

            } catch (NumberFormatException e) {
                System.out.println("ID must be a valid number ");
            } catch (TaskNotAvailable e) {
                System.out.println(e.getMessage());
            }

        }
    }

    /**
     * Helper method to ask the user to input a value.
     *
     * @param prompt the entered value
     * @return the entered string
     */
    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Method to print a singular task formatted.
     *
     * @param task the task to print
     */
    private void printTask(Task task) {
        System.out.printf("%d. %s %s%n", task.getId(), task.getTitle(), task.isCompleted() ? "[X]" : "[ ]");
    }

}
