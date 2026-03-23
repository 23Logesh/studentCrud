package com.student.studentCrud.util;

import com.student.studentCrud.dto.StudentDto;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
public class ResponseStructure<T> {
    T data;
    int status;
    String message;

    public <K, V> ResponseStructure<Map<K, V>> getMapResponseStructure(StudentDto studentDto, Map<K, V> reportMap) {

        String message = "Student Details: " +
                "Roll No: " + studentDto.getRollNumber() +
                ", Name: " + studentDto.getName() +
                ", Email: " + studentDto.getEmail() +
                ", Class: " + studentDto.getClassName() +
                ", GPA: " + studentDto.getGpa() +
                ", Performance: " + studentDto.getPerformanceLevel() +
                ", Rank: " + studentDto.getRank();
        ResponseStructure<Map<K, V>> response = new ResponseStructure<>();
        response.setData(reportMap);
        response.setMessage(message);
        response.setStatus(HttpStatus.OK.value());
        return response;
    }
}