package com.example.mutantdetector.logic;

import com.example.mutantdetector.dao.MutantDetectorDao;
import com.example.mutantdetector.interfaces.MutantDetectorInterface;
import com.example.mutantdetector.models.AdnVerifications;
import com.example.mutantdetector.models.RequestDna;
import com.example.mutantdetector.models.StatsVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MutantDetectorLogic implements MutantDetectorInterface {

    @Autowired
    private MutantDetectorDao mutantDetectorDao;

    public ResponseEntity<?> isMutant(@RequestBody RequestDna dna){
        String[] dnaArray = dna.getDna();
        int lengthArray = dnaArray[0].length();
        char[][] dnaMatrix = new char[dnaArray.length][];
        for(int i = 0; i < dnaArray.length; i++){
            if(lengthArray == dnaArray[i].length()){
                Pattern pattern = Pattern.compile("[^ATGC]");
                Matcher matcher = pattern.matcher(dnaArray[i]);
                boolean validatePattern = matcher.find();
                if(!validatePattern){
                    char[] dnaToCharArray = new char[lengthArray];
                    for(int ind = 0; ind < dnaArray[i].length(); ind++){
                        dnaToCharArray[ind] = dnaArray[i].charAt(ind);
                    }
                    dnaMatrix[i] = dnaToCharArray;
                }else{
                    return new ResponseEntity<>("Los DNA solo deben contener caracteres 'A', 'T', 'C' y 'G'", HttpStatus.FORBIDDEN);
                }
            }else {
                return new ResponseEntity<>("Los DNA deben contar con el mismo numero de caracteres", HttpStatus.FORBIDDEN);
            }
        }
        int sequenceFind = 0;
        for(int row = 0; row < dnaMatrix.length; row++){
            for(int col = 0; col < dnaMatrix[row].length; col++){
                if(col + 3 <= dnaMatrix.length - 1){
                    if(dnaMatrix[row][col] == dnaMatrix[row][col+1] ){
                        if(dnaMatrix[row][col] == dnaMatrix[row][col+2]){
                            if(dnaMatrix[row][col] == dnaMatrix[row][col+3]){
                                if(sequenceFind == 1){
                                    if(mutantDetectorDao.getMutantByDna(Arrays.toString(dnaArray)).isEmpty()){
                                        AdnVerifications adnVerifications =  new AdnVerifications();
                                        adnVerifications.setDna(Arrays.toString(dnaArray));
                                        adnVerifications.setIs_mutant(true);
                                        mutantDetectorDao.saveIsMutant(adnVerifications);
                                    }
                                    return new ResponseEntity<>(true, HttpStatus.OK);
                                }else{
                                    sequenceFind++;
                                }
                            }
                        }
                    }
                }
                if(row + 3 <= dnaMatrix[row].length){
                    if(dnaMatrix[row][col] == dnaMatrix[row+1][col]){
                        if(dnaMatrix[row][col] == dnaMatrix[row+2][col]){
                            if(dnaMatrix[row][col] == dnaMatrix[row+3][col]){
                                if(sequenceFind == 1){
                                    if(mutantDetectorDao.getMutantByDna(Arrays.toString(dnaArray)).isEmpty()){
                                        AdnVerifications adnVerifications =  new AdnVerifications();
                                        adnVerifications.setDna(Arrays.toString(dnaArray));
                                        adnVerifications.setIs_mutant(true);
                                        mutantDetectorDao.saveIsMutant(adnVerifications);
                                    }
                                    return new ResponseEntity<>(true, HttpStatus.OK);
                                }else{
                                    sequenceFind++;
                                }
                            }
                        }
                    }
                }
                if(col + 3 <= dnaMatrix.length - 1 && row + 3 <= dnaMatrix[row].length) {
                    if (dnaMatrix[row][col] == dnaMatrix[row + 1][col + 1]) {
                        if (dnaMatrix[row][col] == dnaMatrix[row + 2][col + 2]) {
                            if (dnaMatrix[row][col] == dnaMatrix[row + 3][col + 3]) {
                                if(sequenceFind == 1){
                                    if(mutantDetectorDao.getMutantByDna(Arrays.toString(dnaArray)).isEmpty()){
                                        AdnVerifications adnVerifications =  new AdnVerifications();
                                        adnVerifications.setDna(Arrays.toString(dnaArray));
                                        adnVerifications.setIs_mutant(true);
                                        mutantDetectorDao.saveIsMutant(adnVerifications);
                                    }
                                    return new ResponseEntity<>(true, HttpStatus.OK);
                                }else{
                                    sequenceFind++;
                                }
                            }
                        }
                    }
                }
                if(col - 3 >= 0 && row + 3 <= dnaMatrix[row].length) {
                    if (dnaMatrix[row][col] == dnaMatrix[row + 1][col - 1]) {
                        if (dnaMatrix[row][col] == dnaMatrix[row + 2][col - 2]) {
                            if (dnaMatrix[row][col] == dnaMatrix[row + 3][col - 3]) {
                                if(sequenceFind == 1){
                                    if(mutantDetectorDao.getMutantByDna(Arrays.toString(dnaArray)).isEmpty()){
                                        AdnVerifications adnVerifications =  new AdnVerifications();
                                        adnVerifications.setDna(Arrays.toString(dnaArray));
                                        adnVerifications.setIs_mutant(true);
                                        mutantDetectorDao.saveIsMutant(adnVerifications);
                                    }
                                    return new ResponseEntity<>(true, HttpStatus.OK);
                                }else{
                                    sequenceFind++;
                                }
                            }
                        }
                    }
                }
            }
        }
        if(mutantDetectorDao.getMutantByDna(Arrays.toString(dnaArray)).isEmpty()){
            AdnVerifications adnVerifications =  new AdnVerifications();
            adnVerifications.setDna(Arrays.toString(dnaArray));
            adnVerifications.setIs_mutant(false);
            mutantDetectorDao.saveIsMutant(adnVerifications);
        }
        return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<?> getStatsVerification() {
        List mutantList = mutantDetectorDao.getAdnVerifications(true);
        List humanList = mutantDetectorDao.getAdnVerifications(false);
        double ratio = 0;
        if(humanList.size()>0){
            ratio = mutantList.size()/humanList.size();
        }else{
            ratio = mutantList.size();
        }
        StatsVerification statsVerification = new StatsVerification(mutantList.size(), humanList.size(), ratio);
        return new ResponseEntity<>(statsVerification, HttpStatus.OK);
    }
}
