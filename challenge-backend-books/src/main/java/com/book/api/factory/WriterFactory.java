package com.book.api.factory;

import com.book.api.controller.http.response.WriterResponse;
import com.book.api.database.entity.WriterEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WriterFactory {

    public List<WriterResponse> entityToResponse(final List<WriterEntity> writerEntities) {
        final List<WriterResponse> writerResponses = new ArrayList<>();

        writerEntities.forEach(writer -> writerResponses
            .add(entityToResponse(writer)));

        return writerResponses;
    }

    public WriterResponse entityToResponse(final WriterEntity writerEntity) {
        return WriterResponse.builder()
            .id(writerEntity.getId())
            .name(writerEntity.getName())
            .build();
    }
}
