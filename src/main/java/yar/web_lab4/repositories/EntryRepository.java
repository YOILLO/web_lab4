package yar.web_lab4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import yar.web_lab4.entities.Entry;
import yar.web_lab4.entities.User;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
    List<Entry> findByUser(User user);

    @Transactional
    long deleteByUser(User user);
}
