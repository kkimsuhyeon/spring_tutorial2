package demo.spring_tutorial2.dto.domain;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.MemberRole;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
public record MemberDto(Long id, String email, String password, String nickname, String memo, Set<MemberRole> roles) {

    public static MemberDto of(Long id, String email, String password, String nickname, String memo, Set<MemberRole> roles) {
        return new MemberDto(id, email, password, nickname, memo, roles);
    }

    public static MemberDto of(String email, String password, String nickname, String memo, Set<MemberRole> roles) {
        return new MemberDto(null, email, password, nickname, memo, roles);
    }

    public static MemberDto fromEntity(Member entity) {
        return MemberDto.of(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getRoles());
    }

    public Member toEntity() {
        return Member.of(email, password, nickname, memo);
    }

}
