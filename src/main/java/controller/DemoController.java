package controller;

import org.springframework.web.bind.annotation.*;
import model.Demo;

@RestController
@RequestMapping("/")
public class DemoController {

    //("/") + ("/hello")
    @GetMapping("/hello")
    public String getHello(@RequestParam String str) {
        return str;
    }

    @PostMapping
    public Demo setValue(@RequestBody Demo demo) {
        demo.setNumber(demo.getNumber() + 1);
        return demo;
    }

}
