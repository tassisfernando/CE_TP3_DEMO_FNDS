package app;

import utils.FileUtils;
import model.Individual;

import java.io.IOException;
import java.util.*;

public class DifferencialEvolution {
  private final int QTD_POP = 20;
  private final double F = 0.5;
  private final double CROSSOVER_RATE = 0.8;

  private final int MAX_VALUE = 10;
  private final int MIN_VALUE = -10;
  private final int MAX_GEN = 100;

  private List<Integer> gensToPlot = Arrays.asList(20, 40, 60, MAX_GEN);

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

    createPlotFile(popInd, numGen);

    while (numGen <= MAX_GEN) {
      List<Individual> newPop = new ArrayList<>(QTD_POP);
      List<Individual> intermediatePopulation = new ArrayList<>(popInd);

      for (int i = 0; i < QTD_POP; i++) {
        Individual u = generateUInd(popInd);

        Individual exp = recombine(popInd.get(i), u);
        exp.evaluate();

        intermediatePopulation.add(exp);
      }

      List<List<Individual>> borders = FNDS.fnds(intermediatePopulation);

      for (List<Individual> border : borders) {
        if (newPop.size() >= QTD_POP) break;

        if (border.size() + newPop.size() > QTD_POP) {
          List<Individual> individuoCD = new CrowdingDistance().evaluate(border);
          for (Individual individual : individuoCD) {
            if (newPop.size() < QTD_POP) {
              newPop.add(individual);
            } else break;
          }

        } else {
          newPop.addAll(border);
        }
      }

      popInd = newPop;
      numGen++;

      if (gensToPlot.contains(numGen)) {
        createPlotFile(popInd, numGen);
      }
    }

    printIndividuals(popInd);
    return popInd.get(0);
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

    Double[] val = new Double[ind1.getGenes().length];

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

  private void createPlotFile(List<Individual> individuals, int numGen) {
    try {
      createGensFile(individuals, numGen);
      createFunctionValuesFile(individuals, numGen);
    } catch (IOException erro){
      System.out.printf("Erro: %s", erro.getMessage());
    }
  }

  private void createGensFile(List<Individual> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_genes.txt";
    FileUtils.createFile(path, individuals, false);
  }

  private void createFunctionValuesFile(List<Individual> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_values.txt";
    FileUtils.createFile(path, individuals, true);
  }

  /**
   * UNUSED METHODS
   */
  private Individual getRandomInd(List<Individual> individuals) {
    int pos = (int) Math.round(Math.random());

    return individuals.get(pos);
  }

  private void printIndividual(List<Individual> popInd, int numGen) {
    System.out.printf("Geração: %d - indivíduos: %s \n", numGen, popInd.toString());
  }
}