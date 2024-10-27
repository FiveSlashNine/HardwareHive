package com.example.HardwareHive_Backend.CPU;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class CPUService {
   private final CPURepository cpuRepository;

   @Autowired
   public CPUService(CPURepository cpuRepository){
        this.cpuRepository = cpuRepository;
   }

    public List<Hardware> getCPUs(){
        return new ArrayList<Hardware>(cpuRepository.findAll());
    }

    public void addNewCPU(CPU cpu) {
        Optional<CPU> caseOptional = cpuRepository.findCPUByName(cpu.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            cpuRepository.save(cpu);
        }
    }

    public void updateCPU(CPU cpu){
        cpuRepository.save(cpu);
    }

    public void deleteCPU(Long id) {
       if(!cpuRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        cpuRepository.deleteById(id);
    }
}
