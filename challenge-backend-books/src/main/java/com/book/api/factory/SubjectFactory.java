package com.book.api.factory;

import com.book.api.controller.http.response.SubjectResponse;
import com.book.api.database.entity.SubjectEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectFactory {

    public List<SubjectResponse> entityToResponse(final List<SubjectEntity> subjectEntities) {
        final List<SubjectResponse> subjectResponses = new ArrayList<>();

        subjectEntities.forEach(writer -> subjectResponses
            .add(entityToResponse(writer)));

        return subjectResponses;
    }

    public SubjectResponse entityToResponse(final SubjectEntity subjectEntity) {
        return SubjectResponse.builder()
            .id(subjectEntity.getId())
            .description(subjectEntity.getDescription())
            .build();
    }
}
