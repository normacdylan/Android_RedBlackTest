package com.august.redblack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import org.w3c.dom.Node;

/**
 * Created by mrx on 2017-12-22.
 */

public class GameLayout extends View {
    private float height, width;
    private Paint paint;


    public GameLayout(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setDimensions(canvas);




    }

    private void drawTree(Canvas canvas) {

    }

    private void drawNode(Canvas canvas, Node node, float x, float y, float radius) {
    //    paint.setColor(node.getColor());
        canvas.drawCircle(x, y, radius, paint);
    }

    private void drawButton(Canvas canvas, float x, float y, float width, float height, String text) {
        canvas.drawRect(x, y, width, height, paint);
        canvas.drawText(text, x+0.1f*width, y+0.4f*height, paint);
    }

    private void drawInfo(Canvas canvas, float x, float y, float xMargin, float yMargin) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        float currentX = x;

        canvas.drawText("Next node in", x, y, paint);
        currentX += paint.measureText("Next node in")+xMargin;

        canvas.drawText("Score", currentX, y, paint);
        currentX += paint.measureText("Score")+xMargin;

        canvas.drawText("Is in order", currentX, y, paint);

        float currentY = y+paint.getTextSize()+yMargin;
        canvas.drawRect(x, currentY, paint.measureText("Next node in"), paint.getTextSize()*2, paint);



    }

    private void setDimensions(Canvas canvas) {
        if (height==0) {
            height = canvas.getHeight();
            width = canvas.getWidth();
        }
    }
    private float x(float x) {
        return x/width;
    }
    private float y(float y) {return y/height;}
}
