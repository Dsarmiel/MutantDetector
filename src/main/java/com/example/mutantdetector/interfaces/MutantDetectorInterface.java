package com.example.mutantdetector.interfaces;

import com.example.mutantdetector.models.RequestDna;
import com.example.mutantdetector.models.StatsVerification;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MutantDetectorInterface {
    ResponseEntity<?> isMutant(RequestDna dna);
    ResponseEntity<?> getStatsVerification();
}
