package com.luv2code.jobportal.services;

import com.luv2code.jobportal.entity.*;
import com.luv2code.jobportal.repository.JobPostActivityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobPostActivityService {

    private final JobPostActivityRepository jobPostActivityRepository;

    public JobPostActivityService(JobPostActivityRepository jobPostActivityRepository) {
        this.jobPostActivityRepository = jobPostActivityRepository;
    }

    public JobPostActivity addNew(JobPostActivity jobPostActivity) {
        return jobPostActivityRepository.save(jobPostActivity);
    }

    public List<RecruiterJobsDto> getRecruiterJobs(int recruiter) {

            List<IRecruiterJobs> recruiterJobs = jobPostActivityRepository.getRecruiterJobs(recruiter);
            List<RecruiterJobsDto> recruiterJobsDtoList = new ArrayList<>();
            for (IRecruiterJobs recruiterJob : recruiterJobs) {
                JobLocation loc=new JobLocation(recruiterJob.getLocationId(), recruiterJob.getCity(),recruiterJob.getState(),recruiterJob.getCountry());
                JobCompany comp=new JobCompany(recruiterJob.getCompanyId(),recruiterJob.getName(),"");
                recruiterJobsDtoList.add(new RecruiterJobsDto(recruiterJob.getTotalCandidates(),recruiterJob.getJob_post_id()
                                                ,recruiterJob.getJob_title(),loc,comp));

            }
        return recruiterJobsDtoList;
    }

    public JobPostActivity getOne(int id) {
        return jobPostActivityRepository.findById(id).orElseThrow(()->new RuntimeException("Job not found"));
    }
}
