package sample;

import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import view.Global;


public class CoinAnimation extends Transition {
    private ImageView imageView;
    private Label label;
    int count=0,rand;
    public CoinAnimation(ImageView imageView, Label label) {
        this.imageView = imageView;
        this.label = label;
        setCycleDuration(Duration.millis(2000));
        setCycleCount(-1);
        rand = Global.random.nextInt(250);
    }

    @Override
    protected void interpolate(double v) {
        int frame = (int) Math.floor(v * 20);
        if(frame==20) frame=19;
        imageView.setImage(new Image(getClass().getResourceAsStream("images/coin/" + frame + ".png")));
        count++;
        if(count==250+rand) {
            if(frame<=9)
                label.setText("You go first!");
            else
                label.setText("You go second!");
            this.stop();
        }
    }
}
