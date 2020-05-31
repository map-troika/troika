package it.uniba.controller;

import it.uniba.model.Item;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameLoader {
    private HashMap<String, Item>  plotItems;
    private HashMap<String, Object>  plotRooms;

    //costanti
    private final String pathNameYaml="plot-adventure.yaml";

    public GameLoader() {
        plotItems = new HashMap<String, Item>();
        loadGameConfiguration(pathNameYaml);
    }

    private void loadGameConfiguration (String yamlPlotPath) {
        HashMap<String, Item> items = new HashMap<String, Item>();
        HashMap<String, Object> rooms = new HashMap<String, Object>();

        File fin = new File(pathNameYaml);
        Map<String, Object> yamlData = new HashMap<>();

        //estrazione yaml
        try (InputStream input = new FileInputStream(fin)) {
            Yaml yaml = new Yaml();
            yamlData = yaml.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //estrazione items  ArrayList<HashMap<string, Object>>
        ArrayList<HashMap<String,Object>> rowItems = (ArrayList) yamlData.get("items");
        for (int i=0; i<rowItems.size(); i++)
        {

            items.put(
                 rowItems.get(i).get("name").toString(),
                new Item(
                    rowItems.get(i).get("name").toString(),
                    rowItems.get(i).get("pattern").toString()
                )
            );
        }
        plotItems = items;


    }

    private HashMap<String, Item> getPlotItems () {

        return plotItems;
    }
}
