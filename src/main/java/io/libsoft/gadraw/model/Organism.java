package io.libsoft.gadraw.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Organism {

  private static Random random = new Random();
  private static Mat calc = new Mat();
  private Mat body;
  private byte[][] genes;
  private double mutationRate = .03;
  private double fitness;
  private Size size;

  public Organism(int noGenes, Size size) {
    this.size = size;
    // todo change to accept parent genes
    genes = new LinkedList<>();
    for (int i = 0; i < noGenes; i++) {
      genes.add(new Gene(size));
    }
  }

  public Organism(Size size) {
    this.size = size;
    genes = new LinkedList<>();
  }


  public void express() {

    clear(body);
    for (Gene gene : genes) {
      Imgproc.fillPoly(body, Collections.singletonList(gene.loci), gene.color);
    }
  }

  private void clear(Mat body) {
    for (int i = 0; i < body.cols(); i++) {
      for (int j = 0; j < body.rows(); j++) {
        body.put(j, i, new byte[]{0, 0, 0});
      }
    }
  }

  public void computeFitness(Mat target) {
    Core.subtract(target, body, calc);
    fitness = Core.norm(calc, Core.NORM_L2);
  }

  public double getFitness() {
    return fitness;
  }

  public Mat getBody() {
    return body;
  }

  public void mutate() {
    for (Gene gene : genes) {
      gene.color = Gene.newColor(gene.color, mutationRate);
      gene.loci = Gene.newLoci(gene.loci, mutationRate, size);
    }
  }

  @Override
  public String toString() {
    return "Organism{" +
        "body=" + body +
        ", genes=" + genes +
        ", mutationRate=" + mutationRate +
        ", fitness=" + fitness +
        ", size=" + size +
        '}';
  }

  public void update(Organism p1, Organism p2) {
    for (int i = 0; i < p1.genes.size(); i++) {
      genes.get(i).update(Gene.combGene(p1.genes.get(i), p2.genes.get(i)));
    }

  }

  private static class Gene {

    private Scalar color;
    private MatOfPoint loci;

    public Gene(Size size) {

      color = new Scalar();
      Point[] points = new Point[3];
      // new polygon, with 3 points
      double x = random.nextDouble() * size.width;
      double y = random.nextDouble() * size.height;
      for (int i = 0; i < 3; i++) {
        points[i] = new Point(x, y);
      }
      loci = new MatOfPoint(points);
    }


    public static Scalar newColor(Scalar color, double rate) {

      int c1 = (int) color.val[0];
      int c2 = (int) color.val[1];
      int c3 = (int) color.val[2];

      if (random.nextDouble() < rate) {
        c1 = (int) Math.max(0, Math.min(color.val[0] + (random.nextInt(4) - 2), 255));
      }
      if (random.nextDouble() < rate) {
        c1 = (int) Math.max(0, Math.min(color.val[1] + (random.nextInt(4) - 2), 255));
      }
      if (random.nextDouble() < rate) {
        c1 = (int) Math.max(0, Math.min(color.val[2] + (random.nextInt(4) - 2), 255));
      }

      return new Scalar(c1, c2, c3);
    }

    public static MatOfPoint newLoci(MatOfPoint loci, double rate, Size size) {
      Point[] points = new Point[3];
      // new polygon, with 3 points

      for (int i = 0; i < 3; i++) {
        if (random.nextDouble() < rate) {
          points[i] = new Point(
              Math.max(0,
                  Math.min(loci.toArray()[i].x + (random.nextDouble() * 6) - 3, size.width)),
              Math.max(0,
                  Math.min(loci.toArray()[i].y + (random.nextDouble() * 6) - 3, size.height)));

        } else {
          points[i] = loci.toArray()[i];
        }
      }
      loci.release();
      return new MatOfPoint(points);
    }

    public Scalar getColor() {
      return color;
    }

    public MatOfPoint getLoci() {
      return loci;
    }

    public void update(Gene combGene) {

    }
  }
}
