package cursos.com.petagramendpoint.restApi.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cursos.com.petagramendpoint.restApi.ConstantesRestApi;
import cursos.com.petagramendpoint.restApi.EndPointApi;
import cursos.com.petagramendpoint.restApi.deserializador.MascotaRecentMediaDeserializador;
import cursos.com.petagramendpoint.restApi.deserializador.MascotaSearchDeserializador;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Create
 */
public class RestApiAdapter {

    public EndPointApi establecerConexionRestApiInstagram(Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        return retrofit.create(EndPointApi.class);

    }

    public Gson construyeGsonDeserializadorMediaRecent(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class,new MascotaRecentMediaDeserializador());

        return   gsonBuilder.create();
    }


    public Gson construyeGsonDeserializadorSearch(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(MascotaResponse.class,new MascotaSearchDeserializador());

        return   gsonBuilder.create();
    }
}
