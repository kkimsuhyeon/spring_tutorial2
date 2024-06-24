package demo.spring_tutorial2.dto.response;

import demo.spring_tutorial2.dto.domain.MemberDto;

public record ResponseMember(
        Long id
) {

    public static ResponseMember of(Long id) {
        return new ResponseMember(id);
    }

    public static ResponseMember from(MemberDto dto) {
        return ResponseMember.of(dto.id());
    }


}
