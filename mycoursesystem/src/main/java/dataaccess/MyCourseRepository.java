package dataaccess;

import domain.Course;
import domain.CourseType;

import java.sql.Date;
import java.util.List;

/**
 * Das DAO-Interface erbt vom BaseRepository (Basis-DAO)
 * Enthält Kursspezifische Methoden
 * Course = Entity
 * Long = Id-Typ
 */

public interface MyCourseRepository extends BaseRepository<Course, Long> {
    List<Course> findAllCoursesByName(String name);
    List<Course> findAllCoursesByDescription(String description);
    List<Course> findAllCoursesByNameOrDescription(String searchText);
    List<Course> findAllCoursesByCourseType(String courseType);
    List<Course> findAllCoursesByStartDate(Date startDate);
    List<Course> findAllRunningCourses();
}
