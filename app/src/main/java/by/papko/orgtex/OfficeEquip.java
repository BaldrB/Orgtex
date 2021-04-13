package by.papko.orgtex;

public class OfficeEquip {
    private String inv, serial, nameequio, gropequimp, additional;

    public OfficeEquip() {}

    public OfficeEquip(String inv, String serial, String nameequio, String gropequimp, String additional) {
        this.inv = inv;
        this.serial = serial;
        this.nameequio = nameequio;
        this.gropequimp = gropequimp;
        this.additional = additional;
    }

    public String getInv() {
        return inv;
    }

    public void setInv(String inv) {
        this.inv = inv;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getNameequio() {
        return nameequio;
    }

    public void setNameequio(String nameequio) {
        this.nameequio = nameequio;
    }

    public String getGropequimp() {
        return gropequimp;
    }

    public void setGropequimp(String gropequimp) {
        this.gropequimp = gropequimp;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
