package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.domain.constant.MemberSearchType;
import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.dto.response.ResponseList;
import demo.spring_tutorial2.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private MemberService memberService;

    @GetMapping
    public ResponseEntity<ResponseList<MemberDto>> members(
            @RequestParam MemberSearchType searchType,
            @RequestParam String searchValue,
            @PageableDefault(size = 10, page = 1, sort = "nickname", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MemberDto> members = memberService.searchMember(searchType, searchValue, pageable);
        ResponseList<MemberDto> result = ResponseList.of(
                members.getTotalElements(),
                members.getTotalPages(),
                pageable.getSort(),
                members.getContent());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
