package it.uniba.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
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

    HTMLDocument document; //documento componente text

    public ClientGUI() {
        this.startGUI();
        startClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientGUIVersion client = new ClientGUIVersion();
                try {
                    client.runThreadClient();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                startClientButton.setEnabled(false);
            }
        });
        endSessionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

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
    }

    public void startGUI() {

        JFrame frame = new JFrame("Client");
        frame.setContentPane(this.mainPanel);

        //setta JtextPane per  html
        formattedOutputClient.setContentType("text/html");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);

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
    public void appendText(String textToAppend) throws InterruptedException {

        try {
            document.insertAfterEnd(document.getCharacterElement(document.getLength()), textToAppend);
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
    public void scrollToEnd() throws InterruptedException {
        Thread.sleep(100);

        //scrolla alla fine della JTextArea
        this.scrollBar.getVerticalScrollBar().setValue(this.scrollBar.getVerticalScrollBar().getMaximum());

        //fa un redraw del component, risolve artefatti del render dopo aver scrollato automaticamente
        formattedOutputClient.repaint();
    }
}
