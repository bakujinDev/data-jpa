package study.datajpa.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;
import study.datajpa.entity.QMember;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {
    @PersistenceContext
    private EntityManager em;


    QMember qMember = QMember.member;

    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    public void delete(Member member) {
        em.remove(member);
    }

    public List<Member> findAll() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .selectFrom(qMember)
                .fetch();
    }

    public long count() {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.select(qMember.count())
                .from(qMember)
                .fetchOne();
    }

    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findByUsernameAndAgeGreaterThen(String username, int age) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .selectFrom(qMember)
                .where(
                        usernameEq(username),
                        ageGreater(age)
                ).fetch();
    }

    private BooleanExpression usernameEq(String username) {
        if (username == null) {
            return null;
        }
        return qMember.username.eq(username);
    }

    private BooleanExpression ageGreater(int age) {
        return qMember.age.gt(age);
    }

    public List<Member> findByPage(int age, int offset, int limit) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .selectFrom(qMember)
                .where(ageGreater(age))
                .orderBy(qMember.username.desc())
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    public long totalCount(int age) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(qMember.count())
                .from(qMember)
                .where(ageGreater(age))
                .fetchOne();
    }

    public long bulkAgePlus(int age){
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .update(qMember)
                .set(qMember.age, qMember.age.add(1))
                .where(qMember.age.goe(age))
                .execute();
    }
}
