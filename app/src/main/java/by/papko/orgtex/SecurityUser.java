package by.papko.orgtex;

public class SecurityUser {

    private String uid, access;

    public SecurityUser() {};

    public SecurityUser(String uid, String access) {
        this.uid = uid;
        this.access = access;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }
}
