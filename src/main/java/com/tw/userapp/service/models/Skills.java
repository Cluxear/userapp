package com.tw.userapp.service.models;

import java.util.ArrayList;
import java.util.List;

public class Skills {

    private List<Skill> skillList = new ArrayList<>();

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
    }

    public void addSkill( Skill skill) {

        skillList.add(skill);
    }
}
