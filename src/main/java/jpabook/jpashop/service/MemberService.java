package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor //생성자 만들어줌
@RequiredArgsConstructor //final만 만들어줌
public class MemberService {

    //@Autowired //injection 스프링 빈에 등록 되어 있는 레포지토리를 인젝션 시켜 단점이 있음 못바꿈 이미 access할수가 없
    private final MemberRepository memberRepository;

//    //@Autowired //setter injection 가짜 repository주입 가
//    public void setMemberRepository(MemberRepository memberRepository){
//       this.memberRepository =memberRepository;
//    }

    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {//was가 두개일때 어떻게 함?? 멀티 쓰레드 상황에 한 로직을 두개 사용할때
        List<Member> findMembers = memberRepository.findByName(member.getName()); //마지막 방어 조건으로 DB에 유니크 걸어야
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체 조회
    //@Transactional(readOnly = true)//성능을 최대화함 읽기 전용 트랜젝션이라 리소스 많이 안씀 데이터 변경 안
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //@Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
