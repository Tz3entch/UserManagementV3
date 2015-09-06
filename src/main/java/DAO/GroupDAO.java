package DAO;

import beans.Group;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface GroupDAO {
    public Response addGroup(String groupName) throws SQLException;
    public Response updateGroup(int groupId, String groupName) throws SQLException;
    public Response getGroup(int groupId) throws SQLException;
    public Response getAllGroups() throws SQLException;
    public Response deleteGroup(int groupId) throws SQLException;


}
