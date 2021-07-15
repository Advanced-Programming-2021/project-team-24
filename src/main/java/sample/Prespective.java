package sample;

import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Prespective extends Transition {

    ImageView node;
    double startAngle;
    double endAngle;
    double x;

    public Prespective(ImageView node,double startAngle,double endAngle) {
        setCycleDuration(Duration.millis(10000));
        setCycleCount(-1);
        this.node = node;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        x = startAngle;
        if(startAngle>endAngle) number*=-1;
    }
    int n = 100;

//    public void setPrespective(ImageView imageView, double x, double y, double angle) {
//        final double w = imageView.getFitWidth();
//        final double h = imageView.getFitHeight();
//        final double a = (2 * w + 2 * n) / (2 * n + w);
//        final double b = 2 - a;
//        double f = angle / 90;
//        PerspectiveTransform perspectiveTransform = new PerspectiveTransform();
//
//        perspectiveTransform.setUly(y + (f * (a - 1)) * h / 2);
//        perspectiveTransform.setUry(y + (f * (b - 1)) * h / 2);
//        perspectiveTransform.setLly(y + h - (f * (a - 1)) * h / 2);
//        perspectiveTransform.setLry(y + h - (f * (b - 1)) * h / 2);
//
//        perspectiveTransform.setUlx(x - (1 - Math.abs(f)) * w / 2);
//        perspectiveTransform.setUrx(x + (1 - Math.abs(f)) * w / 2);
//        perspectiveTransform.setLlx(x - (1 - Math.abs(f)) * w / 2);
//        perspectiveTransform.setLrx(x + (1 - Math.abs(f)) * w / 2);
//        imageView.setEffect(perspectiveTransform);
//    }

    int number = 1;
    int counter = 1;
    double speed  = 0.5 ;
    @Override
    protected void interpolate(double v) {
        twoPaneCards.setPrespective(node,0,0,x);
        x+=number*speed;
        counter++;
        if(Math.abs(x-endAngle)<0.1) {
            this.stop();
        }
    }

}
