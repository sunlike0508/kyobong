package kyobong.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class EnumResponseDto<T> {

    private T data;


    @Builder
    public EnumResponseDto(T data) {
        this.data = data;
    }


    public static <T> EnumResponseDto<T> of(T data) {
        return new EnumResponseDto<>(data);
    }
}
