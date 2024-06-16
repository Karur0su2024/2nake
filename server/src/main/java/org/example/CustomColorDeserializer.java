package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.*;
import java.io.IOException;

public class CustomColorDeserializer extends StdDeserializer<Color> {

    public CustomColorDeserializer() {
        this(null);
    }

    protected CustomColorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Color deserialize(com.fasterxml.jackson.core.JsonParser jsonParser, com.fasterxml.jackson.databind.DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        int red = node.get("red").asInt();
        int green = node.get("green").asInt();
        int blue = node.get("blue").asInt();
        int alpha = node.get("alpha").asInt();
        return new Color(red, green, blue, alpha);
    }
}
