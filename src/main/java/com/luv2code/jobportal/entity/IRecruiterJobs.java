package com.luv2code.jobportal.entity;

public interface IRecruiterJobs {

     Long getTotalCandidates();

     Integer getJob_post_id();

     String getJob_title();
     Integer getLocationId();
     String getState();
     String getCity();
     String getCountry();

     Integer getCompanyId();
     String getName();
}
