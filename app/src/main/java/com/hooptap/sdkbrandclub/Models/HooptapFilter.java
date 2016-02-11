package com.hooptap.sdkbrandclub.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by carloscarrasco on 11/2/16.
 */

public class HooptapFilter {

    private JSONObject jsonFilter = new JSONObject();

    public HooptapFilter() {
    }

    public HooptapFilter(JSONObject jsonFilter) {
        this.jsonFilter = jsonFilter;
    }

    public String filterToString() {
        return jsonFilter.toString();
    }

    public static class Builder {

        private final JSONObject jsonFilter;

        public enum Order {asc, desc}

        public Builder() {
            jsonFilter = new JSONObject();
        }

        public HooptapFilter.Builder sort(String key, Order order) {
            try {
                jsonFilter.put("sort", new JSONObject().put(key, order));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public HooptapFilter.Builder where(String key, String value) {
            try {
                JSONObject jsonWhere;
                JSONObject jsonValues = new JSONObject().put("value", value);
                jsonValues = createWhereWithParameterInclude(false, jsonValues);
                if (jsonFilter.isNull("where")) {
                    jsonWhere = new JSONObject().put(key, jsonValues);
                } else {
                    jsonWhere = jsonFilter.getJSONObject("where").put(key, jsonValues);
                }
                jsonFilter.put("where", jsonWhere);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public HooptapFilter.Builder where(String key, String value, boolean include) {
            try {
                JSONObject jsonWhere;
                JSONObject jsonValues = new JSONObject().put("value", value);
                jsonValues = createWhereWithParameterInclude(include, jsonValues);
                if (jsonFilter.isNull("where")) {
                    jsonWhere = new JSONObject().put(key, jsonValues);
                } else {
                    jsonWhere = jsonFilter.getJSONObject("where").put(key, jsonValues);
                }
                jsonFilter.put("where", jsonWhere);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        private JSONObject createWhereWithParameterInclude(boolean include, JSONObject jsonKeys) throws JSONException {
            JSONObject jsonKeysInclude = (include) ? jsonKeys.put("search_type", "include") : jsonKeys;
            return jsonKeysInclude;
        }

        public HooptapFilter.Builder group(String key) {
            try {
                jsonFilter.put("group", key);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public HooptapFilter build() {

            return new HooptapFilter(jsonFilter);
        }

    }
}

