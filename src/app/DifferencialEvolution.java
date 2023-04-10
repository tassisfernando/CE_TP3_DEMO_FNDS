package app;

import model.Individual;

import java.util.*;

import static java.util.Arrays.asList;

public class DifferencialEvolution {
  private final int QTD_POP = 20;
  private final double F = 0.5;
  private final double CROSSOVER_RATE = 0.8;

  private final int MAX_VALUE = 20;
  private final int MIN_VALUE = -20;
  private final int MAX_GEN = 10000;

  private static final Random random = new Random();

  public static void main(String[] args) {
    DifferencialEvolution diffEvol = new DifferencialEvolution();
    Individual bestIndividual = diffEvol.init();

    //System.out.println(bestIndividual);
  }

  private Individual init() {
    int numGen = 1;

    List<Individual> popInd = generateRandomIndividuals();
    evaluateIndividuals(popInd);

    while (numGen <= MAX_GEN) {
      List<Individual> newPop = new ArrayList<>(QTD_POP);

      for (int i = 0; i < QTD_POP; i++) {
        Individual u = generateUInd(popInd);

        Individual exp = recombine(popInd.get(i), u);
        exp.evaluate();

        if (popInd.get(i).dominates(exp.getFunctionValues())) {
          newPop.add(popInd.get(i));
        } else if (exp.dominates(popInd.get(i).getFunctionValues())) {
          newPop.add(exp);
        } else {
          newPop.add(getRandomInd(asList(popInd.get(i), exp)));
        }
      }

      popInd = newPop;
      numGen++;
    }

    printIndividuals(popInd);
    return popInd.get(0);
  }

  private Individual getRandomInd(List<Individual> individuals) {
    int pos = (int) Math.round(Math.random());

    return individuals.get(pos);
  }

  private void printIndividual(List<Individual> popInd, int numGen) {
    System.out.printf("Geração: %d - indivíduos: %s \n", numGen, popInd.toString());
  }

  private List<Individual> generateRandomIndividuals() {
    List<Individual> individuals = new ArrayList<>();
    for (int i = 0; i < QTD_POP; i++) {
      individuals.add(new Individual(MIN_VALUE, MAX_VALUE));
    }
    return individuals;
  }

  private void evaluateIndividuals(List<Individual> individuals) {
    for (Individual individual : individuals) {
      individual.evaluate();
    }
  }

  private Individual generateUInd(List<Individual> popInd) {
    Individual u = new Individual();
    int randomIndex1 = random.nextInt(QTD_POP);
    int randomIndex2 = random.nextInt(QTD_POP);
    int randomIndex3 = random.nextInt(QTD_POP);

    Individual ind1 = popInd.get(randomIndex1);
    Individual ind2 = popInd.get(randomIndex2);
    Individual ind3 = popInd.get(randomIndex3);

    double[] val = new double[ind1.getGenes().length];

    for (int i = 0; i < val.length; i++) {
      val[i] = ind3.getGenes()[i] + (F * (ind1.getGenes()[i] - ind2.getGenes()[i]));
    }
    u.setGenes(val);

    return u;
  }

  private Individual recombine(Individual individual, Individual u) {
    Individual son = new Individual(MIN_VALUE, MAX_VALUE);

    for (int i = 0; i < individual.getGenes().length; i++) {
      double r = random.nextDouble();

      if (r < CROSSOVER_RATE) {
        son.getGenes()[i] = individual.getGenes()[i];
      } else {
        son.getGenes()[i] = u.getGenes()[i];
      }
    }

    return son;
  }

  private void printIndividuals(List<Individual> popInd) {
    System.out.printf("Resultado do processamento após %d gerações: \n", MAX_GEN);
    popInd.forEach(System.out::println);
  }
}