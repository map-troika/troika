package it.uniba.view;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.ActionMap;
import javax.swing.AbstractAction;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

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
}
