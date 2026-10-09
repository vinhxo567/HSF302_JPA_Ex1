package com.hsf302.Chapter6.config;

import com.hsf302.Chapter6.entity.Major;
import com.hsf302.Chapter6.entity.Student;
import com.hsf302.Chapter6.repository.MajorRepository;
import com.hsf302.Chapter6.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final StudentRepository studentRepository;
    private final MajorRepository majorRepository;

    public DataInitializer(StudentRepository studentRepository, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.majorRepository = majorRepository;
    }

    @Override
    public void run(String... args) {
        if (majorRepository.count() == 0) {
            majorRepository.saveAll(List.of(
                    new Major("CNTT"),
                    new Major("KTPM"),
                    new Major("HTTT"),
                    new Major("ATTT"),
                    new Major("MMT")
            ));
        }

        if (studentRepository.count() > 0) {
            log.info("Bảng students đã có dữ liệu → bỏ qua seed");
            return;
        }

        List<Major> majors = majorRepository.findAll();
        
        studentRepository.saveAll(List.of(
                new Student("Nguyễn Văn An",  "an@fpt.edu.vn",    20, majors.get(0), 3.5),
                new Student("Trần Thị Bình",  "binh@fpt.edu.vn",  21, majors.get(1), 3.2),
                new Student("Lê Minh Cường",  "cuong@fpt.edu.vn", 19, majors.get(3), 3.8),
                new Student("Phạm Thị Dung",  "dung@fpt.edu.vn",  22, majors.get(2), 2.9)
        ));
        log.info("Đã seed {} sinh viên", studentRepository.count());
    }
}