package mtuci.rbpomtuci2024.service;

import mtuci.rbpomtuci2024.model.Demo;

import java.util.List;

public interface DemoService {
    void save(Demo demo);
    List<Demo> findAll();
    Demo findById(long id);
}
