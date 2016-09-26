package dido.auntaccount.db;

import com.google.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by orysiadeyneka on 24.09.16.
 */
public class EntityManagerProvider implements Provider<EntityManager> {

    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("auntaccount");

    public EntityManager get() {
        return factory.createEntityManager();
    }

}