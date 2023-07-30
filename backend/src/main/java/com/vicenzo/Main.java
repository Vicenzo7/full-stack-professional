package com.vicenzo;


import com.vicenzo.customer.Customer;
import com.vicenzo.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
//        printBeans(applicationContext);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
            Customer alex = new Customer("Alex", "alex@gmail.com", 21);
            Customer jamila = new Customer("Jamila", "jamila@gmail.com", 21);

            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
    }


    // creating a bean
    @Bean("foo")
    public Foo getFoo() {
        return new Foo("bar");
    }

    public record Foo(String name) {
    }

    private static void printBeans(ConfigurableApplicationContext applicationContext) {
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
