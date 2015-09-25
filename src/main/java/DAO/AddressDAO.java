package DAO;

import beans.Address;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

public interface AddressDAO {
    public Response addAddress(int userId, int zip, String country, String city, String district, String street) throws SQLException;
    public Response updateAddress(int adressId, int zip, String country, String city, String district, String street) throws SQLException;
    public Response getAddress(int adressId) throws SQLException;
    public Response deleteAddress(int adressId) throws SQLException;
}
