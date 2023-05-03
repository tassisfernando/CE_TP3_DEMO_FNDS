package app;

import model.Individual;
import model.Point;

import java.util.ArrayList;
import java.util.List;

public class FNDS {
    public static List<List<Individual>> execute(List<Individual> listIndividual){
        List<Point> listPoint = new ArrayList<>(listIndividual.size());

        for (Individual individual : listIndividual) {
            Point pontoIndividual = new Point(individual);
            listPoint.add(pontoIndividual);
        }

        // F
        List<List<Point>> listaFronteiras = new ArrayList<>();

        List<Point> fronteira1 = new ArrayList<>();

        //Parte 1
        for (int i = 0; i < listPoint.size(); i++) {
            Point p = listPoint.get(i);
            p.S = new ArrayList<>();
            p.numDomin = 0;

            for (int j = 0; j < listPoint.size(); j++) {
                if(i != j){
                    Point q = listPoint.get(j);

                    if(dominates(p,q)){
                        p.S.add(q);
                    }else if(dominates(q,p)){
                        p.numDomin++;
                    }
                }
            }

            if(p.numDomin == 0){
                p.rank = 1;
                fronteira1.add(p);
            }

        }
        listaFronteiras.add(fronteira1);

        // Parte 2
        int i = 0;
        List<Point> fronteiraI = listaFronteiras.get(i);
        while(!fronteiraI.isEmpty()){
            // Q
            List<Point> novaFronteira = new ArrayList<>();

            for (Point pontoIndividual: fronteiraI) {
                List<Point> Sp = pontoIndividual.S;

                for (Point pontoIndividual2 : Sp) {
                    pontoIndividual2.numDomin--;
                    if(pontoIndividual2.numDomin == 0){
                        pontoIndividual2.rank = i + 1;
                        novaFronteira.add(pontoIndividual2);
                    }
                }
            }
            i++;
            fronteiraI = novaFronteira;
            listaFronteiras.add(novaFronteira);
        }

        List<List<Individual>> retornoIndividual = new ArrayList<>();

        for (List<Point> fronteiraJ : listaFronteiras) {
            List<Individual> fronteiraIndividualJ = new ArrayList<>();

            if (!fronteiraJ.isEmpty()) {
                for (Point p : fronteiraJ) {
                    Individual individual = p.getIndividual();
                    fronteiraIndividualJ.add(individual);
                }

                retornoIndividual.add(fronteiraIndividualJ);
            }
        }

        return retornoIndividual;
    }

    private static boolean dominates(Point a, Point b) {
        boolean dominate = false;

        Double[] p1coordinates = a.getIndividual().getFunctionValues();
        Double[] p2coordinates = b.getIndividual().getFunctionValues();

        for (int i = 0; i < p1coordinates.length; i++) {
            if (p1coordinates[i] > p2coordinates[i]) {
                return false;
            }

            if (p1coordinates[i] < p2coordinates[i]) {
                dominate = true;
            }
        }

        return dominate;
    }
}
