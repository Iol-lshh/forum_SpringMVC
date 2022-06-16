package com.iollshh.forum.domain.repository;

import com.iollshh.forum.domain.entity.Member;
import com.iollshh.forum.domain.repository.decorator.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
}