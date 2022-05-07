package com.korotkov.main.enums;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEnum implements GrantedAuthority {
    //для авторизации использовалась просто копия значения, менять нельзя
    LEVEL_ONE,
    LEVEL_TWO,
    LEVEL_THREE,
    LEVEL_FOUR,
    LEVEL_FIVE;

    @Override
    public String getAuthority() {
        return name();
    }
}
