package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Member;
import lombok.Builder;

@Builder
public record MemberDto(Long id, String email, String password, String nickname, String memo) {

    public static MemberDto of(Long id, String email, String password, String nickname, String memo) {
        return new MemberDto(id, email, password, nickname, memo);
    }

    public static MemberDto of(String email, String password, String nickname, String memo) {
        return new MemberDto(null, email, password, nickname, memo);
    }

    public static MemberDto fromEntity(Member entity) {
        return MemberDto.of(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getMemo());
    }

    public Member toEntity() {
        return Member.of(email, password, nickname, memo);
    }

}
