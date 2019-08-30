package com.imooc.ad.mysql.listener;

import com.imooc.ad.mysql.dto.BinlogRowData;

public interface IListener {

    void register();

    void onEvent(BinlogRowData eventData);
}
