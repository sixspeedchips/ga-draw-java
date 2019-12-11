package io.libsoft.gadraw.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.opencv.core.Mat;
import org.opencv.core.Size;

public class Generation {

  // need a fitness metric (sum of squared differences)
  // need a sort on fitness
  // need a cross-over function
  // need a mutate function


  private List<Organism> organisms;
  private Mat target;
  private int populationTarget;
  private Random random = new Random();

  public static Generation newPool(int noOrganisms, int noGenes, Size size) {
    Generation generation = new Generation();
    List<Organism> organisms = new LinkedList<>();
    for (int i = 0; i < noOrganisms; i++) {
      organisms.add(new Organism(noGenes, size));
    }
    generation.setOrganisms(organisms);
    generation.populationTarget = noOrganisms;
    return generation;
  }

  public List<Organism> getOrganisms() {
    return organisms;
  }

  public void setOrganisms(List<Organism> organisms) {
    this.organisms = organisms;
  }

  public void step() {
    for (Organism organism : organisms) {
      organism.express();
      organism.computeFitness(target);
    }
    organisms.sort(Comparator.comparingDouble(Organism::getFitness));
    // keep only top 40% of fitness

    int s = (int) (populationTarget * .2);
    for (int i = s; i < populationTarget; i++) {
      organisms.get(i).update(organisms.get(random.nextInt(s)), organisms.get(random.nextInt(s)));
      organisms.get(i).mutate();
    }
//
//    Generation next = new Generation();
//    next.setOrganisms(organisms);
//    next.setTarget(target);
//    next.populationTarget = populationTarget;
//    return next;
  }

  public Generation setTarget(Mat target) {
    this.target = target;
    return this;
  }
}
