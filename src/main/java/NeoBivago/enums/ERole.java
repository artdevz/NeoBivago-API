package NeoBivago.enums;

public enum ERole {
    USER("USER"),
    MOD("MOD"),
    ADMIN("ADMIN");

    private final String role;

    ERole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
