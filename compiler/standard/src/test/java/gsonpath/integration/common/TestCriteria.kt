package gsonpath.integration.common

class TestCriteria(val resourcePath: String,
                   val relativeSourceNames: List<String> = emptyList(),
                   val relativeGeneratedNames: List<String> = emptyList(),
                   val absoluteSourceNames: List<String> = emptyList())