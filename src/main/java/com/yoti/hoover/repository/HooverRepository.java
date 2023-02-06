package com.yoti.hoover.repository;

import com.yoti.hoover.model.HooverData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HooverRepository extends MongoRepository<HooverData, String> {

    HooverData save(HooverData hooverData);
}
