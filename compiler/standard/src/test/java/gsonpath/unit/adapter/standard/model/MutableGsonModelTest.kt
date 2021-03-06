package gsonpath.unit.adapter.standard.model

import gsonpath.adapter.standard.model.*
import gsonpath.emptyGsonObject
import gsonpath.model.FieldInfo
import gsonpath.processingExceptionMatcher
import gsonpath.simpleGsonArray
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.mockito.Mockito.mock

class MutableGsonModelTest {
    @JvmField
    @Rule
    val expectedException: ExpectedException = ExpectedException.none()

    @Test
    fun testGsonField() {
        val fieldInfo = mock(FieldInfo::class.java)
        val mutableGsonField = MutableGsonField(0, fieldInfo, "variable", "path", true)

        Assert.assertEquals(
                GsonField(0, fieldInfo, "variable", "path", true),
                mutableGsonField.toImmutable())
    }

    @Test
    fun testGsonObject() {
        val fieldInfo = mock(FieldInfo::class.java)
        val mutableGsonField = MutableGsonField(0, fieldInfo, "variable", "path", true)

        val mutableGsonObject = MutableGsonObject()
        mutableGsonObject.addField("field1", mutableGsonField)
        mutableGsonObject.addObject("object1", MutableGsonObject())
        val array = mutableGsonObject.addArray("array1")
        array.getObjectAtIndex(0)

        Assert.assertEquals(
                GsonObject(
                        mapOf(
                                "field1" to GsonField(0, fieldInfo, "variable", "path", true),
                                "object1" to emptyGsonObject(),
                                "array1" to simpleGsonArray(0, emptyGsonObject())
                        )
                ),
                mutableGsonObject.toImmutable())
    }

    @Test
    fun testGsonArray() {
        val fieldInfo = mock(FieldInfo::class.java)
        val mutableGsonField = MutableGsonField(0, fieldInfo, "variable", "path", true)
        val mutableGsonArray = MutableGsonArray()

        mutableGsonArray.addField(0, mutableGsonField)
        mutableGsonArray.getObjectAtIndex(1)

        Assert.assertEquals(
                GsonArray(
                        mapOf(
                                0 to GsonField(0, fieldInfo, "variable", "path", true),
                                1 to emptyGsonObject()
                        ),
                        1
                ),
                mutableGsonArray.toImmutable())
    }

    @Test
    fun testInvalidGsonArray() {
        expectedException.expect(`is`(processingExceptionMatcher(null, "Array should not be empty")))
        MutableGsonArray().toImmutable()
    }
}