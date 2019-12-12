package io.libsoft.gadraw.view;


import io.libsoft.gadraw.model.Environment;
import java.io.ByteArrayInputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class GenerationViewer extends Pane {


  private ImageView original;
  private ImageView best;
  private Environment environment;
  private MatOfByte byteMat;


  public GenerationViewer() {
    original = new ImageView();
    best = new ImageView();
    getChildren().addAll(new HBox(original, best));
    byteMat = new MatOfByte();

  }

  public void setEnvironment(Environment environment) {
    this.environment = environment;
    original.setImage(getImage(environment.getTarget()));

  }

  public void draw() {
    best.setImage(getImage(environment.getBest()));
//    original.setImage(getImage(environment.getTarget()));

  }


  private Image getImage(Mat mat) {
    Imgcodecs.imencode(".bmp", mat, byteMat);
    return new Image(new ByteArrayInputStream(byteMat.toArray()));
  }

}
