package com.yoti.hoover.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoti.hoover.model.HooverData;
import com.yoti.hoover.request.HooverRequest;
import com.yoti.hoover.response.HooverResponse;
import com.yoti.hoover.service.HooverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = HooverController.class)
class HooverControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private HooverService hooverService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testHoover() throws Exception {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setCoords(new int[]{1, 2});
        hooverRequest.setRoomSize(new int[]{5, 5});

        List<int[]> patches = List.of(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}, new int[]{1, 3});
        hooverRequest.setPatches(patches);
        hooverRequest.setInstructions("NNESEESWNWWWS");
        String hooverRequestJson = objectMapper.writeValueAsString(hooverRequest);

        HooverResponse hooverResponse = new HooverResponse();
        hooverResponse.setCoords(new int[]{0, 2});
        hooverResponse.setPatches(2);
        when(hooverService.hoovering(any(HooverRequest.class))).thenReturn(hooverResponse);

        mockMvc.perform(post("/api/1.0/yoti/hoover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hooverRequestJson.getBytes()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coords.[0]").value(0))
                .andExpect(jsonPath("$.coords.[1]").value(2))
                .andExpect(jsonPath("$.patches").value(2));
    }

    @Test
    void testHooverWhenInvalidInstruction() throws Exception {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setCoords(new int[]{1, 2});
        hooverRequest.setRoomSize(new int[]{5, 5});

        List<int[]> patches = List.of(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}, new int[]{1, 3});
        hooverRequest.setPatches(patches);
        hooverRequest.setInstructions("NNESEESWNWWWSA8");
        String hooverRequestJson = objectMapper.writeValueAsString(hooverRequest);

        mockMvc.perform(post("/api/1.0/yoti/hoover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hooverRequestJson.getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].code").value("instructions"))
                .andExpect(jsonPath("$.errors.[0].message").value("Invalid instructions"));
    }

    @Test
    void testHooverWhenInvalidCoords() throws Exception {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setCoords(new int[]{1, 2, 4});
        hooverRequest.setRoomSize(new int[]{5, 5});

        List<int[]> patches = List.of(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}, new int[]{1, 3});
        hooverRequest.setPatches(patches);
        hooverRequest.setInstructions("NNESEESWNWWWS");
        String hooverRequestJson = objectMapper.writeValueAsString(hooverRequest);

        mockMvc.perform(post("/api/1.0/yoti/hoover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hooverRequestJson.getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].code").value("coords"))
                .andExpect(jsonPath("$.errors.[0].message").value("Invalid hoover coordinates"));
    }

    @Test
    void testHooverWhenInvalidRoomSize() throws Exception {
        HooverRequest hooverRequest = new HooverRequest();
        hooverRequest.setCoords(new int[]{1, 4});
        hooverRequest.setRoomSize(new int[]{5, 6, 5});

        List<int[]> patches = List.of(new int[]{1, 0}, new int[]{2, 2}, new int[]{2, 3}, new int[]{1, 3});
        hooverRequest.setPatches(patches);
        hooverRequest.setInstructions("NNESEESWNWWWS");
        String hooverRequestJson = objectMapper.writeValueAsString(hooverRequest);

        mockMvc.perform(post("/api/1.0/yoti/hoover")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(hooverRequestJson.getBytes()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.[0].code").value("roomSize"))
                .andExpect(jsonPath("$.errors.[0].message").value("Invalid hoover roomSize"));
    }

    @Test
    void testFetch_WhenDataExists() throws Exception {
        final InputStream inputStream = loadJsonFile("json/hoover-data.json");
        List<HooverData> hooverData = objectMapper.readValue(inputStream, new TypeReference<List<HooverData>>() {
        });

        when(hooverService.fetchRecords()).thenReturn(hooverData);
        mockMvc.perform(get("/api/1.0/yoti/hoover"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].request.instructions").value("NNESEESWNWWWS"))
                .andExpect(jsonPath("$.[0].request.roomSize").isArray())
                .andExpect(jsonPath("$.[0].request.roomSize", hasSize(2)))
                .andExpect(jsonPath("$.[0].request.patches").isArray())
                .andExpect(jsonPath("$.[0].request.coords").isArray())
                .andExpect(jsonPath("$.[0].request.coords", hasSize(2)));
    }

    public InputStream loadJsonFile(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}