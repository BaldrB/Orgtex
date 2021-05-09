package by.papko.orgtex;

import java.io.Serializable;

public class RepairParts implements Serializable {
    private String id, name, serial, dop;
    private int quantity = 0;

    RepairParts () {}

    RepairParts(String id, String name, String serial, String dop, int quantity) {
        this.id = id;
        this.name = name;
        this.serial = serial;
        this.dop = dop;
        this.quantity = quantity;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDop() {
        return dop;
    }

    public void setDop(String dop) {
        this.dop = dop;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
