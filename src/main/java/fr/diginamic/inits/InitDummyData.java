package fr.diginamic.inits;

import fr.diginamic.services.DummyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitDummyData implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DummyDataService dummyData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        dummyData.insererQuelquesVillesEtDepartements();

    }

}
