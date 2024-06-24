package demo.spring_tutorial2.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RequestCreateMember {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @Nullable
    private String nickname;
}
