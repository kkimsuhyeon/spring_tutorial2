package demo.spring_tutorial3.controller;

import demo.spring_tutorial3.domain.Member;
import demo.spring_tutorial3.service.MemberService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping(value = "/api/members", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<CreateMemberResponse> saveMember(@ModelAttribute @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new ResponseEntity<>(new CreateMemberResponse(id), HttpStatus.OK);
    }

    @PutMapping(value = "/api/members/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<UpdateMemberResponse> updateMember(@PathVariable("id") Long id, @ModelAttribute @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());

        Member member = memberService.findOne(id);
        return new ResponseEntity<>(new UpdateMemberResponse(id, member.getName()), HttpStatus.OK);
    }

    @GetMapping(value = "/api/members")
    public ResponseEntity<Result<List<MemberDto>>> members() {
        List<MemberDto> members = memberService.findMembers().stream().map((member) -> {
            return new MemberDto(member.getName());
        }).collect(Collectors.toList());

        return new ResponseEntity<>(new Result<>(members.size(), members), HttpStatus.OK);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }


    @Data
    @AllArgsConstructor
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }
}
