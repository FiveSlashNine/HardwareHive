package com.example.HardwareHive_Backend.PSU;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class PSUService {
   private final PSURepository psuRepository;

   @Autowired
   public PSUService(PSURepository psuRepository){
        this.psuRepository = psuRepository;
   }

    public List<Hardware> getPSUs(){
        return new ArrayList<Hardware>(psuRepository.findAll());
    }

    public void addNewPSU(PSU psu) {
        Optional<PSU> caseOptional = psuRepository.findPSUByName(psu.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            psuRepository.save(psu);
        }
    }

    public void updatePSU(PSU psu){
        psuRepository.save(psu);
    }

    public void deletePSU(Long id) {
       if(!psuRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        psuRepository.deleteById(id);
    }
}
