package com.asterion.webtesting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("charts")
public class PieController
{

    @GetMapping
    String rerout()
    {
        return "redirect:piechart";
    }

    @GetMapping("piechart")
    ModelAndView piechart()
    {
        ModelAndView modelAndView = new ModelAndView("piechart");
        modelAndView.addObject("activities", new String[]{"TW 3.0 Design", "TW 3.0 Development"});
        modelAndView.addObject("percentages", new Integer[]{80, 20});
        return modelAndView;
    }

}
