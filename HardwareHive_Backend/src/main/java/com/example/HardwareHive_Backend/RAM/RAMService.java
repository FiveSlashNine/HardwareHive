package com.example.HardwareHive_Backend.RAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class RAMService {
   private final RAMRepository ramRepository;

   @Autowired
   public RAMService(RAMRepository ramRepository){
        this.ramRepository = ramRepository;
   }

    public List<Hardware> getRAMs(){
        return new ArrayList<Hardware>(ramRepository.findAll());
    }

    public void addNewRAM(RAM ram) {
        Optional<RAM> caseOptional = ramRepository.findRAMByName(ram.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            ramRepository.save(ram);
        }
    }

    public void updateRAM(RAM ram){
        ramRepository.save(ram);
    }

    public void deleteRAM(Long id) {
       if(!ramRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        ramRepository.deleteById(id);
    }
}
