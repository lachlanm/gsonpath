package gsonpath.generator.standard

import gsonpath.generator.GeneratorTester.assertGeneratedContent
import gsonpath.generator.TestCriteria
import org.junit.Test

class SizeExtensionTest {
    @Test
    fun testSizeMutable() {
        assertGeneratedContent(TestCriteria("generator/standard/size/valid/mutable",
                absoluteSourceNames = listOf("generator/standard/TestGsonTypeFactory.java"),
                relativeSourceNames = listOf("TestMutableSize.java"),
                relativeGeneratedNames = listOf("TestMutableSize_GsonTypeAdapter.java")
        ))
    }

    @Test
    fun testSizeImmutable() {
        assertGeneratedContent(TestCriteria("generator/standard/size/valid/immutable",
                absoluteSourceNames = listOf("generator/standard/TestGsonTypeFactory.java"),
                relativeSourceNames = listOf("TestImmutableSize.java"),
                relativeGeneratedNames = listOf("TestImmutableSize_GsonTypeAdapter.java")
        ))
    }
}