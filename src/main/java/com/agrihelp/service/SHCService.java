package com.agrihelp.service;

import com.agrihelp.model.SHC;
import com.agrihelp.model.User;
import com.agrihelp.repository.SHCRepository;
import com.agrihelp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SHCService {

    @Autowired
    private SHCRepository shcRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Save or update SHC entry for a user.
     */
    public SHC saveOrUpdateSHC(SHC shc) {
        Instant now = Instant.now();
        if (shc.getCreatedAt() == null) {
            shc.setCreatedAt(now);
        }
        shc.setUpdatedAt(now);
        return shcRepository.save(shc);
    }

    /**
     * Fetch SHC by userId.
     */
    public Optional<SHC> getByUserId(String userId) {
        return shcRepository.findByUserId(userId);
    }

    /**
     * Compute regional average values for ph, N, P, K.
     * Falls back to state-level if district data is missing.
     */
    public Map<String, Double> getRegionalAverage(String state, String district) {
        List<SHC> list = null;

        if (district != null && !district.isBlank()) {
            list = shcRepository.findByStateAndDistrict(state, district);
        }

        // Fallback to state-level if no district data found
        if (list == null || list.isEmpty()) {
            list = shcRepository.findByState(state);
        }

        Map<String, Double> avg = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return avg; // return empty map if nothing found
        }

        double sumPh = 0, sumN = 0, sumP = 0, sumK = 0;
        int countPh = 0, countN = 0, countP = 0, countK = 0;

        for (SHC s : list) {
            if (s.getPh() != null) { sumPh += s.getPh(); countPh++; }
            if (s.getNitrogen() != null) { sumN += s.getNitrogen(); countN++; }
            if (s.getPhosphorus() != null) { sumP += s.getPhosphorus(); countP++; }
            if (s.getPotassium() != null) { sumK += s.getPotassium(); countK++; }
        }

        if (countPh > 0) avg.put("ph", sumPh / countPh);
        if (countN > 0) avg.put("nitrogen", sumN / countN);
        if (countP > 0) avg.put("phosphorus", sumP / countP);
        if (countK > 0) avg.put("potassium", sumK / countK);

        return avg;
    }

    /**
     * Get soil values for recommendations:
     * 1. If SHC exists for user => return SHC values
     * 2. Else if user has location => return regional averages
     * 3. Else return empty map
     */
    public Map<String, Double> getSoilMapForUser(String userId) {
        // First check if SHC exists
        Optional<SHC> shcOpt = getByUserId(userId);
        if (shcOpt.isPresent()) {
            SHC s = shcOpt.get();
            Map<String, Double> m = new HashMap<>();
            if (s.getPh() != null) m.put("ph", s.getPh());
            if (s.getNitrogen() != null) m.put("nitrogen", s.getNitrogen());
            if (s.getPhosphorus() != null) m.put("phosphorus", s.getPhosphorus());
            if (s.getPotassium() != null) m.put("potassium", s.getPotassium());
            return m;
        }

        // If no SHC, fallback to location data
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User u = userOpt.get();
            String state = u.getState();
            String district = u.getDistrict();
            if (state != null && !state.isBlank()) {
                return getRegionalAverage(state, district);
            }
        }

        // Nothing available
        return new HashMap<>();
    }
}
