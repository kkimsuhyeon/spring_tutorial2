package demo.spring_tutorial2.domain.constant;

import lombok.Getter;

public enum MemberSearchType {
    EMAIL("이메일"),
    NICKNAME("닉네임"),
    MEMO("메모");

    @Getter
    private final String description;

    MemberSearchType(String description) {
        this.description = description;
    }
}
