package Model;

import java.time.LocalDateTime;

public class Computer extends Equipment{
    private String gpuType;
    private String purchasePrice;

    public Computer(int equipmentId, String type, String modelNumber, String serialNumber, String location, LocalDateTime entryDateTime, int userId, String gpuType, String purchasePrice) {
        super(equipmentId, type, modelNumber, serialNumber, location, entryDateTime, userId);
        this.gpuType = gpuType;
        this.purchasePrice = purchasePrice;
    }

    public String getGpuType() {
        return gpuType;
    }

    public void setGpuType(String gpuType) {
        this.gpuType = gpuType;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
