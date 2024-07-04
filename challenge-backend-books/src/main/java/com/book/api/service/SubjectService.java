package com.book.api.service;

import com.book.api.controller.http.response.SubjectResponse;

import java.util.List;

public interface SubjectService {

    List<SubjectResponse> getAllSubjects();
}
