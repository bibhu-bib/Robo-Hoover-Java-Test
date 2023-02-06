package com.yoti.hoover.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Room {
    private final int x;
    private final int y;
    private final List<int[]> patches;
    private int cleanPatches;
}