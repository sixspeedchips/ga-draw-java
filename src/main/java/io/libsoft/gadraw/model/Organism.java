package io.libsoft.gadraw.model;

import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Organism {


  private Mat body;
  private List<Gene> genes;
  private double mutationRate;


  private void express() {
    for (Gene gene : genes) {
      Imgproc.fillPoly(body, gene.loci, gene.color);
    }
  }


  private static class Gene {

    private Scalar color;
    private List<MatOfPoint> loci;


    public Scalar getColor() {
      return color;
    }

    public List<MatOfPoint> getLoci() {
      return loci;
    }
  }

}
