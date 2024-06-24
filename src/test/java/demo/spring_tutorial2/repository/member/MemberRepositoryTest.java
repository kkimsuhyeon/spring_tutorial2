package demo.spring_tutorial2.repository.member;

import demo.spring_tutorial2.config.JpaConfig;
import demo.spring_tutorial2.config.RepositoryConfig;
import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.constant.MemberSearchType;
import jakarta.persistence.NoResultException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({JpaConfig.class, RepositoryConfig.class})
//@Sql(scripts = {"classpath:data2.sql"})
public class MemberRepositoryTest {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Test
    @DisplayName("전체 가져오기")
    public void findAllTest() {
        Page<Member> result = memberRepository
                .findBySearchType(
                        null, "",
                        PageRequest.of(0, 100, Sort.by(Sort.Direction.ASC, "nickname")));

        List<Member> members = result.getContent();
        System.out.println(members);
        Assertions.assertThat(members).hasSize(5).first().hasFieldOrPropertyWithValue("email", "test1@test.com");
    }

    @Test
    @DisplayName("search 값을 통해서 가져오기")
    public void searchTest() {
        Page<Member> resultByTest1 = memberRepository.findBySearchType(MemberSearchType.EMAIL, "test1", PageRequest.of(0, 100));
        Page<Member> resultByTest = memberRepository.findBySearchType(MemberSearchType.EMAIL, "test", PageRequest.of(0, 100));

        List<Member> membersByTest1 = resultByTest1.getContent();
        List<Member> membersByTest = resultByTest.getContent();

        Assertions.assertThat(membersByTest1).hasSize(1).first().hasFieldOrPropertyWithValue("email", "test1@test.com");
        Assertions.assertThat(membersByTest).hasSize(5);
    }

    @Test
    @DisplayName("ID로 값 가져오기")
    public void findByIdTest() {
        Optional<Member> member = memberRepository.findById(1L);
        Assertions.assertThat(member).isNotNull().get().hasFieldOrPropertyWithValue("email", "test1@test.com");
    }

    @Test
    @DisplayName("email로 값 가져오기")
    public void findByEmailTest() {
        String email = "test1@test.com";
        Optional<Member> member = memberRepository.findByEmail(email);
        Assertions.assertThat(member).isNotNull().get().hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    @DisplayName("Member 생성")
    public void saveTest() {
        String email = "test10@test.com";
        String nickname = "test10";

        Member newMember = Member.of(email, "1234", nickname, "memo");
        memberRepository.save(newMember);

        Optional<Member> member = memberRepository.findByEmail(email);

        Assertions.assertThat(member)
                .isNotNull().get()
                .hasFieldOrPropertyWithValue("email", email)
                .hasFieldOrPropertyWithValue("nickname", nickname);

    }

    @Test
    @DisplayName("Member 수정")
    public void updateTest() {
        String newEmail = "test11@test.com";
        String newNickname = "test11";

        Member newMember = Member.of("test10@test.com", "1234", "test10", "memo");
        memberRepository.save(newMember);

        Member member = memberRepository.findByEmail("test10@test.com").orElseThrow();
        member.setEmail(newEmail);
        member.setNickname(newNickname);

        assertThrows(NoResultException.class, () -> memberRepository.findByEmail("test10@test.com"));
        Member updatedMember = memberRepository.findByEmail(newEmail).orElseThrow();

        Assertions.assertThat(updatedMember).isNotNull().hasFieldOrPropertyWithValue("nickname", newNickname);
    }

    @Test
    @DisplayName("Member expired_at 추가")
    public void deleteTest() {
        Member member = memberRepository.findById(1L).orElseThrow();
        Assertions.assertThat(member.getExpiredAt()).isNull();

        memberRepository.delete(member);

        Assertions.assertThat(member.getExpiredAt()).isNotNull().isInstanceOf(LocalDateTime.class);
    }

    @Test
    @DisplayName("Member 삭제")
    public void deleteFromDatabaseTest() {
        Long memberId = 1L;
        Optional<Member> member = memberRepository.findById(memberId);

        Assertions.assertThat(member).isNotNull();

        memberRepository.deleteFromDatabase(member.get());
        ((MemberRepositoryJPA) memberRepository).flush();

        assertThrows(NoSuchElementException.class, () -> {
            memberRepository.findById(memberId).get();
        });
    }
}
