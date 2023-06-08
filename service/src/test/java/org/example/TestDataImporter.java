package org.example;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {
    public static void importData(SessionFactory sessionFactory) {
        /*Session session = HibernateUtil.getSession(sessionFactory);
        UserRepository userRep =new UserRepository(session);

        session.beginTransaction();

        Position admin = Position.builder()
                .positionName(PositionsEnum.ADMIN.getName())
                .build();

        Position user = Position.builder()
                .positionName(PositionsEnum.USER.getName())
                .build();

        admin.setId((Integer)session.save(admin));
        user.setId((Integer)session.save(user));

        User nazar = User.builder()
                .username("Nazar")
                .phoneNumber("+375336328517")
                .email("nazar@mail.ru")
                .password("Nazar17")
                .position(admin)
                .money(BigDecimal.ZERO)
                .build();

        User sergey = User.builder()
                .username("Sergey")
                .phoneNumber("+375296144667")
                .email("sergey@mail.ru")
                .password("Sergey20")
                .position(user)
                .money(BigDecimal.ZERO)
                .build();

        User helena = User.builder()
                .username("Helena")
                .phoneNumber("+375299575344")
                .email("helena@mail.ru")
                .password("Helena20")
                .position(user)
                .money(BigDecimal.ZERO)
                .build();

        nazar.setId((Long) session.save(nazar));
        helena.setId((Long) session.save(helena));
        sergey.setId((Long) session.save(sergey));


        Category phone = Category.builder()
                .categoryName("Phones and accessories")
                .build();

        Category laptop = Category.builder()
                .categoryName("Laptops and accessories")
                .build();

        Category moto = Category.builder()
                .categoryName("Motorbikes")
                .build();

        Category music = Category.builder()
                .categoryName("Musical instruments")
                .build();

        phone.setId((Integer) session.save(phone));
        laptop.setId((Integer) session.save(laptop));
        moto.setId((Integer) session.save(moto));
        music.setId((Integer) session.save(music));

        Product mi11 = Product.builder()
                .productName("Xiaomi Mi 11 Lite")
                .price(new BigDecimal(300))
                .description("Работает прекрасно, использовался 2 года")
                .category(phone)
                .status(Status.ON_SALE)
                .user(nazar)
                .build();

        Product mac15 = Product.builder()
                .productName("MacBook Pro 15 2018")
                .price(new BigDecimal(1500))
                .description("Все рабочее, внешние следы износа")
                .category(laptop)
                .status(Status.SALES)
                .user(sergey)
                .build();

        Product hondaCRF = Product.builder()
                .productName("Honda CRF 650L")
                .price(new BigDecimal(3500))
                .description("Требуеется капитальный ремонт двигателя")
                .category(moto)
                .status(Status.REVIEW)
                .user(sergey)
                .build();

        Product crafterDE = Product.builder()
                .productName("Гитара Crafter DE/7N")
                .price(new BigDecimal(500))
                .description("Новый инсрумент")
                .category(music)
                .status(Status.SALES)
                .user(helena)
                .build();

        mi11.setId((Long) session.save(mi11));
        mac15.setId((Long) session.save(mac15));
        hondaCRF.setId((Long) session.save(hondaCRF));
        crafterDE.setId((Long) session.save(crafterDE));

        session.getTransaction().commit();*/
    }
}
