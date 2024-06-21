package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.repository.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Test
    @DisplayName("search가 아무것도 없을 때, 전체 데이터 가져오는지 확인")
    public void findAllTest() {

        Pageable pageable = Pageable.ofSize(100);
        List<Member> mockMembers = List.of(
                Member.builder().email("test1").nickname("test1").build(),
                Member.builder().email("test2").nickname("test2").build(),
                Member.builder().email("test3").nickname("test3").build());
        Page<Member> mockPage = new PageImpl<>(mockMembers);

        given(memberRepository.findBySearchType(null, null, pageable)).willReturn(mockPage);
        Page<MemberDto> result = memberService.searchMember(null, null, pageable);
        List<MemberDto> members = result.getContent();

        Assertions.assertThat(members).hasSize(3).first().hasFieldOrPropertyWithValue("email", "test1");
    }

    @Test
    @DisplayName("id로 member 찾기")
    public void findByIdTest() {
        given(memberRepository.findById(1L)).willReturn(Optional.of(createMember()));
        MemberDto member = memberService.getMember(1L);
        Assertions.assertThat(member).hasFieldOrPropertyWithValue("email", "email");
    }

    @Test
    @DisplayName("member 생성")
    public void saveTest() {

    }

    @Test
    @DisplayName("member 수정")
    public void updateTest() {

    }

    @Test
    @DisplayName("member 삭제")
    public void deleteTest() {

    }

    private Member createMember() {
        Member member = Member.of("email", "password", "nickname", "memo");
        ReflectionTestUtils.setField(member, "id", 1L);

        return member;
    }

}
