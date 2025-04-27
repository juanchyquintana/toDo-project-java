package com.databaseproject.cinedev.stages;

import com.databaseproject.cinedev.CinedevApplication;
import com.databaseproject.cinedev.models.base.User;
import com.databaseproject.cinedev.services.base.user.UserService;
import com.databaseproject.cinedev.services.tasks.category.CategoryService;
import com.databaseproject.cinedev.stages.components.forms.UserEditForm;
import com.databaseproject.cinedev.utils.Utils;
import javafx.beans.property.ReadOnlyStringWrapper;
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

public class UserPage implements IWindowScene {
    private User user;

    UserService userService;
    CategoryService categoryService;

    public UserPage(User user) {
        this.user = user;
        this.userService = CinedevApplication.getSpringContext().getBean(UserService.class);
        this.categoryService = CinedevApplication.getSpringContext().getBean(CategoryService.class);
    }

    @Override
    public Scene showWindow(Stage primaryStage) {
        StackPane form = userPage(primaryStage);

        VBox root = new VBox(form);
        root.setBackground(Utils.backgroundImageWindows());
        root.setSpacing(10);

        return new Scene(root, 1280, 720);
    }

    private StackPane userPage(Stage primaryStage) {
        Rectangle rectangle = new Rectangle(1280, 720);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);

        Label title = new Label("Welcome " + user.getFullName() + " to the User Panel!");
        title.setStyle(
                "-fx-font-weight: bold;" +
                        "-fx-font-size: 20px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-family: 'Arial';"
        );

        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setSpacing(2);

        Button buttonAddUser = Utils.createButton("Add User", "#4CAF50");
        TableView<User> table = createTableTasks(primaryStage);
        buttonAddUser.setOnAction(e -> {
            User newUser = new User();
            UserEditForm showModalTask = new UserEditForm(newUser, () -> table.setItems(getUsers()));
            showModalTask.showFormModal(primaryStage);
        });

        Button returnToMainPage = Utils.createButton("Back to Main", "#000000");
        returnToMainPage.setOnAction(e -> {
            Utils.loadWindowsToShow(new MainPage(user), primaryStage);
        });

        HBox buttons = new HBox(25, buttonAddUser, returnToMainPage);
        buttons.setAlignment(Pos.CENTER);

        VBox form = new VBox(50, titleBox, buttons, table);
        form.setPadding(new Insets(50));
        form.setMaxWidth(Double.MAX_VALUE);
        form.setFillWidth(true);

        return new StackPane(form);
    }

    private TableView<User> createTableTasks(Stage primaryStage) {
        TableView<User> table = new TableView<>();
        Map<String, Double> columnWidths = Map.of(
                "id", 0.05,
                "fullName", 0.15,
                "email", 0.25
        );


        String[] columnData = {"ID", "Full Name", "Email"};
        String[] properties = {"id", "fullName", "email"};

        for (int i = 0; i < columnData.length; i++) {
            TableColumn<User, String> column = new TableColumn<>(columnData[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(properties[i]));
            column.prefWidthProperty().bind(table.widthProperty().multiply(columnWidths.getOrDefault(properties[i], 0.10)));
            table.getColumns().add(column);
        }

        TableColumn<User, String> rolesCol = new TableColumn<>("Roles");
        rolesCol.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            String roles = user.getUserRoles().stream()
                    .map(ur -> ur.getRoles().getName())
                    .reduce((r1, r2) -> r1 + ", " + r2)
                    .orElse("No Role");
            return new ReadOnlyStringWrapper(roles);
        });
        rolesCol.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        table.getColumns().add(rolesCol);

        table.getColumns().add(actionsColumn(table, primaryStage));
        table.setItems(getUsers());
        return table;
    }

    private TableColumn<User, Void> actionsColumn(TableView<User> table, Stage primaryStage) {
        TableColumn<User, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("", Utils.typeOfIcon("fas-pencil-alt", "green"));
            private final Button btnDelete = new Button("", Utils.typeOfIcon("fas-trash", "red"));

            {
                LocalDateTime now = LocalDateTime.now();
                String formattedNow = Utils.formatDate(now);

                btnEdit.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());UserEditForm editForm = new UserEditForm(user, () -> {
                        table.setItems(getUsers());
                        table.refresh();
                    });
                    editForm.showFormModal(primaryStage);
                });

                btnDelete.setOnAction(e -> {
                    User userView = getTableView().getItems().get(getIndex());

                    if (userView.getId().equals(user.getId())) {
                        Utils.sendMessage("You can't delete yourself!", Alert.AlertType.WARNING);
                        return;
                    }

                    boolean confirmed = Utils.confirmDialog("Are you sure you want to eliminate " + userView.getFullName() + "?");
                    if (confirmed) {
                        User managedUser = userService.findByIdWithEverything(userView.getId());

                        categoryService.categoriesFromUserAdmin(managedUser.getId());
                        userService.deleteUser(managedUser);

                        table.setItems(getUsers());
                        table.refresh();
                        Utils.sendMessage("User deleted successfully.", Alert.AlertType.INFORMATION);
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

    private ObservableList<User> getUsers() {
        List<User> users = userService.getAllUsersWithRoles();
        return FXCollections.observableArrayList(users);
    }
}
