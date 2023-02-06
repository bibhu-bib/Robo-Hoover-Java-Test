package com.yoti.hoover.service;

import com.yoti.hoover.model.HooverData;
import com.yoti.hoover.repository.HooverRepository;
import com.yoti.hoover.request.HooverRequest;
import com.yoti.hoover.response.HooverResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HooverServiceTest {

    @Mock
    HooverRepository hooverRepository;

    HooverService hooverService;

    @BeforeEach
    void setUp() {
        hooverService = new HooverService(hooverRepository);
    }

    @Test
    void testHoovering() {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setCoords(new int[]{1, 2});
        hooverRequest.setRoomSize(new int[]{5, 5});
        List<int[]> patches = new ArrayList<>();
        patches.add(new int[]{1, 0});
        patches.add(new int[]{2, 2});
        patches.add(new int[]{2, 3});
        patches.add(new int[]{1, 3});
        hooverRequest.setPatches(patches);
        hooverRequest.setInstructions("NNESEESWNWWWS");

        HooverData hooverData = HooverData.builder()
                .id("63ded0446a63f7092")
                .request(hooverRequest)
                .build();
        when(hooverRepository.save(any(HooverData.class))).thenReturn(hooverData);
        HooverResponse response = hooverService.hoovering(hooverRequest);

        assertEquals(response.getPatches(), Integer.valueOf(2));
        assertEquals(response.getCoords().length, 2);
        assertEquals(Arrays.toString(response.getCoords()), Arrays.toString(new int[]{0, 2}));
    }
}