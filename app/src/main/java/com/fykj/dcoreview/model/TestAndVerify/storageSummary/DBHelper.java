package com.fykj.dcoreview.model.TestAndVerify.storageSummary;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by administrator on 2018/8/16.
 * sql
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;            //数据库版本
    private static final String DB_NAME = "myTest.db";  // 数据库名称
    public static String TABLE_NAME = "Orders";                //表名

    private String columns = "Id integer primary key, CustomName Text, OrderPrice integer, Country text";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (" + columns + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        upgradeTest(db);
    }

    private void upgradeTest(SQLiteDatabase db) {
        // Orders 表新增1个字段
        //db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN deleted VARCHAR");

        //思路
        //1，将表A重命名，改了A_temp。
        //2，创建新表A。
        //3，将表A_temp的数据插入到表A
        //4. 删除表A_temp
        try {
            db.beginTransaction();
            // 1, Rename table.
            String tempTableName = TABLE_NAME + "_temp";
            String sql = "ALTER TABLE " + TABLE_NAME + " RENAME TO " + tempTableName;
            db.execSQL(sql);
            // 2, Create table.
            onCreate(db);
            // 3, Load data
            sql = "INSERT INTO " + TABLE_NAME +
                    " (" + columns + ") " +
                    " SELECT " + columns + " FROM " + tempTableName;
            db.execSQL(sql);
            // 4, Drop the temporary table.
            db.execSQL("DROP TABLE IF EXISTS " + tempTableName);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

}
