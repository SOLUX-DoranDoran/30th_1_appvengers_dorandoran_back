package com.app.dorandoran_backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    // localhost:8080 => Swagger UI로 리다이렉트
    @GetMapping({"", "/"})
    public RedirectView redirectToSwagger() {
        return new RedirectView("/swagger-ui/index.html");
    }

}
