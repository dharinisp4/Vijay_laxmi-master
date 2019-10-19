package util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseCartHandler extends SQLiteOpenHelper {

    private static String DB_NAME = "vadb";
    private static int DB_VERSION = 3;
    private SQLiteDatabase db;

    public static final String CART_TABLE = "cart";

    public static final String COLUMN_ID = "product_id";
    public static final String COLUMN_CID = "cart_id";
    public static final String COLUMN_AID = "attr_id";
    public static final String COLUMN_QTY = "qty";
    public static final String COLUMN_IMAGE = "product_image";
    public static final String COLUMN_CAT_ID = "cat_id";
    public static final String COLUMN_NAME = "product_name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_MRP = "mrp";
    public static final String COLUMN_UNIT_PRICE = "unit_price";
    public static final String COLUMN_UNIT = "unit";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_DESC = "product_description";
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_ATRCOLOR = "attr_color";
    public static final String COLUMN_ATRIMG = "attr_img";
    public static final String COLUMN_PRODUCT_ATTR = "product_attribute";
    public static final String COLUMN_REWARDS = "rewards";
    public static final String COLUMN_INCREMENT = "increment";
    public static final String COLUMN_TITLE = "title";








    public DatabaseCartHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String exe = "CREATE TABLE IF NOT EXISTS " + CART_TABLE
                + "(" + COLUMN_CID + " integer primary key AUTOINCREMENT, "
                + COLUMN_ID + " integer NOT NULL,"
                + COLUMN_AID + " integer,"
                + COLUMN_QTY + " DOUBLE NOT NULL,"
                + COLUMN_IMAGE + " TEXT NOT NULL, "
                + COLUMN_CAT_ID + " TEXT NOT NULL, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_PRICE + " DOUBLE NOT NULL, "
                + COLUMN_MRP + " DOUBLE NOT NULL, "
                + COLUMN_UNIT_PRICE + " DOUBLE NOT NULL, "
                + COLUMN_UNIT + " TEXT NOT NULL, "
                + COLUMN_TYPE + " TEXT NOT NULL ,"
                + COLUMN_DESC + " TEXT ,"
                + COLUMN_STOCK + " TEXT NOT NULL, "
                + COLUMN_ATRIMG + " TEXT ,"
                + COLUMN_ATRCOLOR + " TEXT,"
                + COLUMN_PRODUCT_ATTR + " TEXT,"
                + COLUMN_REWARDS + " TEXT,"
                + COLUMN_INCREMENT + " TEXT ,"
                + COLUMN_TITLE + " TEXT "



                + ")";

        db.execSQL(exe);

    }

    public boolean setCart(HashMap<String, String> map, Float Qty) {
        db = getWritableDatabase();
        if (isInCart(map.get(COLUMN_ID))) {

             if(isAttrInCart(map.get(COLUMN_ID),map.get(COLUMN_AID)))
             {

                 if(isAttrColInCart(map.get(COLUMN_ID),map.get(COLUMN_AID),map.get(COLUMN_ATRCOLOR)))
                             {
                                 db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "'," + COLUMN_PRICE + " = '" + map.get(COLUMN_PRICE) + "' where " + COLUMN_ID + "=" + map.get(COLUMN_ID)+" AND "+ COLUMN_AID+ " = " +map.get(COLUMN_AID)+" AND "+ COLUMN_ATRCOLOR+ " = '" + map.get(COLUMN_ATRCOLOR)+"'");
                                 return false;
                             }
                             else
                             {
                                 ContentValues values = new ContentValues();
                                 values.put(COLUMN_ID, map.get(COLUMN_ID));
                                 values.put(COLUMN_AID, map.get(COLUMN_AID));
                                 values.put(COLUMN_QTY, Qty);
                                 values.put(COLUMN_CAT_ID, map.get(COLUMN_CAT_ID));
                                 values.put(COLUMN_IMAGE, map.get(COLUMN_IMAGE));
                                 values.put(COLUMN_NAME, map.get(COLUMN_NAME));
                                 values.put(COLUMN_PRICE, map.get(COLUMN_PRICE));
                                 values.put(COLUMN_MRP, map.get(COLUMN_MRP));
                                 values.put(COLUMN_UNIT_PRICE, map.get(COLUMN_UNIT_PRICE));
                                 values.put(COLUMN_UNIT, map.get(COLUMN_UNIT));
                                 values.put(COLUMN_TYPE, map.get(COLUMN_TYPE));
                                 values.put(COLUMN_DESC, map.get(COLUMN_DESC));
                                 values.put(COLUMN_STOCK, map.get(COLUMN_STOCK));
                                 values.put(COLUMN_ATRIMG, map.get(COLUMN_ATRIMG));
                                 values.put(COLUMN_ATRCOLOR, map.get(COLUMN_ATRCOLOR));
                                 values.put(COLUMN_PRODUCT_ATTR, map.get(COLUMN_PRODUCT_ATTR));
                                 values.put(COLUMN_REWARDS, map.get(COLUMN_REWARDS));
                                 values.put(COLUMN_INCREMENT, map.get(COLUMN_INCREMENT));
                                 values.put(COLUMN_TITLE, map.get(COLUMN_TITLE));
                                 db.insert(CART_TABLE, null, values);

                                 return true;

                             }
             }
             else
             {
                 ContentValues values = new ContentValues();
                 values.put(COLUMN_ID, map.get(COLUMN_ID));
                 values.put(COLUMN_AID, map.get(COLUMN_AID));
                 values.put(COLUMN_QTY, Qty);
                 values.put(COLUMN_CAT_ID, map.get(COLUMN_CAT_ID));
                 values.put(COLUMN_IMAGE, map.get(COLUMN_IMAGE));
                 values.put(COLUMN_NAME, map.get(COLUMN_NAME));
                 values.put(COLUMN_PRICE, map.get(COLUMN_PRICE));
                 values.put(COLUMN_MRP, map.get(COLUMN_MRP));
                 values.put(COLUMN_UNIT_PRICE, map.get(COLUMN_UNIT_PRICE));
                 values.put(COLUMN_UNIT, map.get(COLUMN_UNIT));
                 values.put(COLUMN_TYPE, map.get(COLUMN_TYPE));
                 values.put(COLUMN_DESC, map.get(COLUMN_DESC));
                 values.put(COLUMN_STOCK, map.get(COLUMN_STOCK));
                 values.put(COLUMN_ATRIMG, map.get(COLUMN_ATRIMG));
                 values.put(COLUMN_ATRCOLOR, map.get(COLUMN_ATRCOLOR));
                 values.put(COLUMN_PRODUCT_ATTR, map.get(COLUMN_PRODUCT_ATTR));
                 values.put(COLUMN_REWARDS, map.get(COLUMN_REWARDS));
                 values.put(COLUMN_INCREMENT, map.get(COLUMN_INCREMENT));
                 values.put(COLUMN_TITLE, map.get(COLUMN_TITLE));
                 db.insert(CART_TABLE, null, values);

                 return true;

             }
            //db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "'," + COLUMN_PRICE + " = '" + map.get(COLUMN_PRICE) + "' where " + COLUMN_CID + "=" + map.get(COLUMN_CID));

        } else {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, map.get(COLUMN_ID));
            values.put(COLUMN_AID, map.get(COLUMN_AID));
            values.put(COLUMN_QTY, Qty);
            values.put(COLUMN_CAT_ID, map.get(COLUMN_CAT_ID));
            values.put(COLUMN_IMAGE, map.get(COLUMN_IMAGE));
            values.put(COLUMN_NAME, map.get(COLUMN_NAME));
            values.put(COLUMN_PRICE, map.get(COLUMN_PRICE));
            values.put(COLUMN_MRP, map.get(COLUMN_MRP));
            values.put(COLUMN_UNIT_PRICE, map.get(COLUMN_UNIT_PRICE));
            values.put(COLUMN_UNIT, map.get(COLUMN_UNIT));
            values.put(COLUMN_TYPE, map.get(COLUMN_TYPE));
            values.put(COLUMN_DESC, map.get(COLUMN_DESC));
            values.put(COLUMN_STOCK, map.get(COLUMN_STOCK));
            values.put(COLUMN_ATRIMG, map.get(COLUMN_ATRIMG));
            values.put(COLUMN_ATRCOLOR, map.get(COLUMN_ATRCOLOR));
            values.put(COLUMN_PRODUCT_ATTR, map.get(COLUMN_PRODUCT_ATTR));
            values.put(COLUMN_REWARDS, map.get(COLUMN_REWARDS));
            values.put(COLUMN_INCREMENT, map.get(COLUMN_INCREMENT));
            values.put(COLUMN_TITLE, map.get(COLUMN_TITLE));
            db.insert(CART_TABLE, null, values);

            return true;
        }
    }


    public boolean setWithoutAttrCart(HashMap<String, String> map, Float Qty)
    {
     db=getReadableDatabase();
     if(isInCart(map.get(COLUMN_ID)))
     {

         db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "'," + COLUMN_PRICE + " = '" + map.get(COLUMN_PRICE) + "' where " + COLUMN_CID + "=" + map.get(COLUMN_ID));
         return false;

     } else {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, map.get(COLUMN_ID));
        values.put(COLUMN_AID, map.get(COLUMN_AID));
        values.put(COLUMN_QTY, Qty);
        values.put(COLUMN_CAT_ID, map.get(COLUMN_CAT_ID));
        values.put(COLUMN_IMAGE, map.get(COLUMN_IMAGE));
        values.put(COLUMN_NAME, map.get(COLUMN_NAME));
        values.put(COLUMN_PRICE, map.get(COLUMN_PRICE));
        values.put(COLUMN_MRP, map.get(COLUMN_MRP));
        values.put(COLUMN_UNIT_PRICE, map.get(COLUMN_UNIT_PRICE));
        values.put(COLUMN_UNIT, map.get(COLUMN_UNIT));
        values.put(COLUMN_TYPE, map.get(COLUMN_TYPE));
        values.put(COLUMN_DESC, map.get(COLUMN_DESC));
        values.put(COLUMN_STOCK, map.get(COLUMN_STOCK));
        values.put(COLUMN_ATRIMG, map.get(COLUMN_ATRIMG));
        values.put(COLUMN_ATRCOLOR, map.get(COLUMN_ATRCOLOR));
        values.put(COLUMN_PRODUCT_ATTR, map.get(COLUMN_PRODUCT_ATTR));
        values.put(COLUMN_REWARDS, map.get(COLUMN_REWARDS));
        values.put(COLUMN_INCREMENT, map.get(COLUMN_INCREMENT));
        values.put(COLUMN_TITLE, map.get(COLUMN_TITLE));
        db.insert(CART_TABLE, null, values);

        return true;
    }

    }


    public boolean updateCartWithQty(String id,String price,Float Qty)
    {
        db = getWritableDatabase();
        if (isInCartID(id)) {
            db.execSQL("update " + CART_TABLE + " set " + COLUMN_QTY + " = '" + Qty + "'," + COLUMN_PRICE + " = '" + price + "' where " + COLUMN_CID + "=" +id);
            return true;
        }
        return false;


    }

    public boolean isInCartID(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_CID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public boolean isInCart(String id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id;
         Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public boolean isAttrInCart(String id,String atr_id) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id +" AND "+ COLUMN_AID+ " = " + atr_id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }

    public boolean isAttrColInCart(String id,String atr_id,String color) {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_ID + " = " + id + " AND "+ COLUMN_AID + " = " + atr_id + " AND " + COLUMN_ATRCOLOR + " = '" + color + "'";
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) return true;

        return false;
    }



    public String getCartItemQty(String id) {

        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_CID + " = " + id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        return cursor.getString(cursor.getColumnIndex(COLUMN_QTY));

    }

    public String getInCartItemQty(String id) {
        if (isInCart(id)) {
            db = getReadableDatabase();
            String qry = "Select *  from " + CART_TABLE + " where " + COLUMN_CID + " = " + id;
            Cursor cursor = db.rawQuery(qry, null);
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex(COLUMN_QTY));
        } else {
            return "0.0";
        }
    }

    public int getCartCount() {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        return cursor.getCount();
    }

    public String getTotalAmount() {
        db = getReadableDatabase();
        String qry = "Select SUM(" + COLUMN_PRICE + ") as total_amount  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String total = cursor.getString(cursor.getColumnIndex("total_amount"));
        if (total != null) {

            return total;
        } else {
            return "0";
        }
    }

    public String getTotalMRP() {
        db = getReadableDatabase();
        String qry = "Select SUM(" + COLUMN_MRP + ") as total_mrp  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String total = cursor.getString(cursor.getColumnIndex("total_mrp"));
        if (total != null) {

            return total;
        } else {
            return "0";
        }
    }


    public String getAllMRP() {
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();


        float res=0;

        for(int i=0; i<cursor.getColumnCount();i++)
        {
            float qty=cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY));
            String mrp=cursor.getString(cursor.getColumnIndex(COLUMN_MRP));
            float m=Float.parseFloat(mrp);
            float sum=qty*m;
           res=res+sum;
        }

        //String total = cursor.getString(cursor.getColumnIndex("total_mrp"));
        if (res != 0) {

            return String.valueOf(res);
        } else {
            return "0";
        }
    }



    public ArrayList<HashMap<String, String>> getCartAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_CID, cursor.getString(cursor.getColumnIndex(COLUMN_CID)));
            map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            map.put(COLUMN_AID, cursor.getString(cursor.getColumnIndex(COLUMN_AID)));
            map.put(COLUMN_QTY, cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
            map.put(COLUMN_IMAGE, cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            map.put(COLUMN_CAT_ID, cursor.getString(cursor.getColumnIndex(COLUMN_CAT_ID)));
            map.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            map.put(COLUMN_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            map.put(COLUMN_MRP, cursor.getString(cursor.getColumnIndex(COLUMN_MRP)));
            map.put(COLUMN_UNIT_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_PRICE)));
            map.put(COLUMN_UNIT, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)));

            map.put(COLUMN_STOCK, cursor.getString(cursor.getColumnIndex(COLUMN_STOCK)));
            map.put(COLUMN_ATRIMG, cursor.getString(cursor.getColumnIndex(COLUMN_ATRIMG)));
            map.put(COLUMN_ATRCOLOR, cursor.getString(cursor.getColumnIndex(COLUMN_ATRCOLOR)));
            map.put(COLUMN_PRODUCT_ATTR, cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ATTR)));
            map.put(COLUMN_REWARDS, cursor.getString(cursor.getColumnIndex(COLUMN_REWARDS)));
            map.put(COLUMN_INCREMENT, cursor.getString(cursor.getColumnIndex(COLUMN_INCREMENT)));
            map.put(COLUMN_TITLE, cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            map.put(COLUMN_TYPE, cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            map.put(COLUMN_DESC, cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));

            list.add(map);
            cursor.moveToNext();
        }
        return list;
    }

    public ArrayList<HashMap<String, String>> getCartProduct(int product_id) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        db = getReadableDatabase();
        String qry = "Select *  from " + CART_TABLE+ " where " + COLUMN_CID + " = " + product_id;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put(COLUMN_CID, cursor.getString(cursor.getColumnIndex(COLUMN_CID)));
            map.put(COLUMN_ID, cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            map.put(COLUMN_AID, cursor.getString(cursor.getColumnIndex(COLUMN_AID)));
            map.put(COLUMN_QTY, cursor.getString(cursor.getColumnIndex(COLUMN_QTY)));
            map.put(COLUMN_IMAGE, cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
            map.put(COLUMN_CAT_ID, cursor.getString(cursor.getColumnIndex(COLUMN_CAT_ID)));
            map.put(COLUMN_NAME, cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            map.put(COLUMN_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            map.put(COLUMN_MRP, cursor.getString(cursor.getColumnIndex(COLUMN_MRP)));
            map.put(COLUMN_UNIT_PRICE, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT_PRICE)));
            map.put(COLUMN_UNIT, cursor.getString(cursor.getColumnIndex(COLUMN_UNIT)));

            map.put(COLUMN_STOCK, cursor.getString(cursor.getColumnIndex(COLUMN_STOCK)));
            map.put(COLUMN_ATRIMG, cursor.getString(cursor.getColumnIndex(COLUMN_ATRIMG)));
            map.put(COLUMN_ATRCOLOR, cursor.getString(cursor.getColumnIndex(COLUMN_ATRCOLOR)));
            map.put(COLUMN_PRODUCT_ATTR, cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_ATTR)));

            map.put(COLUMN_REWARDS, cursor.getString(cursor.getColumnIndex(COLUMN_REWARDS)));
            map.put(COLUMN_INCREMENT, cursor.getString(cursor.getColumnIndex(COLUMN_INCREMENT)));
            map.put(COLUMN_TITLE, cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
            map.put(COLUMN_TYPE, cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
            map.put(COLUMN_DESC, cursor.getString(cursor.getColumnIndex(COLUMN_DESC)));

            list.add(map);
            cursor.moveToNext();
        }
        return list;
    }

    public String getColumnRewards() {
        db = getReadableDatabase();
        String qry = "SELECT rewards FROM "+ CART_TABLE;
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
        String qry = "Select *  from " + CART_TABLE;
        Cursor cursor = db.rawQuery(qry, null);
        cursor.moveToFirst();
        String concate = "";
        for (int i = 0; i < cursor.getCount(); i++) {
            if (concate.equalsIgnoreCase("")) {
                concate = cursor.getString(cursor.getColumnIndex(COLUMN_CID));
            } else {
                concate = concate + "_" + cursor.getString(cursor.getColumnIndex(COLUMN_CID));
            }
            cursor.moveToNext();
        }
        return concate;
    }

    public void clearCart() {
        db = getReadableDatabase();
        db.execSQL("delete from " + CART_TABLE);
    }

    public void removeItemFromCart(String id) {
        db = getReadableDatabase();
        db.execSQL("delete from " + CART_TABLE + " where " + COLUMN_CID + " = " + id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
