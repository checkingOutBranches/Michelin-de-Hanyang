package com.ssafy.michelin_de_hanyang.domain.auth.dto;

import com.ssafy.michelin_de_hanyang.domain.user.dto.Role;
import com.ssafy.michelin_de_hanyang.domain.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    @Id
    private String id;
    private String email;
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
    private List<User.Food> todayMenus;
    private int workers;
    private int onDutyWorkers;
    private List<User.Food> soldFood;
    private boolean noSound;
    private int questSuccess;
    private String lastScene;
    private List<Double> lastXy;
    private List<User.Inventory> inventory;
    private String currentField;
    private List<String> boardIds = new ArrayList<>();
    private List<String> replyIds = new ArrayList<>();
    public static UserDto getUser(User user){
        return UserDto.builder()
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
                .currentField(user.getCurrentField())
                .boardIds(user.getBoardIds())
                .replyIds(user.getReplyIds())
                .build();
    }
}

