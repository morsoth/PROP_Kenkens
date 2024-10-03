package edu.upc.prop.clusterxx.domain;

import java.util.ArrayList;
import java.util.Comparator;

import edu.upc.prop.clusterxx.PairSI;

public class Ranking {
    private ArrayList<PairSI> ranking;

    public Ranking() {
        ranking = new ArrayList<PairSI>();
    }

    // As we can create a user without points, we need to
    // add an addUser method with only the name parameter
    public void add(String name) {
        ranking.add(new PairSI(name, 0));
    }

    public void add(String name, int points) {
        ranking.add(new PairSI(name, points));
    }

    public int getSize() {
        return ranking.size();
    }

    public void sort() {
        ranking.sort(new Comparator<PairSI>() {
            public int compare(PairSI p1, PairSI p2) {
                return p2.getSecond() - p1.getSecond();
            }
        });
    }

    public ArrayList<PairSI> getRanking() {
        return ranking;
    }

    public void deleteUser(String name) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.remove(i);
                break;
            }
        }
    }

    public void setPoints(String name, int points) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.get(i).setSecond(points);
                break;
            }
        }
    }

    public void updatePoints(String name, int points) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.get(i).setSecond(ranking.get(i).getSecond() + points);
                break;
            }
        }
    }
}