package com.example.a25467.moneymanager.manager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a25467.moneymanager.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class DbManager {

    /**
     * 单例模式
     */
    public static DbManager get(){
        return DbManagerHolder.instance;
    }

    private static class DbManagerHolder{
        private static DbManager instance = new DbManager();
    }

    private DbManager(){
    }

    private SQLiteDatabase db;
    private boolean initSuccess;

    /**
     * 初始化化数据库文件
     */
    public boolean initDb(Context context){
        try {
            File dbFile = new File(context.getExternalFilesDir("db").getAbsolutePath(), "CenterWeatherCityCode.db");
            //如果文件不存在，复制过去
            if (!dbFile.exists()){
                FileUtils.createFile(dbFile);
                //将assets文件夹下的 CenterWeatherCityCode.db 拷贝到 dbFile
                FileUtils.copyFile(context.getAssets().open("CenterWeatherCityCode.db"), dbFile);
            }
            db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
            initSuccess = (db != null);
        }catch (Exception e){
            e.printStackTrace();
            initSuccess = false;
        }
        return initSuccess;
    }

    /**
     * 根据城市名获取城市code
     * @param city 城市名
     * @return 城市code
     *
     * 该方法为模糊查找，只取了查到的最前面一个
     * 该方法要异步调用
     *
     */
    public String getCityCode(Context context, String city){
        //如果没有初始化，进行初始化
        if(!initSuccess || db == null){
            initDb(context);
        }
        //从city_code表中查townName为city的townId
        //LIKE 模糊查找

        Cursor cursor = null;
        try {
            String sql = "SELECT townID FROM city_code WHERE townName LIKE ?";
            cursor = db.rawQuery(sql, new String[]{"%"+city+"%"});
            cursor.moveToFirst();
            return cursor.getString(cursor.getColumnIndex("townID"));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
