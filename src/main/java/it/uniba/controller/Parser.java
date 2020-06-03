package it.uniba.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Parser {

    private Pattern pattern;
    private GameLoader gLoader;

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
        };

        Parser p = new Parser(new GameLoader());

        for (String t : testCaseIT) {
            System.out.printf("*** %-20s>>%s<<\n", p.parse(t), t);
        }

    }

    public Parser(final GameLoader gl) {
        this.gLoader = gl;
        // System.out.println("*** Parser!!!");
        // System.out.println("*** keySet:" + gl.getPlotCommands().keySet());
        String[] pa = new String[gl.getPlotCommands().size()];
        int i = 0;

        for (String k: gl.getPlotCommands().keySet()) {
            pa[i++] = String.format("(?<%s>\\b(%s)\\b)", k, gl.getPlotCommands().get(k));
        }

        System.out.println("*** Pattern:" + String.join("|", pa));
        pattern = Pattern.compile(String.join("|", pa));
    }



    public String parse(final String token) {
        Matcher m = pattern.matcher(token);
        List<String> commandList = new ArrayList();

        if (m.find()) {
            for (String c  : gLoader.getPlotCommands().keySet()) {
                if (m.group(c.toLowerCase()) != null) {
                    commandList.add(c);
                }
            }
        }

        if (commandList.isEmpty()) {
            return "unrecognised";
        } else if (commandList.size() == 1) {
            return commandList.get(0);
        } else {
            return "ambiguous";
        }
    }
}
