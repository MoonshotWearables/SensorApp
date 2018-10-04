package com.example.anjie.sensorapp.Model;

/**
 * Created by anjie on 2018/9/5.
 */

public class AccData {
    float x;
    float y;
    float z;
    long time;

    public AccData() {
        x = y = z = 0;
        time = 0;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
