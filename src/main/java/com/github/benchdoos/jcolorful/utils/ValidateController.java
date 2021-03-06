package com.github.benchdoos.jcolorful.utils;

import com.github.benchdoos.jcolorful.beans.components.BinaryElement;
import com.github.benchdoos.jcolorful.core.ModelConstants;
import com.github.benchdoos.jcolorful.serializers.BinaryElementDeserializer;
import com.google.gson.*;

public class ValidateController {
    private final int[] SUPPORTED_JSON_THEMES_VERSIONS = new int[]{1};

    private boolean contains(int value) {
        for (int supported_json_themes_version : SUPPORTED_JSON_THEMES_VERSIONS) {
            if (supported_json_themes_version == value) {
                return true;
            }
        }
        return false;
    }

    public boolean validate(String content) {
        JsonElement rootElement = new JsonParser().parse(content);
        return validateInformationStructure(rootElement) && validateCommonData(rootElement);
    }

    private boolean validateCommonData(JsonElement rootElement) {
        final JsonObject rootObject = rootElement.getAsJsonObject();
        final JsonArray theme = rootObject.getAsJsonArray("theme");
        final JsonObject commonDataObject = theme.get(0).getAsJsonObject();



        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(BinaryElement.class, new BinaryElementDeserializer());
        Gson gson = builder.create();

        return gson.fromJson(commonDataObject, BinaryElement.class) != null;
    }

    private boolean validateInformationStructure(JsonElement rootElement) {
        final JsonObject rootObject = rootElement.getAsJsonObject();
        final JsonPrimitive typeObject = rootObject.getAsJsonPrimitive("type");
        final JsonPrimitive versionObject = rootObject.getAsJsonPrimitive("version");

        if (typeObject.getAsString().equalsIgnoreCase(ModelConstants.THEME_TYPE)) {
            return contains(versionObject.getAsInt());
        }
        return false;
    }
}
