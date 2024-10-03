package edu.upc.prop.clusterxx.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.upc.prop.clusterxx.domain.Box;
import edu.upc.prop.clusterxx.domain.Region;
import edu.upc.prop.clusterxx.exceptions.InvalidKenkenSizeException;

/**
 * Clase Kenken.
 *
 * @see edu.upc.prop.clusterxx.domain.KenkenController
 *
 * @author David Cañadas López
 */
public class Kenken {
    /**
     * Valor distintivo que identifica el Kenken.
     */
    private String id;

    /**
     * Número de filas y columnas del Kenken (2 < size < 10).
     */
    private final int size;

    /**
     * El tipo de partida del Kenken.
     */
    private GameType gameType;

    /**
     * El estado actual de la partida del Kenken.
     */
    private GameState gameState;

    /**
     * Matriz con las casillas del Kenken.
     */
    private final Box[][] boxes;

    /**
     * Lista con todas las regiones del Kenken.
     */
    private List<Region> regions;

    /**
     * Enumeración de los posibles tipos de partida a jugar.
     */
    public enum GameType {
        NORMAL,
        DAILY,
        RANKED
    }

    /**
     * Enumeración de los posibles estados de la partida.
     */
    public enum GameState {
        IN_GAME,
        IN_EDITION
    }

    /**
     * Crea una nueva instancia de Kenken con tamaño size y tipo de partida NORMAL.
     *
     * @param size Tamaño del nuevo Kenken
     * @throws InvalidKenkenSizeException Si el tamaño no es válido
     */
    public Kenken(int size) throws InvalidKenkenSizeException {
        this(size, GameType.NORMAL);
    }

    /**
     * Crea una nueva instancia de Kenken con tamaño size, identificador id y tipo de partida NORMAL.
     *
     * @param size Tamaño del nuevo Kenken
     * @param id Identificador del nuevo Kenken
     * @throws InvalidKenkenSizeException Si el tamaño no es válido
     */
    public Kenken(int size, String id) throws InvalidKenkenSizeException {
        this(size, GameType.NORMAL);
        this.id = id;
    }

    /**
     * Crea una nueva instancia de Kenken con tamaño size y tipo de partida gameType.
     *
     * @param size Tamaño del nuevo Kenken
     * @param gameType Tipo de partida del nuevo Kenken
     * @throws InvalidKenkenSizeException Si el tamaño no es válido
     */
    public Kenken(int size, GameType gameType) {
        if (size < 3 || size > 9) {
            throw new InvalidKenkenSizeException();
        }
        this.id = "";
        this.size = size;
        this.gameType = gameType;
        this.gameState = GameState.IN_EDITION;
        this.boxes = new Box[size][size];
        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                this.boxes[x][y] = new Box(this, x, y);
            }
        }
        this.regions = new ArrayList<Region>();
    }

    /**
     * Crea una nueva instancia de Kenken usando la matriz de casillas boxes.
     *
     * @param boxes Matriz de casillas
     */
    public Kenken(Box[][] boxes) {
        int len1 = boxes.length;
        if (len1 < 3 || len1 > 9) {
            throw new InvalidKenkenSizeException();
        } else {
            int len2 = boxes[0].length;
            if (len2 < 3 || len2 > 9 || len1 != len2) {
                throw new InvalidKenkenSizeException();
            }
        }
        for (Box[] row : boxes) {
            for (Box b : row) {
                if (b == null) throw new NullPointerException();
            }
        }
        this.id = "";
        this.size = len1;
        this.gameType = GameType.NORMAL;
        this.gameState = GameState.IN_EDITION;
        this.boxes = boxes;
        this.regions = new ArrayList<Region>();
    }

    /**
     * Comprueba si las coordenadas horizontal y vertical de son válidas en este Kenken.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return true ambas coordenadas son válidas, false en caso contrario
     */
    private boolean checkCoords(int x, int y) {
        return (x >= 0 && x < size && y >= 0 && y < size);
    }
    
    /**
     * Comprueba si insertar el valor num es válido en este Kenken.
     *
     * @param num Número a comprobar.
     * @return true si es válido, false en caso contrario
     */
    private boolean checkNum(int num) {
        return (num > 0 && num <= size);
    }

    /**
     * Obtener el ID de este Kenken
     *
     * @return el ID del Kenken
     */
    public String getID() {
        return this.id;
    }
    
    /**
     * Modificar el ID de este Kenken
     *
     * @param id El nuevo ID que debe usar el Kenken
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Obtener el tamaño de este Kenken
     *
     * @return el tamaño del Kenken
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Obtener el tipo de partida de este Kenken
     *
     * @return el tipo de partida
     */
    public GameType getType() {
        return this.gameType;
    }
    
    /**
     * Modificar el tipo de partida de este Kenken
     *
     * @param gameType el nuevo tipo de partida que debe tener el Kenken
     */
    public void setType(GameType gameType) {
        this.gameType = gameType;
    }

    /**
     * Obtener el estado de la partida de este Kenken
     *
     * @return el estado de la partida
     */
    public GameState getState() {
        return this.gameState;
    }

    /**
     * Modificar el estado de la partida de este Kenken
     *
     * @param gameState el nuevo estado de la partida que debe tener el Kenken.
     */
    public void setState(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Asignar un número a una casilla del Kenken.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @param num Número con el que llenar la casilla
     * @return false si las coordenadas indicadas o el valor a asignar no son válidos, true en caso contrario
     */
    public boolean fillBox(int x, int y, int num) {
        if (!checkCoords(x, y) || !checkNum(num)) return false;
        this.boxes[x][y].setValue(num);
        return true;
    }

    /**
     * Elimina el valor que tuviera una casilla del Kenken.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return false si las coordenadas indicadas no son válidas, true en caso contrario
     */
    public boolean clearBox(int x, int y) {
        if (!checkCoords(x, y)) return false;
        this.boxes[x][y].clearValue();
        return true;
    }

    /**
     * Obtener un vector con todas las casillas del tablero.
     *
     * @return el vector que contiene las casillas del tablero
     */
    public Box[] getBoxes() {
        Box[] bs = new Box[size*size];
        int i = 0;
        for (Box[] row : boxes) {
            for (Box b : row) {
                bs[i++] = b;
            }
        }
        return bs;
    }

    /**
     * Devuelve una referencia a una casilla del Kenken
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return la casilla de las coordenadas indicadas, o null en caso de que no exista
     */
    public Box getBox(int x, int y) {
        if (!checkCoords(x, y)) return null;
        return this.boxes[x][y];
    }

    /**
     * Obtener un vector con todas las casillas del tablero de coordenadas concretas.
     *
     * @param boxCoords parejas de filas y columnas que corresponden con las coordenadas de las casillas deseadas.
     * @return el vector que contiene las casillas del tablero indicadas
     */
    public Box[] getBoxes(int[] boxCoords) {
        int len = boxCoords.length;
        if (len == 0 || len%2 == 1) return null;
        Box[] bs = new Box[len/2];

        for (int i = 0; i < len; i = i+2) {
            int x = boxCoords[i], y = boxCoords[i+1];
            if (!checkCoords(x, y)) return null;
            bs[i/2] = boxes[x][y];
        }
        return bs;
    }


    /**
     * Obtener un vector con todas las regiones del tablero.
     *
     * @return el vector que contiene las regiones del tablero
     */
    public List<Region> getRegions() {
        return this.regions;
    }

    
    /**
     * Obtener una lista con todas las regiones del tablero.
     *
     * @param r Región a añadir al tablero
     * @return true si todo sale correctamente, false en caso contrario
     */
    public boolean addRegion(Region r) {
        if (r == null || regions.contains(r)) return false;
        Box[] bs = r.getBoxList();
        for (Box b : bs) {
            if (b.getRegion() != null) return false;
        }
        for (Box b : bs) {
            b.setRegion(r);
        }
        return this.regions.add(r);
    }

    /**
     * Comprueba si todas las casillas del tablero contienen un valor.
     *
     * @return true si todas las casillas tienen un valor, false en caso contrario
     */
    public boolean isBoardFull() {
        for (Box[] row : boxes) {
            for (Box b : row) {
                if (b.isEmpty()) return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si un valor en una casilla es válido en su fila, columna y región (si la hubiera).
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @param num El valor con el que hacer la comprobación
     * @return true si es válida, false en caso contrario
     */
    public boolean checkBox(int x, int y, int num) {
        return (checkRowsCols(x, y, num) && checkRegion(x, y, num));
    }

    /**
     * Comprueba una casilla tiene un valor válido en su fila, columna y región (si la hubiera).
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return true si es válida, false en caso contrario
     */
    public boolean checkBox(int x, int y) {
        return (checkRowsCols(x, y) && checkRegion(x, y));
    }

    /**
     * Comprueba una casilla tiene un valor válido en su fila, columna y región (si la hubiera).
     *
     * @param b Casilla del tablero a comprobar
     * @return true si es válida, false en caso contrario
     */
    public boolean checkBox(Box b) {
        int x = b.getX(), y = b.getY();
        return (checkRowsCols(x, y) && checkRegion(x, y));
    }

    /**
     * Implementa el algoritmo que comprueba si el valor de una casilla es válido en su fila y columna.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @param num El valor con el que hacer la comprobación
     * @return true si es válida, false en caso contrario
     */
    private boolean internal_checkRowsCols(int x, int y, int num) {
        boolean[] row_set = new boolean[size];
        Arrays.fill(row_set, false);
        boolean[] col_set = new boolean[size];
        Arrays.fill(col_set, false);

        row_set[num-1] = true;
        col_set[num-1] = true;

        for (int i = 0; i < size; ++i) {
            if (i != x) {
                Box row_b = boxes[i][y];
                if (!row_b.isEmpty()) {
                    int row_n = row_b.getValue()-1;
                    if (row_set[row_n]) return false;
                    else row_set[row_n] = true;
                }
            }

            if (i != y) {
                Box col_b = boxes[x][i];
                if (!col_b.isEmpty()) {
                    int col_n = col_b.getValue()-1;
                    if (col_set[col_n]) return false;
                    else col_set[col_n] = true;
                }
            }
        }
        return true;
    }
    
    /**
     * Comprueba la fila y columna de una casilla. Comprueba si las coordenadas y el valor son válidos.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @param num El valor con el que hacer la comprobación
     * @return true si es válida, false en caso contrario
     */
    public boolean checkRowsCols(int x, int y, int num) {
        if (!checkCoords(x, y) || !checkNum(num)) return false;
        return internal_checkRowsCols(x, y, num);
    }
    
    /**
     * Comprueba la fila y columna de una casilla. Comprueba si las coordenadas son válidas.
     *
     * @param x Fila de la casilla
     * @param y Columna de la casilla
     * @return true si es válida, false en caso contrario
     */
    public boolean checkRowsCols(int x, int y) {
        if (!checkCoords(x, y)) return false;
        Box b = boxes[x][y];
        if (b.isEmpty()) return false;
        return internal_checkRowsCols(x, y, b.getValue());
    }

    /**
     * Implementa el algoritmo que comprueba el resultado de la región de un casilla a partir de los valores de las casillas que la componen.
     *
     * @param b Casilla del tablero a comprobar
     * @param num Valor con el que hacer la comprobación
     * @return true si es válida, false en caso contrario
     */
    private boolean internal_checkRegion(Box b, int num) {
        boolean res = true, e = b.isEmpty();
        Region r = b.getRegion();
        if (r != null && r.isFull()) {
            if (e) {
                b.setValue(num);
                res = r.check();
                b.clearValue();
            } else {
                res = r.check();		
            }
        }
        return res;
    }

    /**
     * Comprueba una región a partir de una casilla. Comprueba si las coordenadas y el valor son válidos.
     *
     * @param x Fila de una casilla de la región
     * @param y Columna de una casilla de la región
     * @return true si es válida, false en caso contrario
     */
    public boolean checkRegion(int x, int y, int num) {
        if (!checkCoords(x, y) || !checkNum(num)) return false;
        return internal_checkRegion(boxes[x][y], num);
    }

    /**
     * Comprueba una región a partir de una casilla. Comprueba si las coordenadas son válidas.
     *
     * @param x Fila de una casilla de la región
     * @param y Columna de una casilla de la región
     * @return true si es válida, false en caso contrario
     */
    public boolean checkRegion(int x, int y) {
        if (!checkCoords(x, y)) return false;
        Box b = boxes[x][y];
        return internal_checkRegion(b, b.getValue());
    }

    /**
     * Implementa un algoritmo sencillo que revisa el tablero y verifica si el Kenken ha sido resuelto correctamente.
     *
     * @return true no hay fallos, false en caso contrario
     */
    public boolean checkBoard() {
        if (!isBoardFull()) return false;
        for (int i = 0; i < size; ++i) {
            if (!checkRowsCols(i, i)) return false;
        }
        for (Region r : regions) {
            if (!r.check()) return false;
        }
        return true;
    }
}

/* Class created by David Cañadas */
