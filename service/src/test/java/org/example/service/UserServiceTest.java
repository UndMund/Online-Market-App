package org.example.service;

import org.example.TestDataImporter;
import org.example.dto.userDto.UserDtoLoginResponse;
import org.example.dto.userDto.UserDtoRegResponse;
import org.example.exception.ServiceException;
import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceTest {
    private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    private UserService userService;

    @BeforeAll
    public void setUp() throws Exception {
        TestDataImporter.importData(sessionFactory);
        System.err.println("IMPORT!!!");
        userService = UserService.getInstance();
    }

    @AfterAll
    public void tearDown() throws Exception {
        sessionFactory.close();
    }

    @Test
    public void login() {
        assertThat(
                userService.login(
                        UserDtoLoginResponse.builder()
                                .username("Nazar")
                                .password("Nazar17")
                                .build()
                )).isNotNull();

        assertThrows(ServiceException.class,
                () -> userService.login(
                        UserDtoLoginResponse.builder()
                                .username("Nar")
                                .password("Nazar1")
                                .build()));
    }

    @Test
    public void create() {
        assertThat(
                userService.create(
                        UserDtoRegResponse.builder()
                                .username("Oleg")
                                .phoneNumber("+375296328517")
                                .email("oleg@mail.ru")
                                .password("Nazar17")
                                .position("User")
                                .build())
        ).isNotNull();
    }

    @Test
    public void updatePassword() {
//        userService.updatePassword(4L, "Nazar30");
    }
}