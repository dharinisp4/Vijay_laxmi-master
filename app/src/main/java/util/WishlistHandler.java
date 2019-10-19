package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class WishlistHandler extends SQLiteOpenHelper {
    private static String DB_NAME = "dbwi";
    private static int DB_VERSION = 3;
    private SQLiteDatabase db;

    public static final String WISHTABLE_TABLE = "wishlist1";
    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_IMAGE = "product_image";
    public static final String COLUMN_CAT_ID = "cat_id";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_MRP = "mrp";
    public static final String COLUMN_UNIT_PRICE = "unit_price";
    public static final String COLUMN_UNIT_VALUE = "unit_value";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_DESC = "product_description";
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_PRODUCT_ATTRIBUTE = "product_attribute";
    public static final String COLUMN_REWARDS = "rewards";
    public static final String COLUMN_INCREMENT = "increment";
    public static final String COLUMN_TITLE = "title";
    public WishlistHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String exe = "CREATE TABLE IF NOT EXISTS " + WISHTABLE_TABLE
                + "(" + COLUMN_ID + " integer primary key, "
//                + COLUMN_QTY + " DOUBLE NOT NULL,"
                + COLUMN_IMAGE + " TEXT NOT NULL, "
                + COLUMN_CAT_ID + " TEXT NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_PRICE + " DOUBLE NOT NULL, "
                + COLUMN_MRP + " DOUBLE NOT NULL, "
                + COLUMN_UNIT_PRICE + " DOUBLE NOT NULL, "
                + COLUMN_UNIT + " TEXT NOT NULL, "
                + COLUMN_UNIT_VALUE + " TEXT NOT NULL, "
                + COLUMN_STOCK + " TEXT NOT NULL, "
                + COLUMN_PRODUCT_ATTRIBUTE + " TEXT NOT NULL, "
                + COLUMN_REWARDS + " TEXT NOT NULL, "
                + COLUMN_INCREMENT + " TEXT NOT NULL, "
                + COLUMN_TITLE + " TEXT NOT NULL, "
                + COLUMN_DESC + " TEXT NOT NULL "



                + ")";

        db.execSQL(exe);


    }
    public boolean setwishTable(HashMap<String, String> map) {
        db = getWritableDatabase();
        if (isInWishtable(map.get(COLUMN_ID))) {
            //db.execSQL("update " + WISHTABLE_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "' where " + COLUMN_ID + "=" + map.get(COLUMN_ID));
            return false;
        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, map.get(COLUMN_ID));
          //  values.put(COLUMN_QTY, Qty);
            values.put(COLUMN_CAT_ID, map.get(COLUMN_CAT_ID));
            values.put(COLUMN_IMAGE, map.get(COLUMN_IMAGE));
            values.put(COLUMN_NAME, map.get(COLUMN_NAME));
            values.put(COLUMN_PRICE, map.get(COLUMN_PRICE));
            values.put(COLUMN_MRP, map.get(COLUMN_MRP));
            values.put(COLUMN_UNIT_PRICE, map.get(COLUMN_UNIT_PRICE));
            values.put(COLUMN_UNIT, map.get(COLUMN_UNIT));
            values.put(COLUMN_DESC, map.get(COLUMN_DESC));
            values.put(COLUMN_UNIT_VALUE, map.get(COLUMN_UNIT_VALUE));
            values.put(COLUMN_STOCK, map.get(COLUMN_STOCK));
            values.put(COLUMN_PRODUCT_ATTRIBUTE, map.get(COLUMN_PRODUCT_ATTRIBUTE));
            values.put(COLUMN_REWARDS, map.get(COLUMN_REWARDS));
            values.put(COLUMN_INCREMENT, map.get(COLUMN_INCREMENT));
            values.put(COLUMN_TITLE, map.get(COLUMN_TITLE));
            db.insert(WISHTABLE_TABLE, null, values);
            return true;
        }
    }

    public boolean isInWishtable(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + WISHTABLE_TABLE + " where " + COLUMN_ID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

//    public String getWishtableItemQty(String id) {
//
//        db = getReadableDatabase();
//        String qry = "Select *  from " + WISHTABLE_TABLE + " where " + COLUMN_ID + " = " + id;
//        Cursor cursor = db.rawQuery(qry, null);
//        cursor.moveToFirst();
//        return cursor.getString(cursor.getColumnIndex(COLUMN_QTY));
//
//    }

//    public String getInWishtableItemQty(String id) {
//        if (isInWishtable(id)) {
//            db = getReadableDatabase();
//            String qry = "Select *  from " + WISHTABLE_TABLE + " where " + COLUMN_ID + " = " + id;
//            Cursor cursor = db.rawQuery(qry, null);
//            cursor.moveToFirst();
//            return cursor.getString(cursor.getColumnIndex(COLUMN_QTY));
//        } else {
//            return "0.0";
//        }
//    }

    public int getWishtableCount() {
        db = getReadableDatabase();
        String qry = "Select *  from " + WISHTABLE_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        return cursor.getCount();
    }

//    public String getTotalAmountFromWishtable() {
//        db = getReadableDatabase();
//        String qry = "Select SUM(" + COLUMN_QTY + " * " + COLUMN_PRICE + ") as total_amount  from " + WISHTABLE_TABLE;
//        Cursor cursor = db.rawQuery(qry, null);
//        cursor.moveToFirst();
//        String total = cursor.getString(cursor.getColumnIndex("total_amount"));
//        if (total != null) {
//
//            return total;
//        } else {
//            return "0";
//        }
//    }


    public ArrayList<HashMap<String, String>> getWishtableAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + WISHTABLE_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            //map.put(COLUMN_QTY, cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
            map.put(COLUMN_IMAGE, cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            map.put(COLUMN_CAT_ID, cursor.getString(cursor.getColumnIndex(COLUMN_CAT_ID)));
            map.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            map.put(COLUMN_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            map.put(COLUMN_MRP, cursor.getString(cursor.getColumnIndex(COLUMN_MRP)));
            map.put(COLUMN_UNIT_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_PRICE)));
            map.put(COLUMN_UNIT, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)));
            map.put(COLUMN_DESC, cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
            map.put(COLUMN_UNIT_VALUE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_VALUE)));
            map.put(COLUMN_STOCK, cursor.getString(cursor.getColumnIndex(COLUMN_STOCK)));
            map.put(COLUMN_PRODUCT_ATTRIBUTE, cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ATTRIBUTE)));
            map.put(COLUMN_REWARDS, cursor.getString(cursor.getColumnIndex(COLUMN_REWARDS)));
            map.put(COLUMN_INCREMENT, cursor.getString(cursor.getColumnIndex(COLUMN_INCREMENT)));
            map.put(COLUMN_TITLE, cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            list.add(map);
            cursor.moveToNext();
        }
        return list;
    }

    public ArrayList<HashMap<String, String>> getCartProduct(int product_id) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + WISHTABLE_TABLE+ " where " + COLUMN_ID + " = " + product_id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
           // map.put(COLUMN_QTY, cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
            map.put(COLUMN_IMAGE, cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            map.put(COLUMN_CAT_ID, cursor.getString(cursor.getColumnIndex(COLUMN_CAT_ID)));
            map.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            map.put(COLUMN_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            map.put(COLUMN_MRP, cursor.getString(cursor.getColumnIndex(COLUMN_MRP)));
            map.put(COLUMN_UNIT_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_PRICE)));
            map.put(COLUMN_UNIT, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)));
            map.put(COLUMN_DESC, cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));
            map.put(COLUMN_UNIT_VALUE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_VALUE)));
            map.put(COLUMN_STOCK, cursor.getString(cursor.getColumnIndex(COLUMN_STOCK)));
            map.put(COLUMN_PRODUCT_ATTRIBUTE, cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ATTRIBUTE)));
            map.put(COLUMN_REWARDS, cursor.getString(cursor.getColumnIndex(COLUMN_REWARDS)));
            map.put(COLUMN_INCREMENT, cursor.getString(cursor.getColumnIndex(COLUMN_INCREMENT)));
            map.put(COLUMN_TITLE, cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));


            list.add(map);
            cursor.moveToNext();
        }
        return list;
    }

    public String getColumnRewards() {
        db = getReadableDatabase();
        String qry = "SELECT rewards FROM " + WISHTABLE_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String reward = cursor.getString(cursor.getColumnIndex("rewards"));
        if (reward != null) {

            return reward;
        } else {
            return "0";
        }
    }

    public String getFavConcatString() {
        db = getReadableDatabase();
        String qry = "Select *  from " + WISHTABLE_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String concate = "";
        for (int i = 0; i < cursor.getCount(); i++) {
            if (concate.equalsIgnoreCase("")) {
                concate = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            } else {
                concate = concate + "_" + cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            }
            cursor.moveToNext();
        }
        return concate;
    }
    public void clearWishtable() {
        db = getReadableDatabase();
        db.execSQL("delete from " + WISHTABLE_TABLE);
    }

    public void removeItemFromWishtable(String id) {
        db = getReadableDatabase();
        db.execSQL("delete from " + WISHTABLE_TABLE + " where " + COLUMN_ID + " = " + id);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
