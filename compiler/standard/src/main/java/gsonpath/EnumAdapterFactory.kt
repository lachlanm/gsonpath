package gsonpath

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import gsonpath.AdapterFactoryUtil.getAnnotatedModelElements
import gsonpath.compiler.CLASS_NAME_STRING
import gsonpath.compiler.generateClassName
import gsonpath.generator.AdapterMethodBuilder.createReadMethodBuilder
import gsonpath.generator.AdapterMethodBuilder.createWriteMethodBuilder
import gsonpath.generator.Constants
import gsonpath.generator.HandleResult
import gsonpath.generator.adapter.properties.AutoGsonAdapterProperties
import gsonpath.generator.adapter.properties.AutoGsonAdapterPropertiesFactory
import gsonpath.generator.writeFile
import gsonpath.model.FieldNamingPolicyMapper
import gsonpath.util.*
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.ElementKind
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

class EnumAdapterFactory(
        private val typeHandler: TypeHandler,
        private val annotationFetcher: AnnotationFetcher,
        private val fieldNamingPolicyMapper: FieldNamingPolicyMapper
) : AdapterFactory {

    override fun generateGsonAdapters(
            env: RoundEnvironment,
            logger: Logger,
            annotations: Set<TypeElement>,
            dependencies: Dependencies): List<HandleResult> {

        return getAnnotatedModelElements<AutoGsonAdapter>(env, annotations, listOf(ElementKind.ENUM))
                .onEach { logger.printMessage("Generating TypeAdapter (${it.element})") }
                .map { (element, annotation) ->
                    val properties = AutoGsonAdapterPropertiesFactory().create(element, annotation, false)
                    val fields = typeHandler.getFields(element) { it.kind == ElementKind.ENUM_CONSTANT }

                    val typeName = ClassName.get(element)
                    val adapterClassName = ClassName.get(typeName.packageName(),
                            generateClassName(typeName, "GsonTypeAdapter"))

                    createEnumAdapterSpec(adapterClassName, element, properties, fields)
                            .writeFile(dependencies.fileWriter, adapterClassName.packageName()) {
                                it.addStaticImport(GsonUtil::class.java, "*")
                            }
                    HandleResult(arrayOf(typeName), adapterClassName)
                }
    }


    private fun createEnumAdapterSpec(
            adapterClassName: ClassName,
            element: TypeElement,
            properties: AutoGsonAdapterProperties,
            fields: List<FieldElementContent>) = TypeSpecExt.finalClassBuilder(adapterClassName).apply {

        val typeName = ClassName.get(element)
        superclass(ParameterizedTypeName.get(ClassName.get(TypeAdapter::class.java), typeName))
        addAnnotation(Constants.GENERATED_ANNOTATION)

        field("mGson", Gson::class.java) {
            addModifiers(Modifier.PRIVATE, Modifier.FINAL)
        }
        field("nameToConstant", TypeNameExt.createMap(CLASS_NAME_STRING, typeName)) {
            addModifiers(Modifier.PRIVATE, Modifier.FINAL)
            initializer("new \$T()", TypeNameExt.createHashMap(CLASS_NAME_STRING, typeName))
        }
        field("constantToName", TypeNameExt.createMap(typeName, CLASS_NAME_STRING)) {
            addModifiers(Modifier.PRIVATE, Modifier.FINAL)
            initializer("new \$T()", TypeNameExt.createHashMap(typeName, CLASS_NAME_STRING))
        }

        // Add the constructor which takes a gson instance for future use.
        constructor {
            addModifiers(Modifier.PUBLIC)
            addParameter(Gson::class.java, "gson")
            code {
                assign("this.mGson", "gson")
                newLine()

                handleFields(element, fields, properties) { enumConstantName, label ->
                    addStatement("nameToConstant.put(\"$label\", \$T.$enumConstantName)", typeName)
                }

                newLine()

                handleFields(element, fields, properties) { enumConstantName, label ->
                    addStatement("constantToName.put(\$T.$enumConstantName, \"$label\")", typeName)
                }
            }
        }

        addMethod(createReadMethod(typeName))
        addMethod(createWriteMethod(typeName))
    }

    private fun createReadMethod(enumTypeName: TypeName) = createReadMethodBuilder(enumTypeName).applyAndBuild {
        code {
            `if`("!isValidValue(${Constants.IN})") {
                `return`(Constants.NULL)
            }
            `return`("nameToConstant.get(in.nextString())")
        }
    }

    private fun createWriteMethod(enumTypeName: TypeName) = createWriteMethodBuilder(enumTypeName).applyAndBuild {
        addStatement("out.value(value == null ? null : constantToName.get(value))")
    }

    private fun handleFields(
            element: TypeElement,
            fields: List<FieldElementContent>,
            properties: AutoGsonAdapterProperties,
            fieldFunc: (String, String) -> Unit) {

        fields.forEach { field ->
            val serializedName = annotationFetcher.getAnnotation(element, field.element, SerializedName::class.java)
            val enumConstantName = field.element.simpleName.toString()
            val label = serializedName?.value
                    ?: getFieldLabel(enumConstantName, properties.gsonFieldNamingPolicy)
            fieldFunc(enumConstantName, label)
        }
    }

    private fun getFieldLabel(fieldName: String, fieldNamingPolicy: FieldNamingPolicy): String {
        if (fieldNamingPolicy == FieldNamingPolicy.IDENTITY) {
            return fieldName
        }

        // If not using identity, we need to modify the enum label so that naming policies work.
        val adjustedFieldName = fieldName
                .toLowerCase()
                .let { lowerCaseFieldName ->
                    Regex("_.")
                            .findAll(lowerCaseFieldName)
                            .fold(lowerCaseFieldName) { oldValue, group ->
                                oldValue.replace(group.value, group.value.toUpperCase())
                            }
                }
                .replace("_", "")

        return fieldNamingPolicyMapper.applyFieldNamingPolicy(fieldNamingPolicy, adjustedFieldName)
    }

}