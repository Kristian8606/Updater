package sample;


import javafx.concurrent.Task;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    static TextField fieldURL;
    static Pane root;
    public static int wight = 400;
    public static int hight = 150;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    /*


        private void update() throws IOException {
            String url = "https://github.com/Kristian8606/hellofx/releases/download/0.0.1/hellofx.jar";
            String path = System.getProperty("user.dir"); //"/Users/kristiandimitrov/Desktop/fx";

            System.out.println(download(url, path));
        }

        private static String download(String sourceURL, String targetDirectory) throws IOException {
            Path targetPath = null;
            try {
                URL url = new URL(sourceURL);
                String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
                targetPath = new File(targetDirectory + File.separator + fileName).toPath();

                Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

                return String.valueOf(targetPath);

            } catch (Exception ex) {
                System.out.println(ex);
                return String.valueOf(ex);
            }
        }
        */





    ///////////


    static Parent createContent() {
        String url = "https://github.com/Kristian8606/hellofx/releases/download/0.0.1/hellofx.jar";
        Button btn = new Button("Update");
        Button btnCancel = new Button("Cancel");
        btnCancel.setLayoutX(230);
        btnCancel.setLayoutY(100);
        btn.setLayoutX(100);
        btn.setLayoutY(100);

         root = new Pane();
        root.setPrefSize(wight, hight);

         fieldURL = new TextField(url);
         fieldURL.setMinSize(wight,20);
        root.getChildren().addAll(fieldURL , btn,btnCancel);

        btn.setOnAction(event -> {
            Task<Void> task = new DownloadTask(fieldURL.getText());
            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(350);
            progressBar.setLayoutY(40);
            progressBar.setLayoutX(25);
            progressBar.progressProperty().bind(task.progressProperty());
            root.getChildren().add(progressBar);

            fieldURL.clear();

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });
        btnCancel.setOnAction(actionEvent -> System.exit(0));

        return root;
    }

    private static class DownloadTask extends Task<Void> {

        private String url;

        public DownloadTask(String url) {
            this.url = url;
        }

        @Override
        protected Void call() throws Exception {
            String ext = url.substring(url.lastIndexOf('/') + 1, url.length());

            URLConnection connection = new URL(url).openConnection();
            long fileLength = connection.getContentLengthLong();

            try (InputStream is = connection.getInputStream();
                 OutputStream os = Files.newOutputStream(Paths.get( ext))) {

                long nread = 0L;
                byte[] buf = new byte[8192];
                int n;
                while ((n = is.read(buf)) > 0) {
                    os.write(buf, 0, n);
                    nread += n;
                    updateProgress(nread, fileLength);
                }
            }

            return null;
        }

        @Override
        protected void failed() {
            System.out.println("failed");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Download Failed!");

            alert.showAndWait();
            System.exit(0);
        }

        @Override
        protected void succeeded() {
            System.out.println("downloaded");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Download Completed!");

            alert.showAndWait();
            System.exit(0);
        }
    }

}

