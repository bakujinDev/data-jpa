package study.datajpa.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;
import study.datajpa.entity.QMember;

import java.util.List;


public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final EntityManager em;
    private final JPAQueryFactory query;

    QMember qMember = QMember.member;

    public MemberRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Member> findMemberCustom(){
        return query
                .selectFrom(qMember)
                .fetch();
    }
}
