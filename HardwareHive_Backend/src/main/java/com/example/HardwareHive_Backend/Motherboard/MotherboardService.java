package com.example.HardwareHive_Backend.Motherboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class MotherboardService {
   private final MotherboardRepository motherboardRepository;

   @Autowired
   public MotherboardService(MotherboardRepository motherboardRepository){
        this.motherboardRepository = motherboardRepository;
   }

    public List<Hardware> getMotherboards(){
        return new ArrayList<Hardware>(motherboardRepository.findAll());
    }

    public void addNewMotherboard(Motherboard motherboard) {
        Optional<Motherboard> caseOptional = motherboardRepository.findMotherboardByName(motherboard.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            motherboardRepository.save(motherboard);
        }
    }

    public void updateMotherboard(Motherboard motherboard){
        motherboardRepository.save(motherboard);
    }

    public void deleteMotherboard(Long id) {
       if(!motherboardRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        motherboardRepository.deleteById(id);
    }
}
