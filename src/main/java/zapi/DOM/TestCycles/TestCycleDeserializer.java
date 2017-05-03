package zapi.DOM.TestCycles;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Chaitanya on 24-Jun-15.
 */
public class TestCycleDeserializer implements JsonDeserializer<TestCycleList> {
    public TestCycleList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        List<TestCyclesDOM> testCyclesDOMs = new ArrayList<TestCyclesDOM>();
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            // For individual City objects, we can use default deserialisation:
            TestCyclesDOM deserialized = context.deserialize(entry.getValue(), TestCyclesDOM.class);
            deserialized.setId(Integer.parseInt(entry.getKey()));
            testCyclesDOMs.add(deserialized);
        }
        return new TestCycleList(testCyclesDOMs);
    }
}
