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
                "nord est",
                "pluto p paperino",
                "pos",
                "pluto n paperino",
                "enigma e totrtura",
                "ov",
                "c"
        };


        Parser p = new Parser();
        for (String t : testCaseIT) {
            System.out.printf("*** %-20s>>%s<<\n", p.parse(t), t);
        }
    }

    public Parser() {
        pattern = Pattern.compile(
            "(?<north>\\b(nord|n)\\b)|" +
            "(?<east>\\b(est|e)\\b)|" +
            "(?<south>\\b(sud|s)\\b)|" +
            "(?<west>\\b(ovest|o)\\b)|" +
            "(?<home>\\b(casa|c)\\b)|" +
            "(?<help>(aiuto))|" +
            "(?<position>\\b(dove sono|posizione|pos|p)\\b)|" +
            "(?<oil>\\b(olio|ol)\\b)|" +
            "(?<boy>\\b(fanciullo|fan|f)\\b)|" +
            "(?<antidote>\\b(antidoto|ant|a)\\b)|" +
            "(?<clew>\\b(gomitolo|gom|g)\\b)|" +
            "(?<bat>\\b(pipistrello|pip)\\b)|" +
            "(?<minotaur>\\b(minotauro|non|m)\\b)|" +
            "(?<flag>\\b(bandiera|ban|b)\\b)|" +
            "(?<takelantern>\\b(lanterna|lan|l)\\b)|" +
            "(?<uselantern>\\b(uso.*lanterna)\\b)"
        );

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
            return commandType.UNRECOGNISED;
        }
        else if (commandList.size() == 1) {
            return commandList.get(0);
        }
        else {
            return commandType.AMBIGUOUS;
        }
    }
}
