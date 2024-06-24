package demo.spring_tutorial2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestUpdateMember {

    @NotNull
    private String password;

    private String nickname;
}
