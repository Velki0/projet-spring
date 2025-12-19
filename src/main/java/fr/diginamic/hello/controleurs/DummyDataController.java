package fr.diginamic.hello.controleurs;

import fr.diginamic.hello.dummydatas.InsertionDummyData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyDataController {

    @Autowired
    private InsertionDummyData dummyData;

    @PostConstruct
    public void initData() {

        dummyData.insererQuelquesVillesEtDepartements();

    }

}
