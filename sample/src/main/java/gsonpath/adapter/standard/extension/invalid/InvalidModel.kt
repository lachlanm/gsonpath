package gsonpath.adapter.standard.extension.invalid

import gsonpath.GsonFieldValidationType
import gsonpath.annotation.AutoGsonAdapter
import gsonpath.extension.annotation.RemoveInvalidElements

interface InvalidModel {
    @AutoGsonAdapter
    interface ArrayModel {
        @get:RemoveInvalidElements
        val value: Array<Data>
    }

    @AutoGsonAdapter
    interface CollectionModel {
        @get:RemoveInvalidElements
        val value: Collection<Data>
    }

    @AutoGsonAdapter(fieldValidationType = [GsonFieldValidationType.VALIDATE_ALL_EXCEPT_NULLABLE])
    data class Data(val text: String)
}
