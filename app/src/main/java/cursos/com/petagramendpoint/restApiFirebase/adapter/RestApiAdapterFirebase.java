package cursos.com.petagramendpoint.restApiFirebase.adapter;

import cursos.com.petagramendpoint.restApiFirebase.ConstantesRestApiFirebase;
import cursos.com.petagramendpoint.restApiFirebase.EndpointsFirebase;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Usuario on 31/10/2017.
 */
public class RestApiAdapterFirebase {

    public EndpointsFirebase establecerConexionRestAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApiFirebase.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;

        return retrofit.create(EndpointsFirebase.class);

    }

}
