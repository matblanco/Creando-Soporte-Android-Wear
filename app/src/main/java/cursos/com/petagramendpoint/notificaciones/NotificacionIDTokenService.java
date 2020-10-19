package cursos.com.petagramendpoint.notificaciones;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Usuario on 30/10/2017.
 */
public class NotificacionIDTokenService extends FirebaseInstanceIdService {

        private static final String TAG = "FIREBASE_TOKEN";
    String id_dispositivo;
        @Override
        public void onTokenRefresh() {
            //super.onTokenRefresh();
            Log.d(TAG, "Solicitando Token");
            id_dispositivo = FirebaseInstanceId.getInstance().getToken();

            enviarTokenRegristro();
        }

    private void enviarTokenRegristro() {
        Log.d(TAG, id_dispositivo);
    }


}



