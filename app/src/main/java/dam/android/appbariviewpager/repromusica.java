package dam.android.appbariviewpager;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by alexis on 22/02/2016.
 */
public class repromusica extends Thread {
    private MediaPlayer mediaPlayer;

    public repromusica(Context ctt) {
        mediaPlayer = MediaPlayer.create(ctt, R.raw.cancion1);
    }

    @Override
    public void run() {
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void stopmusic() {
        Thread.interrupted();
        mediaPlayer.stop();
    }

}
