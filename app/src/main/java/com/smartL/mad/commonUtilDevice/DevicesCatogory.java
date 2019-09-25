package com.smartL.mad.commonUtilDevice;

public class DevicesCatogory {


    public Boolean isRgb(String id){ return    (id.startsWith("S!-RGB")); }
    public  Boolean isPlug(String id){
        return    (id.startsWith("S!-PLG"));
    }
    public  Boolean isnBulb(String id){
        return    (id.startsWith("S!-BLB"));
    }

}
