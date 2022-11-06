package pl.umk.workshop.springintroduction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class TestsBase {

    public static void assertNotConfiguredWithAnnotations(Class clazz) {
        try {
            assertFalse(clazz.isAnnotationPresent(Service.class));
            assertFalse(clazz.isAnnotationPresent(Component.class));
            assertFalse(clazz.isAnnotationPresent(Repository.class));
            assertFalse(clazz.isAnnotationPresent(Controller.class));
            assertFalse(clazz.isAnnotationPresent(Autowired.class));
        } catch (Exception e) {
            throw new RuntimeException("Reflection failed :(");
        }
    }
}
