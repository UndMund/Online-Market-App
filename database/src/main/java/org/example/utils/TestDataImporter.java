package org.example.utils;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.example.entity.Position;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {
    public static void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Position admin = Position.builder()
                .positionName("Admin")
                .build();

        Position user = Position.builder()
                .positionName("User")
                .build();

        admin.setId((Integer)session.save(admin));
        user.setId((Integer)session.save(user));

    }
}
