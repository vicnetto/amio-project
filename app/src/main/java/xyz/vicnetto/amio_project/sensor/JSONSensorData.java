package xyz.vicnetto.amio_project.sensor;

import java.util.List;

public class JSONSensorData {
    public List<JSONSensor> data;

    public static class JSONSensor {
        public long timestamp;
        public String label;
        public float value;
        public float mote;
    }
}
