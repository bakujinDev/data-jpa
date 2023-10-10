package study.datajpa.repository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.QTeam;
import study.datajpa.entity.Team;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {
    @PersistenceContext
    private EntityManager em;
    JPAQueryFactory query = new JPAQueryFactory(em);
    QTeam qTeam = QTeam.team;

    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    public void delete(Team team) {
        em.remove(team);
    }

    public List<Team> findAll() {
        return query
                .selectFrom(qTeam)
                .fetch();
    }

    public long count() {
        return query.select(qTeam.count())
                .from(qTeam)
                .fetchOne();
    }

    public Optional<Team> findById(Long id) {
        Team team = em.find(Team.class, id);
        return Optional.ofNullable(team);
    }

    public Team find(Long id) {
        return em.find(Team.class, id);
    }
}
