package com.example.HardwareHive_Backend.Coolers.CPUCooler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class CPUCoolerService {
   private final CPUCoolerRepository cpuCoolerRepository;

   @Autowired
   public CPUCoolerService(CPUCoolerRepository cpuCoolerRepository){
        this.cpuCoolerRepository = cpuCoolerRepository;
   }

    public List<Hardware> getCPUCoolers(){
        return new ArrayList<Hardware>(cpuCoolerRepository.findAll());
    }

    public void addNewCPUCooler(CPUCooler cpuCooler) {
        Optional<CPUCooler> caseOptional = cpuCoolerRepository.findCPUCoolerByName(cpuCooler.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            cpuCoolerRepository.save(cpuCooler);
        }
    }

    public void updateCPUCooler(CPUCooler cpuCooler){
        cpuCoolerRepository.save(cpuCooler);
    }

    public void deleteCPUCooler(Long id) {
       if(!cpuCoolerRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        cpuCoolerRepository.deleteById(id);
    }
}
