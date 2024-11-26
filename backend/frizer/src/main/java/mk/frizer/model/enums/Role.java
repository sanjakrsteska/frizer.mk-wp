package mk.frizer.model.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_EMPLOYEE,
    ROLE_OWNER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
