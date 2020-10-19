package cursos.com.petagramendpoint.restApi.deserializador;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cursos.com.petagramendpoint.pojo.Mascota;
import cursos.com.petagramendpoint.restApi.JsonKeys;
import cursos.com.petagramendpoint.restApi.model.MascotaResponse;

/**
 * Created .
 */
public class MascotaSearchDeserializador implements JsonDeserializer<MascotaResponse> {


    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        MascotaResponse contactoResponse = gson.fromJson(json,MascotaResponse.class);

        JsonArray contactoResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        contactoResponse.setMascotas(deserializarMascotaDeJson(contactoResponseData));

        return contactoResponse;
    }


    private List<Mascota> deserializarMascotaDeJson(JsonArray contactoResponseData){
        List<Mascota> mascotas = new ArrayList<>();

        for (int i = 0; i < contactoResponseData.size(); i++) {
            JsonObject contactoResponseDataObject = contactoResponseData.get(i).getAsJsonObject();

            String id = contactoResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto = contactoResponseDataObject.get(JsonKeys.USER_FULL_NAME).getAsString();
            String profilePicture = contactoResponseDataObject.get(JsonKeys.PROFILE_PICTURE).getAsString();

            Mascota mascota = new Mascota();
            mascota.setIdMascota(id);
            mascota.setNombre(nombreCompleto);
            mascota.setImagen(profilePicture);

            mascotas.add(mascota);

        }
        return mascotas;
    }

}
