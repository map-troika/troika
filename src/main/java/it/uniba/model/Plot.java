package it.uniba.model;

import it.uniba.controller.GameLoader;
import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class Plot {

    private Map<String, Object> config;
    private ArrayList<String> commandsList;
    private ArrayList<String> itemsList;
    private ArrayList<String> actionsList;
    private ArrayList<Room>   roomsList;

    public static void main(String[] args) {
        System.out.println("*** Plot main");

        Plot p = new Plot();

        GameLoader gLoader = new GameLoader();
        /*
        p.dumpCommandsList();
        p.dumpItemsList();
        p.dumpActionsList();
        p.dumpRoomsList();
        */
    }

    public Plot() {

    }
}

