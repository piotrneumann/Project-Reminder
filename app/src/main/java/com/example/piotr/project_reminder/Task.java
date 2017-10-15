package com.example.piotr.project_reminder;

/**
 * Created by Piotr on 10.09.2017.
 */

public class Task {
    private int id;
    private String time;
    private String descripion;
    private int vibration;

    public Task(int id, String time, String descripion, int vibration) {
        this.id = id;
        this.time = time;
        this.descripion = descripion;
        this.vibration = vibration;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDescripion() {
        return descripion;
    }

    public int getVibration() {
        return vibration;
    }

    public String toString() {
        return id + " " + time + " " + descripion + " " + vibration;
    }
}
