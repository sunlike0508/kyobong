package kyobong.enums;

import java.io.IOException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kyobong.config.RestDocsConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.MediaType;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureRestDocs
@ImportAutoConfiguration(RestDocsConfiguration.class)
@WebMvcTest(controllers = EnumController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {Converter.class})})
class EnumDocControllerTest {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected RestDocumentationResultHandler restDocs;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getRentalStatusEnum() throws Exception {
        ResultActions result =
                this.mockMvc.perform(get("/enums/rental-status").contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andReturn();

        EnumDocs enumDocs = this.getEnumDocsData(mvcResult);

        result.andExpect(status().isOk()).andDo(restDocs.document(
                CustomResponseFieldsSnippet.customResponseFields("custom-response",
                        beneathPath("data.rentalStatus").withSubsectionId("rental-status"),
                        CustomResponseFieldsSnippet.enumConvertFieldDescriptor(enumDocs.getRentalStatus()))));
    }


    private EnumDocs getEnumDocsData(MvcResult result) throws IOException {
        EnumResponseDto<EnumDocs> apiResponseDto =
                objectMapper.readValue(result.getResponse().getContentAsByteArray(), new TypeReference<>() {});
        return apiResponseDto.getData();
    }

}