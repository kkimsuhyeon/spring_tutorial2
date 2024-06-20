package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Member;

public record MemberDto(Long id, String email, String password, String nickname, String memo) {

    public static MemberDto of(Long id, String email, String password, String nickname, String memo) {
        return new MemberDto(id, email, password, nickname, memo);
    }

    public static MemberDto from(Member member) {
        return MemberDto.of(
                member.getId(),
                member.getEmail(),
                member.getPassword(),
                member.getNickname(),
                member.getMemo());
    }

    public Member toEntity() {
        return Member.of(email, password, nickname, memo);
    }

}
