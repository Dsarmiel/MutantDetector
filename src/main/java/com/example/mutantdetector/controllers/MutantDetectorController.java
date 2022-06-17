package com.example.mutantdetector.controllers;

import com.example.mutantdetector.interfaces.MutantDetectorInterface;
import com.example.mutantdetector.logic.MutantDetectorLogic;
import com.example.mutantdetector.models.RequestDna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MutantDetectorController implements MutantDetectorInterface {

    @Autowired
    MutantDetectorLogic mutantDetectorLogic;

    @RequestMapping(value = "/mutant", method = RequestMethod.POST)
    public ResponseEntity<?> isMutant(@RequestBody RequestDna dna){
        return mutantDetectorLogic.isMutant(dna);
    }

    @Override
    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    public ResponseEntity<?> getStatsVerification() {
        return mutantDetectorLogic.getStatsVerification();
    }
}
