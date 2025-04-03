package com.student.studentCrud.service;

import com.student.studentCrud.dto.GradeDto;

import java.util.List;

public interface GradeService {
    GradeDto saveGrade(GradeDto gradeDto);

    GradeDto findGrade(long gradeId);

    GradeDto updateGrade(GradeDto gradeDto);

    GradeDto deleteGrade(long gradeId);

    List<GradeDto> findAllGrade();

    GradeDto updateScore(long gradeId, double score);

    List<GradeDto> findGradeByStudent(long rollNumber);
}
