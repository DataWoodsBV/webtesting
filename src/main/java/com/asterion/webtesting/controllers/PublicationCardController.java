package com.asterion.webtesting.controllers;

import com.asterion.webtesting.domain.NicheSimulation;
import com.asterion.webtesting.domain.PublicationCard;
import com.asterion.webtesting.utilities.FolderHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("publicationCards")
public class PublicationCardController {

    @GetMapping
    ModelAndView publicationCards() {
        return getSimulation(getNicheNames().get(0));
    }

    @GetMapping("{nicheName}")
    ModelAndView findByNicheName(@PathVariable("nicheName") String nicheName) {
        return getSimulation(nicheName);
    }

    private ModelAndView getSimulation(String name){
        NicheSimulation simulation = new NicheSimulation(getSimulationFolder(name));
        return new ModelAndView("publicationCards")
                .addObject("name", name)
                .addObject("nicheNames", getNicheNames())
                .addObject("informationList", simulation.getInformationList())
                .addObject("nicheCards", simulation.getNicheCards())
                .addObject("publicationCards", simulation.getSimulationCards());
    }

    private File getSimulationFolder(String name){
        Set<File> folders = FolderHandler.getSubFolders(new File("E:\\NLP\\analysis\\Cycle simulations"));
        Optional<File> folder = folders.stream().filter(f -> f.getName().startsWith(name)).findFirst();
        if(folder.isPresent()) return folder.get();
        else throw new IllegalArgumentException("Could not find niche folder: " + name);
    }

    private List<String> getNicheNames(){
        return FolderHandler.getSubFolders(new File("E:\\NLP\\analysis\\Cycle simulations")).stream()
                .map(File::getName)
                .map(s -> s.split("\\(")[0])
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    private List<PublicationCard> getPublicationCards() {
        NicheSimulation simulation = new NicheSimulation(new File("E:\\NLP\\analysis\\Cycle simulations\\Beeldschermen en Touchsreens (01-01-2020 - 01-01-2022)"));
        return simulation.getSimulationCards();
    }
}