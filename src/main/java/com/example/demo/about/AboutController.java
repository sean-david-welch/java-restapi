package com.example.demo.about;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/api/about")
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @GetMapping
	public List<About> GetAbout() {
        return aboutService.GetAbout();
	}

    @PutMapping(path = "{aboutId}")
    public void updateabout(@PathVariable("aboutId") String aboutId, @RequestBody About about) {
        aboutService.PutAbout(aboutId, about);
    }
    

    @PostMapping
    public void registerAbout(@RequestBody About about) {
        aboutService.CreateAbout(about);
    }

    @DeleteMapping(path = "{aboutId}")
    public void deleteAbout(@PathVariable("aboutId") String aboutId) {
        aboutService.RemoveAbout(aboutId);
    }
}