package it.uniba.controller;

import it.uniba.model.Item;
import it.uniba.model.Room;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameLoader {
    private HashMap<String, Item>  plotItems;
    private HashMap<Integer, Room>  plotRooms;

    //costanti
    private final String pathNameYaml="plot-adventure.yaml";

    public GameLoader() {
        plotItems = new HashMap<String, Item>();
        loadGameConfiguration(pathNameYaml);
    }

    private void loadGameConfiguration (String yamlPlotPath) {
        HashMap<String, Item> items = new HashMap<String, Item>();
        HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();

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

        //estrazione items
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

        //estrazione rooms
        ArrayList<HashMap<String,Object>> rowRooms = (ArrayList) yamlData.get("rooms");
        for (int i = 0; i<rowRooms.size(); i++)
        {
            Room generatedRoom = new Room(
                    (int)rowRooms.get(i).get("id"),
                    (String)rowRooms.get(i).get("title"),
                    (String)rowRooms.get(i).get("descr")
            );

            //generazione items per plotRoom
            if(rowRooms.get(i).get("items") != null) { //se la stanza contiene almeno un item

                ArrayList<String> extractedRoomsItemsId = (ArrayList<String>) rowRooms.get(i).get("items");
                //per ogni id items contenuto nello yaml della stanza, istanza un dump dell'item nelle plotRoom
                for (int j=0; j<extractedRoomsItemsId.size(); j++)
                {
                    /*
                        a partire dall'id dell'item all'interno della stanza(yaml),
                        aggiungi il dump Item nella stanza estratta
                     */
                    generatedRoom.addItemRoom(getDumpedItem(extractedRoomsItemsId.get(j)));
                }
            }
            //generazione exit generatedRoom


            //aggiungi stanza estratta alle rooms estratte
            rooms.put(
                    (int)rowRooms.get(i).get("id"),
                    generatedRoom
            );
        }
        plotRooms = rooms;
    }



    //data una key(corrispondente alla key dei plotItems) restituisce il dump di un item (copia)
    private Item getDumpedItem (String itemName) {
        Item newDumpedItem;
        newDumpedItem = new Item(
                plotItems.get(itemName).getItemName(),
                plotItems.get(itemName).getItemPattern()
        );

        return newDumpedItem;
    }

    public HashMap<Integer, Room> getPlotRooms() {
        return plotRooms;
    }
}
