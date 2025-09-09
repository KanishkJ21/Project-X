package com.agrihelp.controller;

import com.agrihelp.model.SHC;
import com.agrihelp.model.User;
import com.agrihelp.service.SHCService;
import com.agrihelp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/shc")
public class SHCController {

    @Autowired
    private SHCService shcService;

    @Autowired
    private UserService userService;

    /**
     * Save or update SHC. 
     * Request body must include userId.
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveSHC(@RequestBody SHC shc) {
        if (shc.getUserId() == null || shc.getUserId().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "userId is required"));
        }

        // Save or update SHC
        SHC saved = shcService.saveOrUpdateSHC(shc);

        // Ensure user's skip flag is cleared
        userService.findById(shc.getUserId()).ifPresent(user -> {
            user.setShcSkipped(false);
            userService.updateUser(user); // ensure this exists in UserService
        });

        return ResponseEntity.ok(saved);
    }

    /**
     * Get SHC for a user.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getByUser(@PathVariable String userId) {
        Optional<SHC> shcOpt = shcService.getByUserId(userId);
        return shcOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    /**
     * Mark SHC as skipped for a user.
     */
    @PostMapping("/skip/{userId}")
    public ResponseEntity<?> skipSHC(@PathVariable String userId) {
        Optional<User> u = userService.findById(userId);
        if (u.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        User user = u.get();
        user.setShcSkipped(true);
        userService.updateUser(user);

        return ResponseEntity.ok(Map.of("skipped", true));
    }

    /**
     * Get regional averaged soil data (ph, nitrogen, phosphorus, potassium).
     * District is optional. Falls back to state-level if district not found.
     */
    @GetMapping("/regional")
    public ResponseEntity<?> regional(@RequestParam String state,
                                      @RequestParam(required = false) String district) {
        if (state == null || state.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "state is required"));
        }

        Map<String, Double> avg = shcService.getRegionalAverage(state, district);
        if (avg.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(avg);
    }

    /**
     * Get soil map (ph, N, P, K) for a user.
     * Uses: user SHC → regional averages → empty map.
     */
    @GetMapping("/soilmap/{userId}")
    public ResponseEntity<?> getSoilMapForUser(@PathVariable String userId) {
        Map<String, Double> soilMap = shcService.getSoilMapForUser(userId);
        if (soilMap.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(soilMap);
    }
}
