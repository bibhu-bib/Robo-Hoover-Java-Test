package com.yoti.hoover.request;

import com.yoti.hoover.validation.CoordinateConstraint;
import com.yoti.hoover.validation.InstructionConstraint;
import lombok.Data;

import java.util.List;

@Data
public class HooverRequest {
    @InstructionConstraint(message = "Invalid instructions")
    private String instructions;

    @CoordinateConstraint(message = "Invalid hoover roomSize")
    private int[] roomSize;
    private List<int[]> patches;

    @CoordinateConstraint(message = "Invalid hoover coordinates")
    private int[] coords;
}