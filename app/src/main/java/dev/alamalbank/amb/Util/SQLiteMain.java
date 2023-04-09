package dev.alamalbank.amb.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteMain {
    static final String DATABASE_NAME = "ALAMALMOBILEDBASE.DB";
    static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase ourDatabase;
    private final Context ourContext;
    private DbHelper ourHelper;


    public SQLiteMain(Context c) {
        ourContext = c;
    }

    public static void create_Tables(SQLiteDatabase dbName) {
        dbName.execSQL(Utils.CREATE_LOGIN_TABLE);
        dbName.execSQL(Utils.CREATE_HOME_TABLE);
        dbName.execSQL(Utils.CREATE_STATIC_TABLE);
        dbName.execSQL(Utils.CREATE_CURR_TABLE);
        dbName.execSQL(Utils.CREATE_IDCARDS_TABLE);
        dbName.execSQL(Utils.CREATE_MENUS_TABLE);
        dbName.execSQL(Utils.CREATE_SERVICES_TABLE);
        dbName.execSQL(Utils.CREATE_SERVICE_COL_TABLE);
        dbName.execSQL(Utils.CREATE_TELECOMS_TABLE);
        dbName.execSQL(Utils.CREATE_TELECOM_INFO_TABLE);
        dbName.execSQL(Utils.CREATE_TELECOM_SERVICES_TABLE);
        dbName.execSQL(Utils.CREATE_TELECOM_SERVICES_DTL_TABLE);
        dbName.execSQL(Utils.CREATE_ACCOUNTS_TABLE);
        dbName.execSQL(Utils.CREATE_ACCOUNTS_SERVICES_TABLE);
        dbName.execSQL(Utils.CREATE_TRANSACTIONS_TABLE);
        dbName.execSQL(Utils.CREATE_LAST_SEND_CMD_TABLE);
        dbName.execSQL(Utils.CREATE_INBOX_TABLE);
        dbName.execSQL(Utils.CREATE_OUTBOX_TABLE);
        dbName.execSQL(Utils.CREATE_FAVORITE_TABLE);
        dbName.execSQL(Utils.CREATE_LAST_ENTRY_TABLE);
        dbName.execSQL(Utils.CREATE_BRANCHES_INFO_TABLE);
        dbName.execSQL(Utils.CREATE_OPEN_ACCOUNTS_TABLE);
        dbName.execSQL(Utils.CREATE_NOTIFICATIONS_TABLE);
    }

    public  void execute_SQL(String insStatement) {
        ourDatabase.execSQL(insStatement);
    }

    public Cursor execute_Query(String sql) {
        return ourDatabase.rawQuery(sql, null);
    }

    public SQLiteMain fopen()  {
        try {
            ourHelper = new DbHelper(ourContext);
            ourDatabase = ourHelper.getWritableDatabase();
            return this;
        }catch (Exception e){
            return  this;
        }
    }
    public SQLiteMain open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            create_Tables(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        }
    }
}
