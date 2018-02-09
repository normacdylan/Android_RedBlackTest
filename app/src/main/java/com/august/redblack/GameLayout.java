package com.august.redblack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mrx on 2017-12-22.
 */

public class GameLayout extends View {
    private float height, width;
    private Paint paint;
    private List<Node> nodes;
    private float treeWidth, treeX, treeY;


    public GameLayout(Context context) {
        super(context);
        paint = new Paint();
        paint.setAntiAlias(true);
        nodes = new ArrayList<>();
        //setupNodes(35);
        setupPartialNodes(30);
        treeWidth = 0.4f;
        treeX = 0.1f;
        treeY = 0.4f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setDimensions(canvas);

       // drawTree(canvas, nodes, treeWidth, treeX, treeY);
        drawPartialTree(canvas, nodes, treeWidth, treeX, treeY);
    }

    private void setupNodes(int amount) {
        Random r = new Random();
        int layer = 0;
        int position = 0;
        nodes.clear();

        for (int i=0;i<amount;i++) {
            Node n = new Node(r.nextInt(50), r.nextInt(2)==0? "Black":"Red");
            n.setLayer(layer);
            n.setPosition(position);

            nodes.add(n);

            if (position+1==Math.pow(2, layer)) {
                layer++;
                position = 0;
            } else
                position++;
        }
    }
    private void setupPartialNodes(int amount) {
        Random r = new Random();
        int layer = 0;
        int position = 0;
        nodes.clear();

        for (int i=0;i<amount;i++) {
            if (i>1 && (r.nextInt(5)==0 || nodes.get(getParent(nodes, i))==null))
                nodes.add(null);
            else {
                Node n = new Node(r.nextInt(50), r.nextInt(2)==0? "Black":"Red");
                n.setLayer(layer);
                n.setPosition(position);
                nodes.add(n);
            }

            if (position+1==Math.pow(2, layer)) {
                layer++;
                position = 0;
            } else
                position++;
        }
    }

    private void drawTree(Canvas canvas, List<Node> nodes, float width, float x, float y) {
        int depth = getTreeDepth(nodes);
        int treeWidth = getTreeWidth(nodes);
        float radius = width/(3*treeWidth);
        float synapseWidth = x(radius/4);

        for (Node n:nodes) {
            float slots = width/(power(2,n.getLayer())+1);
            int place = n.getPosition()+1;
            n.setX(x+(slots*place));
            n.setY(y+(n.getLayer()*radius*3));
        }
        for (int i=0;i<nodes.size();i++) {
            if (nodes.get(i).getLayer()<depth) {
                if (nodes.size()>getChild(nodes, i, "left"))
                    drawSynapse(canvas, nodes.get(i), nodes.get(getChild(nodes, i, "left")), synapseWidth);
                if (nodes.size()>getChild(nodes, i, "right"))
                    drawSynapse(canvas, nodes.get(i), nodes.get(getChild(nodes, i ,"right")), synapseWidth);
            }
        }

        for (Node n:nodes)
            drawNode(canvas, n, n.getX(), n.getY(), radius);
    }
    private void drawPartialTree(Canvas canvas, List<Node> nodes, float width, float x, float y) {
        int depth = getTreeDepth(nodes);
        int treeWidth = getTreeWidth(nodes);
        float radius = width/(3*treeWidth);
        float synapseWidth = x(radius/4);

        for (int i=0;i<nodes.size();i++) {
            if (nodes.get(i)!=null) {
                float slots = width/(power(2,nodes.get(i).getLayer())+1);
                int place = nodes.get(i).getPosition()+1;
                nodes.get(i).setX(x+(slots*place));
                nodes.get(i).setY(y+(nodes.get(i).getLayer()*radius*3));
            }
        }
        for (int i=0;i<nodes.size();i++) {
            if (nodes.get(i)!=null && nodes.get(i).getLayer()<depth) {
                if (nodes.size()>getChild(nodes, i, "left") && nodes.get(getChild(nodes, i , "left"))!=null)
                    drawSynapse(canvas, nodes.get(i), nodes.get(getChild(nodes, i, "left")), synapseWidth);
                if (nodes.size()>getChild(nodes, i, "right") && nodes.get(getChild(nodes, i , "right"))!=null)
                    drawSynapse(canvas, nodes.get(i), nodes.get(getChild(nodes, i ,"right")), synapseWidth);
            }
        }

        for (int i=0;i<nodes.size();i++) {
            if (nodes.get(i)!=null)
                drawNode(canvas, nodes.get(i), nodes.get(i).getX(), nodes.get(i).getY(), radius);
        }
    }

    private void drawNode(Canvas canvas, Node node, float x, float y, float radius) {
        paint.setColor(node.getColor());
        canvas.drawCircle(x(x), y(y), x(radius), paint);
    }
    private void drawSynapse(Canvas canvas, Node start, Node end, float width) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(width);
        canvas.drawLine(x(start.getX()), y(start.getY()), x(end.getX()), y(end.getY()), paint);
    }

    private void drawButton(Canvas canvas, float x, float y, float width, float height, String text) {
        paint.setColor(Color.GRAY);
        canvas.drawRect(x(x), y(y), x(x+width), y(y+height), paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(y(height/2.5f));
        canvas.drawText(text, x(x+0.1f*width), y(y+0.4f*height), paint);
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

    private int getTreeDepth(List<Node> nodes) {
        for (int i=nodes.size()-1;i>0;i--) {
            if (nodes.get(i)!=null)
                return nodes.get(i).getLayer();
        }
        return 0;
    }
    private int getTreeWidth(List<Node> nodes) {
        for (int i=nodes.size()-1;i>0;i--) {
            if (nodes.get(i)!=null)
                return (int) power(2, nodes.get(i).getLayer());
        }
        return 0;
    }
    private int getChild(List<Node> nodes, int node, String side) {
        int leftChild = node*2+1;
        return side.equals("left")? leftChild: leftChild+1;
    }
    private int getParent(List<Node> nodes, int node) {
        return node%2==1? (node-1)/2:(node-2)/2;
    }

    public float power(final float base, final int power) {
        float result = 1;
        for( int i = 0; i < power; i++ ) {
            result *= base;
        }
        return result;
    }

    public boolean touchedTree(float x, float y) {
        float radius = treeWidth/(getTreeWidth(nodes)*3);
        float treeHeight = getTreeDepth(nodes) * 3 * radius;

        return x>x(treeX) && x<x(treeX+treeWidth) && y>y(treeY) && y<y(treeY+treeHeight);
    }
    public void moveTree(float x, float y) {
        float radius = treeWidth/(getTreeWidth(nodes)*3);
        float treeHeight = getTreeDepth(nodes) * 3 * radius;

        treeX = x/width - 0.5f*treeWidth;
        treeY = y/height - 0.5f*treeHeight;
    }
    public void resizeTree(float amount) {
        float change = amount/width;
        treeWidth += 0.1f * change;
        treeWidth = Math.max(0.05f, treeWidth);
    }
    public void newTree() {
        Random r = new Random();
     //   setupNodes(r.nextInt(100)+2);
        setupPartialNodes(r.nextInt(100)+2);
    }

    private void setDimensions(Canvas canvas) {
        if (height==0) {
            height = canvas.getHeight();
            width = canvas.getWidth();
        }
    }
    private float x(float x) {
        return x*width;
    }
    private float y(float y) {return y*height;}
}
