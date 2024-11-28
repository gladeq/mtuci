package ru.mtuci.rbpomtuci2024.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mtuci.rbpomtuci2024.model.Demo;
import ru.mtuci.rbpomtuci2024.model.Details;
import ru.mtuci.rbpomtuci2024.Repository.DetailsRepository;
import ru.mtuci.rbpomtuci2024.service.DemoService;

@RestController
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailsController {

    private final DetailsRepository detailsRepository;
    private final DemoService demoService;

    @PostMapping("/{demo_id}/save")
    public void save(@PathVariable(value = "demo_id") Long demoId,
                     @RequestBody Details details) {
        Demo demo = demoService.findById(demoId);
        details.setDemo(demo);
        detailsRepository.save(details);
    }
}

