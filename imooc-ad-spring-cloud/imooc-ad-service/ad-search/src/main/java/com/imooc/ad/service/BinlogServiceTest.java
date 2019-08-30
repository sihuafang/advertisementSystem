package com.imooc.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;

import java.io.IOException;


public class BinlogServiceTest {

    /**
     * insert into ad_unit_keyword(unit_id, keyword) values (1,"标志");
     * update ad_unit_keyword set keyword = "标志2" where unit_id = 1;
     * delete from ad_unit_keyword where unit_id = 1;
     * <p>
     * <p>
     * Write-----------
     * WriteRowsEventData{tableId=124, includedColumns={0, 1, 2}, rows=[[2, 1, 标志]]}
     * <p>
     * Update-----------
     * UpdateRowsEventData{tableId=124, includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[{before=[2, 1, 标志], after=[2, 1, 标志2]}]}
     * <p>
     * Delete-----------
     * DeleteRowsEventData{tableId=124, includedColumns={0, 1, 2}, rows=[[2, 1, 标志2]]}
     * <p>
     * <p>
     * select info.TABLE_SCHEMA, info.TABLE_NAME, info.COLUMN_NAME, info.ORDINAL_POSITION
     * from information_schema.`COLUMNS` info
     * where info.TABLE_SCHEMA = "imooc_ad_data" and info.TABLE_NAME = "ad_unit_keyword";
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BinaryLogClient client = new BinaryLogClient("127.0.0.1", 3306, "root", "123456");

        client.registerEventListener(event -> {
            EventData data = event.getData();
            if (data instanceof UpdateRowsEventData) {
                System.out.println("Update-----------");
                System.out.println(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                System.out.println("Write-----------");
                System.out.println(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                System.out.println("Delete-----------");
                System.out.println(data.toString());
            }
        });

        client.connect();
    }


}
