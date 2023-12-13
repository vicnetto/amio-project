package xyz.vicnetto.amio_project.sensor;

import java.util.List;

public class SensorDataHolder {
    public List<SensorInformation> data;

    private static final SensorDataHolder SENSOR_DATA_HOLDER = new SensorDataHolder();

    public static SensorDataHolder getInstance() {
        return SENSOR_DATA_HOLDER;
    }

    public static void updateSingleton(SensorDataHolder sensorSingleton) {
        getInstance().data = sensorSingleton.data;
    }

    public static class SensorInformation {
        public long timestamp;
        public String label;
        public float value;
        public float mote;
    }
}
