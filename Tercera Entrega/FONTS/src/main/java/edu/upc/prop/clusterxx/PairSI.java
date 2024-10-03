package edu.upc.prop.clusterxx;

/**
 * Par de String e Integer
 * @author Raúl Gilabert Gámez
 */
public class PairSI {
    /**
     * Primer elemento del par (String)
     */
    private String first;
    /**
     * Segundo elemento del par (Integer)
     */
    private int second;

    /**
     * Constructor por defecto
     * @param first Primer elemento del par
     * @param second Segundo elemento del par
     */
    public PairSI(String first, int second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Obtiene el primer elemento del par
     * @return El primer elemento del par
     */
    public String getFirst() {
        return first;
    }

    /**
     * Obtiene el segundo elemento del par
     * @return El segundo elemento del par
     */
    public int getSecond() {
        return second;
    }

    /**
     * Establece el primer elemento del par
     * @param first El primer elemento del par
     */
    public void setFirst(String first) {
        this.first = first;
    }

    /**
     * Establece el segundo elemento del par
     * @param second El segundo elemento del par
     */
    public void setSecond(int second) {
        this.second = second;
    }
}
