package com.jadrehaoui.inventoryv3.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jadrehaoui.inventoryv3.model.Product;

import java.util.ArrayList;


// Singleton Class
public class ProductRepository {
    private static ProductRepository repo;
    private String table = "products";
    private static SQLiteDatabase writeDB;
    private static SQLiteDatabase readDB;
    
    private ArrayList<Product> products = new ArrayList<Product>();

    public static ProductRepository getInstance(Context context) {
        if(repo == null) {
            repo = new ProductRepository(context);
            readDB = DBRepository.getReadInventoryDB(context);
            writeDB = DBRepository.getReadInventoryDB(context);
        }
        return repo;
    }

    private ProductRepository(Context context) {}
    // This is a heavy function, because it will aliment the list of products from the database
    // instead of running this code every time we need to get the products, we can run it once and then add to it or remove from it on demand.
    // refresh function will only execute this code and only if the products array list size is 0 else it will return the existing alimented list
    private ArrayList<Product> getProducts() {
        // writing the sql
        String sql = "SELECT * FROM "+table;
        // executing the sql
        Cursor cursor = readDB.rawQuery(sql, null);
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            // going one by one creating products objects and adding them to the list
            Product product = new Product();
            product.setId(cursor.getLong(cursor.getColumnIndexOrThrow("_id")));
            product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
            product.setImage(cursor.getString(cursor.getColumnIndexOrThrow("image")));
            product.setPrice(cursor.getFloat(cursor.getColumnIndexOrThrow("price")));
            product.setSku(cursor.getString(cursor.getColumnIndexOrThrow("sku")));
            product.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("title")));
            product.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
            products.add(product);
        }
        return products;
    }


    // when adding a new product, we will execute this function
    public void createNewProduct(String title, String sku, String description, float price, int quantity) {
        // content values makes the code cleaner and more readable.
        ContentValues values = new ContentValues();
        // feeding values got from user
        values.put("title", title);
        values.put("sku", sku);
        values.put("description", description);
        values.put("price", price);
        values.put("quantity", quantity);
        // writing to db
        long id = writeDB.insert(table, null, values);
        // creating a new product in the application
        Product addedProduct = new Product();
        addedProduct.setId(id);
        addedProduct.setQuantity(quantity);
        addedProduct.setTitle(title);
        addedProduct.setSku(sku);
        addedProduct.setPrice(price);
        addedProduct.setDescription(description);
        // adding product to list
        products.add(addedProduct);
    }

    // this will only execute when updating a product, it needs an index => to know where the product is in the array list
    // needs a product, to update the product in the application
    // and the fields to update it in the database
    // TODO: Revisit update function, needs optimization, and make it cleaner
    public void editExistingProduct(int index, Product product, String title, String sku, String description, float price, int quantity) {
        // content values for sql use
        ContentValues values = new ContentValues();
        // feeding new values got from user
        values.put("title", title);
        values.put("sku", sku);
        values.put("description", description);
        values.put("price", price);
        values.put("quantity", quantity);
        // updating product in the db
        writeDB.update(table, values, "_id=?", new String[] {product.getId().toString()});
        // updating product in the application (UI)
        product.setSku(sku);
        product.setDescription(description);
        product.setTitle(title);
        product.setPrice(price);
        product.setQuantity(quantity);
        // updating product in the list
        this.products.set(index, product);
    }


    // very important function
    // it doesn't query the database every time we need to get the products,
    // it only query the db once and then returns the UI list because it will be a copy of the db
    public ArrayList<Product> refreshProducts() {
        if(this.products.size() == 0) {
            return this.getProducts();
        }
        return this.products;
    }
    
}
