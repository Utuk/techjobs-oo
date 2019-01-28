package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
//
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
//when done visiting url /job?id=X will display the details of an id equal to int x

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }
    //returns the value of findById() and passes that into job-detail.html
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes attributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        //validate in the add handler, AND if its valid, create a NEW job object and add it to the data layer
        //by calling jobData.add(newjob)
        //to create the new job you will need pre-existing objects for all other fields other than name (employer location etc)
        //make sure all the objects are in the correct order
        //redirect to new-job.html when down, orr if there is an error, redirect to add.html
        //-can i create a new job with /job/add/ by entering the name,employer,etc
        //if i have an error will it redirect me back
        //is the url /job?id=(int x)
        //when searching and posting job data, my job shows up where it is supposed to show up
        if (errors.hasErrors()) {
            return "new-job";
        }

        // create job properties from job form
        String jobName = jobForm.getName();
        Employer jobEmp = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location jobLoc = jobData.getLocations().findById(jobForm.getLocationId());
        PositionType jobPos = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());
        CoreCompetency jobComp = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId());

        // construct new job
        Job newJob = new Job(jobName, jobEmp, jobLoc, jobPos, jobComp);

        // add job to jobData
        jobData.add(newJob);

        // add job id to redirect
        attributes.addAttribute("id", newJob.getId());

        // display new job detail
        return "redirect:/job";

    }
}