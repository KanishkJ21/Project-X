package com.agrihelp.service;

import com.agrihelp.model.Pest;
import com.agrihelp.repository.PestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PestService {

    @Autowired
    private PestRepository pestRepository;

    public List<Pest> getPestsByCrop(String cropType) {
        return pestRepository.findByCropType(cropType);
    }

    public Pest addPest(Pest pest) {
        return pestRepository.save(pest);
    }
}
