package com.iollshh.forum.domain.dao;

import com.iollshh.forum.domain.dto.MemberDto;
import com.iollshh.forum.domain.entity.Article;
import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.factory.MemberFactory;
import com.iollshh.forum.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@RequiredArgsConstructor
@Component
public class MemberDao {
    private final EntityManagerFactory emf;

    private final MemberRepository memberRepository;
    private final MemberFactory memberFactory;

    @Transactional
    public MemberDto uploadNewMember(MemberDto memberDto) throws Exception{
        EntityManager em = emf.createEntityManager();
        EntityTransaction tr = em.getTransaction();

        Member newMember;

        try {
            tr.begin();

            newMember = memberRepository.saveNewByDto(memberDto);
            em.persist(newMember);

            tr.commit();
            em.close();
        } catch (Exception e) {
            tr.rollback();
            em.close();
            throw new Exception("생성실패" + e);
        }
        return memberFactory.makeDtoByEntity(newMember);
    }

    @Transactional
    public MemberDto getMember(String accountId) {
        Member member = memberRepository.findOneByAccountId(accountId);
        return memberFactory.makeDtoByEntity(member);
    }
}
