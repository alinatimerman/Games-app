import controllerFX.ControllerFX;
import repository.WinterGameInFileRepository;
import repository.WinterGameRepository;
import repository.EnrollmentInFileRepository;
import repository.EnrollmentRepository;
import service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MainFX extends Application {
    static public String winterGameFileName;
    static public String enrollmentFileName;

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
            Parent root = loader.load();
            ControllerFX controller = loader.getController();
            controller.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Winter games");
            primaryStage.show();
        }catch(Exception exception){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app " + exception);
            alert.showAndWait();
            System.err.println(exception);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

//----------------------------------------------------GetService----------------------------------------------------//
    static Service getService() throws ServiceException{
        readFile();
        WinterGameRepository winterGameRepository = new WinterGameInFileRepository(winterGameFileName);
        EnrollmentRepository enrollmentRepository = new EnrollmentInFileRepository(enrollmentFileName, winterGameRepository);
        Service service = new Service(winterGameRepository, enrollmentRepository);
        return service;
    }

//----------------------------------------------------Read From File----------------------------------------------------//
    public static void readFile() throws ServiceException {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("WinterGamesApp.properties"));
            winterGameFileName = properties.getProperty("WinterGameFile");
            if (winterGameFileName == null){
                winterGameFileName = "WinterGameTextFile.txt";
                System.err.println("WinterGame File not found. Using default" + winterGameFileName);
            }
            enrollmentFileName = properties.getProperty("EnrollmentFile");
            if (enrollmentFileName == null){
                enrollmentFileName = "EnrollmentFile.txt";
                System.err.println("Enrollment File file not found. Using default" + enrollmentFileName);
            }
        }catch (IOException exception){
            throw new ServiceException("Error reading the configuration file" + exception);
        }
    }
}
