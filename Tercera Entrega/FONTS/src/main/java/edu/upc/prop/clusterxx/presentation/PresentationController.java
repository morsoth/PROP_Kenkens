package edu.upc.prop.clusterxx.presentation;

import java.util.Scanner;

import javax.print.attribute.DocAttribute;

import edu.upc.prop.clusterxx.domain.DomainController;
import edu.upc.prop.clusterxx.exceptions.*;
import edu.upc.prop.clusterxx.presentation.ImportKenkenViewController;
import edu.upc.prop.clusterxx.presentation.MainViewController;
import edu.upc.prop.clusterxx.presentation.KenkenSelectionViewController;
import edu.upc.prop.clusterxx.presentation.RankingViewController;
import edu.upc.prop.clusterxx.presentation.CredentialsViewController;
import edu.upc.prop.clusterxx.presentation.ParameterSelectionViewController;

/**
 * Controlador de la capa de presentación.
 * 
 * Encargado de instanciar y controlar el controlador de dominio y
 * los controladores de las vistas. Hace de intermediario entre estos.
 *
 * @author Guillem Nieto Ribo 
 */
public class PresentationController {
    /**
     * Controlador de dominio.
     */
    private DomainController DomainCntrl;

    /**
     * Controlador de la vista principal.
     */
    private MainViewController mainViewCntrl;

    /**
     * Controlador de la vista de importación de kenkens.
     */
    private ImportKenkenViewController importKenkenViewCntrl;

    /**
     * Controlador de la vista de selección de kenkens.
     */
    private KenkenSelectionViewController kenkenSelectionViewCntrl;

    /**
     * Controlador de la vista del ranking.
     */
    private RankingViewController rankingViewCntrl;

    /**
     * Controlador de la vista de registro/iniciar sesión.
     */
    private CredentialsViewController credentialsViewCntrl;

    /**
     * Controlador de la creación/generación de kenkens.
     */
    private ParameterSelectionViewController parameterSelectionViewCntrl;

    private MessageView messageView;

    /**
     * Constructora por defecto.
     * 
     * <p>Instancia el controlador de dominio y los de las vistas.</p>
     */
    public PresentationController() {
        DomainCntrl = new DomainController();
        mainViewCntrl = new MainViewController(this);
        importKenkenViewCntrl = new ImportKenkenViewController(this);
        kenkenSelectionViewCntrl = new KenkenSelectionViewController(this);
        rankingViewCntrl = new RankingViewController(this);
        credentialsViewCntrl = new CredentialsViewController(this);
        parameterSelectionViewCntrl = new ParameterSelectionViewController(this);
        messageView = new MessageView();
    }

    /**
     * Establece la visibilidad de la vista principal.
     * 
     * @param show mostrar o no.
     */
    public void showMainView(boolean show) {
        mainViewCntrl.showView(show);
    }

    /**
     * Establece la visibilidad de la vista del ranking.
     * 
     * <p>Le pasa el ranking de jugadores a la vista.</p>
     * 
     * @param show mostrar o no.
     */
    public void showRankingView(boolean show) {
        rankingViewCntrl.showView(show, DomainCntrl.getRanking());
    }

    /**
     * Establece la visibilidad de la vista de registro/inicio sesión.
     * 
     * @param show mostrar o no.
     */
    public void showRegistrationView(boolean show) {
        credentialsViewCntrl.showView(show);
    }

    /**
     * Establece la visibilidad de la vista de selección de kenkens.
     * 
     * @param show mostrar o no.
     */
    public void showKenkenSelectionView(boolean show) {
        kenkenSelectionViewCntrl.showView(show, DomainCntrl.getKenkens());
    }

    /**
     * Establece la visibilidad de la vista de selección de parámetros.
     * 
     * @param show mostrar o no.
     */
    public void showParameterSelectionView(boolean show) {
        parameterSelectionViewCntrl.showView(show);
    }

    /**
     * Establece la visibilidad de la vista de importación de kenkens.
     * 
     * @param show mostrar o no.
     */
    public void showImportKenkenView(boolean show) {
        importKenkenViewCntrl.showView(show);
    }

    /**
     * Resuelve el kenken actual.
     * 
     * @return true si se ha resuelto correctamente, false en caso contrario.
     */
    public boolean solveKenken() {
        boolean ret = DomainCntrl.solveKenken();
        this.showKenken();
        return ret;
    }

    /**
     * Inicia el proceso de creación de una cuenta.
     */
    public void startCreatingAccount() {
        credentialsViewCntrl.startRegistration();
    }

    /**
     * Inicia el proceso de inicio de sesión.
     */
    public void startLogIn() {
        credentialsViewCntrl.startLogIn();
    }

    /**
     * Crea una cuenta de usuario.
     * 
     * @param username el nombre de usuario.
     * @param password la contraseña.
     * @return true si la cuenta se ha creado correctamente, false en caso contrario.
     */
    public boolean createAccount(String username, String password) {
        boolean ret = DomainCntrl.createAccount(username,password);
        if (ret) mainViewCntrl.setLogged(true, username, "0");
        return ret;
    }

    /**
     * Inicia sesión en una cuenta de usuario.
     * 
     * @param username el nombre de usuario.
     * @param password la contraseña.
     * @return true si el inicio de sesión fue exitoso, false en caso contrario.
     */
    public boolean logIn(String username, String password) {
        boolean ret = DomainCntrl.loginUser(username,password);
        int points = DomainCntrl.getUserPoints();
        if (ret) mainViewCntrl.setLogged(true, username, String.valueOf(points));
        return ret;
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    public void logOut() {
        DomainCntrl.logoutUser();
    }

    /**
     * Carga la partida guardada por el usuario.
     */
    public void resumeGame() {
        boolean ret = DomainCntrl.resumeGame();
        if (ret) {
            int N = DomainCntrl.getKenkenSize();
            mainViewCntrl.setPlayingState(N);
            this.showKenken();
        }
        else messageView.showError("You have no saved game.");
    }

    /**
     * Carga un kenken por su ID.
     * 
     * @param id el ID del kenken a cargar.
     */
    public void loadKenken(String id) {
        try {
            DomainCntrl.loadKenken(id);
            int N = DomainCntrl.getKenkenSize();
            mainViewCntrl.setPlayingState(N);
            this.showKenken();
        }
        catch (Exception e) {
            messageView.showError("Couldn't load kenken.");
        }
    }

    /**
     * Muestra el kenken actual en la vista principal.
     */
    public void showKenken() {
        Object[] data = DomainCntrl.getKenkenInfo();
        int[][] values = (int[][])(data[0]);
        int[][] regions = (int[][])(data[1]);
        int[] ops = (int[])(data[2]);
        int[] results = (int[])(data[3]);
        mainViewCntrl.showKenken(values, regions, ops, results);
    }

    /**
     * Genera un nuevo kenken.
     * 
     * @param N el tamaño del kenken.
     * @param op un array de booleanos que indica las operaciones permitidas.
     */
    public void generateKenken(int N, boolean op[]) {
	    DomainCntrl.playNewGame(N, op);
        mainViewCntrl.setPlayingState(N);
        this.showKenken();
    }

    /**
     * Inicia una partida en modo clasificado.
     */
    public void playRanked() {
        DomainCntrl.playRankedGame();
        int N = DomainCntrl.getKenkenSize();
        mainViewCntrl.setRankedState(N);
        this.showKenken();
    }

    /**
     * Actualiza los puntos del usuario en la vista principal.
     */
    public void updatePoints() {
        String username = DomainCntrl.getUserName();
        int points = DomainCntrl.getUserPoints();
        mainViewCntrl.setLogged(true, username, String.valueOf(points));
    }

    /**
     * Finaliza la partida actual.
     * 
     * @param time el tiempo (en segundos) que se ha tardado en resolver el kenken.
     * @return true si se ha finalizado correctamente, false en caso contrario.
     */
    public boolean finishGame(int time) {
        return DomainCntrl.finishGame(time);
    }

    /**
     * Inicia la partida del kenken del día.
     */
    public void playDailyKenken() {
        DomainCntrl.playKenkenOfTheDay();
        int N = DomainCntrl.getKenkenSize();
        mainViewCntrl.setDailyState(N);
        this.showKenken();
    }

    /**
     * Inicia el proceso de creación de un kenken.
     * 
     * @param N el tamaño del kenken.
     */
    public void startKenkenCreation(int N) {
        mainViewCntrl.startKenkenCreation(N);
    }

    /**
     * Limpia el kenken actual.
     */
    public void clearKenken() {
        DomainCntrl.leaveGame();
    }

    /**
     * Guarda la partida actual.
     */
    public void saveGame() {
        DomainCntrl.saveGame();
    }

    /**
     * Cierra la aplicación.
     */
    public void exit() {
        DomainCntrl.exitApp();
    }

    /**
     * Importa un kenken desde un archivo.
     * 
     * @param path la ruta del archivo.
     */
    public void importKenken(String path) {
        String id = DomainCntrl.importKenken(path);
        this.showMessage("Kenken saved as: " + id);
    }

    /**
     * Rellena una casilla del kenken.
     * 
     * @param x la coordenada x de la casilla.
     * @param y la coordenada y de la casilla.
     * @param value el valor a introducir.
     * @return true si se ha introducido correctamente, false en caso contrario.
     */
    public boolean fillBox(int x, int y, int value) {
        return DomainCntrl.fillBox(x, y, value);
    }

    /**
     * Finaliza el proceso de creación de un kenken.
     * 
     * @param save indica si se debe guardar el kenken.
     * @param play indica si se debe jugar el kenken.
     * @param size el tamaño del kenken.
     * @param values los valores del kenken.
     * @param regionsData los datos de las regiones del kenken.
     */
    public void creationFinished(boolean save, boolean play, int size, int[][] values, String[] regionsData) {
        DomainCntrl.createKenken(size, values, regionsData);
        if (save) {
            String id = DomainCntrl.saveKenkenEdit();
            this.showMessage("Kenken saved as: " + id);
        }
        if (play) {
            DomainCntrl.playKenkenEdit();
            mainViewCntrl.setPlayingState(size);
            this.showKenken();
        }
    }

    /**
     * Inicia la aplicación.
     */
    public void startApp() {
        this.showMainView(true);
    }

    /**
     * Borra el valor de una casilla del kenken.
     * 
     * @param x la coordenada x de la casilla.
     * @param y la coordenada y de la casilla.
     */
    public void clearBox(int x, int y) {
        DomainCntrl.clearBox(x, y);
    }

    /**
     * Muestra un mensaje informativo.
     * 
     * @param msg el mensaje a mostrar.
     */
    public void showMessage(String msg) {
        messageView.showInfo(msg);
    }

    /**
     * Muestra un mensaje de advertencia.
     * 
     * @param msg el mensaje a mostrar.
     */
    public void showWarning(String msg) {
        messageView.showWarning(msg);
    }

    /**
     * Muestra un mensaje de error.
     * 
     * @param msg el mensaje a mostrar.
     */
    public void showError(String msg) {
        messageView.showError(msg);
    }
}

/* Class created by Guillem Nieto */