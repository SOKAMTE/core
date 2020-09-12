package com.app.core;


import com.app.dao.AdminDao;
import com.app.entities.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={
		"com.app.dao", "com.app.core"})
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.app.dao, com.app.core")
@EnableJpaRepositories(basePackages="com.app.dao, com.app.core")
@EnableTransactionManagement
public class CoreApplication implements CommandLineRunner {
    
    @Autowired
    private AdminDao adminDao;

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        adminDao.save(new Admin((long) 1, "youssoufngouou@gmail.com", "16F393S", 693659844, "ngounou"));
        adminDao.save(new Admin((long) 2, "kouambrice@gmail.com", "16F209S", 693679422, "brice"));
        adminDao.save(new Admin((long) 3, "myojordy@gmail.com", "16F297S", 697255466, "jordy"));
        adminDao.findAll().forEach(admins->{
            System.out.println(admins.getUsername());
        });
        System.out.println("................");
    }

}
