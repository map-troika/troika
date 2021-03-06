package it.uniba.model;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *  <p><code>Entity</code></p> La classe <code>Plot</code> implementa metodo per estrarre e inizializzare
 *  i dati del plot da un file di configurazione, mettendo a disposizione dei metodi per ottenere
 *  gli oggetti del plot inizializzati
 */
public class Plot {
    private HashMap<String, Item> plotItems;
    private HashMap<Integer, Room> plotRooms;
    private HashMap<String, String> plotCommands;
    private HashMap<String, ArrayList<String>> plotActions;

    //costanti
    private final String pathNameYaml = "plot-adventure.yaml";

    /**
     * Costruttore, inizializza le strutture dati e avvia l'estrazione del file Yaml
     */
    public Plot() {
        plotItems = new HashMap<String, Item>();
        plotRooms = new HashMap<Integer, Room>();
        plotCommands = new HashMap<String, String>();
        plotActions = new HashMap<String, ArrayList<String>>();

        loadGameConfiguration(pathNameYaml);
    }

    /**
     * ottiene dati del plot dal file Yaml e istanza e inizializza le strutture dati (plotItems, plotRooms,
     * plotCommands, plotActions
     *
     * @param yamlPlotPath percorso del file Yaml in cui sono contenuti i dati plot
     */
    private void loadGameConfiguration(final String yamlPlotPath) {
        HashMap<String, Item> generatedItems = new HashMap<String, Item>();
        HashMap<Integer, Room> generatedRooms = new HashMap<Integer, Room>();
        HashMap<String, String> generatedCommands = new HashMap<String, String>();
        HashMap<String, ArrayList<String>> generatedActions = new HashMap<String, ArrayList<String>>();

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
        ArrayList<HashMap<String, Object>> rowItems = (ArrayList) yamlData.get("items");
        for (int i = 0; i < rowItems.size(); i++) {

            generatedItems.put(
                    rowItems.get(i).get("name").toString(),
                    new Item(
                        rowItems.get(i).get("name").toString(),
                        rowItems.get(i).get("pattern").toString(),
                        rowItems.get(i).get("description").toString(),
                        (boolean) rowItems.get(i).get("isEnemy"),
                        (boolean) rowItems.get(i).get("isWeapon")
                    )
            );
        }
        plotItems = generatedItems;

        //estrazione rooms
        ArrayList<HashMap<String, Object>> rowRooms = (ArrayList) yamlData.get("rooms");
        for (int i = 0; i < rowRooms.size(); i++) {
            Room generatedRoom = new Room(
                    (int) rowRooms.get(i).get("id"),
                    (String) rowRooms.get(i).get("title"),
                    (String) rowRooms.get(i).get("descr")
            );

            //generazione items per plotRoom
            if (rowRooms.get(i).get("items") != null) { //se la stanza contiene almeno un item

                ArrayList<String> extractedRoomsItemsId = (ArrayList<String>) rowRooms.get(i).get("items");
                //per ogni id items contenuto nello yaml della stanza, istanza un dump dell'item nelle plotRoom
                for (int j = 0; j < extractedRoomsItemsId.size(); j++) {
                    /*
                        a partire dall'id dell'item all'interno della stanza(yaml),
                        aggiungi il dump Item nella stanza estratta
                     */
                    generatedRoom.addItemRoom(getDumpedItem(extractedRoomsItemsId.get(j)));
                }
            }
            //generazione exits
            generatedRoom.setExitRoom((HashMap<String, Integer>) rowRooms.get(i).get("exits"));

            //aggiungi stanza estratta alle rooms estratte
            generatedRooms.put(
                    (int) rowRooms.get(i).get("id"),
                    generatedRoom
            );
        }
        plotRooms = generatedRooms;

        //estrazione commands
        ArrayList<HashMap<String, Object>> rowCommands = (ArrayList) yamlData.get("commands");
        for (int i = 0; i < rowCommands.size(); i++) {
            generatedCommands.put(
                    (String) rowCommands.get(i).get("name"),
                    (String) rowCommands.get(i).get("pattern")
            );
        }
        plotCommands = generatedCommands;

        //estrazione actions
        ArrayList<HashMap<String, Object>> rowActions = (ArrayList) yamlData.get("actions");
        for (int i = 0; i < rowActions.size(); i++) {

            generatedActions.put(
                    (String) rowActions.get(i).get("name"),
                    (ArrayList<String>) rowActions.get(i).get("items")
            );
        }
        plotActions = generatedActions;
    }


    /**
     * data una key(corrispondente alla key dei plotItems) restituisce il dump di un item (copia)
     *
     * @param itemName nome dell'item di cui ottenere una copia
     * @return il metodo restituisce l'istanza di un nuovo item richiesto
     */
    private Item getDumpedItem(final String itemName) {
        Item newDumpedItem;
        newDumpedItem = new Item(
                plotItems.get(itemName).getItemName(),
                plotItems.get(itemName).getItemPattern(),
                plotItems.get(itemName).getDescription(),
                plotItems.get(itemName).getIsEnemy(),
                plotItems.get(itemName).getIsWeapon()
        );

        return newDumpedItem;
    }

    /**
     * metodo pubblico che restituisce la struttura dati inizializzata delle Rooms
     *
     * @return restituisce struttura dati plotRooms
     */
    public HashMap<Integer, Room> getPlotRooms() {
        return plotRooms;
    }

    /**
     * metodo pubblico che restituisce la struttura dati dei Commands
     *
     * @return restituisce struttura dati plotCommands
     */
    public HashMap<String, String> getPlotCommands() {
        return plotCommands;
    }

    /**
     * metodo pubblico che restituisce la struttura dati delle Actions
     *
     * @return restituisce struttura dati plotActions
     */
    public HashMap<String, Item> getPlotItems() {
        return plotItems;
    }

    /**
     * metodo pubblico che restituisce la struttura dati delle Actions
     *
     * @return restituisce struttura dati plotActions
     */
    public HashMap<String, ArrayList<String>> getPlotActions() {
        return plotActions;
    }
}
