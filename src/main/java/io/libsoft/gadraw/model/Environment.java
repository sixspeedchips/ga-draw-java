package io.libsoft.gadraw.model;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Environment {


  private Generation generation;
  private Mat target;
  private Mat best;
  private int currentGen;
  private double currentFitness;
  private double mutationRate;


  public Environment(Mat raw, Size size) {
    target = new Mat();
    currentGen = 0;
    best = Mat.zeros(size, CvType.CV_8SC3);
    Imgproc.resize(raw, target, size, 0, 0, Imgproc.INTER_AREA);
    generation = Generation.newPool(100, 500, target);
  }


  public void next() {
    generation.step();
    best = generation.getOrganisms().get(0).getBody();
    currentGen++;
    currentFitness = generation.getOrganisms().get(0).getFitness();
    mutationRate = generation.getOrganisms().get(0).getMutationRate();
  }

  public Mat getBest() {
    return best;
  }


  public Mat getTarget() {
    return target;
  }

  public double getFitness() {
    return currentFitness;
  }

  public int getCurrentGen() {
    return currentGen;
  }

  public double getMutationRate() {
    return mutationRate;
  }
}