package com.example.sensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView salida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        salida = (TextView) findViewById(R.id.salida);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: listaSensores){
            log(sensor.getName());
        }

        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if(!listaSensores.isEmpty()){
            Sensor orientationSensor = listaSensores.get(0);
            sensorManager.registerListener(this, orientationSensor, SensorManager.SENSOR_DELAY_UI);
        }
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(!listaSensores.isEmpty()){
            Sensor acelerometerSensor = listaSensores.get(0);
            sensorManager.registerListener(this, acelerometerSensor, SensorManager.SENSOR_DELAY_UI);
        }
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if(!listaSensores.isEmpty()){
            Sensor magneticSensor = listaSensores.get(0);
            sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_UI);
        }
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(!listaSensores.isEmpty()){
            Sensor temperatureSensor = listaSensores.get(0);
            sensorManager.registerListener(this, temperatureSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    private void log(String string){
        salida.append(string + "\n");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //Cada sensor puede provocar que un thread principal
        //pase por aquí asi que sincronizamos el acceso
        synchronized(this){
            switch (sensorEvent.sensor.getType()){
                case Sensor.TYPE_ORIENTATION:
                    for(int i=0; i<3; i++){
                        log("Orientation " + i + ": " + sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_ACCELEROMETER:
                    for(int i=0; i<3; i++){
                        log("Acelerometro " + i + ": " + sensorEvent.values[i]);
                    }
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for(int i=0; i<3; i++){
                        log("Magnetismo " + i + ": " + sensorEvent.values[i]);
                    }
                    break;
                default:
                    for(int i=0; i<sensorEvent.values.length; i++){
                        log("Temperatura " + i + ": " + sensorEvent.values[i]);
                    }
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
