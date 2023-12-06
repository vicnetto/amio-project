package xyz.vicnetto.amio_project.sensor;

import java.util.List;

public class SensorInformation {
    public List<CaptorInformation> data;

    public static class CaptorInformation {
        public long timestamp;
        public String label;
        public float value;
        public float mote;
    }
}
