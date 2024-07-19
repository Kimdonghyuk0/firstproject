package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller

public class ArticleController {
    @Autowired

    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }
@PostMapping("/articles/create")
public String createArticle(ArticleForm form){
        log.info(form.toString());
       // System.out.println(form.toString());
        //DTO를 엔티티로 변환
    Article article = form.toEntity();
    //System.out.println(article.toString());
    log.info(article.toString());
        // 리파지터리로 엔티티를  DB에 저장
    Article saved = articleRepository.save(article);
   // System.out.println(saved.toString());
    log.info(saved.toString());
        return "redirect:/articles/"+saved.getId();
}
@GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        log.info("id = "+id);
        //id조회하여 데이터 가져오기
        Article articleEntity =  articleRepository.findById(id).orElse(null);
        //모델에 데이터등록

    model.addAttribute("article", articleEntity);
        //뷰페이지반환

        return "articles/show";
}
@GetMapping("/articles")
public String index(Model model){
        //모든데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        //모델에 데이터 등록
    model.addAttribute("articleList", articleEntityList);
        //뷰 페이지 설정하기
        return "articles/index";
}
@GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id,Model model){

        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/edit";
}
@PostMapping("/articles/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        if(target != null){
            articleRepository.save(articleEntity);
        }
        return "redirect:/articles/"+articleEntity.getId();
}

}
