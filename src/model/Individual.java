package model;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable<Individual> {

  private final static int NUM_PROBLEM = 3;
  private final Random random;

  private Double[] genes;
  private Double[] functionValues;

  private double crowdingDistance;

  private int qtdGenes;
  private int qtdValues;

  public Individual(int min, int max) {
    this.random = new Random();
    chooseProblem();
    createRandomGenes(max, min);
  }

  public Individual() {
    this.random = new Random();
  }

  private void chooseProblem() {
    switch (NUM_PROBLEM) {
      case 1:
        qtdGenes = 1;
        qtdValues = 2;
        break;
      case 2:
        qtdGenes = 2;
        qtdValues = 2;
        break;
      case 3:
        qtdGenes = 3;
        qtdValues = 3;
        break;
      default:
        qtdGenes = 1;
        qtdValues = 1;
    }

    this.genes = new Double[qtdGenes];
    this.functionValues = new Double[qtdValues];
  }
  private void createRandomGenes(int min, int max) {
    for (int i = 0; i < this.qtdGenes; i++) {
      genes[i] = random.nextDouble() * max * 2 + min;
    }
  }

  public void evaluate() {
    switch (NUM_PROBLEM) {
      case 1:
        this.functionValues[0] = Math.pow(this.genes[0], 2);
        this.functionValues[1] = Math.pow(this.genes[0] - 1, 2);
        break;
      case 2:
        this.functionValues[0] = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2);
        this.functionValues[1] = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1] - 2, 2);
        break;
      case 3:
        this.functionValues[0] = Math.pow(this.genes[0] - 1, 2) + Math.pow(this.genes[1], 2) + Math.pow(this.genes[2], 2);
        this.functionValues[1] = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1] - 1, 2) + Math.pow(this.genes[2], 2);
        this.functionValues[2] = Math.pow(this.genes[0], 2) + Math.pow(this.genes[1], 2) + Math.pow(this.genes[2]-1, 2);
        break;
      default:
        this.functionValues[0] = this.genes[0];
    }
  }

  public Double[] getFunctionValues() {
    return functionValues;
  }

  public Double[] getGenes() {
    return genes;
  }

  public void setGenes(Double[] genes) {
    this.genes = genes;
  }

  public double getCrowdingDistance() {
    return crowdingDistance;
  }

  public void setCrowdingDistance(double crowdingDistance) {
    this.crowdingDistance = crowdingDistance;
  }

  @Override
  public int compareTo(Individual i2) {
    return Double.compare(i2.crowdingDistance, this.crowdingDistance);
  }

  @Override
  public String toString() {
    return "\nGenes: " +
            Arrays.toString(this.genes) +
          "\nAvaliação: " +
            Arrays.toString(this.functionValues);
  }
}