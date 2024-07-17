package kyobong.controller.document;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import org.springframework.test.web.servlet.ResultHandler;

public class KyobongBookControllerDocument {

    public static ResultHandler getBookList() {
        return document("get-book-list", queryParameters(parameterWithName("author").description("지은이").optional(),
                        parameterWithName("title").description("제목").optional()),
                responseFields(fieldWithPath("[].id").description("책 ID"), fieldWithPath("[].title").description("제목"),
                        fieldWithPath("[].author").description("지은이"),
                        fieldWithPath("[].rentable").description("대여 가능 여부"),
                        subsectionWithPath("[].categoryList").description("<<category-list, 카테고리>> 리스트")),
                responseFields(beneathPath("[].categoryList").withSubsectionId("category-list"),
                        fieldWithPath("[].id").description("카테고리 ID"),
                        fieldWithPath("[].name").description("카테고리 이름")));
    }


    public static ResultHandler enrollBook() {
        return document("enroll-book",
                requestFields(fieldWithPath("title").description("제목"), fieldWithPath("author").description("지은이"),
                        fieldWithPath("categoryList").description("카테고리 리스트")),
                responseFields(fieldWithPath("id").description("책 ID"), fieldWithPath("title").description("제목"),
                        fieldWithPath("author").description("지은이"), fieldWithPath("rentable").description("대여 가능 여부"),
                        subsectionWithPath("categoryList").description("<<enroll-category-list, 카테고리>> 리스트")),
                responseFields(beneathPath("categoryList").withSubsectionId("category-list"),
                        fieldWithPath("id").description("카테고리 ID"), fieldWithPath("name").description("카테고리 이름")));
    }


    public static ResultHandler updateBook() {
        return document("update-book", pathParameters(parameterWithName("id").description("책 ID")),
                requestFields(fieldWithPath("title").description("제목").optional(),
                        fieldWithPath("author").description("지은이").optional(),
                        fieldWithPath("categoryList").description("카테고리 리스트").optional(),
                        fieldWithPath("isRentable").description("대여 가능 여부").optional()),
                responseFields(fieldWithPath("id").description("책 ID"), fieldWithPath("title").description("제목"),
                        fieldWithPath("author").description("지은이"), fieldWithPath("rentable").description("대여 가능 여부"),
                        subsectionWithPath("categoryList").description("<<update-category-list, 카테고리>> 리스트")),
                responseFields(beneathPath("categoryList").withSubsectionId("category-list"),
                        fieldWithPath("id").description("카테고리 ID"), fieldWithPath("name").description("카테고리 이름")));
    }


    public static ResultHandler enrollCategory() {
        return document("enroll-category", requestFields(fieldWithPath("name").description("카테고리 이름")),
                responseFields(fieldWithPath("id").description("카테고리 ID"),
                        fieldWithPath("name").description("카테고리 이름")));
    }


    public static ResultHandler getBookListByCategory() {
        return document("get-book-list-by-category", pathParameters(parameterWithName("id").description("카테고리 ID")),
                responseFields(fieldWithPath("[].id").description("책 ID"), fieldWithPath("[].title").description("제목"),
                        fieldWithPath("[].author").description("지은이"),
                        fieldWithPath("[].rentable").description("대여 가능 여부"),
                        subsectionWithPath("[].categoryList").description(
                                "<<get-book-by-category-category-list, 카테고리>> 리스트")),
                responseFields(beneathPath("[].categoryList").withSubsectionId("category-list"),
                        fieldWithPath("[].id").description("카테고리 ID"),
                        fieldWithPath("[].name").description("카테고리 이름")));
    }
}
