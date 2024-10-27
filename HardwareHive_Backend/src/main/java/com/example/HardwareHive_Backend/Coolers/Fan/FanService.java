package com.example.HardwareHive_Backend.Coolers.Fan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class FanService {
   private final FanRepository fanRepository;

   @Autowired
   public FanService(FanRepository fanRepository){
        this.fanRepository = fanRepository;
   }

    public List<Hardware> getFans(){
        return new ArrayList<Hardware>(fanRepository.findAll());
    }

    public void addNewFan(Fan fan) {
        Optional<Fan> caseOptional = fanRepository.findFanByName(fan.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            fanRepository.save(fan);
        }
    }

    public void updateFan(Fan fan){
        fanRepository.save(fan);
    }

    public void deleteFan(Long id) {
       if(!fanRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        fanRepository.deleteById(id);
    }
}
