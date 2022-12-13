package dataaccess;

import domain.Course;
import domain.CourseType;
import domain.Student;
import util.Assert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository{

    private Connection con;

    public MySqlStudentRepository() throws SQLException, ClassNotFoundException {
        this.con = MySqlDatabaseConnection.getConnection("jdbc:mysql://localhost:3306/kurssystem", "root", "");
    }

    @Override
    public Optional<Student> insert(Student entity) {
        Assert.notNull(entity);

        try {
            String sql = "INSERT INTO `students` (`vorname`, `nachname`, `geburtsdatum`) VALUES(?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //mapping von der Objektorientierten Welt Richtung Datenbank
            preparedStatement.setString(1, entity.getVorname());
            preparedStatement.setString(2, entity.getNachname());
            preparedStatement.setDate(3, entity.getGeburtsdatum());


            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                return Optional.empty();
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return this.getById(generatedKeys.getLong(1));
            } else {
                return Optional.empty();
            }


        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Student> getById(Long id) {
        Assert.notNull(id);

        String sql = "SELECT * FROM `students` WHERE `id` LIKE ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();

            Student student = new Student(
                    resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4)

            );

        return Optional.of(student);
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM `students`";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("vorname"),
                                resultSet.getString("nachname"),
                                resultSet.getDate("geburtsdatum")
                        )
                );

            }
            return students;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occured!");
        }
    }

    private int countStudentsInDbWithId(Long id) {
        try {
            String countSQL = "SELECT COUNT(*) FROM `students` WHERE `id` = ?";
            PreparedStatement preparedStatementCount = con.prepareStatement(countSQL);
            preparedStatementCount.setLong(1, id);
            ResultSet resultSetCount = preparedStatementCount.executeQuery();
            resultSetCount.next();
            int studentCount = resultSetCount.getInt(1);
            return studentCount;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public Optional<Student> update(Student entity) {
        Assert.notNull(entity);

        String sql = "UPDATE `students` SET `vorname` = ?, `nachname` = ?, `geburtsdatum` = ? WHERE `students`.`id` = ?";

        if (countStudentsInDbWithId(entity.getId()) == 0) {
            return Optional.empty();
        } else {
            try {
                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, entity.getVorname());
                preparedStatement.setString(2, entity.getNachname());
                preparedStatement.setDate(3, entity.getGeburtsdatum());
                preparedStatement.setLong(4, entity.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    return Optional.empty();
                } else {
                    return this.getById(entity.getId());
                }

            } catch (SQLException sqlException) {
                throw new DatabaseException(sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Assert.notNull(id);
        String sql = "DELETE FROM `students` WHERE `id` = ?";
        try {
            if (countStudentsInDbWithId(id) == 1) {

                PreparedStatement preparedStatement = con.prepareStatement(sql);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> findAllStudentsByFirstName(String vn) {
        try {
            String sql = "SELECT * FROM `students` WHERE LOWER(`vorname`) LIKE LOWER(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + vn + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentArrayList = new ArrayList<>();
            while (resultSet.next()) {
                studentArrayList.add(new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("vorname"),
                        resultSet.getString("nachname"),
                        resultSet.getDate("geburtsdatum")
                        )
                );
            }
            return studentArrayList;

        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    @Override
    public List<Student> findAllStudentsByLastName(String nn) {
        try {
            String sql = "SELECT * FROM `students` WHERE LOWER(`nachname`) LIKE LOWER(?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, "%" + nn + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentArrayList = new ArrayList<>();
            while (resultSet.next()) {
                studentArrayList.add(new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("vorname"),
                                resultSet.getString("nachname"),
                                resultSet.getDate("geburtsdatum")
                        )
                );
            }
            return studentArrayList;

        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }

    public List<Student> findAllStudentsByBirthYear(String year) {
        try {
            String sql = "SELECT * FROM `students` WHERE YEAR(`birthdate`) LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(year));

            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Student> studentArrayList = new ArrayList<>();
            while (resultSet.next()) {
                studentArrayList.add(new Student(
                                resultSet.getLong("id"),
                                resultSet.getString("vorname"),
                                resultSet.getString("nachname"),
                                resultSet.getDate("geburtsdatum")
                        )
                );
            }
            return studentArrayList;
        } catch (SQLException sqlException) {
            throw new DatabaseException(sqlException.getMessage());
        }
    }
}
