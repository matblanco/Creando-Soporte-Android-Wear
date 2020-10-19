package cursos.com.petagramendpoint.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.pojo.Mascota;
import cursos.com.petagramendpoint.restApi.ConstantesRestApi;
import cursos.com.petagramendpoint.restApi.EndPointApi;
import cursos.com.petagramendpoint.restApi.JsonKeys;
import cursos.com.petagramendpoint.restApi.adapter.RestApiAdapter;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurarCuentaActivity extends AppCompatActivity {


    private static final String TAG = ConfigurarCuentaActivity.class.getName();
    private TextView nombreUsuario;
    private Activity activity;
    String nombre ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        nombreUsuario = (TextView) findViewById(R.id.nombreBusqueda);
        activity = this;


    }

    public void buscarContacto(final View view) {

         nombre = nombreUsuario.getText().toString();

        RestApiAdapter raa = new RestApiAdapter();
        Gson gsonSearch = raa.construyeGsonDeserializadorSearch();
        EndPointApi epa = raa.establecerConexionRestApiInstagram(gsonSearch);

        final Call<MascotaResponse> mascotaResponseCall = epa.search(nombre, ConstantesRestApi.ACCESS_TOKEN);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                List<Mascota> mascotas = response.body().getMascotas();

                if (mascotas != null && !mascotas.isEmpty()) {

                    guardarPreferenciasUsuario(response.body().getMascotas().get(0));
                    Intent intent = new Intent(ConfigurarCuentaActivity.this, MainActivity.class);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(ConfigurarCuentaActivity.this);
                }else{
                    Snackbar.make(view,"No se encontró el usuario ingresado.",Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(ConfigurarCuentaActivity.this, "Fallo conexion, intente de nuevo.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Fallo conexion: " + t.toString());
            }
        });


    }


    public void guardarPreferenciasUsuario(Mascota mascota) {


        SharedPreferences preps = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preps.edit();

        String profilePicture = mascota.getImagen();
        String fullNombre = mascota.getNombre();
        String idUsuario = mascota.getIdMascota();

        edit.putString(JsonKeys.USER, nombre);
        edit.putString(JsonKeys.USER_ID, idUsuario);
        edit.putString(JsonKeys.USER_FULL_NAME, fullNombre);
        edit.putString(JsonKeys.PROFILE_PICTURE, profilePicture);

        edit.commit();
    }
}
