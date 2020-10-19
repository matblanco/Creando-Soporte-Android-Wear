package cursos.com.petagramendpoint.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.adapter.PageAdapter;
import cursos.com.petagramendpoint.fragment.InfoMascotaFragment;
import cursos.com.petagramendpoint.fragment.MascotasFragment;
import cursos.com.petagramendpoint.pojo.Mascota;
import cursos.com.petagramendpoint.restApi.ConstantesRestApi;
import cursos.com.petagramendpoint.restApi.EndPointApi;
import cursos.com.petagramendpoint.restApi.JsonKeys;
import cursos.com.petagramendpoint.restApi.adapter.RestApiAdapter;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import cursos.com.petagramendpoint.restApiFirebase.EndpointsFirebase;
import cursos.com.petagramendpoint.restApiFirebase.adapter.RestApiAdapterFirebase;
import cursos.com.petagramendpoint.restApiFirebase.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private static final String TAG1 = "FIREBASE_TOKEN";
    int numeroTab = 1;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        verificarCuenta();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        generarToolbar();
        setupViewPager();

        Bundle datos = this.getIntent().getExtras();
        if (datos!=null){
         numeroTab = datos.getInt("numeroTab");}

        if(numeroTab==0) {
            tabLayout.getTabAt(0).select();
        }else if(numeroTab==1) {
            tabLayout.getTabAt(1).select();
        }




    }


    private void generarToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setLogo(R.drawable.pata);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.mFavorito:
                Intent intent = new Intent(MainActivity.this, FavoritoActivity.class);
                startActivity(intent);
                break;
            case R.id.mAbout:
                Intent about = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.mContact:
                Intent contact = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contact);
                break;
            case R.id.mConfigurarCuenta:
                Intent configurar = new Intent(MainActivity.this,ConfigurarCuentaActivity.class);
                startActivity(configurar);
                break;
            case R.id.mRecibirNotificacion:
               solicitarToken();
                break;

        }
        return true;
    }

    private void solicitarToken() {
        Log.d(TAG1, "Solicitando Token");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG1, token);
        SharedPreferences preps = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        String id_usuario_instragram  = preps.getString(JsonKeys.USER, "");
        String  likes = "0";
        enviarTokenRegistro(token ,id_usuario_instragram,likes );

    }

    private void enviarTokenRegistro(String token, String idInsta, String likes) {

        RestApiAdapterFirebase restApiAdapterFirebase = new RestApiAdapterFirebase();
        EndpointsFirebase endpointsFirebase = restApiAdapterFirebase.establecerConexionRestAPI();
        Call<UsuarioResponse> usuarioResponseFirebaseCall = endpointsFirebase.registrarTokenID(token,idInsta,likes);

        usuarioResponseFirebaseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                Log.d("ID_FIREBASE" , usuarioResponse.getId());
                Log.d("TOKEN_FIREBASE" , usuarioResponse.getId_dispositivo());
                Log.d("INSTA_FIREBASE" , usuarioResponse.getId_usuario_instagram());
                Log.d("FOTO_FIREBASE" , usuarioResponse.getId_foto_instagram());

            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });

    }


    private List<Fragment> agregarFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MascotasFragment());
      //  fragments.add(new InfoMascotaFragment());
        fragments.add(new InfoMascotaFragment());

        return fragments;
    }

    private void setupViewPager() {
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), agregarFragments()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.cas_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.perro_tab);
    }



    private void verificarCuenta() {


        SharedPreferences preps = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        String fullnameUsuario  = preps.getString(JsonKeys.USER_FULL_NAME, "");

        if(fullnameUsuario.equals("")) {
            fullnameUsuario="scoobymarron";
            RestApiAdapter raa = new RestApiAdapter();
            Gson gsonSearch = raa.construyeGsonDeserializadorSearch();
            EndPointApi epa = raa.establecerConexionRestApiInstagram(gsonSearch);


            final Call<MascotaResponse> mascotaResponseCall = epa.search(fullnameUsuario, ConstantesRestApi.ACCESS_TOKEN);

            mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                @Override
                public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                    List<Mascota> mascotas = response.body().getMascotas();

                    if (mascotas != null && !mascotas.isEmpty()) {

                        guardarPreferenciasUsuario(response.body().getMascotas().get(0));
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                    }
                }

                @Override
                public void onFailure(Call<MascotaResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Fallo conexion, intente de nuevo.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Fallo conexion: " + t.toString());
                }
            });
        }

    }


    public void guardarPreferenciasUsuario(Mascota mascota) {


        SharedPreferences preps = getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preps.edit();

        String profilePicture = mascota.getImagen();
        String nombre = mascota.getNombre();
        String idUsuario = mascota.getIdMascota();
        edit.putString(JsonKeys.USER, "scoobymarron");
        edit.putString(JsonKeys.USER_ID, idUsuario);
        edit.putString(JsonKeys.USER_FULL_NAME, nombre);
        edit.putString(JsonKeys.PROFILE_PICTURE, profilePicture);

        edit.commit();
    }

}
