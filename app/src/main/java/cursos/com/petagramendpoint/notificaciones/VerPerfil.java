package cursos.com.petagramendpoint.notificaciones;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import cursos.com.petagramendpoint.activity.FavoritoActivity;
import cursos.com.petagramendpoint.activity.MainActivity;
import cursos.com.petagramendpoint.restApi.JsonKeys;

/**
 * Created by Usuario on 19/11/2017.
 */

public class VerPerfil extends BroadcastReceiver {

    public static final String VER_PERFIL = "VER_PERFIL";
    public static final String DAR_FOLLOW = "DAR_FOLLOW";
    public static final String VER_USUARIO = "VER_USUARIO";
    @Override
    public void onReceive(Context context, Intent intent) {

        String accion = intent.getAction();

        if (VER_PERFIL.equals(accion)){
            Log.d("WEAR  ", context.toString() );
            Intent star = new Intent(context.getApplicationContext(), MainActivity.class);
            star.putExtra("numeroTab", 1);
            star.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(star);
            Toast.makeText(context, "Ver mi Perfil" , Toast.LENGTH_LONG).show();

        }
        if (DAR_FOLLOW.equals(accion)){
            Log.d("WEAR  ", context.toString() );
           /* Intent star = new Intent(context.getApplicationContext(), MainActivity.class);
            star.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(star);*/
            Toast.makeText(context, "Dar Follow/Unfollow " , Toast.LENGTH_LONG).show();

        }
        if (VER_USUARIO.equals(accion)){
            Log.d("WEAR  ", context.toString() );
            Intent star = new Intent(context.getApplicationContext(), MainActivity.class);
            star.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(star);
            Toast.makeText(context, "Fotos Recientes " , Toast.LENGTH_LONG).show();


        }
    }


}
