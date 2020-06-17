package it.uniba.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.IOException;


public class ClientGUI {
    private JPanel mainPanel;
    private JButton startClientButton;
    private JButton endSessionButton;
    private JScrollPane scrollBar;
    private JTextPane formattedOutputClient;
    private JButton buttonUp;
    private JButton buttonDown;
    private JButton buttonLeft;
    private JButton buttonRight;
    private JTextArea textUserInputArea;
    private JButton inviaComandoButton;

    private ClientGUIVersion client; //classe operazioni Client

    private HTMLDocument document; //documento componente JtextPane

    private static final String TEXT_SUBMIT = "text-submit";
    private static final String INSERT_BREAK = "insert-break";

    //colore textAreaInput
    private final int r = 219;
    private final int g = 241;
    private final int b = 255;

    //thread sleep
    private final int thredSleep = 100;

    public ClientGUI() {
        this.startGUI();
    }

    /**
     * metodo che inizializza i componenti e implementa gli eventi
     */
    public void startGUI() {

        JFrame frame = new JFrame("Client");
        frame.setContentPane(this.mainPanel);

        //setta JtextPane per formato html
        formattedOutputClient.setContentType("text/html");

        //setta colore text input area
        textUserInputArea.setBackground(new Color(r, g, b));
        textUserInputArea.requestFocusInWindow();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);

        //posiziona frame al centro dello schermo
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

        //inizializza documento rendering di stampa
        document = (HTMLDocument) formattedOutputClient.getStyledDocument();
        try {
            //inizializza tags html
            document.insertAfterEnd(document.getCharacterElement(document.getLength()), "<html><body>");
            appendText("");
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creazione listner (events)
        startClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                client = new ClientGUIVersion();
                startClientButton.setEnabled(false); //disabilita tasto start
                try {
                    client.runThreadClient();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        });

        /**
         * focus sulla text area all'apertura della finestra
         */
        frame.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(final WindowEvent e) {
                textUserInputArea.requestFocusInWindow();
            }
        });

        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                quitSession();
            }
        });
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("nord");
                    textUserInputArea.requestFocusInWindow(); //focus su textArea
                } else {
                    appendCommandNotValid();
                }
            }
        });
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("sud");
                    textUserInputArea.requestFocusInWindow(); //focus su textArea
                } else {
                    appendCommandNotValid();
                }
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("ovest");
                    textUserInputArea.requestFocusInWindow(); //focus su textArea
                } else {
                    appendCommandNotValid();
                }
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("est");
                    textUserInputArea.requestFocusInWindow(); //focus su textArea
                } else {
                    appendCommandNotValid();
                }
            }
        });
        inviaComandoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer(textUserInputArea.getText());
                } else {
                    appendCommandNotValid();
                }
                textUserInputArea.setText("");
                textUserInputArea.requestFocusInWindow(); //focus su textArea
            }
        });

        /**
         * Listener per riconoscere pressione del tasto invio all'interno della jtextPane
         *
         * l'evento del tasto invio in questo modo non avvierà l'operazione di new line "on enter"
         */
        InputMap input = textUserInputArea.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        input.put(shiftEnter, INSERT_BREAK);  // input.get(enter)) = "insert-break"
        input.put(enter, TEXT_SUBMIT);

        ActionMap actions = textUserInputArea.getActionMap();
        actions.put(TEXT_SUBMIT, new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer(textUserInputArea.getText());
                    textUserInputArea.setText(""); //pulisci testo input
                } else {
                    appendCommandNotValid();
                }

            }
        });
    }


    /**
     * aggiungi testo al documento da visualizzare nel Text component
     *
     * @param textToAppend parametro stringa "to append"
     * @throws InterruptedException
     */
    public void appendText(final String textToAppend) {

        try {
            document.insertAfterEnd(
                    document.getCharacterElement(document.getLength()),
                    textToAppend.replaceAll("-", "")
            );
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scrollToEnd(); //scrolla alla fine
    }

    /**
     * ripulisci text component
     */
    public void clearOutputText() {
        formattedOutputClient.setText("");
    }

    private void appendCommandNotValid() {
        appendText("<br><font color='orange' face=\"Impact\"><b>"
                + "Console:"
                + "</b></font> "
                + "comando non disponbile, avvia una partita");
    }

    /**
     * Scrolla fino alla fine del componente text
     *
     * @throws InterruptedException
     */
    public void scrollToEnd() {
        try {
            Thread.sleep(thredSleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //scrolla alla fine della JTextArea
        this.scrollBar.getVerticalScrollBar().setValue(this.scrollBar.getVerticalScrollBar().getMaximum());

        //fa un redraw del component, risolve artefatti del render dopo aver scrollato automaticamente
        formattedOutputClient.repaint();
    }

    /**
     * metodo per aggiornare l'interfaccia, uscendo dalla sessione
     */
    public void quitSession() {
        if (client != null) {
            client.closeServerComunications();
            client.sendRequestToServer("quit");
        }

        //update della GUI
        endSessionButton.setEnabled(false);
        startClientButton.setEnabled(true);


        client = null;
    }

    /**
     * metodo per aggiornare l'interfaccia, avviando la sessione
     */
    public void startSession() {
        //update della GUI
        endSessionButton.setEnabled(true);

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(9, 4, new Insets(11, 11, 11, 11), -1, -1));
        mainPanel.setAutoscrolls(true);
        mainPanel.setMinimumSize(new Dimension(800, 500));
        startClientButton = new JButton();
        startClientButton.setText("Avvia partita");
        startClientButton.setToolTipText("Avvia comunicazione con il server");
        mainPanel.add(startClientButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Premi avvia partita per iniziare a giocare");
        mainPanel.add(label1, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endSessionButton = new JButton();
        endSessionButton.setEnabled(false);
        endSessionButton.setText("Termina la sessione");
        mainPanel.add(endSessionButton, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 320), null, null, 0, false));
        scrollBar = new JScrollPane();
        mainPanel.add(scrollBar, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        formattedOutputClient = new JTextPane();
        formattedOutputClient.setAutoscrolls(true);
        formattedOutputClient.setEditable(false);
        scrollBar.setViewportView(formattedOutputClient);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 4, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonUp = new JButton();
        Font buttonUpFont = this.$$$getFont$$$(null, -1, 25, buttonUp.getFont());
        if (buttonUpFont != null) buttonUp.setFont(buttonUpFont);
        buttonUp.setText("↑");
        panel1.add(buttonUp, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonDown = new JButton();
        Font buttonDownFont = this.$$$getFont$$$(null, -1, 25, buttonDown.getFont());
        if (buttonDownFont != null) buttonDown.setFont(buttonDownFont);
        buttonDown.setText("↓");
        panel1.add(buttonDown, new GridConstraints(2, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonLeft = new JButton();
        Font buttonLeftFont = this.$$$getFont$$$(null, -1, 25, buttonLeft.getFont());
        if (buttonLeftFont != null) buttonLeft.setFont(buttonLeftFont);
        buttonLeft.setText("←");
        panel1.add(buttonLeft, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRight = new JButton();
        buttonRight.setEnabled(true);
        Font buttonRightFont = this.$$$getFont$$$(null, -1, 25, buttonRight.getFont());
        if (buttonRightFont != null) buttonRight.setFont(buttonRightFont);
        buttonRight.setHorizontalAlignment(0);
        buttonRight.setText("→");
        panel1.add(buttonRight, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setHorizontalAlignment(0);
        label2.setHorizontalTextPosition(0);
        label2.setText(" Direzioni ");
        panel1.add(label2, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(8, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(565, -1), null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel2, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textUserInputArea = new JTextArea();
        textUserInputArea.setLineWrap(true);
        mainPanel.add(textUserInputArea, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(150, -1), new Dimension(150, 50), null, 0, false));
        inviaComandoButton = new JButton();
        inviaComandoButton.setText("Invia comando");
        mainPanel.add(inviaComandoButton, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 20), null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Inserisci un comando da qui");
        mainPanel.add(label3, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
