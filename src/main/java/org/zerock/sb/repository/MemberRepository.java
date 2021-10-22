package org.zerock.sb.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.sb.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet") //원래는 lazy 인데 내가 원할 때 잠깐만 eager 로 가져와
    @Query("select m from Member m where m.mid = :mid")
    Optional<Member> getMemberEager(String mid);

}
