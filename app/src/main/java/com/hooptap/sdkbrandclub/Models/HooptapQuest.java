package com.hooptap.sdkbrandclub.Models;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 24/2/16.
 */
public class HooptapQuest<T> extends HooptapItem{
    private boolean sequential;
    private boolean active;
    private boolean finished;
    private ArrayList<HooptapStep>steps;
    private ArrayList<T>rewards;

    public boolean isSequential() {
        return sequential;
    }

    public void setSequential(boolean sequential) {
        this.sequential = sequential;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public ArrayList<HooptapStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<HooptapStep> steps) {
        this.steps = steps;
    }

    public ArrayList<T> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<T> rewards) {
        this.rewards = rewards;
    }
}
