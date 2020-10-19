package cursos.com.petagramendpoint.db;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import cursos.com.petagramendpoint.pojo.Mascota;

/**
 * Created
 */
public class ConstructorMascota {

    private static final int LIKE = 1;
    private Context context;


    public ConstructorMascota(Context context) {
        this.context = context;
    }

    public List<Mascota> obtenerDatos(){
        BaseDatos bd = new BaseDatos(context);
        insertarMascotas(bd);
        return bd.obtenerMascotas();
    }

    public void insertarMascotas(BaseDatos bd){



    }

    public void darRatingMascota(Mascota mascota){
        BaseDatos bd = new BaseDatos(context);
        ContentValues cv = new ContentValues();
        cv.put(CBD.TABLE_MASCOTA_RATING_ID_MASCOTA,mascota.getIdMascota());
        cv.put(CBD.TABLE_MASCOTA_RATING_RATING,LIKE);
        bd.insertarRatingMascota(cv);

    }

    public int obtenerRatingMascota(Mascota mascota){
        BaseDatos bd = new BaseDatos(context);
        return bd.obtenerRatingMascota(mascota);
    }

    public List<Mascota> obtenerCincoMejoresMascotas(){
        BaseDatos bd = new BaseDatos(context);
        return bd.obtenerCincoMejoresMascotas();
    }
}
