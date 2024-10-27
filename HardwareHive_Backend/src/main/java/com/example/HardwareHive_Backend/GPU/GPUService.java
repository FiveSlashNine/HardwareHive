package com.example.HardwareHive_Backend.GPU;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class GPUService {
   private final GPURepository gpuRepository;

   @Autowired
   public GPUService(GPURepository gpuRepository){
        this.gpuRepository = gpuRepository;
   }

    public List<Hardware> getGPUs(){
        return new ArrayList<Hardware>(gpuRepository.findAll());
    }

    public void addNewGPU(GPU gpu) {
        Optional<GPU> caseOptional = gpuRepository.findGPUByName(gpu.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            gpuRepository.save(gpu);
        }
    }

    public void updateGPU(GPU gpu){
        gpuRepository.save(gpu);
    }

    public void deleteGPU(Long id) {
       if(!gpuRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        gpuRepository.deleteById(id);
    }
}
