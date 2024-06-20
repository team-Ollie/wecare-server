package ollie.wecare.common.enums;

public enum Role {
    Challenger, Admin;
    private static final String PREFIX = "ROLE_";
    public String getAuthority(){
        return PREFIX + this.name();
    }
}
