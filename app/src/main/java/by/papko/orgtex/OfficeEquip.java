package by.papko.orgtex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OfficeEquip implements Serializable {
    private String id, inv, serial, nameequio, gropequimp, additional;
    private List<String> repairPartsId = new ArrayList<String>();

    public OfficeEquip() {}

    public OfficeEquip(String id, String inv, String serial, String nameequio, String gropequimp, String additional) {
        this.id = id;
        this.inv = inv;
        this.serial = serial;
        this.nameequio = nameequio;
        this.gropequimp = gropequimp;
        this.additional = additional;

    }
    public OfficeEquip(String id, String inv, String serial, String nameequio, String gropequimp, String additional, ArrayList<String> repairPartsId) {
        this.id = id;
        this.inv = inv;
        this.serial = serial;
        this.nameequio = nameequio;
        this.gropequimp = gropequimp;
        this.additional = additional;
        this.repairPartsId.addAll(repairPartsId);

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getRepairPartsId() {
        return repairPartsId;
    }

    public void setRepairPartsId(List<String> repairPartsId) {
        this.repairPartsId = repairPartsId;
    }

    public void addRepairPartsId(String id) {
        repairPartsId.add(id);
    }
}
