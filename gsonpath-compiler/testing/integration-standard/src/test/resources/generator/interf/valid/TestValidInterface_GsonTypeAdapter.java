package generator.interf.valid;

import static gsonpath.GsonUtil.*;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.Integer;
import java.lang.Override;
import javax.annotation.Generated;

@Generated(
        value = "gsonpath.GsonProcessor",
        comments = "https://github.com/LachlanMcKee/gsonpath"
)
public final class TestValidInterface_GsonTypeAdapter extends TypeAdapter<TestValidInterface> {
    private final Gson mGson;

    public TestValidInterface_GsonTypeAdapter(Gson gson) {
        this.mGson = gson;
    }

    @Override
    public TestValidInterface read(JsonReader in) throws IOException {
        // Ensure the object is not null.
        if (!isValidValue(in)) {
            return null;
        }
        Integer value_Json1_Nest1 = null;
        Integer value_value2 = null;
        Integer value_Json1_Nest3 = null;
        Integer value_result = null;
        Integer value_that = null;

        int jsonFieldCounter0 = 0;
        in.beginObject();

        while (in.hasNext()) {
            if (jsonFieldCounter0 == 4) {
                in.skipValue();
                continue;
            }

            switch (in.nextName()) {
                case "Json1":
                    jsonFieldCounter0++;

                    // Ensure the object is not null.
                    if (!isValidValue(in)) {
                        break;
                    }
                    int jsonFieldCounter1 = 0;
                    in.beginObject();

                    while (in.hasNext()) {
                        if (jsonFieldCounter1 == 2) {
                            in.skipValue();
                            continue;
                        }

                        switch (in.nextName()) {
                            case "Nest1":
                                jsonFieldCounter1++;

                                value_Json1_Nest1 = mGson.getAdapter(Integer.class).read(in);
                                break;

                            case "Nest3":
                                jsonFieldCounter1++;

                                value_Json1_Nest3 = mGson.getAdapter(Integer.class).read(in);
                                break;

                            default:
                                in.skipValue();
                                break;
                        }
                    }

                    in.endObject();
                    break;

                case "value2":
                    jsonFieldCounter0++;

                    value_value2 = mGson.getAdapter(Integer.class).read(in);
                    break;

                case "result":
                    jsonFieldCounter0++;

                    value_result = mGson.getAdapter(Integer.class).read(in);
                    break;

                case "that":
                    jsonFieldCounter0++;

                    value_that = mGson.getAdapter(Integer.class).read(in);
                    break;

                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();
        return new TestValidInterface_GsonPathModel(
                value_Json1_Nest1,
                value_value2,
                value_Json1_Nest3,
                value_result,
                value_that
        );
    }

    @Override
    public void write(JsonWriter out, TestValidInterface value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        // Begin
        out.beginObject();

        // Begin Json1
        out.name("Json1");
        out.beginObject();
        Integer obj0 = value.getValue1();
        if (obj0 != null) {
            out.name("Nest1");
            mGson.getAdapter(Integer.class).write(out, obj0);
        }

        Integer obj1 = value.getValue3();
        if (obj1 != null) {
            out.name("Nest3");
            mGson.getAdapter(Integer.class).write(out, obj1);
        }

        // End Json1
        out.endObject();
        Integer obj2 = value.getValue2();
        if (obj2 != null) {
            out.name("value2");
            mGson.getAdapter(Integer.class).write(out, obj2);
        }

        Integer obj3 = value.getResult();
        if (obj3 != null) {
            out.name("result");
            mGson.getAdapter(Integer.class).write(out, obj3);
        }

        Integer obj4 = value.getThat();
        if (obj4 != null) {
            out.name("that");
            mGson.getAdapter(Integer.class).write(out, obj4);
        }

        // End
        out.endObject();
    }
}