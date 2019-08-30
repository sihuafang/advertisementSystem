package com.imooc.ad.mysql.constant;

import com.github.shyiko.mysql.binlog.event.EventType;


public enum OpType {

    ADD,
    UPDATE,
    DELETE,
    OTHER;

    public static OpType to(EventType eventType) {
        switch (eventType) {
            case EXT_WRITE_ROWS:
            case WRITE_ROWS:
                return ADD;
            case UPDATE_ROWS:
            case EXT_UPDATE_ROWS:
                return UPDATE;
            case DELETE_ROWS:
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }
}
