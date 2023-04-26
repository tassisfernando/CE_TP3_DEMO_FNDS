package model;

import java.util.List;

public class Point {
    private Double[] coordinates;
    public List<Point> S;
    public int numDomin;
    public int rank;
    private Individual individual;

    public Point(Individual individual) {
        this.individual = individual;
        this.coordinates = individual.getGenes();
    }

    public Individual getIndividual() {
        return this.individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public Double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Double[] coordinates) {
        this.coordinates = coordinates;
    }

    public List<Point> getS() {
        return S;
    }

    public void setS(List<Point> s) {
        this.S = s;
    }

    public int getNumDomin() {
        return numDomin;
    }

    public void setNumDomin(int numDomin) {
        this.numDomin = numDomin;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
