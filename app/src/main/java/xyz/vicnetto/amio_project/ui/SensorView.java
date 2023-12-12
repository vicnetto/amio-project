package xyz.vicnetto.amio_project.ui;

import android.widget.TextView;

import com.google.android.material.imageview.ShapeableImageView;

public class SensorView {

    private final TextView name;

    private final ShapeableImageView led;

    private final TextView value;

    public SensorView(TextView name, ShapeableImageView led, TextView value) {
        this.name = name;
        this.led = led;
        this.value = value;
    }

    public TextView getName() {
        return name;
    }

    public ShapeableImageView getLed() {
        return led;
    }

    public TextView getValue() {
        return value;
    }
}
