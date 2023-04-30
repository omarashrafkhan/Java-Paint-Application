package Shapes;

import java.awt.*;
import java.util.ArrayList;

public class FreeDraw extends Shape{

    private ArrayList<Circle> path;

    public ArrayList<Circle> getPath() {
        return path;
    }

    public void incrementPath(int x, int y){
        Circle pathCircle = new Circle(x,y,fill,null,0);
        pathCircle.setRadius(strokeVal);
        path.add(pathCircle);
    }

    public FreeDraw(int x, int y, Color fill, int strokeVal){

        this.fill = fill;
        this.strokeVal = strokeVal;
        path = new ArrayList<>();
        Circle firstCircle = new Circle(x,y,fill,null,0);
        firstCircle.setRadius(strokeVal);
        path.add(firstCircle);

    }



    @Override
    public void draw(Graphics g) {
        g.setColor(fill);

        if(strokeVal>0){
            for(int i =0; i< path.size();i++){
                if(i+1< path.size()-1){

                    Graphics2D  g2d = (Graphics2D) g.create();
                    g2d.setStroke(new BasicStroke(strokeVal*2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

                    Point current =new Point( path.get(i).x, path.get(i).y);
                    Point nextPoint =new Point( path.get(i+1).x, path.get(i+1).y);
                    g2d.drawLine(current.x,current.y,nextPoint.x,nextPoint.y);
                    g2d.dispose();
                }

                path.get(i).draw(g);
            }

        }






    }


}
