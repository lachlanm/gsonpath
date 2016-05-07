package adapter.auto.field_annotations.flatten_json;

import static gsonpath.GsonUtil.*;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.Override;

public final class TestFlattenJson_GsonTypeAdapter extends TypeAdapter<TestFlattenJson> {
    private final Gson mGson;

    public TestFlattenJson_GsonTypeAdapter(Gson gson) {
        this.mGson = gson;
    }

    @Override
    public TestFlattenJson read(JsonReader in) throws IOException {

        // Ensure the object is not null.
        if (!isValidValue(in)) {
            return null;
        }
        TestFlattenJson result = new TestFlattenJson();

        int jsonFieldCounter0 = 0;
        in.beginObject();

        while (in.hasNext()) {
            if (jsonFieldCounter0 == 1) {
                in.skipValue();
                continue;
            }

            switch (in.nextName()) {
                case "value1":
                    jsonFieldCounter0++;

                    com.google.gson.JsonElement safeValue0 = mGson.getAdapter(com.google.gson.JsonElement.class).read(in);
                    if (safeValue0 != null) {
                        result.value1 = safeValue0.toString();
                    }
                    break;

                default:
                    in.skipValue();
                    break;
            }
        }


        in.endObject();
        return result;
    }

    @Override
    public void write(JsonWriter out, TestFlattenJson value) throws IOException {
        // GsonPath does not support writing at this stage.
    }
}