package lib;

public enum Role {

    ADMIN("/alluser"),
    CLIENT("/client");

    private String redirectUrl;

    Role(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public static Role getRoleByBooleanValue(Boolean value) {
        return value ? ADMIN : CLIENT;
    }
}
