package com.example.HardwareHive_Backend.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HardwareHive_Backend.Hardware.Hardware;

@Service
public class StorageService {
   private final StorageRepository storageRepository;

   @Autowired
   public StorageService(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
   }

    public List<Hardware> getStorages(){
        return new ArrayList<Hardware>(storageRepository.findAll());
    }

    public void addNewStorage(Storage storage) {
        Optional<Storage> caseOptional = storageRepository.findStorageByName(storage.getName());
        if(caseOptional.isPresent()){
            throw new IllegalStateException("Hardware already exists");
        } else {
            storageRepository.save(storage);
        }
    }

    public void updateStorage(Storage storage){
        storageRepository.save(storage);
    }

    public void deleteStorage(Long id) {
       if(!storageRepository.existsById(id)){
           throw new IllegalStateException("Hardware with id( " + id + ") does not exist");
       }

        storageRepository.deleteById(id);
    }
}
