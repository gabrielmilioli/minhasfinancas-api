package com.milioli.minhasfinancas;

import org.hibernate.mapping.Any;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test") // utiliza o properties de test
@DataJpaTest // cria transações para cada teste e ao fim execução rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // utiliza as mesmas configurações setadas no properties
public class TestAnnotations {

    @Autowired
    public TestEntityManager entityManager;

}
