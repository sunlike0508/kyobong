package kyobong.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

public class CustomResponseFieldsSnippet extends AbstractFieldsSnippet {


    private CustomResponseFieldsSnippet(String type, PayloadSubsectionExtractor<?> subsectionExtractor,
            List<FieldDescriptor> descriptors, boolean ignoreUndocumentedFields) {
        super(type, descriptors, null, ignoreUndocumentedFields, subsectionExtractor);
    }


    public static CustomResponseFieldsSnippet customResponseFields(String type,
            PayloadSubsectionExtractor<?> subsectionExtractor, FieldDescriptor... descriptors) {
        return new CustomResponseFieldsSnippet(type, subsectionExtractor, Arrays.asList(descriptors), true);
    }


    public static FieldDescriptor[] enumConvertFieldDescriptor(Map<String, String> enumValues) {
        return enumValues.entrySet().stream().map(x -> fieldWithPath(x.getKey()).description(x.getValue()))
                .toArray(FieldDescriptor[]::new);
    }


    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }


    @Override
    protected byte[] getContent(Operation operation) {
        return operation.getResponse().getContent();
    }
}
