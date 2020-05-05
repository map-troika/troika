package it.uniba.controller;

import it.uniba.plot.Plot;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private Pattern pattern;

    public enum commandType {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        HOME,
        HELP,
        POSITION,
        OIL,
        BAT,
        BOY,
        MINOTAUR,
        CLEW,
        ANTIDOTE,
        FLAG,
        TAKELANTERN,
        USELANTERN,

        UNRECOGNISED,
        AMBIGUOUS
    }

    public static void main(String[] args) {

        System.out.println("*** Parser main");
        String[] testCase = {
                "go north",
                "go east",
                "go south",
                "go west",
                "north",
                "east",
                "south",
                "west",
                "go home",
                "home",
                "help",
                "get postion",
                "postion",

                "oil",
                "boy",
                "antidote",
                "clew",
                "bat",
                "minotaur",
                "flag",

                "take oil",
                "boy",
                "take antidote",
                "give antidote",
                "take clew",
                "take bat",
                "defeat minotaur",
                "take flag"
        };

        String[] testCaseIT = {
                "vado a nord",
                "vado ad est",
                "vado a sud",
                "vado ad ovest",
                "nord",
                "est",
                "sud",
                "ovest",
                "vado a casa",
                "casa",
                "aiuto",
                "dove sono",
                "posizione",

                "olio",
                "fanciullo",
                "antidoto",
                "gomitolo",
                "pipistrello",
                "minotauro",
                "bandiera",

                "prendo l'olio",
                "fanciullo",
                "prendo l'antidoto",
                "do l'antidoto",
                "prendo il gomitolo",
                "accendo la lanterna",
                "sconfiggo il minotauro",
                "prendo la bandiera",
                "nord est"
        };


        for (String t : testCaseIT) {
            Parser p = new Parser(t);
        }
    }

    public Parser(String token) {
        pattern = Pattern.compile(
            "(?<north>(vado a nord|nord))|" +
            "(?<east>(vado ad est|est))|" + 
            "(?<south>(vado a sud|sud))|" +
            "(?<west>(vado ad ovest|ovest))|" +
            "(?<home>(vado a casa|casa))|" +
            "(?<help>(aiuto))|" +
            "(?<position>(dove sono|posizione))|" +
            "(?<oil>(prendo l'olio|raccolgo l'olio|olio))|" +
            "(?<boy>(fanciullo))|" +
            "(?<antidote>(prendo antidoto|raccolgo antidoto|antidoto))|" +
            "(?<clew>(prendo gomitolo|raccolgo gomitolo|gomitolo))|" +
            "(?<bat>(pipistrello))|" +
            "(?<minotaur>(sconfiggo il minotauro|combatto il minotauro|uccido il minotauro|sconfiggo|combato|uccido|minotauro))|" +
            "(?<flag>(prendo la bandiera|raccolgo la bandiera|bandiera|prendo|raccolgo))|" +
            "(?<takelantern>(prendo la lanterna|raccolgo la lanterna|lanterna|prendo|raccolgo))|" +
            "(?<uselantern>(uso la lanterna|uso))"
        );

        parse(token);
    }

    private Enum parse(String token) {

        Matcher m = pattern.matcher(token);
        List<Enum<commandType>> commandList = new ArrayList();

        if (m.find()) {
            commandType cmd[] = commandType.values();

            for(commandType c  : cmd) {
                if (c != commandType.UNRECOGNISED && c != commandType.AMBIGUOUS)
                    if (m.group(c.toString().toLowerCase()) != null) {
                        commandList.add(c);
                    }
            }
        }

        if (commandList.isEmpty()) {
            System.out.println("*** " + commandType.UNRECOGNISED + " >>" + token + "<<");
            return commandType.UNRECOGNISED;
        }
        else if (commandList.size() == 1) {
            System.out.println("*** " + commandList.get(0) + " >>" + token + "<<");
            return commandList.get(0);
        }
        else {
            System.out.println("*** " + commandType.AMBIGUOUS + " >>" + token + "<<");
            return commandType.AMBIGUOUS;
        }
    }
}
