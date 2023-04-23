package com.jadrehaoui.inventoryv3.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.jadrehaoui.inventoryv3.model.User;
import com.jadrehaoui.inventoryv3.services.InventoryDBService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Singleton Class
public class UserRepository {

    private static UserRepository repo;
    private String table = "users";
    private static SQLiteDatabase readDb;
    private static SQLiteDatabase writeDb;
    private static User currentUser;


    public static UserRepository getInstance(Context context) {
        if(repo == null) {
            repo = new UserRepository(context);
            currentUser = null;
            readDb = DBRepository.getReadInventoryDB(context);
            writeDb = DBRepository.getWriteInventoryDB(context);
        }
        return repo;
    }

    private UserRepository(Context context){

    }
    // login
    public void login(String username, String password) {
        // compare with database
        String password_hash = md5(password) ;
        String sql = "select * from " + table + " where username = ? and password_hash = ?"; // searches for the hash the password is hidden in the database
        Cursor cursor = readDb.rawQuery(sql, new String[] {username, password_hash}); // executing the query
        if(cursor.moveToFirst()) {
            currentUser = new User();
            currentUser.setId(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
            currentUser.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("username")));
            currentUser.setFullName(cursor.getString(cursor.getColumnIndexOrThrow("full_name")));
//            TODO: Feature missing => Privileges
            currentUser.setPrivilege(cursor.getInt(cursor.getColumnIndexOrThrow("privilege")));
        } else {
            currentUser = null;
        }
        Log.d("UserRepo/login", ""+ currentUser);

    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void register( String fullName, String username, String password) {
        // hash password
        String passwordHash = md5(password);
        // resetting currentUser (in case)
        currentUser = null;
        // check if hash is generated
        if(passwordHash != "") {
            ContentValues userVals = new ContentValues();
            userVals.put("username", username);
            userVals.put("password_hash", passwordHash);
            userVals.put("full_name", fullName);
            userVals.put("privilege", 4); // on register a user is a customer only, then it will be changed to Admin = 1, Manager = 2, Sales = 3
            // adding to db and getting the id back
            long id = writeDb.insert(table, null,userVals);
            Log.d("ID", id+"");
            if(id > 0) {
                // Logging in user after register
                login(username, password);
            }
        }
    }

    private String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


}
