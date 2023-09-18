package com.example.demo.about;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

@Service
public class AboutService {
    private final AboutRepository aboutRepository;

    public AboutService(AboutRepository aboutRepository) {
        this.aboutRepository = aboutRepository;
    }

    public List<About> GetAbout() {
        return aboutRepository.findAll();
    }

    public void CreateAbout(About about) {
        Optional<About> aboutOptional = aboutRepository.findAboutById(about.getId());

        if (aboutOptional.isPresent()) {
            throw new IllegalStateException("This content exists");
        }

        about.setId(UUID.randomUUID().toString());

        aboutRepository.save(about);
    }

    public void RemoveAbout(String aboutId) {
        boolean exists = aboutRepository.existsById(aboutId);

        if (!exists) {
            throw new IllegalStateException("Content does not exist" + aboutId);
        }
        aboutRepository.deleteById(aboutId);
    }

    @Transactional
    public void PutAbout(String aboutId, About about) {
        Optional<About> aboutOptional = aboutRepository.findAboutById(about.getId());

        if (aboutOptional.isPresent()) {
            throw new IllegalStateException("This content exists");
        }
       
        About existingAbout = aboutOptional.get();

        existingAbout.setTitle(about.getTitle());
        existingAbout.setDescription(about.getDescription());
        existingAbout.setImage(about.getImage());

        aboutRepository.save(about);
    }
}