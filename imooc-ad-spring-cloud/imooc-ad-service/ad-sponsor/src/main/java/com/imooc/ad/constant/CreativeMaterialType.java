package com.imooc.ad.constant;

import lombok.Getter;


@Getter
public enum CreativeMaterialType {


    JPG(1, "jpg"),
    BMP(2, "bmp"),

    MP4(3, "map4"),
    AVI(4, "avi"),

    TXT(5, "txt");

    private int type;
    private String desc;

    CreativeMaterialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
