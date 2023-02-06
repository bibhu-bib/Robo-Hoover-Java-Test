package com.yoti.hoover.model;

import com.yoti.hoover.request.HooverRequest;
import com.yoti.hoover.response.HooverResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "hoover_data")
public class HooverData {
    @Id
    private String id;
    private HooverRequest request;
    private HooverResponse response;
}
