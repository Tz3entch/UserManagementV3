package DAO.Implementation;

import DAO.AddressDAO;
import beans.Address;
import beans.User;
import org.hibernate.Session;
import persistence.HibernateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

@Path("/address")
public class AddressDAOImpl implements AddressDAO {

    @POST
    @Path("/addToUser/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response addAddress (@PathParam("id")int userId, @FormParam("zip")int zip, @FormParam("country")String country, @FormParam("city")String city,
                                @FormParam("district")String district, @FormParam("street")String street) throws SQLException {
        Session session = null;
        Address mappedAddress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Address address = new Address();
            address.setZip(zip);
            address.setCountry(country);
            address.setCity(city);
            address.setDistrict(district);
            address.setStreet(street);
            session.beginTransaction();
            session.save(address);
            User user = session.load(User.class, userId);
            user.setAddressId(address.getAddressId());
            session.save(user);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedAddress = mapper.map(address,Address.class);
        } catch (Exception e) {
            System.err.println("addAddress failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
        return Response.ok(mappedAddress).build();
    }

    @PUT
    @Path("/update/{id}")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_XML)
    public Response updateAddress(@PathParam("id")int addressId, @FormParam("zip")int zip, @FormParam("country")String country, @FormParam("city")String city,
                              @FormParam("district")String district, @FormParam("street")String street) throws SQLException {
        Session session = null;
        Address mappedAddress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Address address = session.load(Address.class, addressId);
            session.beginTransaction();
            address.setZip(zip);
            address.setCountry(country);
            address.setCity(city);
            address.setDistrict(district);
            address.setStreet(street);
            session.getTransaction().commit();
            Mapper mapper = new DozerBeanMapper();
            mappedAddress = mapper.map(address,Address.class);
        } catch (Exception e) {
            System.err.println("updateAddress failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedAddress).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getAddress(@PathParam("id")int addressId) throws SQLException {
        Session session = null;
        Address mappedAddress = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Address address = (Address) session.load(Address.class, addressId);
            Mapper mapper = new DozerBeanMapper();
            mappedAddress = mapper.map(address,Address.class);
        } catch (Exception e) {
            System.err.println("getAddress failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok(mappedAddress).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteAddress(@PathParam("id") int addressId) throws SQLException {
        Session session = null;
        Address address = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            address = (Address) session.load(Address.class, addressId);
            session.beginTransaction();
            session.delete(address);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("deleteAddress failed, " + e.getMessage());
            return Response.serverError().build();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return Response.ok().build();
    }
}
//TODO Edit responses
//TODO запилить редирект