package com.ssafy.michelin_de_hanyang.domain.user.dto;

import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaveRequest {
    private int lv;
    private int exp;
    private int money;
    private int hp;
    private int currentArm;
    private int currentVehicle;
    private double time;
    private List<String> learnedList;
    private List<User.Food> todayMenus;
    private int workers;
    private int onDutyWorkers;
    private List<User.Food> soldFood;
    private boolean noSound;
    private int questSuccess;
    private String lastScene;
    private List<Double> lastXy;
    private String currentField;
    
    private List<User.Inventory> inventory;


    public User toEntity(User user) {
        user.setLv(this.lv);
        user.setExp(this.exp);
        user.setMoney(this.money);
        user.setHp(this.hp);
        user.setCurrentArm(this.currentArm);
        user.setCurrentVehicle(this.currentVehicle);
        user.setTime(this.time);
        user.setLearnedList(this.learnedList);
        user.setTodayMenus(this.todayMenus);
        user.setWorkers(this.workers);
        user.setOnDutyWorkers(this.onDutyWorkers);
        user.setSoldFood(this.soldFood);
        user.setNoSound(this.noSound);
        user.setQuestSuccess(this.questSuccess);
        user.setLastScene(this.lastScene);
        user.setLastXy(this.lastXy);
        user.setInventory(this.inventory);
        user.setCurrentField(this.currentField);
        return user;
    }
}
