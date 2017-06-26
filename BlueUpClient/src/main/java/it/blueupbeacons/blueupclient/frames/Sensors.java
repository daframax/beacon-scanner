package it.blueupbeacons.blueupclient.frames;

import org.json.JSONException;
import org.json.JSONObject;

import it.blueupbeacons.blueupclient.Frame;

/**
 * Created by massimo on 22/06/17.
 */

public final class Sensors extends Frame {
    double temperature, humidity, pressure;

    @Override
    public Technology technology() {
        return Technology.Sensors;
    }

    @Override
    public Type type() {
        return Type.Sensors;
    }

    @Override
    public boolean isUpdatable() {
        return true;
    }

    public Sensors(byte[] data) {
        super(data);
    }

    @Override
    protected void parse(byte[] data) {
        Frame.ByteReader reader = new ByteReader(data, true);

        // Skip Temp UUID
        reader.skipBytes(2);

        temperature = ((double) reader.readUInt16()) * .01;

        // Skip Humidity UUID
        reader.skipBytes(2);

        humidity = ((double) reader.readUInt16()) * .01;

        // Skip Pressure UUID
        reader.skipBytes(2);

        pressure = ((double) reader.readUInt32()) * .1;
    }

    @Override
    protected JSONObject jsonData() {
        JSONObject object = new JSONObject();
        try {
            object.put("temperature", temperature);
            object.put("humidity", humidity);
            object.put("pressure", pressure);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
}
