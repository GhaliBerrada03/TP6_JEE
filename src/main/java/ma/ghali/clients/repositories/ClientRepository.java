package ma.ghali.clients.repositories;

import ma.ghali.clients.domaine.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    // Méthodes standard héritées
}
