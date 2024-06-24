package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.domain.constant.MemberSearchType;
import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.dto.request.RequestCreateMember;
import demo.spring_tutorial2.dto.request.RequestUpdateMember;
import demo.spring_tutorial2.dto.response.ResponseList;
import demo.spring_tutorial2.dto.response.ResponseMember;
import demo.spring_tutorial2.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseList<MemberDto>> members(
            @RequestParam(required = false) MemberSearchType searchType,
            @RequestParam(required = false) String searchValue,
            @PageableDefault(size = 10, page = 1, sort = "nickname", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MemberDto> members = memberService.searchMember(searchType, searchValue, pageable);


        ResponseList<MemberDto> result = ResponseList.of(
                members.getTotalElements(),
                members.getTotalPages(),
                pageable.getSort(),
                members.getContent());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/{memberId}")
    public ResponseEntity<?> member(@PathVariable(value = "memberId") Long memberId) {
        MemberDto memberDto = memberService.getMember(memberId);
        return new ResponseEntity<>(ResponseMember.from(memberDto), HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> createMember(@ModelAttribute @Valid RequestCreateMember request) {

        MemberDto dto = MemberDto.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .build();

        memberService.save(dto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{memberId}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> updateMember(@PathVariable(value = "memberId") Long memberId, @ModelAttribute RequestUpdateMember request) {
        MemberDto dto = MemberDto.builder().password(request.getPassword()).nickname(request.getNickname()).build();
        memberService.update(memberId, dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{memberId}")
    public ResponseEntity<?> deleteMember(@PathVariable(value = "memberId") Long memberId) {
        memberService.delete(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
