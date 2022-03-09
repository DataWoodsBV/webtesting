package com.asterion.webtesting.controllers;

import com.asterion.webtesting.domain.NicheSimulation;
import com.asterion.webtesting.domain.PublicationCard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;

@Controller
@RequestMapping("publicationCards")
public class PublicationCardController {

    @GetMapping
    ModelAndView publicationCards() {
        NicheSimulation simulation = new NicheSimulation(new File("E:\\NLP\\analysis\\Cycle simulations\\Beeldschermen en Touchsreens (01-01-2020 - 01-01-2022)"));
        return new ModelAndView("publicationCards")
                .addObject("informationList", simulation.getInformationList())
                .addObject("nicheCards", simulation.getNicheCards())
                .addObject("publicationCards", simulation.getSimulationCards())
                ;
    }

    private List<PublicationCard> getPublicationCards() {
        NicheSimulation simulation = new NicheSimulation(new File("E:\\NLP\\analysis\\Cycle simulations\\Beeldschermen en Touchsreens (01-01-2020 - 01-01-2022)"));
        return simulation.getSimulationCards();
    }
}
