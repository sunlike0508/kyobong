package kyobong.config;


import lombok.Builder;
import lombok.Value;

@Value
class ErrorResponse {

    String message;


    @Builder
    private ErrorResponse(String message) {
        this.message = message;
    }

}