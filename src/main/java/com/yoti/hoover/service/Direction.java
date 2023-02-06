package com.yoti.hoover.service;

import java.util.Arrays;

public enum Direction {

    NORTH('N'),
    SOUTH('S'),
    EAST('E'),
    WEST('W');

    private final char typeValue;

    Direction(char e) {
        this.typeValue = e;
    }

    public static Direction getType(char typeName) {
        return Arrays.stream(Direction.values())
                .filter(f -> f.getTypeValue() == typeName)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private char getTypeValue() {
        return typeValue;
    }

}
