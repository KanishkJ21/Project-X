package com.agrihelp.controller;

import com.agrihelp.model.Pest;
import com.agrihelp.service.PestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pest")
public class PestController {

    @Autowired
    private PestService pestService;

    @GetMapping("/getByCrop")
    public List<Pest> getPestsByCrop(@RequestParam String cropType) {
        return pestService.getPestsByCrop(cropType);
    }

    @PostMapping("/add")
    public Pest addPest(@RequestBody Pest pest) {
        return pestService.addPest(pest);
    }
}
