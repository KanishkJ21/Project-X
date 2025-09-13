package com.agrihelp.service;

import com.agrihelp.model.SHC;
import com.agrihelp.repository.SHCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SHCService {

    @Autowired
    private SHCRepository shcRepository;

    public SHC addSHC(SHC shc) {
        return shcRepository.save(shc);
    }

    public List<SHC> getSHCByUser(String userId) {
        return shcRepository.findByUserId(userId);
    }
}
