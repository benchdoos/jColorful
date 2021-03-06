package com.github.benchdoos.jcolorful.serializers;

import com.github.benchdoos.jcolorful.beans.components.BinaryElement;
import com.github.benchdoos.jcolorful.beans.components.BinaryElementImpl;
import com.github.benchdoos.jcolorful.core.ElementConstants;
import com.google.gson.*;

import java.awt.*;
import java.lang.reflect.Type;

public class BinaryElementDeserializer implements JsonDeserializer<BinaryElement> {
    public BinaryElement deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject object = jsonElement.getAsJsonObject();

        BinaryElementImpl binaryElement = new BinaryElementImpl();

        final JsonElement background = object.get(ElementConstants.BACKGROUND);
        if (background != null) {
            binaryElement.setBackgroundColor(Color.decode(background.getAsString()));
        }

        final JsonElement foreground = object.get(ElementConstants.FOREGROUND);
        if (foreground != null) {
            binaryElement.setForegroundColor(Color.decode(foreground.getAsString()));
        }

        return binaryElement;
    }
}