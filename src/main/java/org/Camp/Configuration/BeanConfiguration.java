package org.Camp.Configuration;

import org.Camp.Repository.CampRepository;
import org.Camp.Repository.Implement.CampRepositoryImpl;
import org.Camp.Repository.Implement.RatingRepositoryImpl;
import org.Camp.Repository.Implement.ReservationImpl;
import org.Camp.Repository.Implement.UserRepositoryImpl;
import org.Camp.Repository.RatingRepository;
import org.Camp.Repository.ReservationRepository;
import org.Camp.Repository.UserRepository;
import org.Camp.Controller.*;
import org.Camp.Service.CampService;
import org.Camp.Service.Implement.CampServiceImpl;
import org.Camp.Service.Implement.RatingServiceImpl;
import org.Camp.Service.Implement.ReservationServiceImpl;
import org.Camp.Service.Implement.UserServiceImpl;
import org.Camp.Service.RatingService;
import org.Camp.Service.ReservationService;
import org.Camp.Service.UserService;
import org.Camp.Utils.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class BeanConfiguration {
    @Value("${driver}")
    private String dbDriver;
    @Value("${url}")
    private String url;
    @Value("${dbUser}")
    private String dbUser;
    @Value("${dbPassword}")
    private String dbPassword;

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriver);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public IdGenerator getIdGenerator(){
        return new IdGenerator();
    }

    //Repository
    @Bean
    public UserRepository userRepository(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        return new UserRepositoryImpl(jdbcTemplate, idGenerator);
    }

    @Bean
    public CampRepository campRepository(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        return new CampRepositoryImpl(jdbcTemplate, idGenerator);
    }

    @Bean
    public ReservationRepository reservationRepository(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        return new ReservationImpl(jdbcTemplate, idGenerator);
    }

    @Bean
    public RatingRepository ratingRepository(JdbcTemplate jdbcTemplate, IdGenerator idGenerator) {
        return new RatingRepositoryImpl(jdbcTemplate, idGenerator);
    }

    //service
    @Bean
    public CampService campService(CampRepository campRepository){
        return new CampServiceImpl(campRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public ReservationService reservationService(ReservationRepository reservationRepository, CampRepository campRepository) {
        return new ReservationServiceImpl(reservationRepository, campRepository);
    }

    @Bean
    public RatingService ratingService(RatingRepository ratingRepository, ReservationRepository reservationRepository) {
        return new RatingServiceImpl(ratingRepository, reservationRepository);
    }

    // Controllers
    @Bean
    public CampApiController campApiController(CampService campService){
        return  new CampApiController(campService);
    }

    @Bean
    public UserApiController userApiController(UserService userService){return new UserApiController(userService);}

    @Bean
    public ReservationApiController reservationApiController(ReservationService reservationService){
        return new ReservationApiController(reservationService);
    }
    @Bean
    public RatingApiController ratingApiController(RatingService ratingService){
        return new RatingApiController(ratingService);
    }
}
