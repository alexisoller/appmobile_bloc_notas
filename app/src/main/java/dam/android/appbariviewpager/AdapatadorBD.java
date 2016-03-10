package dam.android.appbariviewpager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.SyncStateContract;
public class AdapatadorBD extends SQLiteOpenHelper {
    public static final String TABLE_ID = "_idNote";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String IMG = "img";
    private static final String DATABASE = "Note";
    private static final String TABLE = "notes";
    public AdapatadorBD(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " ("+ TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +TITLE + " TEXT," + CONTENT + " TEXT," + IMG + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void addNote(String title, String content, String img) {
        ContentValues valores = new ContentValues();
        valores.put(TITLE, title);
        valores.put(CONTENT, content);
        valores.put(IMG, img);
        this.getWritableDatabase().insert(TABLE, null, valores);
    }

    public Cursor getNote(String condition) {
        String columnas[] = {TABLE_ID, TITLE, CONTENT, IMG};
        String args[] = new String[]{condition};
        Cursor c = this.getReadableDatabase().query(TABLE, columnas, TITLE + "=?", args, null, null, null);
        return c;
    }

    public void deletenota(String condition) {
        String args[] = new String[]{condition};
        this.getWritableDatabase().delete(TABLE, TITLE + "=?", args);
    }

    public void updatenota(String title, String content, String img, String condition) {
        String args[] = new String[]{condition};
        ContentValues valores = new ContentValues();
        valores.put(TITLE, title);
        valores.put(CONTENT, content);
        valores.put(IMG, img);
        this.getWritableDatabase().update(TABLE, valores, TITLE + "=?", args);
    }

    public Cursor getNotes() {
        String columnas[] = {TABLE_ID, TITLE, CONTENT, IMG};
        Cursor c = this.getReadableDatabase().query(TABLE, columnas, null, null, null, null, null);
        return c;
    }

    public void deletenotes() {
        this.getWritableDatabase().delete(TABLE, null, null);
    }

}

