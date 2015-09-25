package DAO.Implementation;

import DAO.UserDAO;
import beans.User;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import persistence.HibernateUtil;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Path("/users")
public class UserDAOImpl implements UserDAO{

    @POST
    @Path("/add")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response addUser (@FormParam("firstName")String firstName, @FormParam("lastName")String lastName,
                             @FormParam("username")String username, @FormParam("password")String password,
                             @FormParam("email")String email, @FormParam("birthday")String birthday) throws SQLException {
        Session session = null;
        User mappedUser = null;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setBirthday(df.parse(birthday));
            session.save(user);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedUser = mapper.map(user, User.class);

        } catch (Exception e) {
            System.err.println("addUser failed, " + e.getMessage());
            return Response.serverError().entity("addUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response updateUser(@PathParam("id") int userId, @FormParam("firstName")String firstName,
                               @FormParam("lastName")String lastName, @FormParam("username")String username,
                               @FormParam("password")String password, @FormParam("email")String email,
                               @FormParam("birthday")String birthday) throws SQLException {
        Session session = null;
        User mappedUser = null;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = session.load(User.class, userId);
            if (user.isActive()) {
                session.beginTransaction();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setBirthday(df.parse(birthday));
                user.setLastUpdateTS(new Date());
                session.save(user);
                session.getTransaction().commit();
                Mapper mapper = new DozerBeanMapper();
                mappedUser = mapper.map(user, User.class);
            } else {
                System.err.println("Attempt to update passive user");
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Attempt to update passive user").build();
            }
        } catch (Exception e) {
            System.err.println("updateUser failed, " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("updateUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getUser(@PathParam("id") int userId) throws SQLException {
        Session session = null;
        User mappedUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = session.load(User.class, userId);
            Mapper mapper = new DozerBeanMapper();
            mappedUser = mapper.map(user, User.class);
        } catch (Exception e) {
            System.err.println("getUser failed, " + e.getMessage());
            return Response.serverError().entity("getUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllUsers() throws SQLException {
        Session session = null;
        List users = new ArrayList<User>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createCriteria(User.class).list();
        } catch (Exception e) {
            System.err.println("getAllUsers failed, " + e.getMessage());
            return Response.serverError().entity("getAllUsers failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        GenericEntity entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @GET
    @Path("groupId/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAllUsersInGroup(@PathParam("id") int groupId) throws SQLException {
        Session session = null;
        List users = new ArrayList<User>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            int group_id = groupId;
            Query query = session.createQuery("from User where groupId = :groupId ").setInteger("groupId", group_id);
            users = (List<User>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            System.err.println("getAllUsersInGroup failed, " + e.getMessage());
            return Response.serverError().entity("getAllUsersInGroup failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        GenericEntity entity = new GenericEntity<List<User>>(users) {};
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") int userId) throws SQLException {
        Session session = null;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            user = session.load(User.class, userId);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("deleteUser failed, " + e.getMessage());
            return Response.serverError().entity("deleteUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok().build();
    }

    @PUT
    @Path("{id}/move")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response moveUserToGroup(@PathParam("id") int userId, @FormParam("groupId")int groupId) throws SQLException {
        Session session = null;
        User mappedUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            User user = session.load(User.class, userId);
            session.beginTransaction();
            user.setGroupId(groupId);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedUser = mapper.map(user, User.class);
        } catch (Exception e) {
            System.err.println("moveUserToGroup failed, " + e.getMessage());
            return Response.serverError().entity("moveUserToGroup failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }
    @PUT
    @Path("/passivate/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response passivateUser(@PathParam("id")int userId) throws SQLException {
        Session session = null;
        User mappedUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
             User user = session.load(User.class, userId);
            if (user.isActive()) {
                session.beginTransaction();
                user.setActive(false);
                session.getTransaction().commit();
                Mapper mapper = new DozerBeanMapper();
                mappedUser = mapper.map(user, User.class);
            }
        } catch (Exception e) {
            System.err.println("passivateUser failed, " + e.getMessage());
            return Response.serverError().entity("passivateUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }

    @PUT
    @Path("/activate/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response activateUser(@PathParam("id")int userId) throws SQLException {
        Session session = null;
        User mappedUser = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
             User user = session.load(User.class, userId);
            if (!user.isActive()) {
                session.beginTransaction();
                user.setActive(true);
                session.getTransaction().commit();
                Mapper mapper = new DozerBeanMapper();
                mappedUser = mapper.map(user, User.class);
            }
        } catch (Exception e) {
            System.err.println("activateUser failed, " + e.getMessage());
            return Response.serverError().entity("activateUser failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedUser).build();
    }

    @GET
    @Path("/search/{text}")
//    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response search(@PathParam("text")String text) {
        Session session = null;
        List result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            Transaction tx = fullTextSession.beginTransaction();
            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(User.class).get();
            org.apache.lucene.search.Query query = qb
                    .keyword()
                    .onFields("firstName", "lastName", "email", "birthday")
                    .ignoreFieldBridge()
                    .matching(text)
                    .createQuery();
            org.hibernate.Query hibQuery =
                    fullTextSession.createFullTextQuery(query, User.class);
            result = hibQuery.list();
            tx.commit();
        } catch (Exception e) {
            System.err.println("search failed, " + e.getMessage());
            return Response.serverError().entity("search failed").build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        GenericEntity entity = new GenericEntity<List<User>>(result) {};
        return Response.ok(entity).build();

    }
}
//TODO validate user
