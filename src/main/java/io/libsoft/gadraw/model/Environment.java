package io.libsoft.gadraw.model;

import java.util.Iterator;
import java.util.Random;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Environment implements Iterator<Generation> {


  private Generation generation;
  private Random random = new Random();
  private Mat target;
  private Mat best;


  public Environment() {
    target = new Mat();

  }

  public void setTarget(Mat raw, Size size) {
    best = Mat.zeros(size, CvType.CV_8SC3);
    Imgproc.resize(raw, target, size, 0, 0, Imgproc.INTER_AREA);
  }

  /**
   * Infinite generator
   *
   * @return always returns true.
   */
  public boolean hasNext() {
    return true;
  }

  public Generation next() {
    // todo implement generation step
    generation = generation.next();
    for (int k = 0; k < 10; k++) {
      for (int i = 0; i < best.cols(); i++) {
        for (int j = 0; j < best.rows(); j++) {
          byte[] b = {0, 0, 0};
          random.nextBytes(b);
          best.put(j, i, b);
        }
      }
    }
    return generation;
  }

  public Mat getBest() {
    return best;
  }


  public Mat getTarget() {
    return target;
  }
}
