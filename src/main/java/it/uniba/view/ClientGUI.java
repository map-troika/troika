package it.uniba.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    HTMLDocument document; //documento componente text

    public ClientGUI() {
        this.startGUI();

        //inizializza documento rendering di stampa
        document = (HTMLDocument) formattedOutputClient.getStyledDocument();
        try {
            //inizializza tags html
            document.insertAfterEnd(document.getCharacterElement(document.getLength()), "<html><body>");
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creazione listner (events)
        startClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client = new ClientGUIVersion();
                try {
                    client.runThreadClient();

                    //update della GUI
                    endSessionButton.setEnabled(true);
                    startClientButton.setEnabled(false);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }


            }
        });
        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitSession();
            }
        });
        buttonUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("nord");
                } else {
                    appendText("<br><font color='orange' face=\"Agency FB\"><b>" +
                            "Attenzione:" +
                            "</b></font> " +
                            "comando non disponbile, avvia una partita");
                }
            }
        });
        buttonDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("sud");
                } else {
                    appendText("<br><font color='orange' face=\"Agency FB\"><b>" +
                            "Attenzione:" +
                            "</b></font> " +
                            "comando non disponbile, avvia una partita");
                }
            }
        });
        buttonLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("ovest");
                } else {
                    appendText("<br><font color='orange' face=\"Agency FB\"><b>" +
                            "Attenzione:" +
                            "</b></font> " +
                            "comando non disponbile, avvia una partita");
                }
            }
        });
        buttonRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer("est");
                } else {
                    appendText("<br><font color='orange' face=\"Agency FB\"><b>" +
                            "Attenzione:" +
                            "</b></font> " +
                            "comando non disponbile, avvia una partita");
                }
            }
        });
        inviaComandoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client != null) {
                    client.sendRequestToServer(textUserInputArea.getText());
                    textUserInputArea.setText("");
                } else {
                    appendText("<br><font color='orange' face=\"Agency FB\"><b>" +
                            "Attenzione:" +
                            "</b></font> " +
                            "comando non disponbile, avvia una partita");
                }
            }
        });
    }

    public void startGUI() {

        JFrame frame = new JFrame("Client");
        frame.setContentPane(this.mainPanel);

        //setta JtextPane per formato html
        formattedOutputClient.setContentType("text/html");

        //setta colore text input area
        textUserInputArea.setBackground(new Color(219, 241, 255));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(true);

        //posiziona frame al centro dello schermo
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }


    /**
     * aggiungi testo al documento da visualizzare nel Text component
     *
     * @param textToAppend parametro stringa "to append"
     * @throws InterruptedException
     */
    public void appendText(String textToAppend) {

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

    /**
     * Scrolla fino alla fine del componente text
     *
     * @throws InterruptedException
     */
    public void scrollToEnd() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //scrolla alla fine della JTextArea
        this.scrollBar.getVerticalScrollBar().setValue(this.scrollBar.getVerticalScrollBar().getMaximum());

        //fa un redraw del component, risolve artefatti del render dopo aver scrollato automaticamente
        formattedOutputClient.repaint();
    }

    public void quitSession () {
        client.closeServerComunications();
        client.sendRequestToServer("quit");

        //update della GUI
        endSessionButton.setEnabled(false);
        startClientButton.setEnabled(true);
    }
}
