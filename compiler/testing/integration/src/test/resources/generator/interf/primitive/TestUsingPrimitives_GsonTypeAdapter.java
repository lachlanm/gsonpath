package generator.interf.primitive;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import gsonpath.GsonPathTypeAdapter;
import gsonpath.GsonUtil;
import gsonpath.JsonReaderHelper;
import java.io.IOException;
import java.lang.Boolean;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import javax.annotation.Generated;

@Generated(
        value = "gsonpath.GsonProcessor",
        comments = "https://github.com/LachlanMcKee/gsonpath"
)
public final class TestUsingPrimitives_GsonTypeAdapter extends GsonPathTypeAdapter<TestUsingPrimitives> {
    private static final int MANDATORY_INDEX_INTEXAMPLE = 0;

    private static final int MANDATORY_INDEX_LONGEXAMPLE = 1;

    private static final int MANDATORY_INDEX_DOUBLEEXAMPLE = 2;

    private static final int MANDATORY_INDEX_BOOLEANEXAMPLE = 3;

    private static final int MANDATORY_FIELDS_SIZE = 4;

    public TestUsingPrimitives_GsonTypeAdapter(Gson gson) {
        super(gson);
    }

    @Override
    public TestUsingPrimitives readImpl(JsonReader in) throws IOException {
        int value_intExample = 0;
        long value_longExample = 0L;
        double value_doubleExample = 0d;
        boolean value_booleanExample = false;
        int[] value_intArrayExample = null;
        long[] value_longArrayExample = null;
        double[] value_doubleArrayExample = null;
        boolean[] value_booleanArrayExample = null;
        JsonReaderHelper jsonReaderHelper = new JsonReaderHelper(in, 1, 0);
        boolean[] mandatoryFieldsCheckList = new boolean[MANDATORY_FIELDS_SIZE];

        while (jsonReaderHelper.handleObject(0, 8)) {
            switch (in.nextName()) {
                case "intExample":
                    Integer value_intExample_safe = gson.getAdapter(Integer.class).read(in);
                    if (value_intExample_safe != null) {
                        value_intExample = value_intExample_safe;
                        mandatoryFieldsCheckList[MANDATORY_INDEX_INTEXAMPLE] = true;

                    } else {
                        throw new gsonpath.JsonFieldNullException("intExample", "generator.interf.primitive.TestUsingPrimitives_GsonPathModel");
                    }
                    break;

                case "longExample":
                    Long value_longExample_safe = gson.getAdapter(Long.class).read(in);
                    if (value_longExample_safe != null) {
                        value_longExample = value_longExample_safe;
                        mandatoryFieldsCheckList[MANDATORY_INDEX_LONGEXAMPLE] = true;

                    } else {
                        throw new gsonpath.JsonFieldNullException("longExample", "generator.interf.primitive.TestUsingPrimitives_GsonPathModel");
                    }
                    break;

                case "doubleExample":
                    Double value_doubleExample_safe = gson.getAdapter(Double.class).read(in);
                    if (value_doubleExample_safe != null) {
                        value_doubleExample = value_doubleExample_safe;
                        mandatoryFieldsCheckList[MANDATORY_INDEX_DOUBLEEXAMPLE] = true;

                    } else {
                        throw new gsonpath.JsonFieldNullException("doubleExample", "generator.interf.primitive.TestUsingPrimitives_GsonPathModel");
                    }
                    break;

                case "booleanExample":
                    Boolean value_booleanExample_safe = gson.getAdapter(Boolean.class).read(in);
                    if (value_booleanExample_safe != null) {
                        value_booleanExample = value_booleanExample_safe;
                        mandatoryFieldsCheckList[MANDATORY_INDEX_BOOLEANEXAMPLE] = true;

                    } else {
                        throw new gsonpath.JsonFieldNullException("booleanExample", "generator.interf.primitive.TestUsingPrimitives_GsonPathModel");
                    }
                    break;

                case "intArrayExample":
                    value_intArrayExample = gson.getAdapter(int[].class).read(in);
                    break;

                case "longArrayExample":
                    value_longArrayExample = gson.getAdapter(long[].class).read(in);
                    break;

                case "doubleArrayExample":
                    value_doubleArrayExample = gson.getAdapter(double[].class).read(in);
                    break;

                case "booleanArrayExample":
                    value_booleanArrayExample = gson.getAdapter(boolean[].class).read(in);
                    break;

                default:
                    jsonReaderHelper.onObjectFieldNotFound(0);
                    break;

            }
        }

        // Mandatory object validation
        for (int mandatoryFieldIndex = 0; mandatoryFieldIndex < MANDATORY_FIELDS_SIZE; mandatoryFieldIndex++) {

            // Check if a mandatory value is missing.
            if (!mandatoryFieldsCheckList[mandatoryFieldIndex]) {

                // Find the field name of the missing json value.
                String fieldName = null;
                switch (mandatoryFieldIndex) {
                    case MANDATORY_INDEX_INTEXAMPLE:
                        fieldName = "intExample";
                        break;

                    case MANDATORY_INDEX_LONGEXAMPLE:
                        fieldName = "longExample";
                        break;

                    case MANDATORY_INDEX_DOUBLEEXAMPLE:
                        fieldName = "doubleExample";
                        break;

                    case MANDATORY_INDEX_BOOLEANEXAMPLE:
                        fieldName = "booleanExample";
                        break;

                }
                throw new gsonpath.JsonFieldNoKeyException(fieldName, "generator.interf.primitive.TestUsingPrimitives_GsonPathModel");
            }
        }
        return new TestUsingPrimitives_GsonPathModel(
                value_intExample,
                value_longExample,
                value_doubleExample,
                value_booleanExample,
                value_intArrayExample,
                value_longArrayExample,
                value_doubleArrayExample,
                value_booleanArrayExample);
    }

    @Override
    public void writeImpl(JsonWriter out, TestUsingPrimitives value) throws IOException {
        // Begin
        out.beginObject();
        int obj0 = value.getIntExample();
        out.name("intExample");
        gson.getAdapter(Integer.class).write(out, obj0);

        long obj1 = value.getLongExample();
        out.name("longExample");
        gson.getAdapter(Long.class).write(out, obj1);

        double obj2 = value.getDoubleExample();
        out.name("doubleExample");
        gson.getAdapter(Double.class).write(out, obj2);

        boolean obj3 = value.getBooleanExample();
        out.name("booleanExample");
        gson.getAdapter(Boolean.class).write(out, obj3);

        int[] obj4 = value.getIntArrayExample();
        if (obj4 != null) {
            out.name("intArrayExample");
            GsonUtil.writeWithGenericAdapter(gson, obj4.getClass(), out, obj4);
        }

        long[] obj5 = value.getLongArrayExample();
        if (obj5 != null) {
            out.name("longArrayExample");
            GsonUtil.writeWithGenericAdapter(gson, obj5.getClass(), out, obj5);
        }

        double[] obj6 = value.getDoubleArrayExample();
        if (obj6 != null) {
            out.name("doubleArrayExample");
            GsonUtil.writeWithGenericAdapter(gson, obj6.getClass(), out, obj6);
        }

        boolean[] obj7 = value.getBooleanArrayExample();
        if (obj7 != null) {
            out.name("booleanArrayExample");
            GsonUtil.writeWithGenericAdapter(gson, obj7.getClass(), out, obj7);
        }

        // End
        out.endObject();
    }

    @Override
    public String getModelClassName() {
        return "generator.interf.primitive.TestUsingPrimitives_GsonPathModel";
    }
}