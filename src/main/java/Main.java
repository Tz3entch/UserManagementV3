//import DAO.AddressDAO;
//import DAO.Factory;
//import DAO.GroupDAO;
//import DAO.Implementation.GroupDAOImpl;
//import DAO.UserDAO;
//import beans.Address;
//import beans.Group;
//import beans.User;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import persistence.HibernateUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
        Session session= null;

        try {
              session = HibernateUtil.getSessionFactory().openSession();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();
            session.close();
            //addressDAO.addAddress(address);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
