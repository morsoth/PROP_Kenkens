package edu.upc.prop.clusterxx.presentation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.PresentationController;
import edu.upc.prop.clusterxx.presentation.MainView;
import edu.upc.prop.clusterxx.presentation.ParameterSelectionViewController.CreationType;

/**
 * Controlador de la vista principal
 * 
 * Es el intermediario entre la vista principal y el controlador de presentacion.
 * Dispone de los metodos y estructuras de datos necesarias para las funciones relacionadas con
 * jugar y crear kenkens y con el menu principal.
 * 
 * @author Guillem Nieto Ribo
 */

public class MainViewController {
    /**
     * Controlador de presentación.
     */
    private PresentationController PresentationCntrl;

    /**
     * Vista principal.
     */
    private MainView mainView;

    /**
     * Tamaño del kenken actual.
     */
    private int size;

    /**
     * Estado del programa.
     */
    private State currentState;
    
    /**
     * Sesión iniciado o no.
     */
    private boolean logged;

    /**
     * Coordenadas de la casilla seleccionada.
     */
    private int selectedX, selectedY;

    /** 
     * Número de casillas vacías.
     */
    private int emptyBoxes;

    /**
     * Número de regiones creadas.
     */
    private int regions;

    /**
     * Matriz de valores del kenken en creación.
     */
    private int[][] valueMatrix;

    /**
     * Matriz de casillas seleccionadas en la creación de regiones.
     */
    private boolean[][] isSelected;

    /**
     * Indica las regiones de cada casilla del kenken en creación.
     */
    private int[][] inRegionMatrix;

    /**
     * Número de casillas seleccionadas en la creación de regiones.
     */
    private int boxesSelected;

    /**
     * Número de casillas que ya están en una región.
     */
    private int boxesInRegion;
    
    /**
     * Operación que se asigna a cada región creada.
     */
    private Map<Integer,Integer> opRegions;

    /**
     * Tamaño de cada región creada.
     */
    private Map<Integer,Integer> sizeRegions;

    /**
     * Matriz de las regiones sin valor.
     */
    private boolean[][] isEmpty;

    /**
     * Verifica si la casilla en (x, y) está tocando alguna casilla seleccionada adyacente.
     * 
     * @param x La coordenada x de la casilla.
     * @param y La coordenada y de la casilla.
     * @return true si la casilla está tocando alguna casilla seleccionada adyacente, de lo contrario false.
     */
    private boolean isTouching(int x, int y) {
        if (x > 0 && isSelected[x-1][y]) return true;
        else if (x < size-1 && isSelected[x+1][y]) return true;
        else if (y > 0 && isSelected[x][y-1]) return true;
        else if (y < size-1 && isSelected[x][y+1]) return true;
        else return false;
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) para recorrer una región.
     * 
     * @param selected Matriz que indica las casillas seleccionadas.
     * @param visited Matriz que indica las casillas visitadas.
     * @param x Coordenada x de la casilla actual.
     * @param y Coordenada y de la casilla actual.
     */
    private static void dfs(boolean[][] selected, boolean[][] visited, int x, int y) {
        if (x < 0 || x >= selected.length || y < 0 || y >= selected[0].length || visited[x][y] || !selected[x][y]) {
            return;
        }

        visited[x][y] = true;

        // Recorrer las casillas adyacentes
        dfs(selected, visited, x + 1, y);
        dfs(selected, visited, x - 1, y);
        dfs(selected, visited, x, y + 1);
        dfs(selected, visited, x, y - 1);
    }

    /**
     * Verifica si la región sigue conectada después de eliminar la casilla en (x, y).
     * 
     * @param x La coordenada x de la casilla.
     * @param y La coordenada y de la casilla.
     * @return true si la región sigue conectada, en caso contrario false.
     */
    private boolean stillConnected(int x, int y) {
        if (boxesSelected < 3) return true;
        int rows = isSelected.length;
        int cols = isSelected[0].length;

        // Crear una copia de la matriz de casillas seleccionadas sin la casilla (x, y)
        boolean[][] auxMatrix = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            auxMatrix[i] = Arrays.copyOf(isSelected[i], cols);
        }
        auxMatrix[x][y] = false;

        // Encontrar un punto de inicio que forme parte de la región
        int startX = -1, startY = -1;
        outerLoop:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (auxMatrix[i][j]) {
                    startX = i;
                    startY = j;
                    break outerLoop;
                }
            }
        }

        // Si no encontramos ningún otro punto en la región, la región se desconecta
        if (startX == -1 || startY == -1) {
            return false;
        }

        // Realizar DFS para verificar si la región sigue siendo conexa
        boolean[][] visited = new boolean[rows][cols];
        dfs(auxMatrix, visited, startX, startY);

        // Comprobar si todas las casillas de la región se han visitado, excepto la quitada
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (auxMatrix[i][j] && !visited[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Verifica si el valor v no está presente en la fila dada.
     * 
     * @param row La fila a verificar.
     * @param v El valor a buscar.
     * @return true si el valor no está en la fila, de lo contrario false.
     */
    private boolean notInRow(int row, int v) {
        for (int col = 0; col < size; ++col) {
            if (valueMatrix[row][col] == v) return false;
        }
        return true;
    }

    /**
     * Verifica si el valor v no está presente en la columna dada.
     * 
     * @param col La columna a verificar.
     * @param v El valor a buscar.
     * @return true si el valor no está en la columna, de lo contrario false.
     */
    private boolean notInColumn(int col, int v) {
        for (int row = 0; row < size; ++row) {
            if (valueMatrix[row][col] == v) return false;
        }
        return true;
    }

    /**
     * Verifica si los dos valores seleccionados se dividen exactamente uno por el otro (el grande entre el pequeño).
     * 
     * @return true si uno de los valores es divisible por el otro, de lo contrario false.
     */
    private boolean exactDiv() {
        int a = -1, b = -1;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (isSelected[i][j]) {
                    if (a == -1) a = valueMatrix[i][j];
                    else {
                        b = valueMatrix[i][j];
                        break;
                    }
                }
            }
        }
        if (a > b) return a % b == 0;
        else return b % a == 0;
    }

    /**
     * Enumeración de los posibles estados del controlador.
     */
    public enum State {
        IN_MENU,
        PLAYING,
        PLAYING_RANKED,
        PLAYING_DAILY,
        CREATING_NUMBERS,
        CREATING_REGIONS
    }

    /**
     * Constructor del controlador de la vista principal.
     * 
     * @param pc El controlador de la presentación.
     */
    public MainViewController(PresentationController pc) {
        PresentationCntrl = pc;
        mainView = new MainView(this);
        size = -1;
        currentState = State.IN_MENU;
        selectedX = selectedY = -1;
        logged = false;
    }

    /**
     * Establece el estado de juego normal con un Kenken de tamaño especificado.
     * 
     * @param size El tamaño del Kenken.
     */
    public void setPlayingState(int size) {
        currentState = State.PLAYING;
        mainView.setIngame(true);
        mainView.setRanked(false);
        mainView.clearKenken();
        mainView.createKenkenWithSize(size);
        this.size = size;
        emptyBoxes = size * size;
        isEmpty = new boolean[size][size];
        for (int i = 0; i < size; ++i) Arrays.fill(isEmpty[i], true);
        selectedX = selectedY = -1;
    }

    /**
     * Establece el estado de juego de kenken del dia con un Kenken de tamaño especificado.
     * 
     * @param size El tamaño del Kenken.
     */
    public void setDailyState(int size) {
        currentState = State.PLAYING_DAILY;
        mainView.setIngame(true);
        mainView.setRanked(false);
        mainView.clearKenken();
        mainView.createKenkenWithSize(size);
        this.size = size;
        emptyBoxes = size * size;
        isEmpty = new boolean[size][size];
        for (int i = 0; i < size; ++i) Arrays.fill(isEmpty[i], true);
        selectedX = selectedY = -1;
    }

    /**
     * Establece el estado de juego clasificado con un Kenken de tamaño especificado.
     * 
     * @param size El tamaño del Kenken.
     */
    public void setRankedState(int size) {
        currentState = State.PLAYING_RANKED;
        mainView.setIngame(true);
        mainView.setRanked(true);
        this.size = size;
        mainView.clearKenken();
        mainView.createKenkenWithSize(size);
        emptyBoxes = size * size;
        isEmpty = new boolean[size][size];
        for (int i = 0; i < size; ++i) Arrays.fill(isEmpty[i], true);
        selectedX = selectedY = -1;
    }

    /**
     * Establece la visibilidad de la vista principal.
     * 
     * @param show si se muestra o no.
     */
    public void showView(boolean show) {
        mainView.setVisible(show);
    }

    /**
     * Muestra la vista de clasificación.
     */
    public void showRanking() {
        this.showView(false);
        PresentationCntrl.showRankingView(true);
    }

    /**
     * Muestra la vista de selección de parámetros para crear un nuevo Kenken.
     */
    public void createKenken() {
        this.showView(false);
        PresentationCntrl.showParameterSelectionView(true);
    }

    /**
     * Muestra la vista para importar un Kenken.
     */
    public void importKenken() {
        this.showView(false);
        PresentationCntrl.showImportKenkenView(true);
    }

    /**
     * Inicia el proceso de creación de una nueva cuenta.
     */
    public void startCreatingAccount() {
        if (!logged) {
            PresentationCntrl.startCreatingAccount();
            this.showView(false);
            PresentationCntrl.showRegistrationView(true);
        }
    }

    /**
     * Inicia el proceso de inicio de sesión.
     */
    public void startLogIn() {
        if (!logged) {
            PresentationCntrl.startLogIn();
            this.showView(false);
            PresentationCntrl.showRegistrationView(true);
        }
    }

    /**
     * Cierra la sesión activa.
     */
    public void logOut() {
        if (logged) {
            PresentationCntrl.logOut();
            setLogged(false, null, null);
        }
    }

    /**
     * Establece el estado de inicio de sesión del usuario.
     * 
     * @param logged true si el usuario está conectado, en caso contrario false.
     * @param username El nombre de usuario del usuario conectado.
     * @param points Los puntos del usuario conectado.
     */
    public void setLogged(boolean logged, String username, String points) {
        this.logged = logged;
        mainView.setLoggedIn(logged, username, points);
    }

    /**
     * Inicia el proceso de creación de un nuevo Kenken con el tamaño especificado.
     * 
     * @param size El tamaño del Kenken.
     */
    public void startKenkenCreation(int size) {
        mainView.clearKenken();
        this.size = size;
        emptyBoxes = size * size;
        currentState = State.CREATING_NUMBERS;
        mainView.setEdit(true);
        mainView.createKenkenWithSize(size);
        valueMatrix = new int[size][size];
        for (int i = 0; i < size; ++i) {
            Arrays.fill(valueMatrix[i], -1);
        }
        inRegionMatrix = new int[size][size];
        for (int i = 0; i < size; ++i) {
            Arrays.fill(inRegionMatrix[i], -1);
        }
        selectedX = selectedY = -1;
    }

    /**
     * Inicia el proceso de creación de regiones después de que todas las casillas estén llenas.
     */
    public void startCreatingRegions() {
        if (emptyBoxes == 0) {
            currentState = State.CREATING_REGIONS;
            if (selectedX != -1 && selectedY != -1) mainView.setUnselected(selectedX, selectedY);
            selectedX = selectedY = -1;
            boxesSelected = 0;
            boxesInRegion = 0;
            opRegions = new HashMap<Integer, Integer>();
            sizeRegions = new HashMap<Integer, Integer>();
            isSelected = new boolean[size][size];
            for (int i = 0; i < size; ++i) {
                Arrays.fill(isSelected[i], false);
            }
            regions = 0;
        } else {
            PresentationCntrl.showError("You must fill all the boxes before creating regions.");
        }
    }

    /**
     * Inicia una partida clasificatoria si el usuario está loggeado.
     */
    public void playRanked() {
        if (logged) {
            PresentationCntrl.playRanked();
        } else {
            PresentationCntrl.showError("You must be logged in in order to play a ranked game.");
        }
    }

    /**
     * Inicia el Kenken del día si el usuario está loggeado.
     */
    public void playDailyKenken() {
        if (logged) {
            PresentationCntrl.playDailyKenken();
        } else {
            PresentationCntrl.showError("You must be logged in in order to play the Daily Kenken.");
        }
    }

    /**
     * Sale de la partida actual.
     */
    public void leaveKenken() {
        mainView.setIngame(false);
        PresentationCntrl.clearKenken();
        mainView.clearKenken();
        mainView.setEdit(false);
        selectedX = selectedY = -1;
        if (currentState == State.PLAYING_RANKED) PresentationCntrl.updatePoints();
        mainView.stopTimer();
        mainView.showTimer(false);
        currentState = State.IN_MENU;
    }

    /**
     * Guarda la partida actual si el usuario está loggeado.
     */
    public void saveGame() {
        if (logged) {
            PresentationCntrl.saveGame();
            PresentationCntrl.showMessage("Game saved.");
        }
        else PresentationCntrl.showError("You must be logged in in order to save a game.");
    }

    /**
     * Empieza el proceso de seleccionar un kenken de la BD.
     */
    public void startKenkenSelection() {
        this.showView(false);
        PresentationCntrl.showKenkenSelectionView(true);
    }

    /**
     * Muestra el kenken en la vista principal.
     * 
     * @param values valores iniciales.
     * @param regs regiones del kenken.
     * @param ops operaciones de cada región.
     * @param results resultados de cada región.
     */
    public void showKenken(int[][] values, int[][] regs, int[] ops, int[] results) {
        int N = values.length;
        mainView.setRegions(regs);
        boolean[] settedOp = new boolean[ops.length];
        Arrays.fill(settedOp, false);//para saber si se ha puesto en la vista la operaciñon y resultado de esa región
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                String num;
                if (values[i][j] == -1) num = "";
                else num = String.valueOf(values[i][j]);
                mainView.setBoxText(i, j, num);
                //Establece el texto de la región.
                String result = " ";
                String op = " ";
                if (!settedOp[regs[i][j]]) {
                    settedOp[regs[i][j]] = true;
                    switch (ops[regs[i][j]]) {
                        case 0: op = "=";
                                break;
                        case 1: op = "+";
                                break;
                        case 2: op = "-";
                                break;
                        case 3: op = "x";
                                break;
                        case 4: op = "/";
                                break;
                        case 5: op = "%";
                                break;
                        case 6: op = "^";
                                break;
                        default: break;
                    }
                    result = String.valueOf(results[regs[i][j]]);
                }
                mainView.setRegionText(i, j, op + " " + result);
            }
        }

        //Se inicia el timer si se empieza una clasificatoria.
        if (currentState == State.PLAYING_RANKED) {
            mainView.showTimer(true);
            mainView.startTimer();
        }
    }

    /**
     * Lee la tecla pulsado y la procesa dependiendo del estado en que estamos.
     * 
     * @param key tecla pulsada.
     */
    public void pressedKey(char key) {
        if (currentState == State.CREATING_NUMBERS && selectedX > -1 && selectedY > -1) {
            if ((int)(key) == 8) {
                if (valueMatrix[selectedX][selectedY] != -1) ++emptyBoxes;
                mainView.setBoxText(selectedX, selectedY, " ");
                this.valueMatrix[selectedX][selectedY] = -1;
                mainView.setUnselected(selectedX, selectedY);
                selectedX = selectedY = -1;
            }
            else if (key > '0' && key <= (char)(size + '0')) {
                int value = (int)(key - '0');
                if (notInRow(selectedX, value) && notInColumn(selectedY, value)) {
                    if (valueMatrix[selectedX][selectedY] == -1) --emptyBoxes;
                    valueMatrix[selectedX][selectedY] = value;
                    mainView.setBoxText(selectedX, selectedY, "" + key);
                    mainView.setUnselected(selectedX, selectedY);
                    selectedX = selectedY = -1;
                }
            }
        }
        else if (currentState == State.PLAYING && selectedX > -1 && selectedY > -1) {
            if ((int)(key) == 8) {
                if (!isEmpty[selectedX][selectedY]) ++emptyBoxes;
                isEmpty[selectedX][selectedY] = true;
                mainView.setBoxText(selectedX, selectedY, " ");
                PresentationCntrl.clearBox(selectedX, selectedY);
                mainView.setUnselected(selectedX, selectedY);
                selectedX = selectedY = -1;
            }
            else if (key > '0' && key <= (char)(size + '0')) {
                boolean ret = PresentationCntrl.fillBox(selectedX,selectedY,(int)(key - '0'));
                if (ret) {
                    mainView.setBoxText(selectedX, selectedY, "" + key);
                    mainView.setUnselected(selectedX, selectedY);
                    if (isEmpty[selectedX][selectedY]) {
                        --emptyBoxes;
                        isEmpty[selectedX][selectedY] = false;
                    }
                    selectedX = selectedY = -1;
                }
            }
            
            if (emptyBoxes == 0) {
                mainView.winGame(true);
                this.currentState = State.IN_MENU;
            }
        }
        else if (currentState == State.PLAYING_RANKED && selectedX > -1 && selectedY > -1) {
            if ((int)(key) == 8) {
                if (!isEmpty[selectedX][selectedY]) ++emptyBoxes;
                isEmpty[selectedX][selectedY] = true;
                mainView.setBoxText(selectedX, selectedY, " ");
                PresentationCntrl.clearBox(selectedX, selectedY);
                mainView.setUnselected(selectedX, selectedY);
                selectedX = selectedY = -1;
            }
            else if (key > '0' && key <= '9') {
                PresentationCntrl.fillBox(selectedX,selectedY,(int)(key - '0'));
                mainView.setBoxText(selectedX, selectedY, "" + key);
                mainView.setUnselected(selectedX, selectedY);
                if (isEmpty[selectedX][selectedY]) {
                    --emptyBoxes;
                    isEmpty[selectedX][selectedY] = false;
                }
                selectedX = selectedY = -1;
            }
        }
    }

    /**
     * Añade una región al kenken en creación.
     * 
     * @param op operación de la región.
     */
    public void addRegion (char op) {
        if (currentState == State.CREATING_REGIONS && boxesSelected > 0) {
            if (((op == '-' || op == '/' || op == '^' || op == '%') && boxesSelected == 2) || ((op == '+' || op == 'x' ) && boxesSelected > 1) || (op == ' ' && boxesSelected == 1)) {
                if ((op == '/' && exactDiv()) || op == '-' || op == '+' || op == '^' || op == '%' || op == 'x' || op == ' ') {
                    boxesInRegion += boxesSelected;
                    ++regions;
                    sizeRegions.put(regions,boxesSelected);
                    if (boxesSelected == 1) op = '=';
                    switch (op) {
                        case '+':
                            opRegions.put(regions,1);
                            break;
                        case '-':
                            opRegions.put(regions,2);
                            break;
                        case 'x':
                            opRegions.put(regions,3);
                            break;
                        case '/':
                            opRegions.put(regions,4);
                            break;
                        case '%':
                            opRegions.put(regions,5);
                            break;
                        case '^':
                            opRegions.put(regions,6);
                            break;
                        default:
                            opRegions.put(regions,0);
                            break;
                    }
                    
                    for (int i = 0; i < size && boxesSelected > 0; ++i) {
                        for (int j = 0; j < size && boxesSelected > 0; ++j) {
                            if (isSelected[i][j]) {
                                --boxesSelected;
                                inRegionMatrix[i][j] = regions;
                                isSelected[i][j] = false;
                                mainView.setUnselected(i, j);
                                mainView.setRegionText(i, j, "" + op);
                            }
                        }
                    }

                    mainView.setRegions(inRegionMatrix);
                }
                else PresentationCntrl.showWarning("The result of a division must be an integer.");
            }
            else PresentationCntrl.showWarning("The number of boxes doesn't correspond to the selected operation.");
        }
        else PresentationCntrl.showWarning("There are no boxes selected.");
    }

    /**
     * Detecta que casilla se ha pulsado y la procesa dependieno del estado en que estamos.
     * 
     * @param x coordenada x.
     * @param y coordenada y.
     */
    public void pressedButton(int x, int y) {
        if (currentState == State.CREATING_NUMBERS || currentState == State.PLAYING || currentState == State.PLAYING_RANKED) {
            if (selectedX != -1 || selectedY != -1) {
                mainView.setUnselected(selectedX, selectedY);
            }
            selectedX = x;
            selectedY = y;
            mainView.setSelected(x, y);
        }
        else if (currentState == State.CREATING_REGIONS) {
            if (boxesSelected < size || isSelected[x][y]) {
                if (isSelected[x][y]) {
                    isSelected[x][y] = false;
                    if (stillConnected(x,y)) {//revisar les caselles del voltant que segueixin sent tocants
                        mainView.setUnselected(x, y);
                        --boxesSelected;
                        if (inRegionMatrix[x][y] != -1) {
                            ++boxesInRegion;
                            sizeRegions.put(inRegionMatrix[x][y], sizeRegions.get(inRegionMatrix[x][y]) + 1);
                        }
                    }
                    else isSelected[x][y] = true;
                }
                else {
                    if (boxesSelected == 0 || isTouching(x, y)) {
                        mainView.setSelected(x, y);
                        isSelected[x][y] = true;
                        ++boxesSelected;
                        if (inRegionMatrix[x][y] != -1) {
                            --boxesInRegion;
                            sizeRegions.put(inRegionMatrix[x][y], sizeRegions.get(inRegionMatrix[x][y]) - 1);
                        }
                    }
                }
            }
        }
    }

    /**
     * Finalización de la creación de un kenken.
     * 
     * @param save guardar o no el kenken en la BD.
     * @param play jugar o no el kenken creado.
     */
    public void finishEdit(boolean save, boolean play) {
        if (save && !logged) PresentationCntrl.showWarning("You have to be logged in in order to save a kenken.");
        else {
            mainView.setEdit(false);
            if (boxesInRegion == size*size) {
                int Nregions = 0;
                Map<Integer,String> regionsData = new HashMap<Integer,String>();
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        if (!regionsData.containsKey(inRegionMatrix[i][j])) {
                            String actualValue;
                            int regionSize = sizeRegions.get(inRegionMatrix[i][j]);
                            String size_string = String.valueOf(regionSize);
                            int regionOp;
                            if (regionSize > 1) regionOp = opRegions.get(inRegionMatrix[i][j]);
                            else regionOp = 0;
                            ++Nregions;
                            actualValue = (char)(regionOp + '0') + " " + size_string + " " + (char)(i + '0') + " " + (char)(j + '0');
                            regionsData.put(inRegionMatrix[i][j], actualValue);
                        }
                        else {
                            String actualValue = regionsData.get(inRegionMatrix[i][j]);
                            actualValue += " " + (char)(i + '0') + " " + (char)(j + '0');
                            regionsData.put(inRegionMatrix[i][j], actualValue);
                        }
                    }
                }

                String[] kenkenData = new String[Nregions+1];
                kenkenData[0] = String.valueOf(Nregions);
                int r = 1;
                for (String value : regionsData.values()) kenkenData[r++] = value;

                mainView.clearKenken();
                PresentationCntrl.creationFinished(save, play, size, valueMatrix, kenkenData);
            }
        }
    }

    /**
     * Soluciona el kenken actual.
     */
    public void solveKenken() {
        boolean solution = PresentationCntrl.solveKenken();
        if (solution && currentState == State.PLAYING) {
            mainView.winGame(true);
            this.currentState = State.IN_MENU;
        }
        else if (!solution && currentState == State.PLAYING) {
            PresentationCntrl.showError("There is no solution using the actual values.");
        }
        else if (currentState == State.PLAYING_RANKED) {
            mainView.winGame(false);
            mainView.stopTimer();
            PresentationCntrl.updatePoints();
            this.currentState = State.IN_MENU;
        }
        else if (currentState == State.PLAYING_DAILY) {
            PresentationCntrl.showError("Solution not available for daily kenken.");
        }
    }

    /**
     * Función para terminar una partida clasificatoria.
     */
    public void finishGame() {
        if (currentState == State.PLAYING_RANKED) {
            if (emptyBoxes == 0) {
                mainView.stopTimer();
                int time = mainView.getTime();
                if (PresentationCntrl.finishGame(time)) mainView.winGame(true);
                else mainView.winGame(false);
                this.currentState = State.IN_MENU;
                PresentationCntrl.updatePoints();
            }
            else PresentationCntrl.showWarning("You need to fill all the boxes.");
        }
        else PresentationCntrl.showError("This function is only available for ranked mode.");
    }

    /**
     * Carga la partida guardada del usuario.
     */
    public void resumeGame() {
        if (logged) PresentationCntrl.resumeGame();
        else PresentationCntrl.showError("You are not logged.");
    }
}

/* Class created by Guillem Nieto */