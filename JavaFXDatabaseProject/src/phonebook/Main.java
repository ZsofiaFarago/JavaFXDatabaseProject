package phonebook;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // StackPane pane1 = new StackPane();
        // pane1.getChildren().add(new Button("Első Button"));
        // Scene scene1 = new Scene(pane1, 300, 50);
        // primaryStage.setScene(scene1);
        // primaryStage.setTitle("Első Stage");
        // primaryStage.show();
//
        // Stage stage2 = new Stage();
        // stage2.setScene(new Scene(new Button("Második Button"), 300, 100));
        // stage2.setTitle("Második Stage");
        // stage2.show();
//
        // // Egyszerre két ablak jelenik meg
        // // A második esetben a Button teljesen kitölti az ablak méretét
//
        // Stage stage3 = new Stage();
        // Pane pane3 = new Pane();
        // Circle circle = new Circle();
        // circle.setRadius(50);
        // // circle.setCenterX(70);
        // // circle.setCenterY(70);
        // circle.centerXProperty().bind(pane3.widthProperty().divide(2));
        // circle.centerYProperty().bind(pane3.heightProperty().divide(2));
        // circle.setFill(Color.WHITE);
        // pane3.getChildren().add(circle);
        // stage3.setScene(new Scene(pane3, 300, 300));
        // stage3.setTitle("Harmadik Stage");
        // stage3.show();

        // StackPane pane = new StackPane();
        // Image image = new Image("https://media.timeout.com/images/105375857/1024/576/image.jpg");
        // ImageView imageView = new ImageView(image);
        // pane.getChildren().add(imageView);
        // pane.setAlignment(imageView, Pos.TOP_LEFT);
        // imageView.fitWidthProperty().bind(pane.widthProperty());
        // imageView.fitHeightProperty().bind(pane.heightProperty());
        // imageView.setRotate(45);
        // Scene scene = new Scene(pane, 1200, 800);
        // primaryStage.setScene(scene);
        // primaryStage.setTitle("Manhattan");
        // primaryStage.show();

        Parent root = FXMLLoader.load(getClass().getResource("view/View.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Telefonkönyv");
        primaryStage.setWidth(620);
        primaryStage.setHeight(690);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
