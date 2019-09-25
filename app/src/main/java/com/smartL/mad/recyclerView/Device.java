package com.smartL.mad.recyclerView;

public class Device {

    private long id;
    private String deviceId;
    private String deviceName;
    private String nickName;
    private String image;
    private boolean switchState;

    public Device() {

    }

    public Device(String deviceId, String deviceName, String nickName, String image) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.nickName = nickName;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isSwitchState() {
        return switchState;
    }

    public void setSwitchState(boolean switchState) {
        this.switchState = switchState;
    }

}
