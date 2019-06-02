package com.foxminded.univer.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.univer.dao.Dao;
import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.JdbcDao;
import com.foxminded.univer.models.Lecture;

public class LectureDao extends JdbcDao implements Dao<Lecture> {

	private static final Logger log = LogManager.getLogger(LectureDao.class);

	@Override
	public Lecture save(Lecture lecture) throws ClassNotFoundException {
		Lecture groupToReturn = null;
		if (lecture.getId() == null) {
			groupToReturn = create(lecture);
		} else {
			groupToReturn = update(lecture);
		}
		return groupToReturn;
	}

	private Lecture create(Lecture lecture) throws DaoException, ClassNotFoundException {
		log.trace("Entered create() method");
		String query = "insert into lectures (course_id, auditorium_id, teacher_id, group_id, time) VALUES (?, ?, ?, ?, ?)";
		log.trace("Openinging connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, lecture.getCourse().getId());
			statement.setInt(2, lecture.getAuditorium().getId());
			statement.setInt(3, lecture.getTeacher().getId());
			statement.setInt(4, lecture.getGroup().getId());
			statement.setObject(5, lecture.getTime());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			log.trace("Getting result set");
			try (ResultSet resultSet = statement.getGeneratedKeys();) {
				if (resultSet.next()) {
					log.trace("Create lecture to return");
					lecture = findById(resultSet.getInt("id")).get();
					log.info("Created " + lecture);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot create lecture", e);
			throw new DaoException("Cannot create lecture");
		}
		return lecture;
	}

	private Lecture update(Lecture lecture) throws DaoException, ClassNotFoundException {
		log.trace("Entered update() method");
		String query = "update lectures set course_id = ?, auditorium_id = ?, teacher_id = ?, group_id = ?, time = ? where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lecture.getCourse().getId());
			statement.setInt(2, lecture.getAuditorium().getId());
			statement.setInt(3, lecture.getTeacher().getId());
			statement.setInt(4, lecture.getGroup().getId());
			statement.setObject(5, lecture.getTime());
			statement.setInt(6, lecture.getId());
			log.debug("Executing PreparedStatement", statement);
			statement.execute();
			lecture = findById(lecture.getId()).get();
		} catch (SQLException e) {
			log.error("Cannot update lecture", e);
			throw new DaoException("Cannot update lecture");
		}
		return lecture;
	}

	@Override
	public void delete(Lecture lecture) throws ClassNotFoundException {
		Integer lectureId = lecture.getId();
		log.info("Deleting lecture ID = " + lectureId);
		String query = "delete from lectures where id = ?";
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lecture.getId());
			log.debug("Executing PreparedStatement", statement);
			statement.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete lecture", e);
			throw new DaoException("Cannot delete lecture");
		}
	}

	@Override
	public Optional<Lecture> findById(Integer id) throws DaoException, ClassNotFoundException {
		log.trace("Entered findById() method");
		String query = "select * from lectures where id = ?";
		Optional<Lecture> result = Optional.empty();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, id);
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					log.trace("Create lecture to return");
					result = Optional.of(extractLectureFromResultSet(resultSet));
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find lecture", e);
			throw new DaoException("Cannot find lecture");
		}
		return result;
	}

	@Override
	public List<Lecture> findAll() throws DaoException, ClassNotFoundException {
		log.trace("Entered findAll() method");
		String query = "select * from lectures";
		List<Lecture> allLectures = new ArrayList<>();
		log.trace("Opening connection, preparing statement");
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			log.debug("Executing PreparedStatement, getting result set", statement);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					log.trace("Create list of lectures to return");
					Lecture lecture = extractLectureFromResultSet(resultSet);
					allLectures.add(lecture);
				}
			}
		} catch (SQLException e) {
			log.error("Cannot find all lectures", e);
			throw new DaoException("Cannot find all lectures");
		}
		return allLectures;
	}

	public Integer getAuditoriumId(Integer lectureId) throws ClassNotFoundException {
		String query = "select auditorium_id from lectures where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lectureId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					result = resultSet.getInt("auditorium_id");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot find auditorium ID");
		}
		return result;
	}

	public Integer getCourseId(Integer lectureId) throws ClassNotFoundException {
		String query = "select course_id from lectures where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lectureId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					result = resultSet.getInt("course_id");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot find course ID");
		}
		return result;
	}

	public Integer getGroupId(Integer lectureId) throws ClassNotFoundException {
		String query = "select group_id from lectures where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lectureId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					result = resultSet.getInt("group_id");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot find group ID");
		}
		return result;
	}

	public Integer getTeacherId(Integer lectureId) throws ClassNotFoundException {
		String query = "select teacher_id from lectures where id = ?";
		Integer result = null;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, lectureId);
			try (ResultSet resultSet = statement.executeQuery();) {
				if (resultSet.next()) {
					result = resultSet.getInt("teacher_id");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Cannot find teacher ID");
		}
		return result;
	}

	private Lecture extractLectureFromResultSet(ResultSet resultSet)
			throws SQLException, DaoException, ClassNotFoundException {
		Lecture lecture = new Lecture();
		lecture.setId(resultSet.getInt("id"));
		lecture.setTime(resultSet.getTime("time").toLocalTime());
		return lecture;
	}
}
