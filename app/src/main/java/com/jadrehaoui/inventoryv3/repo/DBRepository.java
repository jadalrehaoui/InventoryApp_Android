package com.jadrehaoui.inventoryv3.repo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBRepository {

    private static SQLiteDatabase readInventoryDB;
    private static SQLiteDatabase writeInventoryDB;
    private static InventoryDatabase inventoryDB;

    private static InventoryDatabase getInventoryDB(Context context) {
        if(inventoryDB == null){
            inventoryDB = new InventoryDatabase(context);
        }
        return inventoryDB;
    }

    public static SQLiteDatabase getReadInventoryDB(Context context) {
        if(readInventoryDB == null){
            readInventoryDB = getInventoryDB(context).getReadableDatabase();
        }
        return readInventoryDB;
    }
    public static SQLiteDatabase getWriteInventoryDB(Context context) {
        if(writeInventoryDB == null) {
            writeInventoryDB = getInventoryDB(context).getWritableDatabase();
        }
        return writeInventoryDB;
    }
}
