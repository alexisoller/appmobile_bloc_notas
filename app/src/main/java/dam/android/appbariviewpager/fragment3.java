package dam.android.appbariviewpager;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.Button;


/**
 * Created by vesprada on 20/01/16.
 */
public class fragment3 extends Fragment {
    private View rootView;
    private Button btn;
    private ImageView img;
    private Bitmap bmp;
    private final static int cons=0;
    private static savebitmap sbb;
    private static Context ct;
    private static NotificationManager notifyMgr;

    public static Fragment newInstance(savebitmap sb,Context ctt) {
        fragment3 fragment = new fragment3();
        sbb=sb;
        ct=ctt;
        notifyMgr=(NotificationManager) ct.getSystemService(ct.NOTIFICATION_SERVICE);
        return fragment;
    }

    public fragment3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment3, container, false);
        btn = (Button) rootView.findViewById(R.id.btncamara);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, cons);
            }
        });
        img=(ImageView)rootView.findViewById(R.id.imagen);
        if(sbb.imagensi){
            img.setImageBitmap(sbb.bp);
        }
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            Bundle ext =data.getExtras();
            bmp=(Bitmap)ext.get("data");
            img.setImageBitmap(bmp);
            sbb.bp=bmp;
            sbb.imagensi=true;
            notification(1,"Imagen capturada","La imagen ha sido capturada");
        }
    }
    public void notification(int id, String titulo, String contenido) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(ct)
                        .setSmallIcon(R.drawable.notificacion)
                        //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notificacion))
                        .setLargeIcon(bmp)
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(Color.BLACK);



        notifyMgr.notify(id, builder.build());
    }


}
