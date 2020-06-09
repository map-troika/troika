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

    HTMLDocument doc;

    public ClientGUI () {
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
        doc = (HTMLDocument) formattedOutputClient.getStyledDocument();
        try {
            //inizializza tags html
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), "<html><body>");
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGUI () {

        JFrame frame = new JFrame("Client");
        frame.setContentPane(this.mainPanel);

        //setta JtextPane per renderizzare html
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

    public void appendOutputClientText (String textToAppend) throws InterruptedException {

        try {
            doc.insertAfterEnd(doc.getCharacterElement(doc.getLength()), textToAppend);
        } catch (BadLocationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scroll(); //scrolla alla fine della scroll bar
    }

    public void clearOutputText () {
        formattedOutputClient.setText("");
    }

    public void scroll() throws InterruptedException {
        Thread.sleep(100);

        //scrolla alla fine della JTextArea
        this.scrollBar.getVerticalScrollBar().setValue(this.scrollBar.getVerticalScrollBar().getMaximum());

        //fa un redraw del component, risolve artefatti del render dopo aver scrollato automaticamente
        formattedOutputClient.repaint();
    }
}
