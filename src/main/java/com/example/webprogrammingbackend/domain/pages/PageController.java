package com.example.webprogrammingbackend.domain.pages;

import com.example.webprogrammingbackend.domain.customers.CustomerEnrollForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {


    @GetMapping("/")
    public String homePage() {
        return "home"; // "hello"는 템플릿의 파일명 (hello.html)를 가리킵니다.
    }



    //TODO Member 기능 완료 시, memberId PathVariable 추가할 것
    //TODO 기존 members API 경로에 api 가 붙지 않아서 Path 겹침 해당 부분 수정할 것
    @GetMapping("/member")
    public String myPage(Model model) {
        model.addAttribute("title","My Page");
        return "pages/members/member";
    }


    @GetMapping("/login")
    String login() {
        return "pages/login";
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("title","Admin Page");
        return "pages/admins/admin";
    }

    @GetMapping("/page")
    String test(Model model) {
        model.addAttribute("customerEnrollForm", new CustomerEnrollForm());
        return "pages/test";
    }

}
