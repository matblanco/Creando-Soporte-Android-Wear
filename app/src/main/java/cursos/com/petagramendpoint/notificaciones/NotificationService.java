package cursos.com.petagramendpoint.notificaciones;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.view.Gravity;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import cursos.com.petagramendpoint.R;
import cursos.com.petagramendpoint.activity.MainActivity;

import static cursos.com.petagramendpoint.notificaciones.VerPerfil.DAR_FOLLOW;
import static cursos.com.petagramendpoint.notificaciones.VerPerfil.VER_PERFIL;
import static cursos.com.petagramendpoint.notificaciones.VerPerfil.VER_USUARIO;

/**
 * Created by Usuario on 30/10/2017.
 */
public class NotificationService extends FirebaseMessagingService {
    public static final String TAG = "FIREBASE";
    public static final int NOTIFICATIOIN_ID = 001;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        enviarNotificacion(remoteMessage);

    }

    public void enviarNotificacion(RemoteMessage remoteMessage){

        Intent i1 = new Intent(this , VerPerfil.class);
        i1.setAction(VER_PERFIL);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 0, i1, 0);
        Intent i2 = new Intent(this , VerPerfil.class);
        i2.setAction(DAR_FOLLOW);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 0, i2, 0);
        Intent i3 = new Intent(this , VerPerfil.class);
        i3.setAction(VER_USUARIO);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, 0, i3, 0);

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Action action1 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_perfil,
                        getString(R.string.texto_ver_mi_perfil), pendingIntent1)
                        .build();
        NotificationCompat.Action action2 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_poke,
                        getString(R.string.texto_dar_follow), pendingIntent2)
                        .build();
        NotificationCompat.Action action3 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_historia,
                        getString(R.string.texto_ver_usuario), pendingIntent3)
                        .build();

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),
                                R.drawable.notification_fondo))
                        .setGravity(Gravity.CENTER_VERTICAL)
                        ;



        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_action_name)
                .setContentTitle("Notificacion")
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSound(sonido)
                .setContentIntent(pendingIntent1)
                .setContentIntent(pendingIntent2)
                .setContentIntent(pendingIntent3)

                .extend(wearableExtender.addAction(action1))
                .extend(wearableExtender.addAction(action2))
                .extend(wearableExtender.addAction(action3))
                ;


        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATIOIN_ID, notificacion.build());
    }
}
