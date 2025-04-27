package com.databaseproject.cinedev.stages;

import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.models.task.Category;
import com.databaseproject.cinedev.models.task.Task;
import com.databaseproject.cinedev.services.tasks.task.ITaskService;
import com.databaseproject.cinedev.stages.components.forms.CategoryForm;
import com.databaseproject.cinedev.stages.components.forms.TaskForm;
import com.databaseproject.cinedev.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TaskPage implements IWindowScene {
    ITaskService taskService;

    private User user;
    private Task task;
    private Category category;

    public TaskPage(User existingUser, ITaskService taskService) {
        this.user = existingUser;
        this.taskService = taskService;
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        StackPane form = taskPage(primaryStage);

        VBox root = new VBox(form);
        root.setBackground(Utils.backgroundImageWindows());
        root.setSpacing(10);

        return new Scene(root, 1280, 720);
    }

    private StackPane taskPage(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(1280, 720);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);

        Label title = new Label("Welcome " + user.getFullName() + " to CineDev!");
        title.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 20px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial';"
        );

        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(2);

        Button buttonAddTask = Utils.createButton("Add Task", "#4CAF50");
        TableView<Task> table = createTableTasks(primaryStage);
        buttonAddTask.setOnAction(e -> {
            TaskForm showModalTask = new TaskForm(user, task, () -> table.setItems(getTasks()));
            showModalTask.showFormModal(primaryStage);
        });

        Button buttonAddCategory = Utils.createButton("Add Category", "#2196F3");
        buttonAddCategory.setOnAction(e -> {
            CategoryForm categoryForm = new CategoryForm(user, category);
            categoryForm.showFormModal(primaryStage);
        });

        Button buttonEndedTask = Utils.createButton("Ended Tasks", "#F44336");
        buttonEndedTask.setOnAction(e -> {
            List<Task> completedTask = getTasks().stream().filter(task -> "COMPLETED".equalsIgnoreCase(task.getState().getName())).toList();
            table.getItems().setAll(completedTask);
        });

        Button buttonBackAllTask = Utils.createButton("Active Tasks", "#9C27B0");
        buttonBackAllTask.setOnAction(e -> {
            List<Task> activeTasks = getTasks().stream().filter(task -> {
                String state = task.getState().getName().toUpperCase();
                return state.equals("PENDING") || state.equals("IN_PROGRESS");
            }).toList();
            table.getItems().setAll(activeTasks);
        });

        Button returnToMainPage = Utils.createButton("Back to Main", "#000000");
        returnToMainPage.setOnAction(e -> {
            Utils.loadWindowsToShow(new MainPage(user), primaryStage);
        });

        HBox buttons = new HBox(25, buttonAddTask, buttonAddCategory, buttonEndedTask, buttonBackAllTask, returnToMainPage);
        buttons.setAlignment(Pos.CENTER);

        VBox form = new VBox(50, titleBox, buttons, table);
        form.setPadding(new Insets(50));
        form.setMaxWidth(Double.MAX_VALUE);
        form.setFillWidth(true);

        return new StackPane(form);
    }

    private TableView<Task> createTableTasks(Stage primaryStage) {
        TableView<Task> table = new TableView<>();
        Map<String, Double> columnWidths = Map.of(
                "name", 0.15,
                "description", 0.25,
                "category", 0.10,
                "creationDate", 0.10,
                "expirationDate", 0.10,
                "state", 0.10
        );


        String[] columnData = {"Name", "Description", "Category", "Creation Date", "End Date", "State", "Priority"};
        String[] properties = {"name", "description", "category", "creationDate", "expirationDate", "state", "priority"};

        for (int i = 0; i < columnData.length; i++) {
            if (properties[i].equals("creationDate")) {
                TableColumn<Task, String> creationDateColumn = new TableColumn<>(columnData[i]);
                creationDateColumn.setCellValueFactory(cellData ->
                        new SimpleStringProperty(Utils.formatDate(cellData.getValue().getCreatedAt()))
                );
                creationDateColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.05)));
                table.getColumns().add(creationDateColumn);

            } else if (properties[i].equals("state")) {
                TableColumn<Task, String> stateColumn = new TableColumn<>(columnData[i]);

                stateColumn.setCellValueFactory(cellData -> {
                    var state = cellData.getValue().getState();
                    return new SimpleStringProperty(state != null ? state.getName() : "Empty");
                });
                stateColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.10)));
                table.getColumns().add(stateColumn);

            } else if (properties[i].equals("category")) {
                TableColumn<Task, String> categoryColumn = new TableColumn<>(columnData[i]);

                categoryColumn.setCellValueFactory(cellData -> {
                    var category = cellData.getValue().getCategory();
                    return new SimpleStringProperty(category != null ? category.getName() : "Empty");
                });

                categoryColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.05)));
                table.getColumns().add(categoryColumn);
            }  else if (properties[i].equals("priority")) {
                TableColumn<Task, String> priorityColumn = new TableColumn<>(columnData[i]);

                priorityColumn.setCellValueFactory(cellData -> {
                    var priority = cellData.getValue().getPriority();
                    return new SimpleStringProperty(priority != null ? priority.getName() : "Empty");
                });

                priorityColumn.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.05)));
                table.getColumns().add(priorityColumn);
            } else {
                TableColumn<Task, String> column = new TableColumn<>(columnData[i]);
                column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
                column.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.10)));
                table.getColumns().add(column);
            }
        }

        table.getColumns().add(actionsColumn(table, primaryStage));
        table.setItems(getTasks());
        return table;
    }

    private TableColumn<Task, Void> actionsColumn(TableView<Task> table, Stage primaryStage) {
        TableColumn<Task, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("", Utils.typeOfIcon("fas-pencil-alt", "green"));
            private final Button btnDelete = new Button("", Utils.typeOfIcon("fas-trash", "red"));

            {
                LocalDateTime now = LocalDateTime.now();
                String formattedNow = Utils.formatDate(now);

                btnEdit.setOnAction(e -> {
                    Task task = getTableView().getItems().get(getIndex());
                    TaskForm editForm = new TaskForm(user, task, () -> table.setItems(getTasks()));
                    editForm.showFormModal(primaryStage);
                });

                btnDelete.setOnAction(e -> {
                    Task task = getTableView().getItems().get(getIndex());
                    boolean confirmed = Utils.confirmDialog(
                            "Hey, " + user.getFullName() +
                            ". Are you sure you want to eliminate this task?\n\n" +
                            "This action will be registered on: : " + formattedNow
                    );

                    if (confirmed) {
                        task.setDeletedAt(now);
                        taskService.deleteTask(task);
                        table.setItems(getTasks());
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox actions = new HBox(10, btnEdit, btnDelete);
                    actions.setAlignment(Pos.CENTER);
                    setGraphic(actions);
                }
            }
        });

        actionsColumn.setMinWidth(200);
        actionsColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        return actionsColumn;
    }

    private ObservableList<Task> getTasks() {
        List<Task> userTask = taskService.getTaskByUserId(user);
        return FXCollections.observableArrayList(userTask);
    }
}
