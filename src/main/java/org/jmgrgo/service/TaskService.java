package org.jmgrgo.service;

import org.jmgrgo.exception.TaskNotAvailable;
import org.jmgrgo.exception.TitleIsEmptyException;
import org.jmgrgo.exception.TitleNotAvailableException;
import org.jmgrgo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service responsible for handling task business rules.
 */
public class TaskService {

    // In-memory task storage
    private final List<Task> taskList = new ArrayList<>();

    // Counter to determine created task's id
    private long idCounter = 0;

    /**
     * Method to create a new task.
     *
     * @param title the task title
     * @return the created task
     * @throws TitleIsEmptyException      if the title is blank
     * @throws TitleNotAvailableException if a task with the same title already exists
     */
    public Task createTask(String title) {

        // Set up valid title
        title = validateAndNormalizeTitle(title, null);

        // Create task object
        idCounter++;
        Task task = new Task(idCounter, title, false);

        // Add task to task list
        taskList.add(task);

        // Return created task
        return task;

    }

    /**
     * Method to get an immutable list of all created tasks.
     *
     * @return an unmodifiable list of tasks.
     */
    public List<Task> getTaskList() {
        return List.copyOf(taskList);
    }

    /**
     * Method to toggle a task's completion status.
     *
     * @param id the id of the target task.
     * @return the updated task
     * @throws TaskNotAvailable if the target task does not exist
     */
    public Task toggleTaskCompletion(Long id) {

        // Define specified task by id
        Task taskById = findTaskById(id);

        // Toggle isCompleted boolean
        taskById.setCompleted(!taskById.isCompleted());

        return taskById;
    }

    /**
     * Method to update the title of a target task.
     *
     * @param id    id of the target task
     * @param title the new task title
     * @return the updated task
     * @throws TaskNotAvailable           if the target task does not exist
     * @throws TitleIsEmptyException      if the title is blank
     * @throws TitleNotAvailableException if the title is already in use
     */
    public Task updateTaskTitle(Long id, String title) {

        // Set up valid title
        title = validateAndNormalizeTitle(title, id);

        // Define specified task by id
        Task taskById = findTaskById(id);


        if (!taskById.getId().equals(id) &&
                taskById.getTitle().equalsIgnoreCase(title)) {
            throw new TitleNotAvailableException("Task already exists.");
        }


        // Set new title
        taskById.setTitle(title);

        return taskById;
    }

    /**
     * Method to remove a task identified by its id.
     *
     * @param id the id of the target task
     * @return the removed task
     * @throws TaskNotAvailable if the target task does not exist
     */
    public Task removeTask(Long id) {

        // Define specified task by id
        Task taskById = findTaskById(id);

        // Remove task from list if found
        taskList.remove(taskById);

        // Return deleted task
        return taskById;

    }

    /**
     * Helper method to find a specific task by its id.
     *
     * @param id the task id
     * @return the matching task
     * @throws TaskNotAvailable if the task does not exist
     */
    public Task findTaskById(Long id) {

        // Loop through the task list
        for (Task task : taskList) {

            // Return task object if there's a matching task id
            if (Objects.equals(task.getId(), id)) {
                return task;
            }
        }

        // Throw exception if no task was found
        throw new TaskNotAvailable("Task doesn't exist.");
    }

    /**
     * Helper method to normalize an entered title and verify if it's already used by a task.
     *
     * @param title         the entered task title
     * @param currentTaskId the id of the task being updated, or {@code null} when creating a new task
     * @return the normalized title
     * @throws TitleIsEmptyException      if the title is blank
     * @throws TitleNotAvailableException if the title is already in use
     */
    private String validateAndNormalizeTitle(String title, Long currentTaskId) {

        // Normalize title
        title = title.trim();

        // Check if title is empty
        if (title.isBlank()) {
            throw new TitleIsEmptyException("Title cannot be empty.");
        }

        // Loop through the task list
        for (Task task : taskList) {

            // Check if task exists
            if (task.getTitle().equalsIgnoreCase(title) &&
                    !task.getId().equals(currentTaskId)) {
                throw new TitleNotAvailableException("Task already exists.");
            }
        }

        return title;
    }


}

