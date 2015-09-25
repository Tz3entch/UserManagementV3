package DAO.Implementation;

import DAO.GroupDAO;
import beans.Group;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Session;
import persistence.HibernateUtil;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/groups")
public class GroupDAOImpl implements GroupDAO {

    @POST
    @Path("/add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response addGroup(@FormParam("groupName") String groupName) throws SQLException {
        Session session = null;
        Group mappedGroup = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Group group = new Group();
            group.setGroupName(groupName);
            session.beginTransaction();
            session.save(group);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedGroup = mapper.map(group, Group.class);
        } catch (Exception e) {
            System.err.println("addGroup failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedGroup).build();
    }

    @PUT
    @Path("update/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response updateGroup(@PathParam("id") int groupId, @FormParam("groupName") String groupName) throws SQLException {
        Session session = null;
        Group mappedGroup = new Group();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Group group = session.load(Group.class, groupId);
            session.beginTransaction();
            group.setGroupName(groupName);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedGroup = mapper.map(group, Group.class);
        } catch (Exception e) {
            System.err.println("updateGroup failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedGroup).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getGroup(@PathParam("id") int groupId) throws SQLException {
        Session session = null;
        Group mappedGroup = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Group group = session.load(Group.class, groupId);
            Mapper mapper = new DozerBeanMapper();
            mappedGroup = mapper.map(group, Group.class);
        } catch (Exception e) {
            System.err.println("getGroup failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedGroup).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllGroups() throws SQLException {
        Session session = null;
        List groups = new ArrayList<Group>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            groups = session.createCriteria(Group.class).list();
        } catch (Exception e) {
            System.err.println("getAllGroups failed, " + e.getMessage());
            return  Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        GenericEntity entity = new GenericEntity<List<Group>>(groups) {};
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("delete/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteGroup(@PathParam("id") int groupId) throws SQLException {
        Session session = null;
        Group mappedGroup = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Group group = session.load(Group.class, groupId);
            session.beginTransaction();
            session.delete(group);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedGroup = mapper.map(group, Group.class);
        } catch (Exception e) {
            System.err.println("deleteGroup failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedGroup).build();
    }
}
// TODO обработать фейловую валидацию

