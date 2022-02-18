package com.asterion.webtesting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("test")
public class TestController
{


    @GetMapping("")
    ModelAndView test()
    {
        return new ModelAndView("test");
    }

    @GetMapping("floats")
    ModelAndView floats()
    {
        return new ModelAndView("floats");
    }
    @GetMapping("flexbox")
    ModelAndView flex()
    {
        return new ModelAndView("flexbox");
    }
    @GetMapping("schemes")
    ModelAndView schemes()
    {
        return new ModelAndView("schemes");
    }
    @GetMapping("menu")
    ModelAndView menu()
    {
        return new ModelAndView("menu");
    }
    @GetMapping("responsive-design")
    ModelAndView responsiveDesign()
    {
        return new ModelAndView("responsive-design");
    }
    @GetMapping("article")
    ModelAndView article()
    {
        return new ModelAndView("article");
    }
    @GetMapping("forms")
    ModelAndView forms()
    {
        return new ModelAndView("speaker-submission");
    }
    @GetMapping("web-fonts")
    ModelAndView webFonts()
    {
        return new ModelAndView("web-fonts");
    }
    @GetMapping("history")
    ModelAndView history()
    {
        return new ModelAndView("history");
    }
    @GetMapping("indents")
    ModelAndView indents()
    {
        return new ModelAndView("indents");
    }
    @GetMapping("alignment")
    ModelAndView alignment()
    {
        return new ModelAndView("alignment");
    }
    @GetMapping("spacing")
    ModelAndView spacing()
    {
        return new ModelAndView("spacing");
    }
    @GetMapping("line-length")
    ModelAndView lineLength()
    {
        return new ModelAndView("line-length");
    }
    @GetMapping("nlp")
    ModelAndView nlp()
    {
        return new ModelAndView("nlp");
    }

}
