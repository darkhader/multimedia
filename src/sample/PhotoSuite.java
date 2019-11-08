package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
public class PhotoSuite extends Application {




        private Stage primaryStage;
        private BorderPane rootLayout;

        @Override
        public void start(Stage primaryStage) {

            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("Photo Suite");

            try {

                rootLayout = (BorderPane) FXMLLoader.load(getClass().getResource(
                        "view/MenuPhotoSuite.fxml"));
                Scene scene = new Scene(rootLayout, 800, 600);
                scene.getStylesheets().add(
                        getClass().getResource("view/photosuite.css").toExternalForm());
                primaryStage.setScene(scene);

                primaryStage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            showPhotoSuite();

        }

        public void showPhotoSuite() {
            try {

                BorderPane root = (BorderPane) FXMLLoader.load(getClass()
                        .getResource("view/PhotoSuite.fxml"));
                rootLayout.setCenter(root);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    public static void main(String[] args) {
        launch(args);
    }
}
