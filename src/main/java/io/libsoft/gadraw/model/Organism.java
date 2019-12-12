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
  private Number[][] genes;
  private double fitness;
  private Size size;
  private MatOfPoint expression;
  private Scalar color;
  private Point p1;
  private Point p2;
  private Point p3;
  private Mat target;
  private double mutationRate = .14;

  public Organism(int noGenes, Mat target) {
    this.target = target;
    this.size = target.size();
    expression = new MatOfPoint();
    color = new Scalar(0, 0, 0);
    genes = new Number[noGenes][10];
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

//      genes[i][3] = random.nextInt((int) size.width);
//      genes[i][4] = random.nextInt((int) size.height);
//
//      genes[i][5] = random.nextInt((int) size.width);
//      genes[i][6] = random.nextInt((int) size.height);
//
//      genes[i][7] = random.nextInt((int) size.width);
//      genes[i][8] = random.nextInt((int) size.height);

      int x = random.nextInt((int) size.width);
      int y = random.nextInt((int) size.height);
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
    for (Number[] gene : genes) {
      color.set(new double[]{gene[0].doubleValue(), gene[1].doubleValue(), gene[2].doubleValue()});
      p1.set(new double[]{gene[3].doubleValue(), gene[4].doubleValue()});
      p2.set(new double[]{gene[5].doubleValue(), gene[6].doubleValue()});
      p3.set(new double[]{gene[7].doubleValue(), gene[8].doubleValue()});
      expression.fromArray(p1, p2, p3);
      Imgproc.fillConvexPoly(body, expression, color);
//      Imgproc.rectangle(body, p1, p2, color, gene[9].intValue());
    }
    Core.absdiff(target, body, calc);
    Core.pow(calc, 2, calc);
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
    for (Number[] gene : genes) {
      double r = 9;
      if (random.nextDouble() < mutationRate) {
        gene[0] = Math.round(gene[0].longValue() + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[1] = Math.round(gene[1].longValue() + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[2] = Math.round(gene[2].longValue() + random.nextDouble() * r * 2 - r);
      }
      if (random.nextDouble() < mutationRate) {
        gene[3] = Math.round(gene[3].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[4] = Math.round(gene[4].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[5] = Math.round(gene[5].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[6] = Math.round(gene[6].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[7] = Math.round(gene[7].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[8] = Math.round(gene[8].longValue() + random.nextDouble() * r - r / 2);
      }
      if (random.nextDouble() < mutationRate) {
        gene[9] = Math.round(gene[9].longValue() + random.nextDouble() * r - r / 2);
      }


    }

    if (mutationRate > random.nextDouble()) {
      double r = random.nextDouble() * .005 - .0050 / 2;
      mutationRate += r;
      mutationRate = Math.max(.05, mutationRate);
    }
  }


  public void update(Organism p1, Organism p2) {

    for (int i = 0; i < genes.length; i++) {
      if (random.nextDouble() > .5) {
        genes[i] = p1.genes[i].clone();
      } else {
        genes[i] = p2.genes[i].clone();
      }
    }
    mutationRate = .5 > random.nextDouble() ? p1.mutationRate : p2.mutationRate;


  }

  public double getMutationRate() {
    return mutationRate;
  }
}
