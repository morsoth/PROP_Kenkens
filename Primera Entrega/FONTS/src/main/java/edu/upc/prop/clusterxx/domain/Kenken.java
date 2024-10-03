package edu.upc.prop.clusterxx.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import edu.upc.prop.clusterxx.domain.Box;
import edu.upc.prop.clusterxx.domain.Region;
import edu.upc.prop.clusterxx.exceptions.InvalidKenkenSizeException;

public class Kenken {
    private long id;
    private final int size;
    private GameType gameType;
    private GameState gameState;
    private final Box[][] boxes;
    private List<Region> regions;

    public enum GameType {
	NORMAL,
	DAILY,
	RANKED
    }

    public enum GameState {
	IN_GAME,
	IN_EDITION
    }

    public Kenken(int size) throws InvalidKenkenSizeException {
	this(size, GameType.NORMAL);
    }

    public Kenken(int size, GameType gameType) {
	if (size < 3 || size > 9) {
	    throw new InvalidKenkenSizeException();
	}
	this.id = 0;
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
	this.id = 0;
	this.size = len1;
	this.gameType = GameType.NORMAL;
	this.gameState = GameState.IN_EDITION;
	this.boxes = boxes;
	this.regions = new ArrayList<Region>();
    }

    // Comprobar que los parámetros de los métodos son correctos
    private boolean checkCoords(int x, int y) {
	return (x >= 0 && x < size && y >= 0 && y < size);
    }
    private boolean checkNum(int num) {
	return (num > 0 && num <= size);
    }

    // ID
    public long getID() {
	return this.id;
    }
    public void setID(long id) {
	this.id = id;
    }

    // SIZE
    public int getSize() {
	return this.size;
    }

    // GAME TYPE
    public GameType getType() {
	return this.gameType;
    }
    public void setType(GameType gameType) {
	this.gameType = gameType;
    }

    // GAME STATE
    public GameState getState() {
	return this.gameState;
    }
    public void setState(GameState gameState) {
	this.gameState = gameState;
    }

    // LLENAR CASILLA
    public boolean fillBox(int x, int y, int num) {
	if (!checkCoords(x, y) || !checkNum(num)) return false;
	return this.boxes[x][y].setValue(num);
    }

    // VACIAR CASILLA
    public boolean clearBox(int x, int y) {
	if (!checkCoords(x, y)) return false;
	return this.boxes[x][y].clearValue();
    }

    // VECTOR DE TODAS LAS CASILLAS
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

    // CASILLAS CONCRETAS
    public Box getBox(int x, int y) {
	if (!checkCoords(x, y)) return null;
	return this.boxes[x][y];
    }
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

    // REGIONES DEL TABLERO
    public List<Region> getRegions() {
	return this.regions;
    }

    // AÑADIR REGIÓN AL TABLERO
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

    // ELIMINAR REGIÓN DEL TABLERO
    public boolean removeRegion(int x, int y) {
	if (!checkCoords(x, y)) return false;
	Box b = boxes[x][y];
	Region r = b.getRegion();
	if (r == null) return false;
	for (Box other : r.getBoxList()) {
	    other.removeRegion();
	}
	return regions.remove(r);
    }

    // COMPROVAR SI EL TABLERO ESTÁ LLENO
    public boolean isBoardFull() {
	for (Box[] row : boxes) {
	    for (Box b : row) {
		if (b.isEmpty()) return false;
	    }
	}
	return true;
    }

    // COMPROBAR CASILLA (FILA, COLUMNA y REGIÓN)
    public boolean checkBox(int x, int y, int num) {
	return (checkRowsCols(x, y, num) && checkRegion(x, y, num));
    }
    public boolean checkBox(int x, int y) {
	return (checkRowsCols(x, y) && checkRegion(x, y));
    }
    public boolean checkBox(Box b) {
	int x = b.getX(), y = b.getY();
	return (checkRowsCols(x, y) && checkRegion(x, y));
    }

    // COMPROBAR FILAS Y COLUMNAS
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
    public boolean checkRowsCols(int x, int y, int num) {
	if (!checkCoords(x, y) || !checkNum(num)) return false;
	return internal_checkRowsCols(x, y, num);
    }
    public boolean checkRowsCols(int x, int y) {
	if (!checkCoords(x, y)) return false;
	Box b = boxes[x][y];
	if (b.isEmpty()) return false;
	return internal_checkRowsCols(x, y, b.getValue());
    }

    // COMPROBAR REGIÓN
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
    public boolean checkRegion(int x, int y, int num) {
	if (!checkCoords(x, y) || !checkNum(num)) return false;
	return internal_checkRegion(boxes[x][y], num);
    }
    public boolean checkRegion(int x, int y) {
	if (!checkCoords(x, y)) return false;
	Box b = boxes[x][y];
	return internal_checkRegion(b, b.getValue());
    }

    // COMPROBAR TABLERO
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
