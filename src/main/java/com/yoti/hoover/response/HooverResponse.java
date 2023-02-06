package com.yoti.hoover.response;

import lombok.Data;

@Data
public class HooverResponse {
    private int[] coords;
    private Integer patches;
}
