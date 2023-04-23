package com.jadrehaoui.inventoryv3.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jadrehaoui.inventoryv3.repo.InventoryDatabase;

public class InventoryDBService {
    private InventoryDatabase inventory;
    private SQLiteDatabase db;

    private Context context;

    public InventoryDBService(Context context) {
        this.context = context;
        inventory = new InventoryDatabase(context);
    }

    public SQLiteDatabase getReadableDB() {
        db = inventory.getReadableDatabase();
        return db;
    }

    public SQLiteDatabase getWritableDB() {
        db = inventory.getWritableDatabase();
        return db;
    }



}
