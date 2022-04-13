package Model;

import java.time.LocalDateTime;

public class Equipment {
    private int equipmentId;
    private String type;
    private String modelNumber;
    private String serialNumber;
    private String location;
    private LocalDateTime entryDateTime;
    private int userId;

    public Equipment(int equipmentId, String type, String modelNumber, String serialNumber, String location, LocalDateTime entryDateTime, int userId) {
        this.equipmentId = equipmentId;
        this.type = type;
        this.modelNumber = modelNumber;
        this.serialNumber = serialNumber;
        this.location = location;
        this.entryDateTime = entryDateTime;
        this.userId = userId;
    }

    @Override
    public String toString(){
        return ("Equipment - Model: " + modelNumber + " Serial: " + serialNumber);
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(LocalDateTime entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
