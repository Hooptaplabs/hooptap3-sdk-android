package com.hooptap.sdkbrandclub.Models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 10/7/15.
 */
public class HooptapQuest extends HooptapItem {

    private boolean finished;
    private HooptapQuestUserStatus user_status;
    private ArrayList<HooptapQuestStep> steps = new ArrayList<>();
    private ArrayList<HooptapItem> rewards = new ArrayList<>();

    public HooptapQuest(String jsonObj) {
        super(jsonObj);
        try {
            JSONObject json = new JSONObject(jsonObj);
            if (!json.isNull("finished"))
                this.finished = json.getBoolean("finished");
            if (!json.isNull("steps")) {
                JSONArray stepsArray = json.getJSONArray("steps");
                for (int i = 0; i < stepsArray.length(); i++) {
                    JSONObject stepJson = stepsArray.getJSONObject(i);
                    steps.add(new HooptapQuestStep(stepJson.toString()));
                }
            }
            if (!json.isNull("rewards")) {
                JSONArray rewardsArray = json.getJSONArray("rewards");
                for (int i = 0; i < rewardsArray.length(); i++) {

                    String typeReward = "Reward";
                    if (!rewardsArray.getJSONObject(i).isNull("itemType"))
                        typeReward = rewardsArray.getJSONObject(i).getString("itemType");

                    try {
                        Class<HooptapItem> cls;
                        cls = (Class<HooptapItem>) Class.forName("com.hooptap.sdkbrandclub.Models.Hooptap" + typeReward);
                        HooptapItem cl = cls.getDeclaredConstructor(String.class).newInstance(rewardsArray.getJSONObject(i).toString());
                        cl.setType(typeReward);
                        rewards.add(cl);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!json.isNull("user_status")) {
                user_status = new HooptapQuestUserStatus(json.getJSONObject("user_status").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public HooptapQuestUserStatus getUser_status() {
        return user_status;
    }

    public ArrayList<HooptapQuestStep> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<HooptapQuestStep> steps) {
        this.steps = steps;
    }

    public ArrayList<HooptapItem> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<HooptapItem> rewards) {
        this.rewards = rewards;
    }

    public void setUser_status(HooptapQuestUserStatus user_status) {
        this.user_status = user_status;
    }
}
