package br.com.achimid.emissao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EmissaoNFeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmissaoNFeApplication.class, args);
    }

}

