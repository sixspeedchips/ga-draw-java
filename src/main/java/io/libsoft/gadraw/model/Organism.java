package io.libsoft.gadraw.model;

import java.security.SecureRandom;
import java.util.Random;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Organism {

  private static Random random = new SecureRandom();
  private Mat calc;
  private Mat body;
  private long[][] genes;
  private double mutationRate = .1;
  private double fitness;
  private Size size;
  private MatOfPoint expression;
  private Scalar color;
  private Point p1;
  private Point p2;
  private Point p3;
  private Mat target;

  public Organism(int noGenes, Mat target) {
    this.target = target;
    this.size = target.size();
    expression = new MatOfPoint();
    color = new Scalar(0, 0, 0);
    genes = new long[noGenes][10];
    body = new Mat(size, CvType.CV_8UC3);
    calc = new Mat(size, CvType.CV_8UC3);
    p1 = new Point();
    p2 = new Point();
    p3 = new Point();

    newGenes(noGenes, size);
  }

  private void newGenes(int noGenes, Size size) {
    for (int i = 0; i < noGenes; i++) {
      // colors
//      genes[i][0] = random.nextInt(255);
//      genes[i][1] = random.nextInt(255);
//      genes[i][2] = random.nextInt(255);
      genes[i][0] = 128;
      genes[i][1] = 128;
      genes[i][2] = 128;

      int x = random.nextInt((int) size.width);
      int y = random.nextInt((int) size.height);
//      genes[i][3] = random.nextInt((int) size.width);
//      genes[i][4] = random.nextInt((int) size.height);
//
//      genes[i][5] = random.nextInt((int) size.width);
//      genes[i][6] = random.nextInt((int) size.height);
//
//      genes[i][7] = random.nextInt((int) size.width);
//      genes[i][8] = random.nextInt((int) size.height);
      genes[i][3] = x;
      genes[i][4] = y;

      genes[i][5] = x;
      genes[i][6] = y;

      genes[i][7] = x;
      genes[i][8] = y;

      genes[i][9] = random.nextInt(10);

    }
  }


  public void computeFitness() {
    Core.multiply(body, Scalar.all(0), body);
//    body.release();
//    body = Mat.zeros(size, CvType.CV_8UC3);
//    shuffle(genes);
    for (long[] gene : genes) {
      color.set(new double[]{(double) gene[0], (double) gene[1], (double) gene[2]});
      p1.set(new double[]{gene[3], gene[4]});
      p2.set(new double[]{gene[5], gene[6]});
      p3.set(new double[]{gene[7], gene[8]});
      expression.fromArray(p1, p2, p3);
      Imgproc.fillConvexPoly(body, expression, color);
//      Imgproc.rectangle(body, p1, p2, color, (int) gene[9]);
    }
    Core.absdiff(target, body, calc);
    Core.multiply(calc, calc, calc);
    fitness = 0;
    for (double v : Core.sumElems(calc).val) {
      fitness += v;
    }

//    fitness = Core.norm(calc, Core.NORM_L2);
//    fitness = norm(target,body);
  }


  public double getFitness() {
    return fitness;
  }

  public Mat getBody() {
    return body;
  }

  public void mutate() {
    for (long[] gene : genes) {
      double r = 5;

      if (random.nextDouble() < mutationRate) {
        gene[0] = Math.round(gene[0] + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[1] = Math.round(gene[1] + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[2] = Math.round(gene[2] + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[3] = Math.round(gene[3] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[4] = Math.round(gene[4] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[5] = Math.round(gene[5] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[6] = Math.round(gene[6] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[7] = Math.round(gene[7] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[8] = Math.round(gene[8] + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[9] = Math.round(gene[9] + random.nextDouble() * r - r / 2);
      }


    }

//    for (int i = 0; i < genes.length - 1; i++) {
//      if (random.nextDouble() < mutationRate) {
//        swap(i);
//      }
//    }
  }

  private void swap(int i) {
    long[] temp = genes[i];
    genes[i] = genes[i + 1];
    genes[i + 1] = temp;

  }


  public void update(Organism p1, Organism p2) {
    int split = random.nextInt(p1.genes.length);
    for (int i = 0; i < split; i++) {
      genes[i] = p1.genes[i].clone();
    }
    for (int i = split; i < p1.genes.length; i++) {
      genes[i] = p2.genes[i].clone();
    }


  }


}
