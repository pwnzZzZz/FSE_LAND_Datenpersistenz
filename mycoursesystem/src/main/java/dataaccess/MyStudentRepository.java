package dataaccess;

import domain.Course;
import domain.Student;

import java.sql.Date;
import java.util.List;

public interface MyStudentRepository extends BaseRepository<Student, Long>{
    List<Student> findAllStudentsBySureName(String vn);
    List<Student> findAllStudentsByLastName(String nn);
    List<Student> findStudentById(Long id);
}
