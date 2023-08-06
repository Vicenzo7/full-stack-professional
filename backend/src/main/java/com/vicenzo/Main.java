package com.vicenzo;


import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.vicenzo.customer.model.Customer;
import com.vicenzo.customer.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class Main {


    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(Main.class, args);
//        printBeans(applicationContext);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository) {
        return args -> {
//            var faker = new Faker(); // use of var : The var keyword is used for type inference in Java,
//            // which means the compiler determines the type of the variable based on the right-hand side of the assignment.
//            Random random = new Random();
//
//            Name name = faker.name();
//            String firstName = name.firstName();
//            String lastName = name.lastName();
//            Customer customer = new Customer(
//                    String.format("%s %s", firstName, lastName),
//                    String.format("%s.%s@example.com", firstName.toLowerCase(), lastName.toLowerCase()),
//                    random.nextInt(16, 100));
//            customerRepository.save(customer);
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
