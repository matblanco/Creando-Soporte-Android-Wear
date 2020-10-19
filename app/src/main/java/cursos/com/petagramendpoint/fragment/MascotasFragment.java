package cursos.com.petagramendpoint.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.adapter.MascotaAdapter;
import cursos.com.petagramendpoint.pojo.Mascota;
import cursos.com.petagramendpoint.restApi.ConstantesRestApi;
import cursos.com.petagramendpoint.restApi.EndPointApi;
import cursos.com.petagramendpoint.restApi.JsonKeys;
import cursos.com.petagramendpoint.restApi.adapter.RestApiAdapter;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */

    public class MascotasFragment extends Fragment {

        private static final String TAG = InfoMascotaFragment.class.getName();
        private List<Mascota> fotos;
        private RecyclerView recyclerView;

        public MascotasFragment() {
            // Required empty public constructor
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_mascotas, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.rvMascotas);

            mostrarDatosUsuario(view);
            generarImagenes();


            GridLayoutManager glm = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(glm);

            return view;
        }

        private void mostrarDatosUsuario(View view) {


            SharedPreferences preps = getContext().getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);



        }

        private void iniciarAdaptador() {

            MascotaAdapter ma = new MascotaAdapter(fotos, getContext());
            recyclerView.setAdapter(ma);
            recyclerView.setHasFixedSize(true);

        }

        private void generarImagenes() {
            Log.i(TAG, "Descargando imagenes");
            fotos = new ArrayList<>();

            SharedPreferences preps = getContext().getSharedPreferences("datosPersonales", Context.MODE_PRIVATE);
            String idUsuario  = preps.getString(JsonKeys.USER_ID, "");


            RestApiAdapter raa = new RestApiAdapter();
            Gson gsonMedia = raa.construyeGsonDeserializadorMediaRecent();
            EndPointApi epa = raa.establecerConexionRestApiInstagram(gsonMedia);

            final Call<MascotaResponse> mascotaResponseCall = epa.getUserRecentMedia(idUsuario, ConstantesRestApi.ACCESS_TOKEN);

            mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                @Override
                public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                    MascotaResponse mr = response.body();
                    if (mr != null) {
                        fotos = mr.getMascotas();
                        Log.i(TAG, "fotos: " + fotos.size());
                        iniciarAdaptador();
                    }
                }

                @Override
                public void onFailure(Call<MascotaResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Fallo conexion, intente de nuevo.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Fallo conexion: " + t.toString());
                }
            });

        }

    }
