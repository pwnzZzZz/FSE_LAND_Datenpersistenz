package dataaccess;

import domain.Student;

import java.util.List;

public interface MyStudentRepository extends BaseRepository<Student, Long>{
    List<Student> findAllStudentsByFirstName(String vn);
    List<Student> findAllStudentsByLastName(String nn);
    List<Student> findAllStudentsByBirthYear(String year);
}
