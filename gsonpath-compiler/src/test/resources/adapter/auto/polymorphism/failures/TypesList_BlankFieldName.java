package adapter.auto.polymorphism.failures;

import adapter.auto.polymorphism.Type1;
import gsonpath.AutoGsonAdapter;
import gsonpath.GsonSubtype;

@AutoGsonAdapter
class TypesList_BlankFieldName {
    @GsonSubtype(
            subTypeKey = "",
            stringValueSubtypes = {
                    @GsonSubtype.StringValueSubtype(value = "type1", subtype = Type1.class)
            }
    )
    Type[] items;
}
