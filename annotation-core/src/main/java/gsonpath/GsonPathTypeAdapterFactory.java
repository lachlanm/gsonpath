package gsonpath;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Lachlan on 7/03/2016.
 */
public class GsonPathTypeAdapterFactory implements TypeAdapterFactory {
    private GsonPathLoader gsonPathLoader;

    public GsonPathTypeAdapterFactory() {
        try {
            gsonPathLoader = (GsonPathLoader) Class.forName("gsonpath.GeneratedGsonPathLoader").newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Unable to find generated GsonPathLoader");
        }
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        return gsonPathLoader.create(gson, type);
    }
}
