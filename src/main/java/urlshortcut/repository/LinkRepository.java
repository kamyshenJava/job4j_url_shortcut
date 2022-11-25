package urlshortcut.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import urlshortcut.model.Client;
import urlshortcut.model.Link;

import java.util.List;
import java.util.Optional;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
    Optional<Link> findByGeneratedCode(String generatedCode);

    List<Link> findAllByClient(Client client);
}
