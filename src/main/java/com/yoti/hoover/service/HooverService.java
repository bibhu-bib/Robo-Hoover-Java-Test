package com.yoti.hoover.service;

import com.yoti.hoover.model.HooverData;
import com.yoti.hoover.repository.HooverRepository;
import com.yoti.hoover.request.HooverRequest;
import com.yoti.hoover.response.HooverResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HooverService {
    private final HooverRepository hooverRepository;

    public List<HooverData> fetchRecords() {
        return hooverRepository.findAll();
    }

    public HooverResponse hoovering(HooverRequest request) {
        HooverData hooverData = hooverRepository.save(HooverData.builder()
                .request(request)
                .build());

        Room room = new Room(request.getRoomSize()[0], request.getRoomSize()[1], request.getPatches());
        Hoover hoover = new Hoover(request.getCoords()[0], request.getCoords()[1]);
        HooverResponse response = navigate(room, hoover, request.getInstructions());
        hooverData.setResponse(response);

        hooverRepository.save(hooverData);
        return response;
    }

    protected HooverResponse navigate(Room room, Hoover hoover, String instructions) {
        final char[] charArray = instructions.toCharArray();
        for (char instruction : charArray) {
            hoover.move(Direction.getType(instruction), room);
        }

        final HooverResponse hooverResponse = new HooverResponse();
        hooverResponse.setCoords(new int[]{hoover.getX(), hoover.getY()});
        hooverResponse.setPatches(room.getCleanPatches());
        return hooverResponse;
    }

}
