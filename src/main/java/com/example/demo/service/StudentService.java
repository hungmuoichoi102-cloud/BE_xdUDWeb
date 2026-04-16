package com.example.demo.service;

import com.example.demo.entity.SinhVien;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<SinhVien> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<SinhVien> getStudentById(String id) {
        return studentRepository.findById(id);
    }

    public SinhVien createStudent(SinhVien student) {
        if(studentRepository.existsById(student.getMssv())){
            throw new RuntimeException("Lỗi: MSSV đã tồn tại");
        }
        return studentRepository.save(student);
    }

    public SinhVien updateStudent(String id, SinhVien studentDetails) {
        Optional<SinhVien> student = studentRepository.findById(id);
        if (student.isPresent()) {
            SinhVien existingStudent = student.get();
            existingStudent.setMssv(studentDetails.getMssv());
            existingStudent.setHo(studentDetails.getHo());
            existingStudent.setTen(studentDetails.getTen());
            existingStudent.setNgaysinh(studentDetails.getNgaysinh());
            existingStudent.setGioitinh(studentDetails.isGioitinh());
            existingStudent.setDiachi(studentDetails.getDiachi());
            existingStudent.setNghihoc(studentDetails.isNghihoc());
            existingStudent.setLop(studentDetails.getLop());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public void deleteStudent(String id) {
        studentRepository.deleteById(id);
    }
}
