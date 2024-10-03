package edu.upc.prop.clusterxx.domain;

import java.util.ArrayList;
import java.util.Comparator;

import edu.upc.prop.clusterxx.PairSI;

/**
 * Clase Ranking
 * @author Raúl Gilabert Gámez
 */
public class Ranking {
    /**
     * Lista de ranking
     */
    private ArrayList<PairSI> ranking;

    /**
     * Constructor por defecto
     */
    public Ranking() {
        ranking = new ArrayList<PairSI>();
    }

    /**
     * Añade un usuario al ranking
     * @param name Nombre del usuario
     */
    public void add(String name) {
        ranking.add(new PairSI(name, 0));
    }

    /**
     * Añade un usuario al ranking con puntos
     * @param name Nombre del usuario
     * @param points Puntos del usuario
     */
    public void add(String name, int points) {
        ranking.add(new PairSI(name, points));
    }

    /**
     * Obtiene el tamaño del ranking
     * @return El tamaño del ranking
     */
    public int getSize() {
        return ranking.size();
    }

    /**
     * Ordena el ranking
     * El ranking se ordena en orden descendente de puntos
     */
    public void sort() {
        ranking.sort(new Comparator<PairSI>() {
            public int compare(PairSI p1, PairSI p2) {
                return p2.getSecond() - p1.getSecond();
            }
        });
    }

    /**
     * Obtiene el ranking
     * @return El ranking
     */
    public ArrayList<PairSI> getRanking() {
        return ranking;
    }

    /**
     * Elimina un usuario del ranking
     * @param name Nombre del usuario
     */
    public void deleteUser(String name) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.remove(i);
                break;
            }
        }
    }

    /**
     * Establece los puntos de un usuario
     * @param name Nombre del usuario
     * @param points Puntos del usuario
     */
    public void setPoints(String name, int points) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.get(i).setSecond(points);
                break;
            }
        }
    }

    /**
     * Actualiza los puntos de un usuario
     * @param name Nombre del usuario
     * @param points Puntos del usuario
     */
    public void updatePoints(String name, int points) {
        for (int i = 0; i < ranking.size(); ++i) {
            if (ranking.get(i).getFirst().equals(name)) {
                ranking.get(i).setSecond(ranking.get(i).getSecond() + points);
                break;
            }
        }
    }
}
