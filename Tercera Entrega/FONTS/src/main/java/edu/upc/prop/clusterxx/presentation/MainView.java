package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.MainViewController;
import edu.upc.prop.clusterxx.presentation.KButton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista principal del programa
 *
 * <p>Implementa la interfaz gráfica con la que interactúa el usuario cuando se
 * inicia el programa, y donde se crean y juegan los Kenkens. Tiene un menú
 * donde se pueden seleccionar diferentes casos de uso de la aplicación.</p>
 *
 * @see edu.upc.prop.clusterxx.presentation.MainViewController
 * @see edu.upc.prop.clusterxx.presentation.KButton
 *
 * @author David Cañadas López
 */
public class MainView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private MainViewController control;

    /**
     * Barra de menús de la vista.
     */
    private static JMenuBar menuBar = new JMenuBar();

    /**
     * Matriz de botones que representan las casillas del tablero.
     */
    private KButton buttons[][];

    /**
     * Texto de región de cada botón.
     */
    private String regionText[][];

    /**
     * Paneles principales de la vista.
     */
    private JPanel mainPanel, matrixPanel = null;

    /**
     * Tamaño de las filas y columnas de la matriz de botones.
     */
    private int gridSize;

    /**
     * Matriz con el número de región para cada casilla del tablero.
     */
    private int regions[][];

    /**
     * Dimensiones máximas de cada botón del tablero.
     */
    private static Dimension buttonSize = new Dimension(60, 60);

    /**
     * Caja que contiene la información del usuario en la barra de menú.
     */
    private static Box nameBox  = Box.createHorizontalBox();

    /**
     * Separación entre los menús de la barra de menú y los datos del usuario.
     */
    private static Component menuGlue = Box.createHorizontalGlue();

    /**
     * Label que contiene el nombre de usuario.
     */
    private static JLabel usernameLabel = new JLabel("");

    /**
     * Menús que son dinámicos de la barra de menú.
     */
    private JMenu menuAction, menuAccount, menuEdit, menuGame;

    /**
     * Botones que son dinámicos de los distintos menús.
     */
    private JMenuItem itemSave, itemFinish, itemLogout, itemLogin, itemRegister;

    /**
     * Caja que contiene la información del temporizador en la barra de menú.
     */
    private static Box timerBox = Box.createHorizontalBox();

    /**
     * Label que contiene el tiempo transcurrido en el temporizador.
     */
    private static JLabel timerLabel = new JLabel("00:00:00");

    /**
     * Cantidad de segundos transcurridos en el temporizador.
     */
    private int seconds = 0;

    /**
     * Temporizador para las partidas clasificatorias.
     */
    private Timer timer;
    
    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public MainView (MainViewController control) {
        super("KENKEN GAME");
        this.control = control;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
        setFocusable(true);
        setResizable(true);
        setSize(300,320);

        // inicializar
        JMenuItem item;
        menuGame = new JMenu("Game");
        menuGame.setMnemonic(KeyEvent.VK_G);

        itemFinish = new JMenuItem("Finish game", KeyEvent.VK_F);
        itemFinish.addActionListener(e -> control.finishGame());
        menuGame.add(itemFinish);

        itemSave = new JMenuItem("Save current game", KeyEvent.VK_S);
        itemSave.addActionListener(e -> control.saveGame());
        menuGame.add(itemSave);

        item = new JMenuItem("Solve Kenken", KeyEvent.VK_V);
        item.addActionListener(e -> control.solveKenken());
        menuGame.add(item);
	
        item = new JMenuItem("Leave current game", KeyEvent.VK_L);
        item.addActionListener(e -> control.leaveKenken());
        menuGame.add(item);
        
        menuAction = new JMenu("Action");
        menuAction.setMnemonic(KeyEvent.VK_A);

        item = new JMenuItem("Start new game", KeyEvent.VK_N);
        item.addActionListener(e -> control.createKenken());
        menuAction.add(item);

        item = new JMenuItem("Start ranked game", KeyEvent.VK_R);
        item.addActionListener(e -> control.playRanked());
        menuAction.add(item);

        item = new JMenuItem("Start daily Kenken", KeyEvent.VK_D);
        item.addActionListener(e -> control.playDailyKenken());
        menuAction.add(item);
	
        item = new JMenuItem("Import from file", KeyEvent.VK_I);
        item.addActionListener(e -> control.importKenken());
        menuAction.add(item);
	
        item = new JMenuItem("Load from system", KeyEvent.VK_L);
        item.addActionListener(e -> control.startKenkenSelection());
        menuAction.add(item);
	
        item = new JMenuItem("Resume last game", KeyEvent.VK_S);
        item.addActionListener(e -> control.resumeGame());
        menuAction.add(item);
	
        item = new JMenuItem("View ranking", KeyEvent.VK_V);
        item.addActionListener(e -> control.showRanking());
        menuAction.add(item);

        menuAccount = new JMenu("Account");
        menuAccount.setMnemonic(KeyEvent.VK_C);
	
        itemRegister = new JMenuItem("Register", KeyEvent.VK_R);
        itemRegister.addActionListener(e -> control.startCreatingAccount());
        menuAccount.add(itemRegister);
	
        itemLogin = new JMenuItem("Login", KeyEvent.VK_L);
        itemLogin.addActionListener(e -> control.startLogIn());
        menuAccount.add(itemLogin);
	
        itemLogout = new JMenuItem("Logout", KeyEvent.VK_O);
        itemLogout.addActionListener(e -> control.logOut());
        menuAccount.add(itemLogout);

        menuEdit = new JMenu("Edit");
        menuEdit.setMnemonic(KeyEvent.VK_E);

        item = new JMenuItem("Start creating regions", KeyEvent.VK_S);
        item.addActionListener(e -> control.startCreatingRegions());
        menuEdit.add(item);
	
        item = new JMenuItem("Leave edition", KeyEvent.VK_L);
        item.addActionListener(e -> control.leaveKenken());
        menuEdit.add(item);

        JMenu regionSubmenu = new JMenu("Create region");
        regionSubmenu.setMnemonic(KeyEvent.VK_C);
        menuEdit.add(regionSubmenu);

        item = new JMenuItem("Additon", KeyEvent.VK_A);
        item.addActionListener(e -> control.addRegion('+'));
        regionSubmenu.add(item);

        item = new JMenuItem("Subtraction", KeyEvent.VK_S);
        item.addActionListener(e -> control.addRegion('-'));
        regionSubmenu.add(item);

        item = new JMenuItem("Multiplication", KeyEvent.VK_M);
        item.addActionListener(e -> control.addRegion('x'));
        regionSubmenu.add(item);

        item = new JMenuItem("Division", KeyEvent.VK_D);
        item.addActionListener(e -> control.addRegion('/'));
        regionSubmenu.add(item);

        item = new JMenuItem("Potentiation", KeyEvent.VK_P);
        item.addActionListener(e -> control.addRegion('^'));
        regionSubmenu.add(item);

        item = new JMenuItem("Modulo", KeyEvent.VK_L);
        item.addActionListener(e -> control.addRegion('%'));
        regionSubmenu.add(item);

        item = new JMenuItem("Empty", KeyEvent.VK_E);
        item.addActionListener(e -> control.addRegion(' '));
        regionSubmenu.add(item);

        JMenu finishSubmenu = new JMenu("Finish");
        regionSubmenu.setMnemonic(KeyEvent.VK_F);
        menuEdit.add(finishSubmenu);

        item = new JMenuItem("Save", KeyEvent.VK_S);
        item.addActionListener(e -> control.finishEdit(true, false));
        finishSubmenu.add(item);

        item = new JMenuItem("Play", KeyEvent.VK_P);
        item.addActionListener(e -> control.finishEdit(false, true));
        finishSubmenu.add(item);

        item = new JMenuItem("Save and Play", KeyEvent.VK_A);
        item.addActionListener(e -> control.finishEdit(true, true));
        finishSubmenu.add(item);

        // Configurar las barras de menú
        menuBar.add(menuAction);
        menuBar.add(menuEdit);
        menuBar.add(menuGame);
        menuBar.add(menuAccount);
        menuBar.add(timerBox);
        menuBar.add(menuGlue);
        menuBar.add(nameBox);

        menuGame.setVisible(false);
        menuEdit.setVisible(false);
        timerBox.setVisible(false);

        setJMenuBar(menuBar);

        nameBox.add(usernameLabel);

        // Configurar opciones de juego
        itemFinish.setVisible(false);
        itemSave.setVisible(false);

        // Configurar opciones de la cuenta
        itemLogout.setVisible(false);

        // Configurar el timer
        timerBox.add(timerLabel);
        
        timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ++seconds;
                    int hours = seconds / 3600;
                    int minutes = (seconds % 3600) / 60;
                    int secs = seconds % 60;
                    timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, secs));
                }
            });

        // Teclado
        addKeyListener(new KeyAdapter() {
                public void keyTyped (KeyEvent e) {
                    control.pressedKey(e.getKeyChar());
                }
            });
    }

    /**
     * Obtiene la posición en la matriz de un botón.
     *
     * @param button El botón de la matriz a comprobar
     * @return las coordenadas del botón en la matriz
     */
    private Dimension getButtonPosition (KButton button) {
        Dimension pos = new Dimension(0, 0);
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                if (buttons[i][j].equals(button)) {
                    pos.setSize(j, i);
                    break;
                }
            }
        }
        return pos;
    }

    /**
     * Genera una matriz gráfica de botones a partir de un tamaño y la pinta.
     *
     * @param kenkenSize la cantidad de filas y columnas de la matriz
     */
    public void createKenkenWithSize (int kenkenSize) {
        setLayout(new BorderLayout());
	
        mainPanel = new JPanel(new GridBagLayout());
        matrixPanel = new JPanel(new GridLayout(kenkenSize, kenkenSize));

        gridSize = kenkenSize;
        buttons = new KButton[kenkenSize][kenkenSize];

        for (int i = 0; i < kenkenSize; ++i) {
            for (int j = 0; j < kenkenSize; ++j) {
                KButton button = new KButton("", buttonSize);
                button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed (ActionEvent event) {
                            KButton pressedButton = (KButton) event.getSource();
                            Dimension position = getButtonPosition(pressedButton);
                            control.pressedButton((int)position.getHeight(),
                                                  (int)position.getWidth());
                            requestFocusInWindow();
                        }
                    });
                buttons[i][j] = button;
                matrixPanel.add(button);
            }
        }

        mainPanel.add(matrixPanel);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        int minSize = gridSize * buttonSize.height;
        setMinimumSize(new Dimension(minSize+20, minSize+60));
        matrixPanel.setPreferredSize(new Dimension(minSize, minSize));
        mainPanel.revalidate();
	
        setLocationRelativeTo(null);
        repaint();
    }

    /**
     * Borra la matriz gráfica de botones de la interfaz.
     */
    public void clearKenken () {
        if (buttons == null) return;
        getContentPane().removeAll();
        buttons = null;
        mainPanel = null;
        matrixPanel = null;
        repaint();
    }

    /**
     * Marca un botón de la matriz como seleccionado
     *
     * @param x Coordenada x del botón
     * @param y Coordenada y del botón
     */
    public void setSelected (int x, int y) {
        KButton button = buttons[x][y];
        button.setBorder(BorderFactory.createLineBorder(Color.BLUE, 5));
    }

    /**
     * Marca un botón de la matriz como no seleccionado
     *
     * @param x Coordenada x del botón
     * @param y Coordenada y del botón
     */
    public void setUnselected (int x, int y) {
        KButton button = buttons[x][y];
        button.resetBorders();
    }

    /**
     * Cambia el texto de un botón de la matriz.
     *
     * @param x Coordenada x del botón
     * @param y Coordenada y del botón
     * @param text El texto que poner en el botón
     */
    public void setBoxText (int x, int y, String text) {
        KButton button = buttons[x][y];
        button.setText(text);
        repaint();
    }

    /**
     * Cambia el texto de región de un botón de la matriz.
     *
     * @param x Coordenada x del botón
     * @param y Coordenada y del botón
     * @param text El texto de región que poner en el botón
     */
    public void setRegionText (int x, int y, String text) {
        KButton button = buttons[x][y];
        button.setRegionText(text);
        repaint();
    }

    /**
     * Cambia el texto de región de un botón de la matriz.
     *
     * @param x Coordenada x del botón
     * @param y Coordenada y del botón
     * @param text El texto de región que poner en el botón
     */
    public void setEdit (boolean selected) {
        menuAction.setVisible(!selected);
        menuAccount.setVisible(!selected);
        menuEdit.setVisible(selected);
    }

    /**
     * Cambia los menús de la barra de menú para cambiar los correspondientes a la partida en juego.
     *
     * @param selected Activar los menús o no
     */
    public void setIngame (boolean selected) {
        menuAction.setVisible(!selected);
        menuAccount.setVisible(!selected);
        menuGame.setVisible(selected);
    }

    /**
     * Cambia los menús de la barra de menú para cambiar los correspondientes a la partida clasificatoria.
     *
     * @param selected Activar los menús o no
     */
    public void setRanked (boolean selected) {
        itemSave.setVisible(!selected);
        itemFinish.setVisible(selected);
    }

    /**
     * Cambia los menús de la barra de menú para cambiar los correspondientes a la cuenta del usuario.
     *
     * @param selected Activar los menús o no
     * @param username Nombre de usuario de la cuenta
     * @param points Puntos de la cuenta
     */
    public void setLoggedIn (boolean selected, String username, String points) {
        itemRegister.setVisible(!selected);
        itemLogin.setVisible(!selected);
        itemLogout.setVisible(selected);
        if (selected) usernameLabel.setText(username + " [" + points + "]");
        else usernameLabel.setText("");
    }

    /**
     * Cambia la región a la que perteneze cada botón de la matriz.
     *
     * @param regions Matriz de números de región para cada casilla
     */
    public void setRegions (int regions[][]) {
        this.regions = regions;

        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize; ++j) {
                int top = 1, left = 1, bottom = 1, right = 1;

                if (i == 0 || regions[i][j] != regions[i-1][j]) top = 3;
                if (j == 0 || regions[i][j] != regions[i][j-1]) left = 3;
                if (i == gridSize-1 || regions[i][j] != regions[i+1][j]) bottom = 3;
                if (j == gridSize-1 || regions[i][j] != regions[i][j+1]) right = 3;
		
                buttons[i][j].updateBorders(top, left, bottom, right);
            }
        }
    }

    /**
     * Inicia el temporizador.
     */
    public void startTimer () {
        timer.start();
    }

    /**
     * Para el temporizador.
     */
    public void stopTimer () {
        timer.stop();
    }

    /**
     * Obtiene la cantidad de segundos transcurridos.
     *
     * @return la cantidad de segundos
     */
    public int getTime () {
        return seconds;
    }

    /**
     * Inicia el temporizador.
     */
    public void showTimer (boolean show) {
        if (show) {
            timerBox.setVisible(true);
        } else {
            timerBox.setVisible(false);
            seconds = 0;
            timerLabel.setText("00:00:00");
        }
    }

    /**
     * Marca el tablero para indicar el fin de la partida.
     *
     * @param win Si se ha ganado o perdido la partida
     */
    public void winGame (boolean win) {
        Color buttonColor;
        if (win) buttonColor = Color.GREEN;
        else buttonColor = Color.RED;

        for (KButton[] row : buttons) {
            for (KButton button : row) {
                button.setBackground(buttonColor);
            }
        }
    }
}
