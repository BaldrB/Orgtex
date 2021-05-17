package by.papko.orgtex;

public class SecurityUser {

    private String uid, access;
    private String fullName;
    private String eMail;

    public SecurityUser() {};

    public SecurityUser(String uid, String access, String eMail, String fullName) {
        this.uid = uid;
        this.access = access;
        this.fullName = fullName;
        this.eMail = eMail;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
