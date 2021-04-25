package com.dickens;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveMatch {

    private JSONArray matchLists;

    SaveMatch() {

        FileReader reader = null;
        try {
            reader = new FileReader("matches.json");
        } catch (FileNotFoundException e) {

            try (FileWriter file = new FileWriter("matches.json")) {

                file.write("[]");
                file.flush();

            } catch (IOException e2) {

                e2.printStackTrace();

            }

        }
        matchLists = new JSONArray(new JSONTokener(reader));

    }

    public void saveMatchToFile(final PrimaryController.Match match) {
        matchLists.put(new JSONObject(match));

        try (FileWriter file = new FileWriter("matches.json")) {

            file.write(matchLists.toString());
            file.flush();

        } catch (IOException e2) {

            e2.printStackTrace();

        }

    }

    public void saveMatches(JSONArray array) {
        try (FileWriter file = new FileWriter("matches.json")) {

            file.write(matchLists.toString());
            file.flush();

        } catch (IOException e2) {

            e2.printStackTrace();

        }
    }


    public JSONArray getMatchLists() {
        return matchLists;
    }
}
