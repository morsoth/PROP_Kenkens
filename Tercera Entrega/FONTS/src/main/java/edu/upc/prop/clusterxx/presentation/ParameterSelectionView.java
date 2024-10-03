package edu.upc.prop.clusterxx.presentation;

import edu.upc.prop.clusterxx.presentation.ParameterSelectionViewController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de selección de parámetros
 * 
 * <p>Implementa la interfaz gráfica donde se pueden seleccionar distintos
 * parámetros para la creación de un Kenken. También ofrece al usuario la
 * elección de crear un Kenken manualmente o bien que lo genere el sistema.</p>
 *
 * @see edu.upc.prop.clusterxx.presentation.ParameterSelectionViewController
 *
 * @author David Cañadas López
 */
public class ParameterSelectionView extends JFrame {
    /**
     * Controlador de la vista.
     */
    private ParameterSelectionViewController control;
    
    /**
     * Botones para indicar si se quiere generar manualmente o automáticamente.
     */
    private static JButton gameButtons[] = new JButton[2];
    
    /**
     * Botones para indicar el tamaño del Kenken.
     */
    private static JButton sizeButtons[] = new JButton[7];
    
    /**
     * Botones para indicar las operaciones que utilizar.
     */
    private static JButton operationButtons[] = new JButton[6];
    
    /**
     * Constructora por defecto.
     *
     * @param control Controlador de la vista
     */
    public ParameterSelectionView (ParameterSelectionViewController control) {
        super();
        this.control = control;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(false);
        setFocusable(true);
        setResizable(false);
        setSize(500,400);

        // inicializar

        JLabel sizeTitle = new JLabel("Select size:");
        sizeTitle.setFont(new Font("Serif", Font.PLAIN, 14));
        sizeTitle.setHorizontalAlignment(JButton.CENTER);
        sizeTitle.setVerticalAlignment(JButton.CENTER);

        JLabel operationsTitle = new JLabel("Select operations:");
        operationsTitle.setFont(new Font("Serif", Font.PLAIN, 14));
        operationsTitle.setHorizontalAlignment(JButton.CENTER);
        operationsTitle.setVerticalAlignment(JButton.CENTER);

        JPanel gamePanel = makeGamePanel();
        JPanel sizesPanel = makeSizesPanel();
        JPanel operationsPanel = makeOperationsPanel();
        JPanel confirmPanel = makeConfirmPanel();

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.add(gamePanel);
        selectionPanel.add(sizeTitle);
        selectionPanel.add(sizesPanel);
        selectionPanel.add(operationsTitle);
        selectionPanel.add(operationsPanel);
        selectionPanel.add(confirmPanel);
        add(selectionPanel);

        control.selectGenerate(true);
        control.selectKenkenSize(3);
    }

    
    /**
     * Construir el panel de tipo de juego.
     *
     * @return el panel de la vista, configurado
     */
    private JPanel makeGamePanel () {
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton manualButton = new JButton("Manual");
        manualButton.setFont(new Font("Serif", Font.BOLD, 14));
        manualButton.setHorizontalAlignment(JButton.CENTER);
        manualButton.setVerticalAlignment(JButton.CENTER);
        manualButton.setBackground(Color.WHITE);
        manualButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    JButton pressedButton = (JButton) event.getSource();
                    if (pressedButton.getBackground() == Color.WHITE) {
                        pressedButton.setBackground(Color.YELLOW);
                        String buttonText = pressedButton.getText();
                        for (JButton other : gameButtons) {
                            if (!buttonText.equals(other.getText())) {
                                other.setBackground(Color.WHITE);
                            }
                        }
                        for (JButton opButton : operationButtons) {
                            opButton.setEnabled(false);
                        }
                        control.selectGenerate(false);
                    }
                }
            });
        gameButtons[0] = manualButton;
        gamePanel.add(manualButton);

        JButton automaticButton = new JButton("Automatic");
        automaticButton.setFont(new Font("Serif", Font.BOLD, 14));
        automaticButton.setHorizontalAlignment(JButton.CENTER);
        automaticButton.setVerticalAlignment(JButton.CENTER);
        automaticButton.setBackground(Color.YELLOW);
        automaticButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    JButton pressedButton = (JButton) event.getSource();
                    if (pressedButton.getBackground() == Color.WHITE) {
                        pressedButton.setBackground(Color.YELLOW);
                        String buttonText = pressedButton.getText();
                        for (JButton other : gameButtons) {
                            if (!buttonText.equals(other.getText())) {
                                other.setBackground(Color.WHITE);
                            }
                        }
                        for (JButton opButton : operationButtons) {
                            opButton.setEnabled(true);
                        }
                        control.selectGenerate(true);
                    }
                }
            });
        gameButtons[1] = automaticButton;
        gamePanel.add(automaticButton);

        return gamePanel;
    }

    /**
     * Construir el panel de tamaños.
     *
     * @return el panel de la vista, configurado
     */
    private JPanel makeSizesPanel () {
        JPanel sizesPanel = new JPanel();
        sizesPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        Dimension buttonSize = new Dimension(60, 60);
        for (int i = 0; i < 7; ++i) {
            JButton button = new JButton(String.valueOf(i+3));
            button.setFont(new Font("Serif", Font.BOLD, 20));
            button.setHorizontalAlignment(JButton.CENTER);
            button.setVerticalAlignment(JButton.CENTER);

            if (i == 0) {
                button.setBackground(Color.BLUE);
                button.setForeground(Color.WHITE);
            }
            else button.setBackground(Color.WHITE);

            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent event) {
                        JButton pressedButton = (JButton) event.getSource();
                        if (pressedButton.getBackground() == Color.WHITE) {
                            pressedButton.setBackground(Color.BLUE);
                            pressedButton.setForeground(Color.WHITE);
                            String buttonText = pressedButton.getText();
                            for (JButton other : sizeButtons) {
                                if (!buttonText.equals(other.getText())) {
                                    other.setBackground(Color.WHITE);
                                    other.setForeground(Color.BLACK);
                                }
                            }
                            control.selectKenkenSize(Integer.parseInt(buttonText));
                        }
                    }
                });
            sizeButtons[i] = button;
            sizesPanel.add(button);
        }

        return sizesPanel;
    }

    /**
     * Construir el panel de operaciones.
     *
     * @return el panel de la vista, configurado
     */
    private JPanel makeOperationsPanel () {
        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        Dimension buttonSize = new Dimension(60, 60);
        for (int i = 0; i < 6; ++i) {
            JButton button = new JButton("" + "+-x/%^".charAt(i));
            button.setFont(new Font("Serif", Font.BOLD, 20));
            button.setHorizontalAlignment(JButton.CENTER);
            button.setVerticalAlignment(JButton.CENTER);
            button.setBackground(Color.BLUE);
            button.setForeground(Color.WHITE);
            button.setMinimumSize(buttonSize);
            button.setMaximumSize(buttonSize);
            button.setPreferredSize(buttonSize);
            button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed (ActionEvent event) {
                        JButton pressedButton = (JButton) event.getSource();
                        control.toggleOperation(pressedButton.getText().charAt(0));
                    }
                });
            operationButtons[i] = button;
            operationsPanel.add(button);
        }

        return operationsPanel;
    }

    /**
     * Construir el panel de confirmación.
     *
     * @return el panel de la vista, configurado
     */
    private JPanel makeConfirmPanel () {
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton acceptButton = new JButton("Accept");
        acceptButton.setFont(new Font("Serif", Font.BOLD, 14));
        acceptButton.setHorizontalAlignment(JButton.CENTER);
        acceptButton.setVerticalAlignment(JButton.CENTER);
        acceptButton.setBackground(Color.GREEN);
        acceptButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    control.confirmSelectionParameters(true);
                }
            });
        confirmPanel.add(acceptButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.BOLD, 14));
        cancelButton.setHorizontalAlignment(JButton.CENTER);
        cancelButton.setVerticalAlignment(JButton.CENTER);
        cancelButton.setBackground(Color.RED);
        cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed (ActionEvent event) {
                    control.confirmSelectionParameters(false);
                }
            });
        confirmPanel.add(cancelButton);

        return confirmPanel;
    }

    public void setSelected (char operationChar, boolean selected) {
        Color fg = Color.WHITE, bg = Color.BLUE;
        if (!selected) {
            fg = Color.BLACK;
            bg = Color.WHITE;
        }

        JButton button = operationButtons[0];
        switch (operationChar) {
        case '+': button = operationButtons[0]; break;
        case '-': button = operationButtons[1]; break;
        case 'x': button = operationButtons[2]; break;
        case '/': button = operationButtons[3]; break;
        case '%': button = operationButtons[4]; break;
        case '^': button = operationButtons[5]; break;
        default: break;
        }

        button.setForeground(fg);
        button.setBackground(bg);
    }
}
