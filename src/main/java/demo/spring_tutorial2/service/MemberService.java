package demo.spring_tutorial2.service;

import demo.spring_tutorial2.domain.Member;
import demo.spring_tutorial2.domain.constant.MemberSearchType;
import demo.spring_tutorial2.dto.domain.MemberDto;
import demo.spring_tutorial2.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        Page<Member> members = memberRepository.findBySearchType(type, searchValue, pageableParser(pageable));

        return members.map(MemberDto::fromEntity);
    }

    public MemberDto getMember(Long id) {
        MemberDto member = memberRepository.findById(id)
                .map(MemberDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("해당 member가 존재하지 않습니다."));

        return member;
    }

    @Transactional
    public void save(MemberDto dto) {
        memberRepository.save(dto.toEntity());
    }

    @Transactional
    public void update(Long id, MemberDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 member 존재하지 않음"));

        member.setNickname(dto.nickname());
        member.setMemo(dto.memo());
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 member는 존재하지 않음"));

        member.setExpiredAt(LocalDateTime.now());
    }

    private Pageable pageableParser(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
    }
}
