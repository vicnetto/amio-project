package xyz.vicnetto.amio_project.ui;

import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class SensorView {

    private TextView name;

    private ShapeableImageView led;

    private TextView value;

    public SensorView(TextView name, ShapeableImageView led, TextView value) {
        this.name = name;
        this.led = led;
        this.value = value;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public ShapeableImageView getLed() {
        return led;
    }

    public void setLed(ShapeableImageView led) {
        this.led = led;
    }

    public TextView getValue() {
        return value;
    }

    public void setValue(TextView value) {
        this.value = value;
    }
}
