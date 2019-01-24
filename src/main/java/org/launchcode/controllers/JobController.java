package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        model.addAttribute("name", jobData.findAll().get(id).getName());
        model.addAttribute("employer", jobData.findAll().get(id).getEmployer());
        model.addAttribute("location", jobData.findAll().get(id).getLocation());
        model.addAttribute("position", jobData.findAll().get(id).getPositionType());
        model.addAttribute("skill", jobData.findAll().get(id).getCoreCompetency());

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            System.out.println("Errors = " + errors.getAllErrors().toString());
            return "new-job";
        }

        Job newJob = new Job();

        newJob.setName(jobForm.getName());
        newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));
        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));

        //add newJobs to jobData
        jobData.add(newJob);

        model.addAttribute("name", newJob.getName());
        model.addAttribute("employer", newJob.getEmployer());
        model.addAttribute("location", newJob.getLocation());
        model.addAttribute("position", newJob.getPositionType());
        model.addAttribute("skill", newJob.getCoreCompetency());

        return "job-detail";

    }
}
