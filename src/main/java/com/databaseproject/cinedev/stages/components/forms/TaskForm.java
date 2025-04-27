package com.databaseproject.cinedev.stages.components.forms;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.enums.CategoryTask;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.task.Category;
import com.databaseproject.cinedev.models.task.Priority;
import com.databaseproject.cinedev.models.task.State;
import com.databaseproject.cinedev.models.task.Task;
import com.databaseproject.cinedev.services.tasks.category.CategoryService;
import com.databaseproject.cinedev.services.tasks.priority.PriorityService;
import com.databaseproject.cinedev.services.tasks.state.StateService;
import com.databaseproject.cinedev.services.tasks.task.TaskService;
import com.databaseproject.cinedev.utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TaskForm {
    private TaskService taskService;
    private StateService stateService;
    private CategoryService categoryService;
    private PriorityService priorityService;

    private User user;
    private Task task;
    private Runnable taskAdded;

    public TaskForm(User user, Task task, Runnable taskAdded) {
        this.taskService = CinedevApplication.getSpringContext().getBean(TaskService.class);
        this.stateService = CinedevApplication.getSpringContext().getBean(StateService.class);
        this.categoryService = CinedevApplication.getSpringContext().getBean(CategoryService.class);
        this.priorityService = CinedevApplication.getSpringContext().getBean(PriorityService.class);
        this.user = user;
        this.task = task;
        this.taskAdded = taskAdded;
    }

    public void showFormModal(Stage primaryStage) {
        Label taskNameLabel = new Label();
        taskNameLabel.setText("Name: ");
        taskNameLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        TextField taskName = new TextField();
        taskName.setMaxWidth(Double.MAX_VALUE);
        taskName.setPromptText("Name of Task");


        Label descriptionLabel = new Label();
        descriptionLabel.setText("Description: ");
        descriptionLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        TextArea taskDescription = new TextArea();
        taskDescription.setMaxWidth(Double.MAX_VALUE);
        taskDescription.setPromptText("Description Task");

        Label dateLabel = new Label();
        dateLabel.setText("Date: ");
        dateLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        DatePicker datePicker = new DatePicker();
        datePicker.setMaxWidth(Double.MAX_VALUE);
        datePicker.setPromptText("Date of Expiration");

        Label expirationTimeLabel = new Label();
        expirationTimeLabel.setText("Time: ");
        expirationTimeLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        TextField expirationTime = new TextField();
        expirationTime.setMaxWidth(Double.MAX_VALUE);
        expirationTime.setPromptText("Expiration Time");

        Label assignmentLabel = new Label();
        assignmentLabel.setText("Assignments: ");
        assignmentLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        TextField assignmentsTask = new TextField();
        assignmentsTask.setMaxWidth(Double.MAX_VALUE);
        assignmentsTask.setPromptText("Assigned by (Separate by comas)");

        Label categoryLabel = new Label();
        categoryLabel.setText("Category: ");
        categoryLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        var defaultCategories = getCategories(CategoryTask.DEFAULT);
        var userCategories = getCategories(CategoryTask.USER);
        ComboBox<Category> comboCategory = new ComboBox<>();
        comboCategory.setMaxWidth(Double.MAX_VALUE);
        comboCategory.getItems().addAll(defaultCategories);
        comboCategory.getItems().addAll(userCategories);
        comboCategory.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Category categoryItem, boolean empty) {
                super.updateItem(categoryItem, empty);
                setText(empty || categoryItem == null ? null : categoryItem.getName());
            }
        });
        comboCategory.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Category categoryItem, boolean empty) {
                super.updateItem(categoryItem, empty);
                setText(empty || categoryItem == null ? null : categoryItem.getName());
            }
        });
        comboCategory.setPromptText("Category");

        Label stateLabel = new Label();
        stateLabel.setText("State: ");
        stateLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        var getAllStates = stateService.getAllState();
        ComboBox<State> comboState = new ComboBox<>();
        comboState.setMaxWidth(Double.MAX_VALUE);
        comboState.getItems().addAll(getAllStates);
        comboState.setPromptText("Status of Task");

        Label priorityLabel = new Label();
        priorityLabel.setText("Priority: ");
        priorityLabel.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 12px;" +
                        "-fx-font-family: 'Arial';"
        );
        var getAllPriority = priorityService.getAllPriority();
        ComboBox<Priority> comboPriority = new ComboBox<>();
        comboPriority.setMaxWidth(Double.MAX_VALUE);
        comboPriority.getItems().addAll(getAllPriority);
        comboPriority.setPromptText("Priority");

        Button addButton = new Button("Add Task");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");

        if(task != null) {
            taskName.setText(task.getName());
            taskDescription.setText(task.getDescription());
            datePicker.setValue(task.getExpirationDate());
            expirationTime.setText(task.getExpirationTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            assignmentsTask.setText(task.getAssignments());
            comboCategory.setValue(task.getCategory());
            comboState.setValue(task.getState());
            comboPriority.setValue(task.getPriority());

            addButton.setText("Save Changes");
        }

        addButton.setOnAction(e -> {
            String name = taskName.getText();
            String description = taskDescription.getText();
            String expirationTimeText = expirationTime.getText();
            String assignments = assignmentsTask.getText();
            LocalDate expirationDate = datePicker.getValue();
            Category category = comboCategory.getValue();
            State state = comboState.getValue();
            Priority priority = comboPriority.getValue();

            if (name.isEmpty() || description.isEmpty() || expirationTimeText.isEmpty() ||
                    expirationDate == null || category == null || state == null || priority == null) {
                Utils.sendMessage("Please, complete all the fields.", Alert.AlertType.WARNING);
                return;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime time = LocalTime.parse(expirationTimeText.trim(), formatter);

                Task newTask = (task != null) ? task : new Task();

                newTask.setName(name);
                newTask.setDescription(description);
                newTask.setCategory(category);
                newTask.setExpirationDate(expirationDate);
                newTask.setExpirationTime(time);
                newTask.setAssignments(assignments);
                newTask.setState(state);
                newTask.setPriority(priority);
                newTask.setUser(user);

                newTask.setUpdatedAt(LocalDateTime.now());

                taskService.saveTask(newTask);

                if(taskAdded != null) {
                    taskAdded.run();
                }

                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
                Utils.sendMessage(task == null ? "Task Save Successfully!" : "Task Edit Successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception ex) {
                Utils.sendMessage("Invalid format hour. Use HH:mm.", Alert.AlertType.ERROR);
            }
        });

        VBox view = new VBox(10, taskNameLabel, taskName, descriptionLabel, taskDescription, dateLabel, datePicker, expirationTimeLabel, expirationTime, assignmentLabel, assignmentsTask, stateLabel,
                comboState, categoryLabel, comboCategory, priorityLabel, comboPriority, addButton);
        view.setPadding(new Insets(20));

        Scene modalScene = new Scene(view, 400, 650);
        Stage modalStage = new Stage();
        modalStage.setScene(modalScene);
        modalStage.setTitle("CineDev");
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(primaryStage);
        modalStage.showAndWait();
    }

    private List<Category> getCategories(CategoryTask type) {
        var allCategories = categoryService.getAllCategories();

        return switch (type) {
            case DEFAULT -> allCategories.stream()
                    .filter(Category::isDefault)
                    .toList();
            case USER -> allCategories.stream()
                    .filter(category ->
                            !category.isDefault() &&
                                    category.getUserAdminId() != null &&
                                    category.getUserAdminId().getId() != null &&
                                    category.getUserAdminId().getId().equals(user.getId())
                    )
                    .toList();
            case ALL -> allCategories;
        };
    }
}
