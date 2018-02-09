package com.august.redblack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class GameActivity extends AppCompatActivity {

    private GameLayout layout;
    private float pointerDistance;

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

                int action = event.getAction() & MotionEvent.ACTION_MASK;

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        float xDistance = Math.abs(event.getX(0)-event.getX(1));
                        float yDistance = Math.abs(event.getY(0)-event.getY(1));
                        pointerDistance = (float)Math.sqrt(xDistance*xDistance + yDistance*yDistance);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int touchCounter = event.getPointerCount();
                            switch (touchCounter) {
                                case 1:
                                    if (layout.touchedTree(x, y)) {
                                        layout.moveTree(x, y);
                                        layout.invalidate();
                                    }
                                    break;
                                case 2:
                                    float newXDistance = Math.abs(event.getX(0)-event.getX(1));
                                    float newYDistance = Math.abs(event.getY(0)-event.getY(1));
                                    float newDistance = (float)Math.sqrt(newXDistance*newXDistance + newYDistance*newYDistance);
                                    layout.resizeTree(newDistance - pointerDistance);
                                    layout.invalidate();
                                    break;
                                case 3:
                                    layout.newTree();
                                    layout.invalidate();
                            }
                        break;
                }

                return true;
            }
        });
    }


}
