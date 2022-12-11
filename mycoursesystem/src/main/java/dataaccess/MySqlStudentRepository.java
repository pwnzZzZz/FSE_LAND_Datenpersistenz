package dataaccess;

import domain.Student;
import util.Assert;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class MySqlStudentRepository implements MyStudentRepository{

    private Connection con;

    public MySqlStudentRepository(Connection con) throws SQLException, ClassNotFoundException {
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
            preparedStatement.setString(3, entity.getGeburtsdatum().toString());


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
        return Optional.empty();
    }

    @Override
    public List<Student> getAll() {
        return null;
    }

    @Override
    public Optional<Student> update(Student entity) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Student> findAllStudentsBySureName(String vn) {
        return null;
    }

    @Override
    public List<Student> findAllStudentsByLastName(String nn) {
        return null;
    }

    @Override
    public List<Student> findStudentById(Long id) {
        return null;
    }
}
