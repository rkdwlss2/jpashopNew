package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit 실행할때 스프링이랑 같이 실행할래
@SpringBootTest //spring boot를 띄우고 실행한다
@Transactional //다시 rollback시킨다
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{
        //given
        Member member =new Member();
        member.setName("kim");

        //when
        Long saveId=memberService.join(member); //insert가 없다


        //then
       // em.flush();
        assertEquals(member,memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class) //validate member 체크
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 =new Member();
        member1.setName("Kim");
        Member member2=new Member();
        member2.setName("Kim");

        //when
        memberService.join(member1);
//        try {
            memberService.join(member2);
//        }catch (IllegalStateException e){
//            return;
//        }
        //then
        fail("예외가 발생해야 한다.");


    }
}