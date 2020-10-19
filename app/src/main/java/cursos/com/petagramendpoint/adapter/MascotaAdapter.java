package cursos.com.petagramendpoint.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.pojo.Mascota;
import cursos.com.petagramendpoint.restApi.EndPointApi;
import cursos.com.petagramendpoint.restApi.adapter.RestApiAdapter;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import cursos.com.petagramendpoint.restApiFirebase.EndpointsFirebase;
import cursos.com.petagramendpoint.restApiFirebase.adapter.RestApiAdapterFirebase;
import cursos.com.petagramendpoint.restApiFirebase.model.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created6.
 */
public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MascotaHolder> {

    private List<Mascota> mascotas;
    private Context context;
    private String likeImagen;
    int likes = 0;
    private static final String TAG = MascotaAdapter.class.getName();
    private static final String TAG1 = "FIREBASE_TOKEN";

    public MascotaAdapter(List<Mascota> mascotas, Context context) {
        this.mascotas = mascotas;
        this.context = context;
    }


    @Override
    public MascotaHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.i(TAG, "Creando view holder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota, parent, false);
        return new MascotaHolder(view);

    }

    @Override
    public void onBindViewHolder(final MascotaHolder holder, int position) {

        Log.i(TAG, "Llenando objeto");
        final Mascota mascota = mascotas.get(position);

        Picasso.with(context)
                .load(mascota.getImagen())
                .placeholder(R.drawable.pata).into(holder.imagen);

        holder.nombre.setText(mascota.getNombre());
        holder.cantidad.setText(String.valueOf(mascota.getLikes()));


        holder.huesoLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "haciendo like");





                 likes = mascota.getLikes()+1;
                mascota.setLikes(likes);
                holder.cantidad.setText(String.valueOf(likes));
                likeImagen = mascota.getImagen();


                Log.d(TAG1, "Solicitando Token");
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG1, token);

                String id_usuario_instragram  = mascota.getNombre();
                String  likes = String.valueOf(mascota.getLikes());

                enviarTokenRegistro(token ,id_usuario_instragram,likes );



            }
        });

    }

    private void enviarTokenRegistro(String token, final String idInsta, String likes) {

        RestApiAdapterFirebase restApiAdapterFirebase = new RestApiAdapterFirebase();
        EndpointsFirebase endpointsFirebase = restApiAdapterFirebase.establecerConexionRestAPI();
        Call<UsuarioResponse> usuarioResponseFirebaseCall = endpointsFirebase.registrarTokenID(token, idInsta, likes);

        usuarioResponseFirebaseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse = response.body();
                Log.d("ID_FIREBASE", usuarioResponse.getId());
                Log.d("TOKEN_FIREBASE", usuarioResponse.getId_dispositivo());
                Log.d("INSTA_FIREBASE", usuarioResponse.getId_usuario_instagram());
                Log.d("FOTO_FIREBASE", usuarioResponse.getId_foto_instagram());
               enviarLikeMascota(usuarioResponse.getId(),usuarioResponse.getId_dispositivo(),idInsta );

            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });



    }

    private void enviarLikeMascota(String id, String id_dispositivo, String idInsta) {

       final UsuarioResponse usuarioResponse = new UsuarioResponse(id,id_dispositivo,idInsta);
        RestApiAdapterFirebase restApiAdapterFirebase = new RestApiAdapterFirebase();
        EndpointsFirebase endpointsFirebase = restApiAdapterFirebase.establecerConexionRestAPI();
        Call<UsuarioResponse> usuarioResponseCall = endpointsFirebase.likeMascota(usuarioResponse.getId() , usuarioResponse.getId_usuario_instagram());
        usuarioResponseCall.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                UsuarioResponse usuarioResponse1 = response.body();
                Log.d("ID_FIREBASE", usuarioResponse1.getId());
                Log.d("TOKEN_FIREBASE", usuarioResponse1.getId_dispositivo());

            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });


    }


    private void generarLikes() {
        RestApiAdapter raa = new RestApiAdapter();
        Gson gsonMedia = raa.construyeGsonDeserializadorMediaRecent();
        EndPointApi epa = raa.establecerConexionRestApiInstagram(gsonMedia);

        final Call<MascotaResponse> mascotaResponseCall = epa.postLikeMedia(likeImagen);
        Log.e(TAG, "Fallo conexion: " + likeImagen.toString());


        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {

                MascotaResponse mr = response.body();
                if (mr != null) {

                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {

                Log.e(TAG, "Fallo conexion: " + t.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    public static class MascotaHolder extends RecyclerView.ViewHolder {

        private ImageView imagen;
        private TextView nombre;
        private TextView cantidad;
        private ImageButton huesoLike;
        private ImageButton huesoRating;

        public MascotaHolder(View view) {
            super(view);
            imagen = (ImageView) view.findViewById(R.id.imgFoto);
            nombre = (TextView) view.findViewById(R.id.nombreMascota);
            cantidad = (TextView) view.findViewById(R.id.cantidadRating);
            huesoLike = (ImageButton) view.findViewById(R.id.imgLike);
            huesoRating = (ImageButton) view.findViewById(R.id.imgRating);

        }
    }
}

