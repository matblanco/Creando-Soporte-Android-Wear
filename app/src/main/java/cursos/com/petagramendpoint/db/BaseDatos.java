package cursos.com.petagramendpoint.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cursos.com.petagramendpoint.pojo.Mascota;


public class BaseDatos extends SQLiteOpenHelper {

    private static final String TAG = BaseDatos.class.getName();
    private Context context;

    public BaseDatos(Context context) {
        super(context, CBD.DATABASE_NAME, null, CBD.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + CBD.TABLE_MASCOTA_NAME + "(" +
                CBD.TABLE_MASCOTA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CBD.TABLE_MASCOTA_NOMBRE + " TEXT," +
                CBD.TABLE_MASCOTA_IMAGEN + " INTEGER " + " )";

        db.execSQL(create);


        create = "CREATE TABLE " + CBD.TABLE_MASCOTA_RATING_NAME + " (" +
                CBD.TABLE_MASCOTA_RATING_ID_RATING + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CBD.TABLE_MASCOTA_RATING_ID_MASCOTA + " INTEGER, " +
                CBD.TABLE_MASCOTA_RATING_RATING + " INTEGER, " +
                "FOREIGN KEY (" + CBD.TABLE_MASCOTA_RATING_ID_MASCOTA + ")" +
                "REFERENCES " + CBD.TABLE_MASCOTA_NAME + "(" + CBD.TABLE_MASCOTA_ID + ") )";

        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + CBD.TABLE_MASCOTA_NAME);
        db.execSQL("DROP TABLE IF EXIST " + CBD.TABLE_MASCOTA_RATING_NAME);
        onCreate(db);
    }

    public List<Mascota> obtenerMascotas() {
        List<Mascota> mascotas = new ArrayList<>();

        String sql = "SELECT * FROM " + CBD.TABLE_MASCOTA_NAME;
        SQLiteDatabase db = this.getReadableDatabase();


        Cursor c = db.rawQuery(sql, null);

        /*
        while (c.moveToNext()) {
            Mascota mascota = new Mascota();
            mascota.setIdMascota(c.getInt(c.getColumnIndex(CBD.TABLE_MASCOTA_ID)));
            mascota.setNombre(c.getString(c.getColumnIndex(CBD.TABLE_MASCOTA_NOMBRE)));
            mascota.setImagen(c.getInt(c.getColumnIndex(CBD.TABLE_MASCOTA_IMAGEN)));

            mascota.setRating(obtenerRatingMascota(mascota));

            mascotas.add(mascota);
        }*/

        db.close();

        return mascotas;
    }

    public void insertarMascota(ContentValues cv) {

        SQLiteDatabase db = this.getReadableDatabase();
        db.insert(CBD.TABLE_MASCOTA_NAME, null, cv);
        db.close();
    }


    public void insertarRatingMascota(ContentValues cv) {
        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getReadableDatabase();
        db.insert(CBD.TABLE_MASCOTA_RATING_NAME, null, cv);
        db.close();
    }

    public int obtenerRatingMascota(Mascota mascota) {
        int rating = 0;

        String sql = "SELECT COUNT(" + CBD.TABLE_MASCOTA_RATING_RATING + ") FROM " + CBD.TABLE_MASCOTA_RATING_NAME +
                " WHERE " + CBD.TABLE_MASCOTA_RATING_ID_MASCOTA + " = " + mascota.getIdMascota();


        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getReadableDatabase();

        Cursor c = db.rawQuery(sql, null);

        if (c.moveToNext()) {
            rating = c.getInt(0);
        }
        db.close();

        return rating;
    }

    public List<Mascota> obtenerCincoMejoresMascotas() {

        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getReadableDatabase();
        List<Mascota> mascotas = new ArrayList<>();

        String sql = "SELECT M."+CBD.TABLE_MASCOTA_ID+", M."+CBD.TABLE_MASCOTA_NOMBRE+", " +
                "COUNT(MR."+CBD.TABLE_MASCOTA_RATING_RATING+") , " +
                "M."+CBD.TABLE_MASCOTA_IMAGEN+" " +
                "FROM "+CBD.TABLE_MASCOTA_NAME+"  M JOIN "+CBD.TABLE_MASCOTA_RATING_NAME+" MR " +
                "ON M."+CBD.TABLE_MASCOTA_ID+" = MR."+CBD.TABLE_MASCOTA_RATING_ID_MASCOTA+
                " GROUP BY M."+CBD.TABLE_MASCOTA_ID+",M."+CBD.TABLE_MASCOTA_NOMBRE+", M."+CBD.TABLE_MASCOTA_IMAGEN+
                " ORDER BY 3 DESC " +
                " LIMIT 5";

        Log.i(TAG,"sql: "+sql);
        Cursor c = db.rawQuery(sql, null);

        /*
        while (c.moveToNext()){
            Mascota mascota = new Mascota();
            mascota.setIdMascota(c.getInt(0));
            mascota.setNombre(c.getString(1));
            mascota.setRating(c.getInt(2));
            mascota.setImagen(c.getInt(3));

            mascotas.add(mascota);
        }*/

        db.close();

        return mascotas;

    }
}
