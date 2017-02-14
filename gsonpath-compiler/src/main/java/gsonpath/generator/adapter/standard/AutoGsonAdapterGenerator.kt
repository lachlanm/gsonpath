package gsonpath.generator.adapter.standard

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonWriter
import com.squareup.javapoet.*
import gsonpath.*
import gsonpath.generator.Generator
import gsonpath.generator.HandleResult
import gsonpath.generator.adapter.interf.ModelInterfaceGenerator
import gsonpath.generator.adapter.generateClassName
import gsonpath.model.*
import java.io.IOException
import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeMirror

class AutoGsonAdapterGenerator(processingEnv: ProcessingEnvironment) : Generator(processingEnv) {

    @Throws(ProcessingException::class)
    fun handle(modelElement: TypeElement): HandleResult {
        val modelClassName = ClassName.get(modelElement)
        val adapterClassName = ClassName.get(modelClassName.packageName(),
                generateClassName(modelClassName, "GsonTypeAdapter"))

        val adapterTypeBuilder = TypeSpec.classBuilder(adapterClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .superclass(ParameterizedTypeName.get(ClassName.get(TypeAdapter::class.java), modelClassName))
                .addField(Gson::class.java, "mGson", Modifier.PRIVATE, Modifier.FINAL)

        // Add the constructor which takes a gson instance for future use.
        adapterTypeBuilder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Gson::class.java, "gson")
                .addStatement("this.\$N = \$N", "mGson", "gson")
                .build())

        val autoGsonAnnotation = modelElement.getAnnotation(AutoGsonAdapter::class.java)

        val concreteClassName: ClassName
        val fieldInfoList: List<FieldInfo>
        val isModelInterface = modelElement.kind.isInterface

        val properties = AutoGsonAdapterPropertiesFactory().create(
                autoGsonAnnotation, getDefaultsAnnotation(autoGsonAnnotation), isModelInterface)

        val fieldInfoFactory = FieldInfoFactory(processingEnv)
        if (!isModelInterface) {
            concreteClassName = modelClassName

            fieldInfoList = fieldInfoFactory.getModelFieldsFromElement(modelElement, properties.fieldsRequireAnnotation)

        } else {
            val interfaceInfo = ModelInterfaceGenerator(processingEnv).handle(modelElement)
            concreteClassName = interfaceInfo.parentClassName

            fieldInfoList = fieldInfoFactory.getModelFieldsFromInterface(interfaceInfo)
        }

        val rootGsonObject = GsonObjectTreeFactory().createGsonObject(fieldInfoList, properties.rootField,
                properties.flattenDelimiter, properties.gsonFieldNamingPolicy, properties.gsonFieldValidationType,
                properties.pathSubstitutions)

        // Adds the mandatory field index constants and also populates the mandatoryInfoMap values.
        val mandatoryInfoMap = MandatoryFieldInfoFactory().createMandatoryFieldsFromGsonObject(rootGsonObject)
        if (mandatoryInfoMap.isNotEmpty()) {
            mandatoryInfoMap.values
                    .mapIndexed { mandatoryIndex, mandatoryField ->
                        FieldSpec.builder(TypeName.INT, mandatoryField.indexVariableName)
                                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                                .initializer("" + mandatoryIndex)
                                .build()
                    }
                    .forEach { adapterTypeBuilder.addField(it) }

            adapterTypeBuilder.addField(FieldSpec.builder(TypeName.INT, "MANDATORY_FIELDS_SIZE")
                    .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                    .initializer("" + mandatoryInfoMap.size)
                    .build())
        }

        adapterTypeBuilder.addMethod(createReadMethod(modelClassName, concreteClassName, mandatoryInfoMap, rootGsonObject))

        if (!isModelInterface) {
            adapterTypeBuilder.addMethod(createWriteMethod(modelClassName, rootGsonObject, properties.serializeNulls))

        } else {
            // Create an empty method for the write, since we do not support writing for interfaces.
            val writeMethod = MethodSpec.methodBuilder("write")
                    .addAnnotation(Override::class.java)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(JsonWriter::class.java, "out")
                    .addParameter(modelClassName, "value")
                    .addException(IOException::class.java)

            adapterTypeBuilder.addMethod(writeMethod.build())
        }

        if (writeFile(adapterClassName.packageName(), adapterTypeBuilder)) {
            return HandleResult(modelClassName, adapterClassName)
        }

        throw ProcessingException("Failed to write generated file: " + adapterClassName.simpleName())
    }

    public override fun onJavaFileBuilt(builder: JavaFile.Builder) {
        builder.addStaticImport(GsonUtil::class.java, "*")
    }

    @Throws(ProcessingException::class)
    private fun getDefaultsAnnotation(autoGsonAnnotation: AutoGsonAdapter): GsonPathDefaultConfiguration? {
        // Annotation processors seem to make obtaining this value difficult!
        var defaultsTypeMirror: TypeMirror? = null

        try {
            autoGsonAnnotation.defaultConfiguration
        } catch (mte: MirroredTypeException) {
            defaultsTypeMirror = mte.typeMirror
        }

        val defaultsElement = processingEnv.typeUtils.asElement(defaultsTypeMirror)

        var defaultsAnnotation: GsonPathDefaultConfiguration? = null
        if (defaultsElement != null) {
            // If an inheritable annotation is used, used the default instead.
            defaultsAnnotation = defaultsElement.getAnnotation(GsonPathDefaultConfiguration::class.java)

            if (defaultsAnnotation == null) {
                throw ProcessingException("Defaults property must point to a class which uses the @GsonPathDefaultConfiguration annotation")
            }
        }

        return defaultsAnnotation
    }
}