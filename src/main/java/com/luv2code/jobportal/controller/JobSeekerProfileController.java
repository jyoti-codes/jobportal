package com.luv2code.jobportal.controller;

import com.luv2code.jobportal.entity.JobSeekerProfile;
import com.luv2code.jobportal.entity.Skills;
import com.luv2code.jobportal.entity.Users;
import com.luv2code.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.luv2code.jobportal.services.JobSeekerProfileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/job-seeker-profile")
public class JobSeekerProfileController{

private JobSeekerProfileService jobSeekerProfileService;

private UsersRepository usersRepository;

@Autowired
    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService, UsersRepository usersRepository) {
        this.jobSeekerProfileService = jobSeekerProfileService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String jobSeekerProfile(Model model) {
        JobSeekerProfile jobSeekerProfile=new JobSeekerProfile();
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        List<Skills> skills=new ArrayList<>();

        if(!(authentication instanceof AnonymousAuthenticationToken)) {
            Users user=usersRepository.findByEmail(authentication.getName()).orElseThrow(()->new UsernameNotFoundException("User not found"));
         Optional<JobSeekerProfile> seekerProfile= jobSeekerProfileService.getOne(user.getUserId());
        if(seekerProfile.isPresent()) {
            jobSeekerProfile=seekerProfile.get();
            if(jobSeekerProfile.getSkills().isEmpty()) {
                skills.add(new Skills());
                jobSeekerProfile.setSkills(skills);
            }
        }
        model.addAttribute("profile", jobSeekerProfile);
        model.addAttribute("skills", skills);
        }
        return "job-seeker-profile";
    }

}
