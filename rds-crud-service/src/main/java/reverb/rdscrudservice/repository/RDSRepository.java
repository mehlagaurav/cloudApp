package reverb.rdscrudservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reverb.rdscrudservice.Modal.Book;
@Repository
public interface RDSRepository extends JpaRepository<Book,Integer> {
}
