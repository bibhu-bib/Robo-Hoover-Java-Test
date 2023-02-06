package com.yoti.hoover.controller;

import com.yoti.hoover.model.HooverData;
import com.yoti.hoover.request.HooverRequest;
import com.yoti.hoover.response.HooverResponse;
import com.yoti.hoover.service.HooverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/1.0/yoti", produces = MediaType.APPLICATION_JSON_VALUE)
public class HooverController {
    private final HooverService hooverService;

    @PostMapping("/hoover")
    public ResponseEntity<HooverResponse> hoover(@Valid @RequestBody HooverRequest request) {
        final HooverResponse response = hooverService.hoovering(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hoover")
    public ResponseEntity<List<HooverData>> hoover() {
        final List<HooverData> response = hooverService.fetchRecords();
        return ResponseEntity.ok(response);
    }
}
