package com.yoti.hoover.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@AllArgsConstructor
@Slf4j
public class Hoover {
    private int x;
    private int y;

    public void move(final Direction direction, final Room room) {
        if (direction == Direction.NORTH && this.y < room.getY()) {
            this.y += 1;
        } else if (direction == Direction.SOUTH && this.y > 0) {
            this.y -= 1;
        } else if (direction == Direction.EAST && this.x < room.getX()) {
            this.x += 1;
        } else if (direction == Direction.WEST && this.x > 0) {
            this.x -= 1;
        }

        for (int[] patch : room.getPatches()) {
            if (patch[0] == this.x && patch[1] == this.y) {
                room.getPatches().remove(patch);
                log.info("found patches at  {} {}", this.x, this.y);
                room.setCleanPatches(room.getCleanPatches() + 1);
                break;
            }
        }
    }
}




