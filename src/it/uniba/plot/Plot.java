package it.uniba.plot;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.Map;

public class Plot {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Plot Test!");
        Plot p = new Plot();
    }

    public  Plot() {
        System.out.println("Plot constructor!");

        Map<String, Object> data = null;
        File fin = new File("test.yaml");
        Yaml yaml = null;


        try (InputStream input = new FileInputStream(fin)) {
            yaml = new Yaml();
            data = yaml.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(data);




    }


}

