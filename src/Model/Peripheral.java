package Model;

import java.time.LocalDateTime;

public class Peripheral extends Equipment {
    private String peripheralType;
    private String condition;

    public Peripheral(int equipmentId, String type, String modelNumber, String serialNumber, String location, LocalDateTime entryDateTime, int userId, String peripheralType, String condition) {
        super(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId);
        this.peripheralType = peripheralType;
        this.condition = condition;
    }

    public String getPeripheralType() {
        return peripheralType;
    }

    public void setPeripheralType(String peripheralType) {
        this.peripheralType = peripheralType;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
