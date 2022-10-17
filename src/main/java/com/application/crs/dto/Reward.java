package com.application.crs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Reward {

    @JsonProperty(value = "rewards_by_month")
    private Map<String, Integer> rewardsByMonth = new HashMap<>();

    @JsonProperty(value = "total_reward")
    private Integer totalReward = 0;
}
