package com.hooptap.sdkbrandclub.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 13/7/15.
 */
public class HooptapQuestUserStatus {
    private HooptapQuestStep step;
    private boolean started;
    private boolean active;
    private boolean finished;
    private ArrayList<String> completed_steps = new ArrayList<>();

    public HooptapQuestUserStatus(String jsonObj) {
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("started"))
                this.started = json.getBoolean("started");
            if (!json.isNull("active"))
                this.active = json.getBoolean("active");
            if (!json.isNull("finished"))
                this.finished = json.getBoolean("finished");
            if (!json.isNull("current_step")) {
                JSONObject stepJson = json.getJSONObject("current_step");
                step = new HooptapQuestStep(stepJson.toString());
            }
            if (!json.isNull("completed_steps")) {
                JSONArray stepsArray = json.getJSONArray("completed_steps");
                for (int i = 0; i < stepsArray.length(); i++) {
                    completed_steps.add(stepsArray.getString(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HooptapQuestStep getStep() {
        return step;
    }

    public void setStep(HooptapQuestStep step) {
        this.step = step;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<String> getCompleted_steps() {
        return completed_steps;
    }

    public void setCompleted_steps(ArrayList<String> completed_steps) {
        this.completed_steps = completed_steps;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}