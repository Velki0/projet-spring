package fr.diginamic.dummydatas;

import fr.diginamic.dummydatas.services.IDummyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitDummyData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IDummyDataService dummyData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // dummyData.insererVillesEtDepartements();

    }

}
