package demo.spring_tutorial2.domain.constant;

import lombok.Getter;

@Getter
public enum Role {

    USER("일반 유저"),
    MANAGER("관리자");

    private final String description;

    Role(String description) {
        this.description = description;
    }

}
