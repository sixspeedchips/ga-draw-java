package io.libsoft.gadraw.model;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.opencv.core.Mat;

public class Generation {

  // need a fitness metric (sum of squared differences)
  // need a sort on fitness
  // need a cross-over function
  // need a mutate function


  private List<Organism> organisms;
  private Mat target;
  private int populationTarget;
  private Random random = new Random();
  private int gen = 0;

  public static Generation newPool(int noOrganisms, int noGenes, Mat target) {
    Generation generation = new Generation();
    List<Organism> organisms = new LinkedList<>();
    for (int i = 0; i < noOrganisms; i++) {
      organisms.add(new Organism(noGenes, target));
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
    for (Organism organism : organisms) {
      organism.computeFitness();
    }
  }

  public void step() {

    for (Organism organism : organisms) {
      organism.computeFitness();
    }
    organisms.sort(Comparator.comparingDouble(Organism::getFitness));

    // keep only top 20% of fitness
    int s = (int) (populationTarget * .4);
    for (int i = s; i < populationTarget; i++) {
      organisms.get(i).update(organisms.get(parent(s)), organisms.get(parent(s)));
      organisms.get(i).mutate();
    }
  }

  // stochastic acceptance
  private int parent(int bound) {
    double wMax = organisms.get(0).getFitness();
    int i = random.nextInt(bound);
    while (random.nextDouble() >= organisms.get(i).getFitness() / wMax) {
      i = random.nextInt(bound);
    }
    return i;
  }


}
