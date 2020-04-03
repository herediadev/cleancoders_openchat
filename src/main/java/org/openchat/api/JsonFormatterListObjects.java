package org.openchat.api;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import spark.ResponseTransformer;

import java.util.List;
import java.util.Map;

public class JsonFormatterListObjects implements ResponseTransformer {

    @Override
    public String render(Object model) throws Exception {
        JsonArray jsonValues = new JsonArray();
        ((List<Map<String, String>>) model).forEach(mapOfFiledValues -> jsonValues.add(this.getSingleJsonObject(mapOfFiledValues)));
        return jsonValues.toString();
    }

    private JsonObject getSingleJsonObject(Map<String, String> map) {
        JsonObject members = new JsonObject();
        map.forEach(members::add);
        return members;
    }
}
