package org.jmgrgo.controller;

import org.jmgrgo.exception.TaskNotAvailable;
import org.jmgrgo.model.Task;
import org.jmgrgo.service.TaskService;

import java.util.List;

/**
 * Controller responsible for coordinating task-related operations.
 * Acts as an intermediary between the view layer and the service layer.
 */
public class TaskController {

    // Declare task service
    private final TaskService taskService;

    // Controller constructor
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Creates a new task with a given title
     *
     * @param title the task title
     * @return the created task
     */
    public Task createTask(String title) {
        return taskService.createTask(title);
    }

    /**
     * Retrieves all created task
     *
     * @return an immutable list of tasks
     */
    public List<Task> getAllTasks() {
        return taskService.getTaskList();
    }

    /**
     * Retrieves a specific task
     * @param id id of the target task
     * @return whether task exists or not
     */
    public boolean existsById(Long id){
        try {

            taskService.findTaskById(id);
            return true;

        } catch (TaskNotAvailable e){
            return false;
        }

    }

    /**
     * Toggles the completion status of a task.
     *
     * @param id id of the target task
     * @return the updated task
     */
    public Task toggleTaskCompletion(Long id) {
        return taskService.toggleTaskCompletion(id);
    }

    /**
     * Updated the title of a specific task.
     *
     * @param id    id of the target task
     * @param title the new task title
     * @return the updated task
     */
    public Task updateTaskTitle(Long id, String title) {
        return taskService.updateTaskTitle(id, title);
    }

    /**
     * Removes a task by its id.
     *
     * @param id id of the target task
     * @return the removed task
     */
    public Task removeTask(Long id) {
        return taskService.removeTask(id);
    }

}
