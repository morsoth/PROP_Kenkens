package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.domain.operations.*;
import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.PairSI;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Collections;

/**
 * Controlador de kenkens.
 * 
 * @see edu.upc.prop.clusterxx.domain.Kenken
 * @see edu.upc.prop.clusterxx.domain.Region
 * @see edu.upc.prop.clusterxx.domain.Box
 * @see edu.upc.prop.clusterxx.domain.operations.Operation
 * 
 * @author Pau Zaragoza Gallardo
 */
public class KenkenController {

    /**
     * Kenken actual.
     */
    private Kenken kenken;

    /**
     * Constructora por defecto.
     * 
     * <p>Asigna null al kenken actual.</p>
     */
    public KenkenController() {
        kenken = null;
    }

    // EDITION

    /**
     * Crea un kenken.
     * 
     * <p>Crea kenken con el tamaño especificado y lo pone en modo edición.</p>
     * 
     * @param size Tamaño del kenken
     * @return true si todo sale correctamente
     */
    public boolean createNewKenken(int size) {
        kenken = new Kenken(size);

        kenken.setState(Kenken.GameState.IN_EDITION);

        return true;
    }

    /**
     * Rellena el tablero del kenken.
     * 
     * <p>Rellena cada casilla del kenken con el valor deseado.</p>
     * 
     * @param board Matriz con los valores de las casillas
     * @return true si todo sale correctamente
     */
    public boolean fillKenkenBoard(int[][] board) {
        for (int i = 0; i < kenken.getSize(); ++i) {
            for (int j = 0; j < kenken.getSize(); ++j) {
                kenken.fillBox(i, j, board[i][j]);
            }
        }

        return true;
    }

    /**
     * Crea las regiones del kenken.
     * 
     * <p>Decodifica la información de las regiones y va creando tales regiones
     * y se las va asignando al kenken.</p>
     * 
     * @param regionData Información de las regiones
     * @return true si todo sale correctamente
     */
    public boolean createRegions(String[] regionsData) {
        String[] line = regionsData[0].split(" ");
        
        int numsRegions = Integer.parseInt(line[0]);
        for (int r = 1; r <= numsRegions; ++r) {
            line = regionsData[r].split(" ");
            int i = 0;

            int op = Integer.parseInt(line[i++]);
            Operation operation = newOperation(op);

            int nums_elem = Integer.parseInt(line[i++]);
            Box[] boxes = new Box[nums_elem];
            for (int e = 0; e < nums_elem; ++e) {
                int x = Integer.parseInt(line[i++]);
                int y = Integer.parseInt(line[i++]);

                boxes[e] = kenken.getBox(x, y);
            }
            
            Region region = new Region(kenken, boxes, operation);
            kenken.addRegion(region);
        }

        return true;
    }

    // Hacer un mapa con las operaciones Map<opCode, Operation>
    /**
     * Devuelve una instancia de la operación deseada.
     * 
     * <p>Devuelve la operación con el código proporcionado como parámetro.</p>
     * 
     * @param opCode Código de la operación
     * @return Una instancia de {@code Operation}
     */
    public Operation newOperation(int opCode) {
        switch (opCode) {
            case 0:
                return null;
            case 1:
                return new Addition();
            case 2:
                return new Substraction();
            case 3:
                return new Multiplication();
            case 4:
                return new Division();
            case 5:
                return new Modulus();
            case 6:
                return new Power();
            default:
                return null;
                // EXCEPTION
        }
    }

    /**
     * Crea un kenken a partir de un String.
     * 
     * <p>Decodifica la información del kenken y lo va creando. Además lo
     * pone en modo edición.</p>
     * 
     * @param kenkenData Información del kenken
     * @return true si todo sale correctamente
     */
    public boolean stringToKenken(String[] kenkenData) {
        String[] line = kenkenData[0].split(" ");

        int size = Integer.parseInt(line[0]);
        kenken = new Kenken(size);
        
        int numsRegions = Integer.parseInt(line[1]);
        for (int r = 1; r <= numsRegions; ++r) {
            line = kenkenData[r].split(" ");
            int i = 0;

            int op = Integer.parseInt(line[i++]);
            Operation operation = newOperation(op);

            int result = Integer.parseInt(line[i++]);

            int nums_elem = Integer.parseInt(line[i++]);
            Box[] boxes = new Box[nums_elem];
            for (int e = 0; e < nums_elem; ++e) {
                int x = Integer.parseInt(line[i++]);
                int y = Integer.parseInt(line[i++]);

                boxes[e] = kenken.getBox(x-1, y-1);

                if (i < line.length && line[i].charAt(0) == '[') {
                    int value = Integer.parseInt(line[i++].replace("[", "").replace("]", ""));
                    boxes[e].setValue(value);
                }
            }
            
            Region region = new Region(kenken, boxes, operation, result);
            kenken.addRegion(region);
        }

        kenken.setState(Kenken.GameState.IN_EDITION);

        return true;
    }

    /**
     * Genera un kenken de cierto tamaño y con ciertas operaciones permitidas.
     * 
     * @param size Tamaño del kenken
     * @param operations Array con las operaciones permitidas
     * @return true si todo sale correctamente
     */
    public boolean generateNewKenken(int size, boolean[] operations) {
        generateKenken(size, operations, new Random());

        return true;
    }

    /**
     * Genera el kenken del día.
     * 
     * <p>Genera un hash con el día actual y general un kenken con tal hash.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean generateDailyKenken() {
        // Generar un hash con el día de hoy
        int currentDay = LocalDate.now().getDayOfYear();
        int hash = Objects.hash(currentDay);

        Random random = new Random(hash);

        // Generar un tamaño de kenken entre 3 y 9
        int size = 3 + random.nextInt(7);

        // Generar operaciones válidas
        boolean[] operations = new boolean[6];
        for (int i = 0; i < operations.length; ++i) operations[i] = (random.nextInt(2) == 1);

        generateKenken(size, operations, random);

        return true;
    }

    /**
     * Genera un kenken.
     * 
     * <p>Rellena el tablero con números aleatorios, crea regiones, les asigna una operación
     * y por útlimo las asigna al kenken.</p>
     * 
     * @return true si todo sale correctamente
     */
    private boolean generateKenken(int size, boolean[] operations, Random random) {
        kenken = new Kenken(size);

        // Matriz con los números del tablero
        int[][] board = new int[size][size];

        // Rellenar tablero sin repetir números en filas y columnas
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                board[i][j] = (i + j) % size + 1;
            }
        }

        // Número de permutaciones a las filas o columnas del tablero
        int permutations = size + random.nextInt(2*size); // Entre N y 3N permutaciones
        for (int p = 0; p < permutations; ++p) {
            // Escoge si permutar una fila o columna
            int swapRow = random.nextInt(2);
            if (swapRow == 1) {
                // Escoge dos filas aleatorias
                int firstRow = random.nextInt(size);
                int secondRow = random.nextInt(size);

                // Intercambia todos los valores de la fila
                for (int j = 0; j < size; ++j) {
                    int tmp = board[firstRow][j];
                    board[firstRow][j] = board[secondRow][j];;
                    board[secondRow][j] = tmp;
                }
            }
            else {
                // Escoge dos columnas aleatorias
                int firstCol = random.nextInt(size);
                int secondCol = random.nextInt(size);

                // Intercambia todos los valores de la columna
                for (int i = 0; i < size; ++i) {
                    int tmp = board[i][firstCol];
                    board[i][firstCol] = board[i][secondCol];
                    board[i][secondCol] = tmp;
                }
            }
        }

        // Si la operación de suma y multiplicación no están permitidas el tamaño máximo de una región será de 2 casillas
        boolean onlyTwoBoxRegions = (!operations[0] && !operations[2]);

        // Limitamos el número de casillas de una región a 4
        int maxRegionSize = Math.min(size, 4);

        // Matriz con la topología de las regiones
        int[][] regions = new int[size][size];
        // Mapa con el número de casillas de cada región
        Map<Integer, Integer> regionCount = new HashMap<>();
        // Id de la próxima región
        int nextRegionInt = 0;

        // Asigna a cada casilla del tablero un identificador de región
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                int regionNum;

                // Probabilidad de 0 ~ 99
                int prob = random.nextInt(100);

                // 45% - Misma región que la casilla de arriba
                if (prob < 45) {
                    // Si no tiene casilla arriba, crea una nueva región
                    if (i == 0) regionNum = nextRegionInt++;

                    // Si la región de arriba tiene ya dos casillas y és el máximo permitido, crea una nueva región
                    else if (onlyTwoBoxRegions && regionCount.get(regions[i-1][j]) == 2) regionNum = nextRegionInt++;

                    // Si la región de la izquierda tiene ya N casillas, crea una nueva región
                    else if (regionCount.get(regions[i-1][j]) == maxRegionSize) regionNum = nextRegionInt++;

                    else regionNum = regions[i-1][j];
                }
                // 45% - Misma región que la casilla de la izquierda
                else if (prob < 90) {
                    // Si no tiene casilla a la izquierda, crea una nueva región
                    if (j == 0) regionNum = nextRegionInt++;

                    // Si la región de la izquierda tiene ya dos casillas y és el máximo permitido, crea una nueva región
                    else if (onlyTwoBoxRegions && regionCount.get(regions[i][j-1]) == 2) regionNum = nextRegionInt++;

                    // Si la región de la izquierda tiene ya N casillas, crea una nueva región
                    else if (regionCount.get(regions[i][j-1]) == maxRegionSize) regionNum = nextRegionInt++;

                    else regionNum = regions[i][j-1];
                }
                // 10% - Nueva región
                else {
                    regionNum = nextRegionInt++;
                }

                regions[i][j] = regionNum;

                if (regionCount.get(regionNum) == null) regionCount.put(regionNum, 0);
                // Añade 1 al contador de la región
                regionCount.put(regionNum, regionCount.get(regionNum) + 1);
            }
        }

        // Mapa con las casillas asociadas a cada región
        Map<Integer, ArrayList<Box>> regionBoxes = new HashMap<>();

        // Añade la casilla del kenken a su correspondiente región 
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                int regionId = regions[i][j];
                ArrayList<Box> boxes = regionBoxes.get(regionId);

                if (boxes == null) {
                    boxes = new ArrayList<>();
                    regionBoxes.put(regionId, boxes);
                }

                // Asigna el valor de la casilla
                kenken.fillBox(i, j, board[i][j]);

                boxes.add(kenken.getBox(i, j));
            }
        }

        // Mapa con las operaciones de cada región
        Map<Integer, Operation> regionOperation = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : regionCount.entrySet()) {
            int regionId = entry.getKey();
            Integer count = entry.getValue();

            int opCode = -2;
            // Si la región sólo tiene una casilla no le asignamos operación
            if (count == 1) opCode = -1;
            // Si la región tiene dos casillas, puede ser cualquier operación
            else if (count == 2) {
                int dividend = Math.max(regionBoxes.get(regionId).get(0).getValue(), regionBoxes.get(regionId).get(1).getValue());
                int divider = Math.min(regionBoxes.get(regionId).get(0).getValue(), regionBoxes.get(regionId).get(1).getValue());
                // Si la división de la región no es exacta no puede ser división
                boolean canBeDivision = ((dividend % divider) == 0);

                do {
                    // Genera un código de operación aloatorio
                    opCode = random.nextInt(operations.length);
                }
                // Comprueba si la operación está permitida
                while (!operations[opCode] || (opCode == 3 && !canBeDivision));
            }
            // Si la región tiene más de dos casillas, sólo puede ser o suma o multiplicación
            else {
                // Si las dos están permitidas
                if (operations[0] && operations[2]) {
                    // Escoge entre el código 0 (suma) o el 2 (multiplicación)
                    opCode = random.nextInt(2);
                    opCode *= 2;
                }
                // Si la suma está permitida pero la multiplicación no
                else if (operations[0] && !operations[2]) {
                    // Escoge la suma
                    opCode = 0;
                }
                // Si la multiplicación está permitida pero la suma no
                else if (!operations[0] && operations[2]) {
                    // Escoge la multiplicación
                    opCode = 2;
                }
                // No puede pasar que se cree una región de más de dos casillas si ni la sumo o multiplicación no están permitidas
            }

            regionOperation.put(regionId, newOperation(opCode + 1));
        }

        // Crea las regiones y las asigna al kenken
        for (Map.Entry<Integer, ArrayList<Box>> entry : regionBoxes.entrySet()) {
            int regionId = entry.getKey();
            ArrayList<Box> boxes = entry.getValue();

            Region region = new Region(kenken, boxes.toArray(new Box[0]), regionOperation.get(regionId));
            kenken.addRegion(region);
        }

        kenken.setState(Kenken.GameState.IN_EDITION);

        return true;
    }

    // PLAYING

    /**
     * Pone en juego el kenken actual.
     * 
     * <p>Asigna el tipo de partida al kenken y lo pone en juego.</p>
     * 
     * @param gameType Tipo de partida
     * @return true si todo sale correctamente
     */
    public boolean startPlayingKenken(Kenken.GameType gameType) {
        kenken.setType(gameType);

        kenken.setState(Kenken.GameState.IN_GAME);

        return true;
    }

    /**
     * Limpia el tablero del kenken actual.
     * 
     * <p>Este método limpia el valor de cada casilla del kenken.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean clearKenkenBoard() {
        for (int i = 0; i < kenken.getSize(); ++i) {
            for (int j = 0; j < kenken.getSize(); ++j) {
                kenken.clearBox(i, j);
            }
        }

        return true;
    }

    /**
     * Rellena la casilla deseada con el valor proporcionado.
     * 
     * <p>Rellena la casilla de las coordenadas (x, y) con el valor deseado y
     * devuelve si hay conflictos con otras casillas. En caso de haber conflicto
     * y no ser una partida clasificatoria no rellenará la casilla.</p>
     * 
     * @param x Coordenada x de la casilla
     * @param y Coordenada y de la casilla
     * @param value Valor a rellenar en la casilla
     * @return false si hay conflicto con otras casillas de la fila, columna o región, true en caso contrario
     */
    public boolean fillBox(int x, int y, int value) {
        int currentValue = kenken.getBox(x, y).getValue();

        kenken.fillBox(x, y, value);

        boolean isCorrect = kenken.checkBox(x, y);

        if (!isCorrect && !kenkenIsRanked()) {
            if (currentValue > 0) kenken.fillBox(x, y, currentValue);
            else kenken.clearBox(x, y);
        }

        return isCorrect;
    }

    /**
     * Vacía la casilla deseada.
     * 
     * <p>Vacía la casilla de las coordenadas (x, y).</p>
     * 
     * @param x Coordenada x de la casilla
     * @param y Coordenada y de la casilla
     * @return true si todo sale correctamente
     */
    public boolean clearBox(int x, int y) {
        kenken.clearBox(x, y);

        return true;
    }

    /**
     * Devuelve las casillas vacía del kenken, ordenadas por los posibles valores.
     * 
     * @return ArrayList de las casillas vacías ordenadas por los posibles valores
     */
    public ArrayList<Box> getEmptyBoxesMRV() {
        ArrayList<Box> emptyBoxes = new ArrayList<>();

        for (int i = 0; i < kenken.getSize(); i++) {
            for (int j = 0; j < kenken.getSize(); j++) {
                Box box = kenken.getBox(i, j);
                if (box.isEmpty()) emptyBoxes.add(box);
            }
        }

        emptyBoxes.sort((box1, box2) -> Integer.compare(countPossibleValues(box1), countPossibleValues(box2)));

        return emptyBoxes;
    }

    /**
     * Devuelve los posibles valores que puede tener una casilla.
     * 
     * @param box Casillas
     * @return Posibles valores que puede tener una casilla
     */
    private int countPossibleValues(Box box) {
        int count = 0;

        for (int num = 1; num <= kenken.getSize(); num++) {
            box.setValue(num);

            if (kenken.checkBox(box)) count++;

            box.clearValue();
        }

        return count;
    }

    /**
     * Resuelve el kenken actual de forma recursiva y devuelve si tiene solución.
     * 
     * <p>Comprueba cada valor posible en la casilla seleccionada, si es posible
     * vuelve a llamarse a este método pero para la siguiente casilla, si no lo es lo borra y
     * prueba el siguiente valor. Si ningún valor es una posible solución para la casilla
     * devuelve false. Si en cambio es la última casilla y encuentra una solución, devuelve true.</p>
     * 
     * @param boxes Lista de casillas
     * @param i Índice de la casilla actual
     * @return false si el kenken tiene solución, true en caso contrario
     */
    public boolean solveKenken(ArrayList<Box> boxes, int i) {
        if (i == boxes.size()) return true;

        Box box = boxes.get(i);

        for (int nums = 1; nums <= kenken.getSize(); ++nums) {
            box.setValue(nums);

            if (kenken.checkBox(box)) {
                if (solveKenken(boxes, i + 1)) return true;
            }

            box.clearValue();
        }

        return false;
    }

    /**
     * Comprueba si la solución del kenken es correcta.
     * 
     * @return true si la solución del kenken es correcta, false en caso contrario
     */
    public boolean checkKenken() {
        return kenken.checkBoard();
    }

    /**
     * Borra el kenken actual.
     * 
     * @return true si todo sale correctamente
     */
    public boolean clearKenken() {
        kenken = null;

        return true;
    }

    // GETTERS

    /**
     * Devuelve el tamaño del kenken actual.
     * 
     * @return Tamaño del kenken actual
     */
    public int getKenkenSize() {
        return kenken.getSize();
    }

    /**
     * Devuelve la información del kenken actual en formato string.
     * 
     * @return Array de strings con la información del kenken
     */
    public String[] getKenkenString() {
        int size = kenken.getSize();

        List<Region> regions = kenken.getRegions();

        int numRegions = regions.size();

        String[] kenkenString = new String[1 + numRegions];

        kenkenString[0] = "" + size + " " + numRegions;

        int r = 1;
        for (Region region : regions) {
            int op = 0;

            Operation operation = region.getOperation();
            if (operation != null) op = operation.getOperationId();

            int result = region.getResult();

            Box[] boxes = region.getBoxList();

            kenkenString[r] = "" + op + " " + result + " " + boxes.length;


            for (Box box : boxes) {
                int x = box.getX();
                int y = box.getY();

                kenkenString[r] += " " + (x+1) + " " + (y+1);

                int boxValue = box.getValue();
                if (boxValue > 0) {
                    kenkenString[r] += " [" + boxValue + "]";
                }
            }

            ++r;
        }

        return kenkenString;
    }

    /**
     * Devuelve la información del kenken actual.
     * 
     * @return Array de {@Object} con la información del tablero y regiones del kenken
     */
    public Object[] getKenkenData() {
        Object[] data = new Object[4];

        int[][] board = new int[kenken.getSize()][kenken.getSize()];

        for (int i = 0; i < kenken.getSize(); ++i) {
            for (int j = 0; j < kenken.getSize(); ++j) {
                board[i][j] = kenken.getBox(i, j).getValue();
            }
        }

        List<Region> regionList = kenken.getRegions();
 
        int[][] regions = new int[kenken.getSize()][kenken.getSize()];

        int[] operations = new int[regionList.size()];

        int[] results = new int[regionList.size()];

        int regionId = 0;

        for (Region region : regionList) {
            Box[] boxes = region.getBoxList();
            for (Box box : boxes) {
                int x = box.getX();
                int y = box.getY();

                regions[x][y] = regionId;
            }

            Operation op = region.getOperation();
            
            if (op == null) operations[regionId] = 0;
            else operations[regionId] = op.getOperationId();

            results[regionId] = region.getResult();

            regionId++;
        }

        data[0] = board;
        data[1] = regions;
        data[2] = operations;
        data[3] = results;

        return data;
    }

    /**
     * Devuelve si hay un kenken activo.
     * 
     * @return true si hay un kenken activo, false en caso contrario
     */
    public boolean kenkenActive() {
        return (kenken != null);
    }

    /**
     * Devuelve si hay un kenken activo y en edición.
     * 
     * @return true si hay un kenken activo y en edición, false en caso contrario
     */
    public boolean kenkenInEdition() {
        return (kenkenActive() && kenken.getState() == Kenken.GameState.IN_EDITION);
    }

    /**
     * Devuelve si hay un kenken activo y en juego.
     * 
     * @return true si hay un kenken activo y en juego, false en caso contrario
     */
    public boolean kenkenInGame() {
        return (kenkenActive() && kenken.getState() == Kenken.GameState.IN_GAME);
    }

    /**
     * Devuelve si el kenken es de tipo normal.
     * 
     * @return true si el kenken es de tipo {@code Kenken.GameType.NORMAL}, false en caso contrario
     */
    public boolean kenkenIsNormal() {
        return (kenken.getType() == Kenken.GameType.NORMAL);
    }

    /**
     * Devuelve si el kenken es de tipo clasificatorio.
     * 
     * @return true si el kenken es de tipo {@code Kenken.GameType.RANKED}, false en caso contrario
     */
    public boolean kenkenIsRanked() {
        return (kenken.getType() == Kenken.GameType.RANKED);
    }

    /**
     * Devuelve si el kenken es el kenken del día.
     * 
     * @return true si el kenken es de tipo {@code Kenken.GameType.DAILY}, false en caso contrario
     */
    public boolean kenkenIsDaily() {
        return (kenken.getType() == Kenken.GameType.DAILY);
    }
}

// Class created by Pau Zaragoza Gallardo