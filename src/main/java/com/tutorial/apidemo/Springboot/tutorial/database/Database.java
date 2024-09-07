package com.tutorial.apidemo.Springboot.tutorial.database;

import com.tutorial.apidemo.Springboot.tutorial.models.Product;
import com.tutorial.apidemo.Springboot.tutorial.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Connect with mysql using JPA
/*
* docker run -d --rm --name mysql-spring-boot-tutorial -e MYSQL_ROOT_PASSWORD=rootpassword -e MYSQL_DATABASE=spring_boot_tutorial -p 3309:3306 --volume mysql-spring-boot-tutorial-volume:/var/lib/mysql mysql:latest
* Host: localhost
    Port: 3309
    Username: root
    Password: rootpassword
* mysql -h localhost -P 3309 --protocol=tcp -u root -p
* show databases;
* use spring_boot_tutorial
 * */


@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    // interface CommandLineRunner -> new CommandLineRunner
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        // Logger
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product productA = new Product("Macbook Pro 16", 2020, 2400.0, "");
//                Product productB = new Product("Ipad Gen Green", 2021, 599.0, "");
//                logger.info("insert data" + productRepository.save(productA));
//                logger.info("insert data" + productRepository.save(productB));
            }
        };
    }
}
