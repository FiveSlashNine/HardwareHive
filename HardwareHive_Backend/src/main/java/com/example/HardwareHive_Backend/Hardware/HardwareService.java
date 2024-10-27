package com.example.HardwareHive_Backend.Hardware;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class HardwareService {
   private final HardwareRepository hardwareRepository;

   @Autowired
   public HardwareService(HardwareRepository hardwareRepository){
        this.hardwareRepository = hardwareRepository;
   }

    public List<Hardware> getHardware(){
        return hardwareRepository.findAll();
    }

     public List<Hardware> getSimilarHardware(String name) {
        return hardwareRepository.findByNameContaining(name.toLowerCase());
    }

    public void updateHardware(Hardware hardware){
        hardwareRepository.save(hardware);
    }

    public void deleteHardware(Long id) {
       if(!hardwareRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

       hardwareRepository.deleteById(id);
    }
}
