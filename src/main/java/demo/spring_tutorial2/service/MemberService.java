package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.constant.MemberSearchType;
import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<MemberDto> searchMember(MemberSearchType type, String searchValue, Pageable pageable) {
        Page<MemberDto> members = memberRepository.findBySearchType(type, searchValue, pageable).map(MemberDto::from);

        return members;
    }

    public MemberDto getMember(Long id) {
        MemberDto member = memberRepository.findById(id)
                .map(MemberDto::from)
                .orElseThrow(() -> new IllegalArgumentException("해당 member가 존재하지 않습니다."));

        return member;
    }

    public void save(MemberDto dto) {

    }

    public void update(MemberDto dto) {

    }

    public void delete(Long memberId) {

    }
}
