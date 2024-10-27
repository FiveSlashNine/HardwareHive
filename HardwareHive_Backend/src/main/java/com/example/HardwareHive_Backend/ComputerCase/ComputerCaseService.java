package com.example.HardwareHive_Backend.ComputerCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class ComputerCaseService {
   private final ComputerCaseRepository computerCaseRepository;

   @Autowired
   public ComputerCaseService(ComputerCaseRepository computerCaseRepository){
        this.computerCaseRepository = computerCaseRepository;
   }

    public List<Hardware> getComputerCases(){
        return new ArrayList<Hardware>(computerCaseRepository.findAll());
    }

    public void addNewComputerCase(ComputerCase computerCase) {
        Optional<ComputerCase> caseOptional = computerCaseRepository.findComputerCaseByName(computerCase.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            computerCaseRepository.save(computerCase);
        }
    }

    public void updateComputerCase(ComputerCase computerCase){
        computerCaseRepository.save(computerCase);
    }

    public void deleteComputerCase(Long id) {
       if(!computerCaseRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        computerCaseRepository.deleteById(id);
    }
}
