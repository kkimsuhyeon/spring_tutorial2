package demo.spring_tutorial2.controller.api;

import demo.spring_tutorial2.domain.constant.MemberSearchType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    @GetMapping
    public ResponseEntity<?> members(
            @RequestParam MemberSearchType searchType,
            @RequestParam String searchValue,
            @PageableDefault(size = 10, page = 1, sort = "nickname", direction = Sort.Direction.DESC) Pageable pageable) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
