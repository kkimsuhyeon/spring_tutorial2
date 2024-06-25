package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.security.TokenProvider;
import demo.spring_tutorial2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenApiController {

    private final MemberService memberService;

    @GetMapping(value = "/create-token")
    public ResponseEntity<?> createToken() {
        MemberDto member = memberService.getMember(1L);

        String token = TokenProvider.createToken(
                member.toEntity().getId(),
                member.toEntity().getEmail(),
                member.toEntity().getRoles(),
                TokenProvider.ACCESS_TOKEN_EXPIRE_TIME);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/create-manager-token")
    public ResponseEntity<?> createManagerToken() {
        MemberDto member = memberService.getMember(2L);

        String token = TokenProvider.createToken(
                member.toEntity().getId(),
                member.toEntity().getEmail(),
                member.toEntity().getRoles(),
                TokenProvider.ACCESS_TOKEN_EXPIRE_TIME);


        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/create-user-token")
    public ResponseEntity<?> createUserToken() {
        MemberDto member = memberService.getMember(3L);

        String token = TokenProvider.createToken(
                member.toEntity().getId(),
                member.toEntity().getEmail(),
                member.toEntity().getRoles(),
                TokenProvider.ACCESS_TOKEN_EXPIRE_TIME);


        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @GetMapping(value = "/check-token")
    public ResponseEntity<?> checkToken(Principal principal) {

        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> checkUser(Principal principal) {
        return new ResponseEntity<>("user", HttpStatus.OK);
    }

    @GetMapping(value = "/manager")
    public ResponseEntity<?> checkManager(Principal principal) {
        return new ResponseEntity<>("manager", HttpStatus.OK);
    }
}
