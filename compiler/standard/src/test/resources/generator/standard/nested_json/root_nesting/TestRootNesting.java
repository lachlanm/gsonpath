package generator.standard.nested_json.root_nesting;

import gsonpath.annotation.AutoGsonAdapter;

@AutoGsonAdapter(rootField = "Root.Nest1")
public class TestRootNesting {
    public int value1;
}