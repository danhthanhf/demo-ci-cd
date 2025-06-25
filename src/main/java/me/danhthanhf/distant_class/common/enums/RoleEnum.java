package me.danhthanhf.distant_class.common.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER"),
    MANAGER("MANAGER");

    private final String content;

    RoleEnum(String content) {
        this.content = content;
    }
}
