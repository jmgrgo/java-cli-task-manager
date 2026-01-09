package org.jmgrgo;

import org.jmgrgo.controller.TaskController;
import org.jmgrgo.service.TaskService;
import org.jmgrgo.view.TaskMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Dependency wiring
        Scanner scanner = new Scanner(System.in);
        TaskService taskService = new TaskService();
        TaskController controller = new TaskController(taskService);
        TaskMenu taskMenu = new TaskMenu(controller, scanner);

        // Print menu
        taskMenu.showMenu();

        // Close scanner
        scanner.close();

    }
}