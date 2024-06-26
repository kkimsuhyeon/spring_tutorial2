package demo.spring_tutorial2.repository.member;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.constant.MemberSearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberRepository {

    public Page<Member> findBySearchType(MemberSearchType type, String searchValue, Pageable pageable);

    public Page<Member> findBySearchTypeWithRoles(MemberSearchType type, String searchValue, Pageable pageable);

    public Optional<Member> findById(Long id);

    public Optional<Member> findByEmail(String email);

    public Optional<Member> findByEmailWithRoles(String email);

    public Member save(Member member);

    public void delete(Member member);

    public void deleteFromDatabase(Member member);
}
