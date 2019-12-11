package io.libsoft.gadraw.model;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Environment {


  private Generation generation;
  private Mat target;
  private Mat best;


  public Environment(Mat raw, Size size) {
    target = new Mat();
    best = Mat.zeros(size, CvType.CV_8SC3);
    Imgproc.resize(raw, target, size, 0, 0, Imgproc.INTER_AREA);
    generation = Generation.newPool(50, 100, target.size()).setTarget(target);
  }


  public void next() {
    generation.step();
    best = generation.getOrganisms().get(0).getBody();
  }

  public Mat getBest() {
    return best;
  }


  public Mat getTarget() {
    return target;
  }
}