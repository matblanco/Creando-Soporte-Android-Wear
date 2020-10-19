package cursos.com.petagramendpoint.restApi;

import cursos.com.petagramendpoint.restApi.model.MascotaResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Creat
 */
public interface EndPointApi {

    @GET(ConstantesRestApi.URL_RECENT_MEDIA_USER)
    public Call<MascotaResponse> getUserRecentMedia(@Path("user_id") String userId, @Query("access_token") String accessToken);

    @GET(ConstantesRestApi.URL_SEARCH_USERS)
    public Call<MascotaResponse> search(@Query("q") String query, @Query("access_token") String accessToken);

    @POST(ConstantesRestApi.URL_LIKES)
    public Call<MascotaResponse> postLikeMedia(@Path("media_id") String mediaId);

    @POST(ConstantesRestApi.URL_FOLLOWS)
    public Call<MascotaResponse> postFollows(@Path("user_id") String userid);

}
