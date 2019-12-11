package io.libsoft.gadraw.controller;

import io.libsoft.gadraw.model.Environment;
import io.libsoft.gadraw.view.GenerationViewer;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.ToggleButton;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

public class DrawController {


  private static final long MS_SLEEP = 0;
  @FXML
  public ToggleButton toggleRun;
  @FXML
  public Button reset;
  @FXML
  public Spinner spinner;
  @FXML
  public Slider slider;
  @FXML
  public GenerationViewer generationViewer;

  private int iterations;
  private boolean running;
  private Environment environment;
  private Updater updater;


  @FXML
  private void initialize() {

    environment = new Environment(
        Imgcodecs.imread("E:\\Projects\\JAVA\\ga-draw-java\\src\\main\\resources\\lonamisa.jpg",
            Imgcodecs.CV_LOAD_IMAGE_COLOR),
        new Size(500, 500));

    generationViewer.setEnvironment(environment);
    updater = new Updater();
    reset(null);
    initSlider();
  }

  private void initSlider() {
    slider.setMin(1);
    slider.setMax(10);
    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
      iterations = newValue.intValue();
    });
    slider.setValue(1);
    iterations = 1;
  }

  private void updateView() {
    generationViewer.draw();
  }

  public void reset(ActionEvent actionEvent) {

  }

  @FXML
  public void toggleRun(ActionEvent actionEvent) {
    if (toggleRun.isSelected()) {
      start();
    } else {
      stop();
      updateControls();
    }
  }


  private void start() {
    running = true;
    updateControls();
    new Runner().start();
    updater.start();
  }

  private void updateControls() {
    if (running) {
      reset.setDisable(true);
    } else {
      reset.setDisable(false);
      if (toggleRun.isSelected()) {
        toggleRun.setSelected(false);
      }
      updateView();
    }
  }

  public void stop() {
    running = false;
    updater.stop();
  }


  class Runner extends Thread {


    @Override
    public void run() {
      while (running) {
        for (int i = 0; i < iterations; i++) {
          environment.next();
        }
        try {
          Thread.sleep(MS_SLEEP);
        } catch (InterruptedException ignored) {
        }
      }


    }
  }

  private class Updater extends AnimationTimer {

    @Override
    public void handle(long now) {

      updateView();
    }

  }
}
