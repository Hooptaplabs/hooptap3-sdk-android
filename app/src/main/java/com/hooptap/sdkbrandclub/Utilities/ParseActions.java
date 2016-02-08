package com.hooptap.sdkbrandclub.Utilities;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by carloscarrasco on 3/2/16.
 */
public class ParseActions {

    private static ArrayList<String> arrayActions = new ArrayList<>();
    private static HashMap<String, String> matchingFields = new HashMap<>();

    public static ArrayList<String> actions(JSONObject jsonRespone) {
        arrayActions.clear();
        try {
            JSONObject jsonActions = jsonRespone.getJSONObject("response");
            JSONArray jsonArrayItems = jsonActions.getJSONArray("items");
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                String nameAction = ((JSONObject) jsonArrayItems.get(i)).getString("name");
                if (!arrayActions.contains(nameAction)) {
                    arrayActions.add(nameAction);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return arrayActions;
    }

    public static HashMap<String, String> matchingFieldsForAction(JSONObject jsonRespone, String action){
        matchingFields.clear();
        try {
            JSONObject jsonActions = jsonRespone.getJSONObject("response");
            JSONArray jsonArrayItems = jsonActions.getJSONArray("items");
            for (int i = 0; i < jsonArrayItems.length(); i++) {
                String nameAction = ((JSONObject) jsonArrayItems.get(i)).getString("name");
                if (nameAction.equals(action)){
                    JSONArray jsonArrayMatchingfields = ((JSONObject) jsonArrayItems.get(i)).getJSONArray("matching_fields");
                    for (int j = 0; j < jsonArrayMatchingfields.length(); j++) {
                        String nameMatchingField = ((JSONObject) jsonArrayMatchingfields.get(j)).getString("name");
                        String typeMatchingField = ((JSONObject) jsonArrayMatchingfields.get(j)).getString("type");
                        matchingFields.put(nameMatchingField, typeMatchingField);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchingFields;
    }

}
