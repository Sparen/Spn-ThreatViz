package com.spnthreatviz;

import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

/** 
 * The Json Transformer class used for translating objects between front end JSON objects and back end POJOs.
 */
public class JsonTransformer implements ResponseTransformer {

    /**
     * GSON object used to convert Java Objects to JSON for output to JavaScript frontend.
     */
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        }
        return gson.toJson(model);
    }

}
