package dam.android.appbariviewpager;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by alexis on 21/02/2016.
 */
public class inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();

        Thread timer= new Thread( ){

            @Override
            public void run() {
                super.run();
                
                try{
                    sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                } finally{
                    Intent intent = new Intent(inicio.this, MainActivity.class);
                    startActivity(intent);

                }


            }

            };
        timer.start();


    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }
}
