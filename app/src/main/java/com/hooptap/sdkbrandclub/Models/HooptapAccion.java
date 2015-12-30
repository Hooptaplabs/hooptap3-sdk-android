package com.hooptap.sdkbrandclub.Models;

import com.hooptap.brandclub.model.InputActionDoneModel;

/**
 * Created by root on 3/12/15.
 */
public class HooptapAccion extends InputActionDoneModel {
    String target_id;
    String interaction_data;

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public String getInteraction_data() {
        return interaction_data;
    }

    public void setInteraction_data(String interaction_data) {
        this.interaction_data = interaction_data;
    }
}
