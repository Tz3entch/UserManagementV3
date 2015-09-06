package DAO;

import beans.Group;
import beans.User;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * Created by Сережа on 24.08.2015.
 */
public interface UserDAO {
    public Response addUser(String firstName, String lastName, String username,
                            String password, String email, String birthday) throws SQLException;
    public Response updateUser(int userId, String firstName, String lastName, String username,
                               String password, String email, String birthday) throws SQLException;
    public Response getUser(int userId) throws SQLException;
    public Response getAllUsers() throws SQLException;
    public Response getAllUsersInGroup(int groupId) throws SQLException;
    public Response deleteUser(int userId) throws SQLException;
    public Response moveUserToGroup(int userId, int groupId) throws SQLException;
    public Response passivateUser(int userId) throws  SQLException;
    public Response activateUser(int userId) throws  SQLException;

}
