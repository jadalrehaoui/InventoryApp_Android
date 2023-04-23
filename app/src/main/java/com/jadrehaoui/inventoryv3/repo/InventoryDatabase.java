package com.jadrehaoui.inventoryv3.repo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// Singleton Class
public class InventoryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    public InventoryDatabase(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    private static final class ProductTable {
        private static final String TABLE = "products";
        private static final String COL_ID = "_id";
        private static final String COL_TITLE = "title";
        private static final String COL_SKU = "sku";
        private static final String COL_QUANTITY = "quantity";
        private static final String COL_PRICE = "price";
        private static final String COL_IMAGE = "image";
        private static final String COL_DESCRIPTION = "description";
    }


    private static final class UserTable {
        private static final String TABLE = "users";
        private static final String COL_ID = "_id";
        private static final String COL_USERNAME = "username";
        private static final String COL_FULL_NAME = "full_name";
        private static final String COL_PASSWORD_HASH = "password_hash";
        private static final String COL_PRIVILEGE = "privilege";
    }

    private static final class PrivilegeTable {
        private static final String TABLE = "privileges";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + ProductTable.TABLE + " (" +
                ProductTable.COL_ID + " integer primary key autoincrement, " +
                ProductTable.COL_TITLE + " text, " +
                ProductTable.COL_SKU + " text, " +
                ProductTable.COL_IMAGE + " text, " +
                ProductTable.COL_PRICE + " float, " +
                ProductTable.COL_DESCRIPTION + " text, " +
                ProductTable.COL_QUANTITY + " integer)");

        db.execSQL(
                "INSERT INTO "+ProductTable.TABLE+
                        " ("+ProductTable.COL_TITLE+", "+ProductTable.COL_SKU+
                        ", "+ProductTable.COL_PRICE+", "+ProductTable.COL_DESCRIPTION+
                        ", "+ProductTable.COL_QUANTITY+", "+ProductTable.COL_IMAGE+") " +
                        "VALUES (\"ANTONIO BANDERAS BLUE SEDUCTION\", \"ANTOBAN0013\", 10.99, \"This seductive perfume arrives in a transparent, azure bottle, echoing the shape of the edition for men. The fragrance opens with fresh, aquatic aromas, with juices of fruit and violet.\", 123, \"https://images.barcodelookup.com/1035/10356480-1.jpg\"), " +
                        "(\"ARIANA GRANDE SWEET LIKE CANDY EDP\", \"ARIAGRN0002\", 15.99, \"Eau De Parfum Spray 100 ML : Sweet Like Candy - Ariana Grande Eau De Parfum Spray 100 ML - A lot of perfumes and fragrances for Womens of the Brand Ariana Grande are available on Sobelia.\", 24, \"https://images.barcodelookup.com/331/3313732-1.jpg\"), " +
                        "(\"ARMAF CLUB DE NUIT INTENSE\", \"ARMAF0012\", 12.39, \"Eau De Toilette Spray 105 ML : Club De Nuit Intense - Armaf Eau De Toilette Spray 105 ML - A lot of perfumes and fragrances for Mens of the Brand Armaf are available on Sobelia.\", 36, \"https://images.barcodelookup.com/1035/10356399-1.jpg\"), " +
                        "(\"AZZARO SILVER BLACK\", \"AZZARO0005\", 14.32, \"Perfume azzaro silver black pour homme eau de toilette masculino, trazendo nesta composi√ß√£o um audacioso frescor alde√≠do, verde, frutal. possui uma nota de base a de fundo.\", 16, \"https://images.barcodelookup.com/22699/226996217-1.jpg\"), "+
                        "(\"BVLGARI OMNIA EDP\", \"BLGAR0005\", 55.25, \"Eau De Parfum Spray 65 ML : Omnia - Bvlgari Eau De Parfum Spray 65 ML - A lot of perfumes and fragrances for Womens of the Brand Bvlgari are available on Sobelia.\", 23, \"https://images.barcodelookup.com/1036/10360356-1.jpg\"), "+
                        "(\"BRITNEY SPEARS FANTASY EDP\", \"BLGAR0005\", 55.25, \"Eau De Parfum Spray 65 ML : Omnia - Bvlgari Eau De Parfum Spray 65 ML - A lot of perfumes and fragrances for Womens of the Brand Bvlgari are available on Sobelia.\", 93, \"https://images.barcodelookup.com/1036/10360356-1.jpg\"), "+
                        "(\"CACHAREL AMOR AMOR\", \"CACHA0005\", 32.73, \"Eau De Toilette Spray 100 ML : Amor Amor - Cacharel Eau De Toilette Spray 100 ML - A lot of perfumes and fragrances for Womens of the Brand Cacharel are available on Sobelia.\", 3, \"https://images.barcodelookup.com/154/1545768-1.jpg\"), "+
                        "(\"CALVIN KLEIN CK ONE\", \"CALKLE0002\",18.31, \"Featuring light, crisp notes of bergamot, cardamom, fresh pineapple, rose, and amber, this CK One by Calvin Klein offers an alluring fragrance for both men and women. Bottom notes of musk and amber round out the fragrance with sophistication.\", 231, \"https://images.barcodelookup.com/331/3312069-1.jpg\"), "+
                        "(\"CARTIER SANTOS\", \"CART0002\", 72.04, \"Eau De Toilette Spray 100 ML : Santos - Cartier Eau De Toilette Spray 100 ML - A lot of perfumes and fragrances for Mens of the Brand Cartier are available on Sobelia.\", 42, \"https://images.barcodelookup.com/154/1545785-1.jpg\"), "+
                        "(\"DIOR JADORE EDP\", \"CDIOR0010\", 105.32, \"J'adore Eau de Toilette is a composition bathed in light. Neroli, one of the most beautiful light-filled flowers grown near Vallauris.\", 6, \"https://images.barcodelookup.com/1035/10359076-1.jpg\"), "+
                        "(\"DOLCE GABBANA LIGHT BLUE\", \"DLCGBNA0031\", 32.73, \"Light Blue Cologne by Dolce & Gabbana, It starts with sicilian mandarin combined with frozen grapefruit peel, bergamot and juniper. Heart notes of rosemary, szechuan pepper and rosewood, and the mix is rounded out with base notes of musk wood, incense and oak moss.\", 14, \"https://images.barcodelookup.com/331/3311137-1.jpg\"), "+
                        "(\"ELIZABETH ARDEN RED DOOR\", \"ELIZARD0004\", 8.98, \"Eau De Toilette Spray 100 ML : Red Door - Elizabeth Arden Eau De Toilette Spray 100 ML - A lot of perfumes and fragrances for Womens of the Brand Elizabeth Arden are available on Sobelia.\", 3, \"https://images.barcodelookup.com/154/1544482-1.jpg\"), "+
                        "(\"GUCCI GUILTY\", \"GUCCI0001\", 122.32, \"Eau De Toilette Spray 90 ml : Gucci Guilty Cologne - Gucci Eau De Toilette Spray 90 ml - A lot of perfumes and fragrances for Mens of the Brand Gucci are available on Sobelia.\", 13, \"https://images.barcodelookup.com/11849/118492932-1.jpg\"), "+
                        "(\"GUESS GOLD EDP\", \"GUESS0012\", 32.73, \"Eau De Parfum Spray 75 ML : Guess Gold - Guess Eau De Parfum Spray 75 ML - A lot of perfumes and fragrances for Womens of the Brand Guess are available on Sobelia.\", 223, \"https://images.barcodelookup.com/15192/151921687-1.jpg\"), "+
                        "(\"HALLOWEEN BLOSSOM  \", \"HALWN0032\", 19.32, \"Halloween Blossom.\", 42, \"https://images.barcodelookup.com/50175/501758018-1.jpg\"), "+
                        "(\"HUGO BOSS NUIT EDP\", \"BOSS0031\", 28.48, \"Eau De Parfum Spray 75 ML : Boss Nuit Pour Femme - Hugo Boss Eau De Parfum Spray 75 ML - A lot of perfumes and fragrances for Womens of the Brand Hugo Boss are available on Sobelia.\", 58, \"https://images.barcodelookup.com/1121/11217793-1.jpg\"), "+
                        "(\"JIMMY CHOO EDP\", \"CHOO0005\", 42.21, \"Eau De Parfum Spray 100 ML : Jimmy Choo - Jimmy Choo Eau De Parfum Spray 100 ML - A lot of perfumes and fragrances for Womens of the Brand Jimmy Choo are available on Sobelia.\", 8, \"https://images.barcodelookup.com/230/2306055-1.jpg\"), "+
                        "(\"LACOSTE BLANC\", \"LACOS0022\", 29.99, \"Lacoste L.12.12 Blanc 3.3 / 3.4 oz 100 ml Spray Men Fragrance.\", 312, \"https://images.barcodelookup.com/513/5139441-1.jpg\"), "+
                        "(\"NAUTICA VOYAGE\", \"NAUTI0021\", 19.32, \"Fragrance was launched by the design house of Nautica, Nautica Voyage men's fragrance is great for any occasion, Men's scent possesses a blend of cool green leaf, fresh cut apple, deep aquatic elements, sailcloth accord and others.\", 1231, \"https://images.barcodelookup.com/331/3310116-1.jpg\"), "+
                        "(\"BENETTON SISTERLAND GREEN JASMIN\", \"BNTTN0029\", 13.99, \"Fragrance was launched by the design house of Britney Spears in 2005, Fantasy Britney Spears possesses a blend of kiwi, white chocolate, musk, lychee, jasmine, orchid, and quince, Women's perfume is recommended for romantic wear.\", 12, \"https://images.barcodelookup.com/1035/10355320-1.jpg\")"
                );

        db.execSQL("create table " + PrivilegeTable.TABLE + " (" +
                PrivilegeTable.COL_ID + " integer primary key autoincrement, " +
                PrivilegeTable.COL_NAME + " text)" );

        db.execSQL("INSERT INTO "+PrivilegeTable.TABLE+" ("+PrivilegeTable.COL_NAME+") VALUES (\"Admin\")");
        db.execSQL("INSERT INTO "+PrivilegeTable.TABLE+" ("+PrivilegeTable.COL_NAME+") VALUES (\"Manager\")");
        db.execSQL("INSERT INTO "+PrivilegeTable.TABLE+" ("+PrivilegeTable.COL_NAME+") VALUES (\"Sales\")");
        db.execSQL("INSERT INTO "+PrivilegeTable.TABLE+" ("+PrivilegeTable.COL_NAME+") VALUES (\"Customer\")");

        db.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " integer primary key autoincrement, " +
                UserTable.COL_USERNAME + " text unique, " +
                UserTable.COL_FULL_NAME + " text, " +
                UserTable.COL_PASSWORD_HASH + " text, " +
                UserTable.COL_PRIVILEGE + " text," +
                "FOREIGN KEY("+UserTable.COL_PRIVILEGE+") references "+ PrivilegeTable.TABLE+"(" +PrivilegeTable.COL_ID+ ") )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + ProductTable.TABLE);
        db.execSQL("drop table if exists " + UserTable.TABLE);
        db.execSQL("drop table if exists " + PrivilegeTable.TABLE);
        onCreate(db);
    }



}
