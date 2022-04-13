package Model;

import java.time.LocalDateTime;

public class ViewingDevice extends Equipment{
    private String screenSize;
    private String inputType;

    public ViewingDevice(int equipmentId, String type, String modelNumber, String serialNumber, String location, LocalDateTime entryDateTime, int userId, String screenSize, String inputType) {
        super(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId);
        this.screenSize = screenSize;
        this.inputType = inputType;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}
