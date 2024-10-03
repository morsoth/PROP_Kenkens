package edu.upc.prop.clusterxx.domain;

import edu.upc.prop.clusterxx.domain.*;
import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.PairSI;

import edu.upc.prop.clusterxx.persistence.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Controlador de la capa de dominio.
 * 
 * @see edu.upc.prop.clusterxx.domain.KenkenController
 * @see edu.upc.prop.clusterxx.domain.UserController
 * @see edu.upc.prop.clusterxx.persistence.PersistenceController
 * 
 * @author Pau Zaragoza Gallardo
 */
public class DomainController {

    /**
     * Controlador para gestionar la persistencia de datos
     */
    private PersistenceController PersistenceCntrl;

    /**
     * Controlador para gestionar los kenken.
     */
    private KenkenController KenkenCntrl;
    /**
     * Controlador para gestionar los usuarios.
     */
    private UserController UserCntrl;

    /**
     * Constructora por defecto.
     * 
     * <p>Inicializa los controladores: {@code PersistenceController},
     * {@code UserController} y {@code KenkenController}, ademas de inicializar el ránking.</p>
     */
    public DomainController() {
        PersistenceCntrl = new PersistenceController();

        KenkenCntrl = new KenkenController();
        UserCntrl = new UserController();

        String[] users = PersistenceCntrl.getUsers();
        ArrayList<PairSI> usersData = new ArrayList<>();
        for (String user : users) {
            String[] data = PersistenceCntrl.loadUserData(user);

            usersData.add(new PairSI(data[0], Integer.parseInt(data[2])));
        }
        UserCntrl.initializeRanking(usersData);
    }

    // USER

    /**
     * Crea una cuenta de usuario y inicia sesión.
     * 
     * <p>Comprueba que no existe un usuario con el mismo nombre.
     * A continuación, guarda los datos de usuario en la capa de
     * persistencia e inicia la sesión del usuario.</p>
     * 
     * @param name Nombre del usuario
     * @param password Contraseña del usuario
     * @return true si todo sale correctamente
     */
    public boolean createAccount(String name, String password) {
        if (PersistenceCntrl.userExists(name)) return false;

        PersistenceCntrl.saveUserData(name, password, 0);

        UserCntrl.logIn(name, password, 0);

        UserCntrl.addUserRanking(new PairSI(name, 0));

        return true;
    }

    /**
     * Inicia sesión.
     * 
     * <p>Comprueba que existe el usuario con el mismo nombre.
     * A continuación, carga los datos del usuario de la capa de persistencia,
     * comprueba que la contraseña es correcta e inicia la sesión del usuario.</p>
     * 
     * @param name Nombre del usuario
     * @param password Contraseña del usuario
     * @return true si todo sale correctamente
     */
    public boolean loginUser(String name, String password) {
        if (!PersistenceCntrl.userExists(name)) return false;

        String[] userData = PersistenceCntrl.loadUserData(name);

        if (!userData[1].equals(password)) return false;

                    //  name         password     points
        UserCntrl.logIn(userData[0], userData[1], Integer.parseInt(userData[2]));

        return true;
    }

    /**
     * Cierra la sesión del usuario.
     * 
     * <p>Guarda los datos del usuario de la capa de persistencia
     * y cierra la sesión del usuario.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean logoutUser() {
        PersistenceCntrl.saveUserData(UserCntrl.getUserName(), UserCntrl.getUserPassword(), UserCntrl.getUserPoints());

        UserCntrl.logOut();

        return true;
    }

    // CREATE KENKEN

   /**
     * Crea un kenken desde cero con la información que proporciona el usuario.
     * 
     * <p>Comprueba que hay una sesión activa y que los parámetros son correctos.
     * A continuación, crea un kenken con el tamaño especificado, lo rellena con los valores
     * deseados y crea las correspondientes regiones.</p>
     * 
     * <p>El formato de la información de las regiones es:
     * <pre>{@code
     *      R
     *      op1 e1 x11 y11 x12 y12  ... x1n y1n
     *      .
     *      .
     *      .
     *      opn en xn1 yn1 xn2 yn2  ... xnn ynn
     * }
     * </pre>
     * </p>
     * 
     * @param size Tamaño del kenken
     * @param board Matriz con los valores de cada casilla del kenken
     * @param regionsData Array con la información de las regiones del kenken
     * @return true si todo sale correctamente
     */
    public boolean createKenken(int size, int[][] board, String[] regionsData) {
        if (KenkenCntrl.kenkenActive()) return false;
        if (board == null || board.length != size || board[0].length != size) return false;
        if (regionsData == null || regionsData.length == 0) return false;

        KenkenCntrl.createNewKenken(size);

        KenkenCntrl.fillKenkenBoard(board);

        KenkenCntrl.createRegions(regionsData);

        return true;
    }

    /**
     * Pone en juego el kenken que está en edición.
     * 
     * <p>Comprueba que hay un kenken en edición. A continuación, limpia
     * los valores del tablero y inicializa la partida.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean playKenkenEdit() {
        if (!KenkenCntrl.kenkenInEdition()) return false;

        KenkenCntrl.clearKenkenBoard();

        KenkenCntrl.startPlayingKenken(Kenken.GameType.NORMAL);

        return true;
    }

    /**
     * Guarda el kenken en edición en la capa de persistencia.
     * 
     * <p>Comprueba que hay un kenken en edición. A continuación, limpia los
     * valores del tablero, obtiene la información del kenken en edición y la
     * guarda en la capa de persistencia.</p>
     * 
     * @return true si todo sale correctamente
     */
    public String saveKenkenEdit() {
        if (!KenkenCntrl.kenkenInEdition()) return null;

        KenkenCntrl.clearKenkenBoard();

        String[] kenkenData = KenkenCntrl.getKenkenString();

        UUID uniqueID = UUID.randomUUID();

        PersistenceCntrl.saveKenken(kenkenData, uniqueID.toString());

        return uniqueID.toString();
    }

    // IMPORT KENKEN

    /**
     * Importa el kenken a partir de un archivo y lo guarda en la persistencia.
     * 
     * <p>Comprueba que no hay un kenken activo. A continuación, obtiene
     * la información del archivo seleccionado por el usuario y lo guarda en el sistema.</p>
     * 
     * @param path Ruta del archivo con la información del kenken
     * @param kenkenName Nombre del kenken
     * @return true si todo sale correctamente
     */
    public String importKenken(String path) {
        if (KenkenCntrl.kenkenActive()) return null;

        UUID uniqueID = UUID.randomUUID();

        PersistenceCntrl.importKenken(path, uniqueID.toString());

        return uniqueID.toString();
    }

    // PLAY KENKEN

    /**
     * Crea y pone en juego una partida con la información que proporciona el usuario.
     * 
     * <p>Comprueba que no hay un kenken activo. A continuación, genera un
     * kenken, limpia los valores del tablero y inicializa la partida.</p>
     * 
     * @param size Tamaño del kenken
     * @param operations Array con las operaciones permitidas
     * @return true si todo sale correctamente
     */
    public boolean playNewGame(int size, boolean[] operations) {
        if (KenkenCntrl.kenkenActive()) return false;

        KenkenCntrl.generateNewKenken(size, operations);

        KenkenCntrl.clearKenkenBoard();

        KenkenCntrl.startPlayingKenken(Kenken.GameType.NORMAL);

        return true;
    }

    /**
     * Crea y pone en juego una partida clasificatoria.
     * 
     * <p>Comprueba no hay un kenken activo. A continuación, genera un kenken
     * clasificatorio (9x9 y todas las operaciones válidas), limpia los valores del
     * tablero e inicializa la partida.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean playRankedGame() {
        if (KenkenCntrl.kenkenActive()) return false;

        boolean[] operations = {true, true, true, true, true, true};

        KenkenCntrl.generateNewKenken(9, operations);

        KenkenCntrl.clearKenkenBoard();
        
        KenkenCntrl.startPlayingKenken(Kenken.GameType.RANKED);

        return true;
    }

    /**
     * Crea y comienza a jugar el Kenken de Día.
     * 
     * <p>Comprueba que no hay un kenken activo. A continuación, genera el kenken
     * del día, limpia los valores del tablero e inicializa la partida.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean playKenkenOfTheDay() {
        if (KenkenCntrl.kenkenActive()) return false;

        KenkenCntrl.generateDailyKenken();

        KenkenCntrl.clearKenkenBoard();

        KenkenCntrl.startPlayingKenken(Kenken.GameType.DAILY);

        return true;
    }

    /**
     * Rellena la casilla deseada con el valor proporcionado.
     * 
     * <p>Comprueba que hay un kenken activo. A continuación, rellena la casilla
     * de las coordenadas (x, y) con el valor deseado y devuelve si hay conflictos con otras
     * casillas.</p>
     * 
     * @param x Coordenada x de la casilla
     * @param y Coordenada y de la casilla
     * @param value Valor a rellenar en la casilla
     * @return false si hay conflicto con otras casillas de la fila, columna o región, true en caso contrario
     */
    public boolean fillBox(int x, int y, int value) {
        if (!KenkenCntrl.kenkenActive()) return false;

        boolean isCorrect = KenkenCntrl.fillBox(x, y, value);

        return isCorrect;
    }

    /**
     * Limpia el valor de la casilla deseada.
     * 
     * <p>Comprueba que hay un kenken activo. A continuación, vacía la casilla
     * de las coordenadas (x, y).</p>
     * 
     * @param x Coordenada x de la casilla
     * @param y Coordenada y de la casilla
     * @return false si hay conflicto con otras casillas de la fila, columna o región, true en caso contrario
     */
    public boolean clearBox(int x, int y) {
        if (!KenkenCntrl.kenkenActive()) return false;

        KenkenCntrl.clearBox(x, y);

        return true;
    }

    // SAVE KENKEN

    /**
     * Guarda el kenken en juego para continuar con la partida más adelante.
     * 
     * <p>Comprueba que hay un kenken en juego y que la partida no es
     * clasificatoria. A continuación, obtiene la información del kenken en juego y
     * la guarda en la capa de persistencia.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean saveGame() {
        if (!KenkenCntrl.kenkenInGame()) return false;
        if (KenkenCntrl.kenkenIsRanked()) return false;

        String[] kenkenData = KenkenCntrl.getKenkenString();

        String savePath = "saves/" + UserCntrl.getUserName();

        PersistenceCntrl.saveKenken(kenkenData, savePath);

        return true;
    }

    // LEAVE KENKEN

    /**
     * Abandona la partida en juego.
     * 
     * <p>Comprueba que hay un kenken en juego. A continuación, resta puntos al usuario
     * en caso de ser una partida clasificatoria y cierra el kenken.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean leaveGame() {
        if (!KenkenCntrl.kenkenInGame()) return false;

        if (KenkenCntrl.kenkenIsRanked()) UserCntrl.updatePoints(-1000);

        KenkenCntrl.clearKenken();

        return true;
    }

    // FINISH KENKEN

    /**
     * Finaliza la partida clasificatoria.
     * 
     * <p>Comprueba que hay un kenken en juego y que es clasificatorio. A continuación,
     * comprueba si la solución del kenken es correcta, suma o resta puntos en función de
     * ello y del tiempo empleado resta puntos al usuario y cierra el kenken.</p>
     * 
     * @param timeElapsed Tiempo empleado para finalizar el kenken
     * @return true si la solución es correcta, false en caso contrario
     */
    public boolean finishGame(int timeElapsed) {
        if (!KenkenCntrl.kenkenInGame()) return false;
        if (!KenkenCntrl.kenkenIsRanked()) return false;

        boolean isCorrect = KenkenCntrl.checkKenken();

        if (isCorrect) {
            int points = Math.min(3000, ((600000) / (timeElapsed)) + 1);
            UserCntrl.updatePoints(points);
        }
        else UserCntrl.updatePoints(-300);

        KenkenCntrl.clearKenken();

        return isCorrect;
    }

    // RESUME GAME

    /**
     * Retoma la partida guardada del usuario.
     * 
     * <p>Comprueba que no hay un kenken activo. A continuación, carga el kenken guardado
     * del usuario de la capa de persistencia y lo pone en juego.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean resumeGame() {
        if (KenkenCntrl.kenkenActive()) return false;

        if (PersistenceCntrl.getKenkenUser(UserCntrl.getUserName()) == null) return false;

        String[] data = PersistenceCntrl.loadKenken("saves/" + UserCntrl.getUserName());

        KenkenCntrl.stringToKenken(data);

        KenkenCntrl.startPlayingKenken(Kenken.GameType.NORMAL);

        return true;
    }

    // LOAD KENKEN

    /**
     * Carga y pone en juego un kenken guardado en el sistema.
     * 
     * <p>Este método comprueba que no hay un kenken activo. A continuación, carga el kenken de la
     * capa de persistencia y lo pone en juego.</p>
     * 
     * @param kenkenName Nombre del kenken
     * @return true si todo sale correctamente
     */
    public boolean loadKenken(String kenkenName) {
        if (KenkenCntrl.kenkenActive()) return false;

        String[] data = PersistenceCntrl.loadKenken(kenkenName);

        KenkenCntrl.stringToKenken(data);

        KenkenCntrl.startPlayingKenken(Kenken.GameType.NORMAL);

        return true;
    }

    // SOLVE KENKEN
    
    /**
     * Resuelve el kenken.
     * 
     * <p>Comprueba que hay una kenken en juego y que la partida no es el Kenken del Día.
     * A continuación, intenta resolver el kenken y devuelve si tiene solución. En caso de
     * ser una partida clasificatoria restará puntos al usuario</p>
     * 
     * @return true si el kenken tiene solución, false an caso contrario
     */
    public boolean solveKenken() {
        if (!KenkenCntrl.kenkenInGame()) return false;
        if (KenkenCntrl.kenkenIsDaily()) return false;

        if (KenkenCntrl.kenkenIsRanked()) {
            UserCntrl.updatePoints(-1000);
            KenkenCntrl.clearKenkenBoard();
        }

        ArrayList<Box> emptyBoxes = KenkenCntrl.getEmptyBoxesMRV();

        boolean hasSolution = KenkenCntrl.solveKenken(emptyBoxes, 0);

        return hasSolution;
    }

    // EXIT

    /**
     * Guarda lo necesario antes de que se cierre la aplicación.
     * 
     * <p>En caso de que haya un kenken en juego, guarda la partida. En caso de que haya
     * una sesión activa la cierra.</p>
     * 
     * @return true si todo sale correctamente
     */
    public boolean exitApp() {
        if (UserCntrl.sessionActive() && KenkenCntrl.kenkenInGame() && !KenkenCntrl.kenkenIsRanked()) saveGame();
        
        if (UserCntrl.sessionActive()) UserCntrl.logOut();

        return true;
    }

    // CONSULTORAS

    /**
     * Devuelve la información del kenken actual.
     * 
     * <p>Devuelve la información del tablero, la topología de las regiones, las operaciones de
     * cada región y su resultado.</p>
     * 
     * @return Array de objetos con la información del tablero y regiones del kenken
     */
    public Object[] getKenkenInfo() {
        if (!KenkenCntrl.kenkenActive()) return null;

        return KenkenCntrl.getKenkenData();
    }

    /**
     * Devuelve el nombre del usuario actual.
     * 
     * @return Nombre del usuario actual
     */
    public String getUserName() {
        if (!UserCntrl.sessionActive()) return null;

        return UserCntrl.getUserName();
    }

    /**
     * Devuelve los puntos del usuario actual.
     * 
     * @return Puntos del usuario actual
     */
    public int getUserPoints() {
        if (!UserCntrl.sessionActive()) return -1;

        return UserCntrl.getUserPoints();
    }

    /**
     * Devuelve el tamaño del kenken actual.
     * 
     * @return Tamaño del kenken actual
     */
    public int getKenkenSize() {
        if (!KenkenCntrl.kenkenActive()) return -1;

        return KenkenCntrl.getKenkenSize();
    }

    /**
     * Devuelve el ránking.
     * 
     * @return ArrayList de PairSI con los usuarios y puntos del ránking
     */
    public ArrayList<PairSI> getRanking() {
        return UserCntrl.getRanking();
    }

    /**
     * Devuelve la información de los kenken del sistema
     * 
     * @return Array de strings con la información de los kenken de la capa de persistencia
     */
    public String[] getKenkens() {
        return PersistenceCntrl.getKenkens();
    }
    
    /**
     * Devuelve la información de la partida a medias guardada por el usuario actual.
     * 
     * @return Información del kenken guardado por el usuario
     */
    public String getUserKenken() {
        return PersistenceCntrl.getKenkenUser(UserCntrl.getUserName());
    }
}

// Class created by Pau Zaragoza Gallardo