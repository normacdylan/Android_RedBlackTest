package com.august.redblack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private GameLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new GameLayout(this);
        setContentView(layout);

        layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();





                return false;
            }
        });
    }


}
