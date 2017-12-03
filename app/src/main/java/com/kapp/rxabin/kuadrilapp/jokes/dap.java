package com.kapp.rxabin.kuadrilapp.jokes;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kapp.rxabin.kuadrilapp.R;

public class dap extends AppCompatActivity {

    private MediaPlayer mp;
    private boolean funny = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dap);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(getResources().getColor(R.color.transparent));
            }
        mp = MediaPlayer.create(this, R.raw.keygen);
        mp.start();
        funny = true;
    }



    public void keygen(View v){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getResources().getDrawable(R.drawable.kuadrilapp_gradient);
            Drawable transparent = getResources().getDrawable(R.color.transparent);

            if (funny) {
                window.setBackgroundDrawable(background);
                funny = !funny;
            } else {
                window.setBackgroundDrawable(transparent);
                funny = !funny;
            }
        }
    }


    @Override
    protected void onPause(){
        super.onPause();
        if (mp.isPlaying()){
            mp.pause();
        }


    }


    @Override
    protected void onResume(){
        super.onResume();
        if (funny) mp.start();
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.stop();

    }
}
