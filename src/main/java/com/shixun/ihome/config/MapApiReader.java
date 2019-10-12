package com.shixun.ihome.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Optional;
import org.apache.ibatis.javassist.*;
import org.apache.ibatis.javassist.bytecode.AnnotationsAttribute;
import org.apache.ibatis.javassist.bytecode.ConstPool;
import org.apache.ibatis.javassist.bytecode.annotation.Annotation;
import org.apache.ibatis.javassist.bytecode.annotation.IntegerMemberValue;
import org.apache.ibatis.javassist.bytecode.annotation.StringMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ResolvedMethodParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;

import java.util.Map;

@Component
@Order
public class MapApiReader implements ParameterBuilderPlugin {
    @Autowired
    private TypeResolver typeResolver;

    @Override
    public void apply(ParameterContext parameterContext) {
        ResolvedMethodParameter methodParameter = parameterContext.resolvedMethodParameter();
        if (methodParameter.getParameterType().canCreateSubtype(Map.class) || methodParameter.getParameterType().canCreateSubtype(String.class)) {
            Optional<ApiJsonObject> optional = methodParameter.findAnnotation(ApiJsonObject.class);
            if (optional.isPresent()) {
                String name = optional.get().name(); //model名称
                ApiJsonProperty[] properties = optional.get().value();

                parameterContext.getDocumentationContext().getAdditionalModels().add(typeResolver.resolve(createRefModel(properties, name)));

                parameterContext.parameterBuilder().parameterType("body").modelRef(new ModelRef(name)).name(name);
            }
        }
    }

    private final static String basePackage = "com.shixun.ihome.publicservice.pojo.";

    private Class createRefModel(ApiJsonProperty[] propertys, String name) {
        ClassPool pool = ClassPool.getDefault();
        CtClass ctClass = pool.makeClass(basePackage + name);

        try {
            for (ApiJsonProperty property : propertys) {
                ctClass.addField(createField(property, ctClass));
            }
            return ctClass.toClass();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private CtField createField(ApiJsonProperty property, CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtField ctField = new CtField(getFieldType(property.type()), property.key(), ctClass);
        ctField.setModifiers(Modifier.PUBLIC);
        ConstPool constPool = ctClass.getClassFile().getConstPool();
        AnnotationsAttribute attr = new AnnotationsAttribute(constPool, AnnotationsAttribute.visibleTag);
        Annotation ann = new Annotation("io.swagger.annotations.ApiModelProperty", constPool);
        ann.addMemberValue("value", new StringMemberValue(property.description(), constPool));
        if (ctField.getType().subclassOf(ClassPool.getDefault().get(String.class.getName())))
            ann.addMemberValue("example", new StringMemberValue(property.example(), constPool));
        if (ctField.getType().subclassOf(ClassPool.getDefault().get(Integer.class.getName())))
            ann.addMemberValue("example", new IntegerMemberValue(Integer.parseInt(property.example()), constPool));
        attr.addAnnotation(ann);
        ctField.getFieldInfo().addAttribute(attr);
        return ctField;
    }

    private CtClass getFieldType(String type) throws NotFoundException {
        CtClass fileType = null;
        switch (type) {
            case "string":
                fileType = ClassPool.getDefault().get(String.class.getName());
                break;
            case "int":
                fileType = ClassPool.getDefault().get(Integer.class.getName());
                break;
        }
        return fileType;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return true;
    }
}
