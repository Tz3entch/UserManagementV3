package DAO;

import DAO.Implementation.AddressDAOImpl;
import DAO.Implementation.GroupDAOImpl;
import DAO.Implementation.UserDAOImpl;

public class Factory {

    private static AddressDAO addressDAO = null;
    private static GroupDAO groupDAO = null;
    private static UserDAO userDAO = null;
    private static Factory instance = null;

    public static synchronized Factory getInstance(){
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public AddressDAO getAddressDAO(){
        if (addressDAO == null){
            addressDAO = new AddressDAOImpl();
        }
        return addressDAO;
    }

    public GroupDAO getGroupDAO(){
        if (groupDAO == null){
            groupDAO = new GroupDAOImpl();
        }
        return groupDAO;
    }

    public UserDAO getUserDAO(){
        if (userDAO == null){
            userDAO = new UserDAOImpl();
        }
        return userDAO;
    }
}
