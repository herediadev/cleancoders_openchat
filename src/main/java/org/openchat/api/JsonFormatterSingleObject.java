package org.openchat.api;

import com.eclipsesource.json.JsonObject;
import spark.ResponseTransformer;

import java.util.Map;

public class JsonFormatterSingleObject implements ResponseTransformer {

    @Override
    public String render(Object model) throws Exception {
        JsonObject members = new JsonObject();
        ((Map<String, String>) model).forEach(members::add);
        return members.toString();
    }
}
