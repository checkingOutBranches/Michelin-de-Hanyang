package com.ssafy.michelin_de_hanyang.domain.user.entity;

import com.ssafy.michelin_de_hanyang.domain.auth.dto.UserDto;
import com.ssafy.michelin_de_hanyang.domain.user.dto.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private Role role;
    private String refreshToken;

    private int lv;
    private int exp;
    private int money;
    private int hp;
    private int currentArm;
    private int currentVehicle;
    private double time;
    private List<String> learnedList;
    private List<Food> todayMenus;
    private int workers;
    private int onDutyWorkers;
    private List<Food> soldFood;
    private boolean noSound;
    private int questSuccess;
    private String lastScene;
    private List<Double> lastXy;
    private List<Inventory> inventory;
    private String currentField;

    public static class Inventory{
        public String code;
        public int count;
        public int idx;
    }
    public static class Food{
        public String code;
        public int count;
    }
    @DBRef
    private List<String> boardIds = new ArrayList<>();
    @DBRef
    private List<String> replyIds = new ArrayList<>();

    public void authorizeUser() {
        this.role = Role.USER;
    }
    public void passwordEncode(BCryptPasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }
    public static User getUser(UserDto user){
        return User.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .refreshToken(user.getRefreshToken())
                .lv(user.getLv())
                .exp(user.getExp())
                .money(user.getMoney())
                .hp(user.getHp())
                .currentArm(user.getCurrentArm())
                .currentVehicle(user.getCurrentVehicle())
                .time(user.getTime())
                .learnedList(user.getLearnedList())
                .todayMenus(user.getTodayMenus())
                .workers(user.getWorkers())
                .onDutyWorkers(user.getOnDutyWorkers())
                .soldFood(user.getSoldFood())
                .questSuccess(user.getQuestSuccess())
                .lastXy(user.getLastXy())
                .inventory(user.getInventory())
                .boardIds(user.getBoardIds())
                .replyIds(user.getReplyIds())
                .currentField(user.getCurrentField())
                .build();
    }
}

