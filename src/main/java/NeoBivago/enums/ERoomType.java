package NeoBivago.enums;

public enum ERoomType {
    STANDARD("STANDARD"),
    FAMILY("FAMILY"),
    DELUXE("DELUXE"),
    SUPERDELUXE("SUPERDELUXE");

    private final String role;

    ERoomType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
