package it.uniba.controller;

import it.uniba.model.Plot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * La classe <code>Parser</code> consente di analizzare un flusso di dati in ingresso e determinarne la correttezza
 * interpretando un pattern grazie ad una data espressione regolare. L'espressione regolare viene generata dinamicamente
 * prendendo in input un file YAML.
 *
 * @author Nicole Stolbovoi
 */

public final class Parser {

    private Pattern patternCommand;
    private Pattern patternItem;
    private Plot gLoader;

    /**
     * Crea un costruttore della classe <code>Parser</code> parametrizzato.
     *
     * @param gl gioco caricato
     */

    public Parser(final Plot gl) {
        this.gLoader = gl;
        // System.out.println("*** Parser!!!");
        // System.out.println("*** keySet:" + gl.getPlotCommands().keySet());
        String[] pa = new String[gl.getPlotCommands().size()];
        int i = 0;

        for (String k : gl.getPlotCommands().keySet()) {
            pa[i++] = String.format("(?<%s>\\b(%s)\\b)", k, gl.getPlotCommands().get(k));
        }

        System.out.println("*** Pattern Command:" + String.join("|", pa));
        patternCommand = Pattern.compile(String.join("|", pa));

        String[] pi = new String[gl.getPlotItems().size()];
        i = 0;

        for (String k : gl.getPlotItems().keySet()) {
            pi[i++] = String.format("(?<%s>\\b(%s)\\b)", k, gl.getPlotItems().get(k).getItemPattern());
        }

        System.out.println("*** Pattern Item:" + String.join("|", pi));
        patternItem = Pattern.compile(String.join("|", pi));

    }


    /**
     * Legge in input un array di stringhe contenente una serie di comandi del gioco, per testare che vengano
     * decodificati correttamente.
     *
     * @param args stringhe da parsificare
     */

    public static void main(final String[] args) {

        System.out.println("*** Parser main");

        String[] testCaseIT = {
                // commands
                "vado a nord",
                "vai a nord",
                "nord",
                "vado ad est",
                "vai ad est",
                "est",
                "vado a sud",
                "vai a sud",
                "sud",
                "vado ad ovest",
                "vai ad ovest",
                "ovest",
                "aiuto",
                "dove sono",
                "prendo",
                "uso",
                "lascio",
                "inventario",
                "osservo",
                "indietro",
                "combatto",

                // items
                "gomitolo",
                "minotauro",
                "spada",

                // actions
                "prendo",
                "prendi",
                "pren",
                "prendo la spada",
                "prendo il gomitolo",
                "raccolgo",
                "raccogli",
                "raccowihewf",
                "raccolgo la spada",
                "raccolgo il gomitolo",
                "lascio",
                "lascia",
                "lascio la spada",
                "lascio il gomitolo",
                "usi",
                "usa",
                "uso il gomitolo",
                "uso la spada",
                "sconfiggi",
                "sconfiggo",
                "sconfiggo il minotauro",
                "sconfiggwef l minot",
                "combatti",
                "combatto",
                "combatto il minotauro",
                "uccidi",
                "uccido",
                "uccido il minotauro",
                "uccido gomitolo",
                "prendo gomitolo e spada, minotauro",
                "prendo minotauro, gomitolo e spada",
                "prendo spada minotauro, gomitolo",
                "spada prendo minotauro, gomitolo, gomitolo",
                "spada prendo  gomitolo, gomitolo",
        };

        Parser p = new Parser(new Plot());

        for (String t : testCaseIT) {
            System.out.printf("*** [%-20s]>>%s<<\n", String.join(", ", p.parse(t)), t);
        }

    }

    /**
     * Parsifica la stringa in input per intercettare comandi e/o item.
     *
     * @param token stringa da parsificare
     * @return array di stringhe in cui l'elemento 0 è un commando (o ambiguous o unrecognized), altri elementi se sono
     *         presenti sono item
     */

    public String[] parse(final String token) {
        Matcher m = patternCommand.matcher(token);
        List<String> commandList = new ArrayList();

        if (m.find()) {
            for (String c : gLoader.getPlotCommands().keySet()) {
                if (m.group(c.toLowerCase()) != null) {
                    commandList.add(c);
                }
            }
        }

        // System.out.println("*** commandList:" + commandList);

        m = patternItem.matcher(token);
        List<String> itemList = new ArrayList();

        // Finchè matcha si aggiungono item alla lista
        while (m.find()) {
            for (String i : gLoader.getPlotItems().keySet()) {
                if (m.group(i.toLowerCase()) != null) { // nome del gruppo
                    itemList.add(i);
                    m.start(i);
                }
            }
        }

        // System.out.println("*** itemList:" + itemList);

        // Dichiarazione e inizializzazione di String Array
        String rc[] = new String[Math.max(commandList.size() + itemList.size(), 1)];

        // Converte ArrayList in Object Array
        Object[] commandObj = commandList.toArray();
        Object[] itemObj = itemList.toArray();

        // Iterazione e conversione in String
        int i = 0;
        for (Object obj : commandObj) {
            rc[i++] = (String) obj;
        }
        for (Object obj : itemObj) {
            rc[i++] = (String) obj;
        }

        // Controllo conformità di action: se combinazione command-item è presente in Actions
        if (!itemList.isEmpty() && commandList.size() == 1) {
            // System.out.println("*** check di conformta di action se ci sono dei items");
            // System.out.println(gLoader.getPlotActions());

            // Controlla che il comando sia presente nelle Actions
            if (gLoader.getPlotActions().containsKey(commandList.get(0))) {
                for (String il: itemList) {
                    if (!gLoader.getPlotActions().get(commandList.get(0)).contains(il)) {
                        rc[0] = "ambiguous action"; // se combinazione command-item non combacia con action
                        return rc;
                    }
                }
                // System.out.println("\t*** " + gLoader.getPlotActions().get(commandList.get(0)));
            }
            // System.out.println(gLoader.getPlotActions().containsKey(commandList.get(0)));
        }

        if (commandList.isEmpty()) {
            rc[0] = "unrecognised";
            return rc;
        } else if (commandList.size() == 1) {
            return rc;
        } else {
            rc[0] = "ambiguous command";
            return rc;
        }
    }
}
