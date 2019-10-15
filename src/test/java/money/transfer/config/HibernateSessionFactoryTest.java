package money.transfer.config;

import com.google.inject.Inject;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HibernateSessionFactoryTest {

    @Inject
    private SessionFactory sessionFactory;

    @Test
    public void testSessionFactoryNotNull() {
        assertNotNull(sessionFactory);
    }
}
