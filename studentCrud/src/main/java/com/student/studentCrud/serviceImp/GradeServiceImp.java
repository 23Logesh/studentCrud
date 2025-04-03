package com.student.studentCrud.serviceImp;

import com.student.studentCrud.Entity.GradeEntity;
import com.student.studentCrud.Entity.StudentEntity;
import com.student.studentCrud.dto.GradeDto;
import com.student.studentCrud.dto.StudentDto;
import com.student.studentCrud.repository.GradeRepo;
import com.student.studentCrud.service.GradeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GradeServiceImp implements GradeService {

    @Autowired
    private GradeRepo gradeRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public GradeDto saveGrade(GradeDto gradeDto) {
        GradeDto savedGrade = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
        log.info("[saveGrade] SUCCESS - Grade saved for Student ID: {}, Subject: {}, Score: {}",
                savedGrade.getStudent().getRollNumber(), savedGrade.getSubject(), savedGrade.getScore());
        return savedGrade;
    }

    @Override
    public GradeDto findGrade(long gradeId) {
        Optional<GradeEntity> gradeOpt = gradeRepo.findById(gradeId);
        log.info("[findGrade] SUCCESS - Found Grade with ID: {}", gradeId);
        return gradeOpt.map(this::convertEntityToDto)
                .orElseGet(() -> {
                    log.warn("[findGrade] FAILED - No Grade found with ID: {}", gradeId);
                    return null;
                });
    }

    @Override
    public List<GradeDto> findAllGrade() {
        List<GradeDto> grades = gradeRepo.findAll().stream().map(this::convertEntityToDto).toList();
        log.info("[findAllGrade] SUCCESS - Retrieved {} grades from database", grades.size());
        return grades;
    }

    @Override
    public GradeDto updateScore(long gradeId, double score) {
        GradeDto gradeDto = findGrade(gradeId);
        if (gradeDto != null) {
            gradeDto.setScore(score);
            GradeDto updatedGrade = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
            log.info("[updateGrade] SUCCESS - Updated Grade ID: {}, Score: {}",
                    updatedGrade.getId(), updatedGrade.getScore());
            return updatedGrade;
        } else {
            log.warn("[updateGrade] FAILED - No Grade found with ID: {} for updateScore", gradeId);
            return null;
        }

    }

    @Override
    public List<GradeDto> findGradeByStudent(long rollNumber) {
        return gradeRepo.findByStudentRollNumber(rollNumber)
                .stream()
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public GradeDto updateGrade(GradeDto gradeDto) {
        if (gradeDto == null || gradeDto.getId() == 0) {
            log.warn("[updateGrade] FAILED - Invalid grade data provided");
            return null;
        }

        if (gradeRepo.existsById(gradeDto.getId())) {
            GradeDto updatedGrade = convertEntityToDto(gradeRepo.save(convertDtoToEntity(gradeDto)));
            log.info("[updateGrade] SUCCESS - Updated Grade ID: {}, Subject: {}, Score: {}",
                    updatedGrade.getId(), updatedGrade.getSubject(), updatedGrade.getScore());
            return updatedGrade;
        } else {
            log.warn("[updateGrade] FAILED - No Grade found with ID: {} for update", gradeDto.getId());
            return null;
        }
    }

    @Override
    public GradeDto deleteGrade(long gradeId) {
        return gradeRepo.findById(gradeId)
                .map(grade -> {
                    gradeRepo.deleteById(gradeId);
                    log.info("[deleteGrade] SUCCESS - Deleted Grade with ID: {}", gradeId);
                    return convertEntityToDto(grade);
                })
                .orElseGet(() -> {
                    log.warn("[deleteGrade] FAILED - No Grade found with ID: {} for deletion", gradeId);
                    return null;
                });
    }

    private GradeDto convertEntityToDto(GradeEntity gradeEntity) {

        StudentDto studentDto = modelMapper.map(gradeEntity.getStudent(), StudentDto.class);

        GradeDto gradeDto = modelMapper.map(gradeEntity, GradeDto.class);
        gradeDto.setStudent(studentDto);
        return gradeDto;

    }

    private GradeEntity convertDtoToEntity(GradeDto gradeDto) {

        StudentEntity studentEntity = modelMapper.map(gradeDto.getStudent(), StudentEntity.class);

        GradeEntity gradeEntity = modelMapper.map(gradeDto, GradeEntity.class);
        gradeEntity.setStudent(studentEntity);
        return gradeEntity;
    }
}
