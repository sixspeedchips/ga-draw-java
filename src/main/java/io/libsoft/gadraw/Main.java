package io.libsoft.gadraw;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

public class Main extends Application {


  private static Random random;

  public static void main(String[] args)  {
    nu.pattern.OpenCV.loadShared();
    random = new Random();
    launch();



  }


  public static Image getImage(Mat mat) {
    MatOfByte byteMat = new MatOfByte();
    Imgcodecs.imencode(".bmp", mat, byteMat);
    return new Image(new ByteArrayInputStream(byteMat.toArray()));
  }

  public void start(Stage stage) throws Exception {

    final ImageView root = new ImageView();
    Scene scene = new Scene(new StackPane(root));
    final Mat mat2 = Mat.zeros(new Size(200, 200), CvType.CV_8SC3);
    System.out.println(Arrays.toString(mat2.get(0, 0)));

    AnimationTimer animationTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        for (int k = 0; k < 10; k++) {
          for (int i = 0; i < mat2.cols(); i++) {
            for (int j = 0; j < mat2.rows(); j++) {
              byte[] b = {0, 0, 0};
              random.nextBytes(b);
              mat2.put(j, i, b);
            }
          }
          root.setImage(getImage(mat2));
        }
      }
    };

    stage.setScene(scene);
    stage.show();
    animationTimer.start();
  }
}
