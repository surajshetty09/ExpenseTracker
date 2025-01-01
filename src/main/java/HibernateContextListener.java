import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application is starting...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        TransactionDAO.shutdown(); // Clean up and close the SessionFactory
        System.out.println("Application is shutting down...");
    }
}
