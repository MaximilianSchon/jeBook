package parser.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.xml.sax.SAXException;
import parser.EPubFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class GUI extends Application {

    private int page = 1;
    private WebView webView = new WebView();
    private EPubFile selectedFile;

    public static final int BUTTON_PADDING = 10;

    @Override
    public void start(Stage primaryStage) {
        Button read = new Button();
        read.setText("Read file");
        read.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("EPUB files only", "*.epub"));
            try {
                selectedFile = new EPubFile(fileChooser.showOpenDialog(primaryStage).getAbsolutePath());
                page = 1;
                webView.getEngine().loadContent(selectedFile.getPage(page));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }

        });

        Button next = new Button();
        next.setText("Next");
        next.setOnAction(event ->{
            if (page < selectedFile.getSize())
                page++;
            try {
                webView.getEngine().loadContent(selectedFile.getPage(page));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Button prev = new Button();
        prev.setText("Previous");
        prev.setOnAction(event -> {
            if (page > 1)
                page--;
            try {
                webView.getEngine().loadContent(selectedFile.getPage(page));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        GridPane right = new GridPane();
        right.setPadding(new Insets(BUTTON_PADDING));
        right.setHgap(BUTTON_PADDING);
        right.setVgap(BUTTON_PADDING);


        GridPane center = new GridPane();
        right.add(read, 0,0);
        right.add(next, 1,1);
        right.add(prev, 0,1);
        center.setPrefSize(primaryStage.getWidth(), primaryStage.getHeight());
        center.getChildren().add(webView);
        root.setRight(right);
        root.setCenter(center);
        Scene scene = new Scene(root, 500, 500);

        scene.widthProperty().addListener((observableValue, oldSceneWidth, newSceneWidth) -> {
            center.setPrefWidth(primaryStage.getWidth()/2);
            webView.setPrefWidth(primaryStage.getWidth()/2);
        });
        scene.heightProperty().addListener((observableValue, oldSceneHeight, newSceneHeight) -> {
            center.setPrefHeight(primaryStage.getHeight());
            webView.setPrefHeight(primaryStage.getHeight());
        });

        primaryStage.setTitle("jReader");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}