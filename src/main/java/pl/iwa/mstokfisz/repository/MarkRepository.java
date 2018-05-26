package pl.iwa.mstokfisz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.iwa.mstokfisz.model.Mark;

@Repository
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public interface MarkRepository extends JpaRepository<Mark, Long>{

}
