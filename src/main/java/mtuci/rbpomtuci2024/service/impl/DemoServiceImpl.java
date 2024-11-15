package mtuci.rbpomtuci2024.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import mtuci.rbpomtuci2024.model.Demo;
import mtuci.rbpomtuci2024.Repository.DemoRepository;
import mtuci.rbpomtuci2024.Repository.DetailsRepository;
import mtuci.rbpomtuci2024.service.DemoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemoServiceImpl implements DemoService {

    private final DemoRepository demoRepository;
    private final DetailsRepository detailsRepository;

    @Override
    public void save(Demo demo) {
        demo.getDetails().forEach(details -> {
            details.setDemo(demo);
            detailsRepository.save(details);
        });
        demoRepository.save(demo);
    }

    @Override
    public List<Demo> findAll() {
        return demoRepository.findAll();
    }

    @Override
    public Demo findById(long id) {
        return demoRepository.findById(id).orElse(new Demo());
    }
}

