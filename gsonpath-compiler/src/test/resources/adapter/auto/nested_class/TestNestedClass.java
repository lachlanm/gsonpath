package adapter.auto.nested_class;

import gsonpath.AutoGsonAdapter;

public class TestNestedClass {
    @AutoGsonAdapter
    public static class Nested {
        public int value1;
    }
}