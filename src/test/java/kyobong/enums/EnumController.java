package kyobong.enums;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import kyobong.config.EnumType;
import kyobong.persistence.RentalStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enums")
public class EnumController {

    @GetMapping("/rental-status")
    public EnumResponseDto<EnumDocs> getCategoryEnum() {

        Map<String, String> rentalStatus = Arrays.stream(RentalStatus.values())
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));


        return EnumResponseDto.of(EnumDocs.builder().rentalStatus(rentalStatus).build());
    }
}
