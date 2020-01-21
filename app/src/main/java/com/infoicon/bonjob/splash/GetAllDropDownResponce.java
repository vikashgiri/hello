package com.infoicon.bonjob.splash;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class GetAllDropDownResponce implements Parcelable {


    /**
     * success : true
     * error : false
     * industryTypes : [{"industry_type_id":"1","industry_type_name":"Catering","positions":[{"position_id":"4","position_name":"Butcher"},{"position_id":"5","position_name":"Baker"},{"position_id":"6","position_name":"Pork Butcher"},{"position_id":"7","position_name":"Event Planner"},{"position_id":"8","position_name":"Chef"},{"position_id":"9","position_name":"Chef in collective catering"},{"position_id":"10","position_name":"Manager in collective catering"},{"position_id":"11","position_name":"Manager in fast food"},{"position_id":"12","position_name":"Kitchen Chef"},{"position_id":"13","position_name":"Station Chef"},{"position_id":"14","position_name":"Production Manager in collective catering"},{"position_id":"15","position_name":"Chef Restaurant Manager / Owner"},{"position_id":"16","position_name":"Chocolatier / Candy maker"},{"position_id":"17","position_name":"Kitchen Clerk"},{"position_id":"18","position_name":"Crêpier"},{"position_id":"19","position_name":"Cook"},{"position_id":"20","position_name":"Cook in the public administration"},{"position_id":"21","position_name":"Dietitian"},{"position_id":"22","position_name":"Restaurant Dietitian"},{"position_id":"23","position_name":"Assistant Director in fast food"},{"position_id":"24","position_name":"Restaurant Director / Manager"},{"position_id":"25","position_name":"Restaurant General Manager"},{"position_id":"26","position_name":"Director / Manager in fast food"},{"position_id":"27","position_name":"Fish preparer"},{"position_id":"28","position_name":"Inventory Manager"},{"position_id":"29","position_name":"Food Service worker"},{"position_id":"30","position_name":"Food Service worker - Kitchen area"},{"position_id":"31","position_name":"Collective catering worker"},{"position_id":"32","position_name":"Restaurant Manager / Owner"},{"position_id":"33","position_name":"Cheese maker"},{"position_id":"34","position_name":"Manager in collective catering"},{"position_id":"35","position_name":"Manager in restauration"},{"position_id":"36","position_name":"Manager in fast food restaurant"},{"position_id":"37","position_name":"Pastry cook"},{"position_id":"38","position_name":"Pizzaiolo"},{"position_id":"39","position_name":"Dishwasher worker"},{"position_id":"40","position_name":"Fishmonger"},{"position_id":"41","position_name":"Preparator / Sales worker in fast food"},{"position_id":"42","position_name":"Revenue or Yield Manager"},{"position_id":"43","position_name":"Second cook"},{"position_id":"44","position_name":"Sous-chef"},{"position_id":"45","position_name":"Caterer"},{"position_id":"46","position_name":"Caterer / Reception Organizer"}]},{"industry_type_id":"2","industry_type_name":"Service","positions":[{"position_id":"47","position_name":"Reservation / Booking Agent"},{"position_id":"48","position_name":"Bartender"},{"position_id":"49","position_name":"Head Waiter"},{"position_id":"50","position_name":"Wine Steward / Sommelier Manager"},{"position_id":"51","position_name":"Service Clerk"},{"position_id":"52","position_name":"Coffee / Bar worker"},{"position_id":"53","position_name":"Food Service worker - Service area"},{"position_id":"54","position_name":"Fast food worker"},{"position_id":"55","position_name":"Coffee or Bar Manager / Owner"},{"position_id":"56","position_name":"Coffee Waiter"},{"position_id":"57","position_name":"Host / Hostess"},{"position_id":"58","position_name":"Beverage waiter"},{"position_id":"59","position_name":"Hotel Butler"},{"position_id":"60","position_name":"Butler"},{"position_id":"61","position_name":"Dining Room / Service Manager"},{"position_id":"62","position_name":"Waiter"},{"position_id":"63","position_name":"Wine Steward / Sommelier"},{"position_id":"64","position_name":"Sales Coordinator / Sales worker"}]},{"industry_type_id":"3","industry_type_name":"Hotel","positions":[{"position_id":"65","position_name":"Assistant General Manager"},{"position_id":"66","position_name":"Sales Coordinator"},{"position_id":"67","position_name":"Porter"},{"position_id":"68","position_name":"Guest Service worker / Hotel messenger"},{"position_id":"69","position_name":"Maintenance Manager"},{"position_id":"70","position_name":"Head Waiter room service"},{"position_id":"71","position_name":"Reception / Front Office Manager"},{"position_id":"72","position_name":"Hotel Concierge"},{"position_id":"73","position_name":"Palace and 5 stars Hotel Concierge"},{"position_id":"74","position_name":"Sales Director"},{"position_id":"75","position_name":"Director of Hotel Operations / Hotel Manager"},{"position_id":"76","position_name":"Director of accommodation"},{"position_id":"77","position_name":"Room Service worker"},{"position_id":"78","position_name":"Chambermaid"},{"position_id":"79","position_name":"Housekeeper"},{"position_id":"80","position_name":"Executive Housekeeper / Housekeeping Manager"},{"position_id":"81","position_name":"Bellhop"},{"position_id":"82","position_name":"Guest relation manager"},{"position_id":"83","position_name":"Lift attendant"},{"position_id":"84","position_name":"Hotel Linen Manager"},{"position_id":"85","position_name":"Manager in upscale hotels and restaurants"},{"position_id":"86","position_name":"Night Auditor"},{"position_id":"87","position_name":"Porter / Doorman"},{"position_id":"88","position_name":"Receptionist / Front Office Associate"},{"position_id":"89","position_name":"Night receptionist / Night Clerk"},{"position_id":"90","position_name":"Room Service Manager"},{"position_id":"91","position_name":"Spa Manager"},{"position_id":"92","position_name":"Maintenance Technician"},{"position_id":"93","position_name":"Valet (room service)"},{"position_id":"94","position_name":"Night watchman"},{"position_id":"95","position_name":"Valet (car service)"}]}]
     * experiences : [{"experience_id":"1","experience":"No experience"},{"experience_id":"2","experience":"Less than 1 year"},{"experience_id":"3","experience":"1-2 years"},{"experience_id":"4","experience":"3-4 years"},{"experience_id":"5","experience":"5 years and +"}]
     * educationLevels : [{"education_id":"1","education_title":"No diploma"},{"education_id":"2","education_title":"Youth training"},{"education_id":"3","education_title":"Vocational training"},{"education_id":"4","education_title":"BP"},{"education_id":"5","education_title":"High-School"},{"education_id":"6","education_title":"High-School (professional training)"},{"education_id":"7","education_title":"Higher Diploma / 12th Grade"},{"education_id":"8","education_title":"Higher Education / Associates's Degree"},{"education_id":"9","education_title":"Bachelor"},{"education_id":"10","education_title":"Master and above"},{"education_id":"11","education_title":"Other"}]
     * candidateSeeks : [{"candidate_seek_id":"1","candidate_seek_title":"Apprenticeship"},{"candidate_seek_id":"2","candidate_seek_title":"Internship"},{"candidate_seek_id":"3","candidate_seek_title":"Assistant General Manager"},{"candidate_seek_id":"4","candidate_seek_title":"Reservation / Booking Agent"},{"candidate_seek_id":"5","candidate_seek_title":"Sales Coordinator"},{"candidate_seek_id":"6","candidate_seek_title":"Porter"},{"candidate_seek_id":"7","candidate_seek_title":"Bartender"},{"candidate_seek_id":"8","candidate_seek_title":"Butcher"},{"candidate_seek_id":"9","candidate_seek_title":"Baker"},{"candidate_seek_id":"10","candidate_seek_title":"Pork Butcher"},{"candidate_seek_id":"11","candidate_seek_title":"Event Planner"},{"candidate_seek_id":"12","candidate_seek_title":"Guest Service worker / Hotel messenger"},{"candidate_seek_id":"13","candidate_seek_title":"Chef"},{"candidate_seek_id":"14","candidate_seek_title":"Chef in collective catering"},{"candidate_seek_id":"15","candidate_seek_title":"Manager in collective catering"},{"candidate_seek_id":"16","candidate_seek_title":"Manager in fast food"},{"candidate_seek_id":"17","candidate_seek_title":"Kitchen Chef"},{"candidate_seek_id":"18","candidate_seek_title":"Maintenance Manager"},{"candidate_seek_id":"19","candidate_seek_title":"Station Chef"},{"candidate_seek_id":"20","candidate_seek_title":"Production Manager in collective catering"},{"candidate_seek_id":"21","candidate_seek_title":"Head Waiter"},{"candidate_seek_id":"22","candidate_seek_title":"Head Waiter room service"},{"candidate_seek_id":"23","candidate_seek_title":"Reception / Front Office Manager"},{"candidate_seek_id":"24","candidate_seek_title":"Chef Restaurant Manager / Owner"},{"candidate_seek_id":"25","candidate_seek_title":"Wine Steward / Sommelier Manager"},{"candidate_seek_id":"26","candidate_seek_title":"Chocolatier / Candy maker"},{"candidate_seek_id":"27","candidate_seek_title":"Kitchen Clerk"},{"candidate_seek_id":"28","candidate_seek_title":"Service Clerk"},{"candidate_seek_id":"29","candidate_seek_title":"Hotel Concierge"},{"candidate_seek_id":"30","candidate_seek_title":"Palace and 5 stars Hotel Concierge"},{"candidate_seek_id":"31","candidate_seek_title":"Crêpier"},{"candidate_seek_id":"32","candidate_seek_title":"Cook"},{"candidate_seek_id":"33","candidate_seek_title":"Cook in the public administration"},{"candidate_seek_id":"34","candidate_seek_title":"Dietitian"},{"candidate_seek_id":"35","candidate_seek_title":"Restaurant Dietitian"},{"candidate_seek_id":"36","candidate_seek_title":"Assistant Director in fast food"},{"candidate_seek_id":"37","candidate_seek_title":"Sales Director"},{"candidate_seek_id":"38","candidate_seek_title":"Director of Hotel Operations / Hotel Manager"},{"candidate_seek_id":"39","candidate_seek_title":"Director of accommodation"},{"candidate_seek_id":"40","candidate_seek_title":"Restaurant Director / Manager"},{"candidate_seek_id":"41","candidate_seek_title":"Restaurant General Manager"},{"candidate_seek_id":"42","candidate_seek_title":"Director / Manager in fast food"},{"candidate_seek_id":"43","candidate_seek_title":"Fish preparer"},{"candidate_seek_id":"44","candidate_seek_title":"Inventory Manager"},{"candidate_seek_id":"45","candidate_seek_title":"Room Service worker"},{"candidate_seek_id":"46","candidate_seek_title":"Coffee / Bar worker"},{"candidate_seek_id":"47","candidate_seek_title":"Food Service worker"},{"candidate_seek_id":"48","candidate_seek_title":"Food Service worker - Kitchen area"},{"candidate_seek_id":"49","candidate_seek_title":"Food Service worker - Service area"},{"candidate_seek_id":"50","candidate_seek_title":"Collective catering worker"},{"candidate_seek_id":"51","candidate_seek_title":"Fast food worker"},{"candidate_seek_id":"52","candidate_seek_title":"Coffee or Bar Manager / Owner"},{"candidate_seek_id":"53","candidate_seek_title":"Restaurant Manager / Owner"},{"candidate_seek_id":"54","candidate_seek_title":"Chambermaid"},{"candidate_seek_id":"55","candidate_seek_title":"Cheese maker"},{"candidate_seek_id":"56","candidate_seek_title":"Coffee Waiter"},{"candidate_seek_id":"57","candidate_seek_title":"Manager in collective catering"},{"candidate_seek_id":"58","candidate_seek_title":"Housekeeper"},{"candidate_seek_id":"59","candidate_seek_title":"Executive Housekeeper / Housekeeping Manager"},{"candidate_seek_id":"60","candidate_seek_title":"Bellhop"},{"candidate_seek_id":"61","candidate_seek_title":"Guest relation manager"},{"candidate_seek_id":"62","candidate_seek_title":"Host / Hostess"},{"candidate_seek_id":"63","candidate_seek_title":"Lift attendant"},{"candidate_seek_id":"64","candidate_seek_title":"Beverage waiter"},{"candidate_seek_id":"65","candidate_seek_title":"Hotel Linen Manager"},{"candidate_seek_id":"66","candidate_seek_title":"Hotel Butler"},{"candidate_seek_id":"67","candidate_seek_title":"Butler"},{"candidate_seek_id":"68","candidate_seek_title":"Manager in restauration"},{"candidate_seek_id":"69","candidate_seek_title":"Manager in fast food restaurant"},{"candidate_seek_id":"70","candidate_seek_title":"Manager in upscale hotels and restaurants"},{"candidate_seek_id":"71","candidate_seek_title":"Night Auditor"},{"candidate_seek_id":"72","candidate_seek_title":"Pastry cook"},{"candidate_seek_id":"73","candidate_seek_title":"Pizzaiolo"},{"candidate_seek_id":"74","candidate_seek_title":"Dishwasher worker"},{"candidate_seek_id":"75","candidate_seek_title":"Fishmonger"},{"candidate_seek_id":"76","candidate_seek_title":"Porter / Doorman"},{"candidate_seek_id":"77","candidate_seek_title":"Preparator / Sales worker in fast food"},{"candidate_seek_id":"78","candidate_seek_title":"Receptionist / Front Office Associate"},{"candidate_seek_id":"79","candidate_seek_title":"Night receptionist / Night Clerk"},{"candidate_seek_id":"80","candidate_seek_title":"Dining Room / Service Manager"},{"candidate_seek_id":"81","candidate_seek_title":"Room Service Manager"},{"candidate_seek_id":"82","candidate_seek_title":"Revenue or Yield Manager"},{"candidate_seek_id":"83","candidate_seek_title":"Second cook"},{"candidate_seek_id":"84","candidate_seek_title":"Waiter"},{"candidate_seek_id":"85","candidate_seek_title":"Wine Steward / Sommelier"},{"candidate_seek_id":"86","candidate_seek_title":"Sous-chef"},{"candidate_seek_id":"87","candidate_seek_title":"Spa Manager"},{"candidate_seek_id":"88","candidate_seek_title":"Maintenance Technician"},{"candidate_seek_id":"89","candidate_seek_title":"Caterer"},{"candidate_seek_id":"90","candidate_seek_title":"Caterer / Reception Organizer"},{"candidate_seek_id":"91","candidate_seek_title":"Valet (room service)"},{"candidate_seek_id":"92","candidate_seek_title":"Night watchman"},{"candidate_seek_id":"93","candidate_seek_title":"Sales Coordinator / Sales worker"},{"candidate_seek_id":"94","candidate_seek_title":"Valet (car service)"},{"candidate_seek_id":"95","candidate_seek_title":"Other"}]
     * jobLanguages : [{"job_language_id":"1","job_language_title":"French"},{"job_language_id":"2","job_language_title":"English"},{"job_language_id":"3","job_language_title":"Spanish"},{"job_language_id":"4","job_language_title":"German"},{"job_language_id":"5","job_language_title":"Portuguese"},{"job_language_id":"6","job_language_title":"Chinese"},{"job_language_id":"7","job_language_title":"Japanese"},{"job_language_id":"8","job_language_title":"Arab"},{"job_language_id":"9","job_language_title":"Russian"},{"job_language_id":"10","job_language_title":"Hindi"}]
     * languageProficiencies : [{"language_proficiency_id":"1","language_proficiency_title":"Beginner"},{"language_proficiency_id":"2","language_proficiency_title":"Intermediate"},{"language_proficiency_id":"3","language_proficiency_title":"Advanced"},{"language_proficiency_id":"4","language_proficiency_title":"Fluent"}]
     * currentStatuses : [{"seeker_current_status_id":"1","seeker_current_status_title":"Student"},{"seeker_current_status_id":"2","seeker_current_status_title":"Apprentice"},{"seeker_current_status_id":"3","seeker_current_status_title":"Employed"},{"seeker_current_status_id":"4","seeker_current_status_title":"Jobseeker"},{"seeker_current_status_id":"5","seeker_current_status_title":"Inactive"}]
     * skills : [{"skill_id":"1","skill_title":"Catering"},{"skill_id":"2","skill_title":"Service"},{"skill_id":"3","skill_title":"Hotel"}]
     * mobilities : [{"mobility_id":"1","mobility_title":"Yes"},{"mobility_id":"2","mobility_title":"No"}]
     * companyCategories : [{"company_category_id":"1","company_category_title":"Restaurant"},{"company_category_id":"2","company_category_title":"Hotel"},{"company_category_id":"3","company_category_title":"Hotel / Restaurant"},{"company_category_id":"4","company_category_title":"Cafe, Bar, Brasserie"},{"company_category_id":"5","company_category_title":"Nightclub, Casino"},{"company_category_id":"6","company_category_title":"Thalassotherapy"},{"company_category_id":"7","company_category_title":"Hotels - Accommodation"},{"company_category_id":"8","company_category_title":"Caterer"}]
     * numberOfEmployees : [{"number_of_employee_id":"1","number_of_employee_title":"1-10 employees"},{"number_of_employee_id":"2","number_of_employee_title":"10-50 employees"},{"number_of_employee_id":"3","number_of_employee_title":"50-100 employees"},{"number_of_employee_id":"4","number_of_employee_title":"100+ employees"}]
     * jobTitles : [{"job_title_id":"1","job_title":"Assistant General Manager"},{"job_title_id":"2","job_title":"Reservation / Booking Agent"},{"job_title_id":"3","job_title":"Sales Coordinator"},{"job_title_id":"4","job_title":"Porter"},{"job_title_id":"5","job_title":"Bartender"},{"job_title_id":"6","job_title":"Butcher"},{"job_title_id":"7","job_title":"Baker"},{"job_title_id":"8","job_title":"Pork Butcher"},{"job_title_id":"9","job_title":"Event Planner"},{"job_title_id":"10","job_title":"Guest Service worker / Hotel messenger"},{"job_title_id":"11","job_title":"Chef"},{"job_title_id":"12","job_title":"Chef in collective catering"},{"job_title_id":"13","job_title":"Manager in collective catering"},{"job_title_id":"14","job_title":"Manager in fast food"},{"job_title_id":"15","job_title":"Kitchen Chef"},{"job_title_id":"16","job_title":"Maintenance Manager"},{"job_title_id":"17","job_title":"Station Chef"},{"job_title_id":"18","job_title":"Production Manager in collective catering"},{"job_title_id":"19","job_title":"Head Waiter"},{"job_title_id":"20","job_title":"Head Waiter room service"},{"job_title_id":"21","job_title":"Reception / Front Office Manager"},{"job_title_id":"22","job_title":"Chef Restaurant Manager / Owner"},{"job_title_id":"23","job_title":"Wine Steward / Sommelier Manager"},{"job_title_id":"24","job_title":"Chocolatier / Candy maker"},{"job_title_id":"25","job_title":"Kitchen Clerk"},{"job_title_id":"26","job_title":"Service Clerk"},{"job_title_id":"27","job_title":"Hotel Concierge"},{"job_title_id":"28","job_title":"Palace and 5 stars Hotel Concierge"},{"job_title_id":"29","job_title":"Crêpier"},{"job_title_id":"30","job_title":"Cook"},{"job_title_id":"31","job_title":"Cook in the public administration"},{"job_title_id":"32","job_title":"Dietitian"},{"job_title_id":"33","job_title":"Restaurant Dietitian"},{"job_title_id":"34","job_title":"Assistant Director in fast food"},{"job_title_id":"35","job_title":"Sales Director"},{"job_title_id":"36","job_title":"Director of Hotel Operations / Hotel Manager"},{"job_title_id":"37","job_title":"Director of accommodation"},{"job_title_id":"38","job_title":"Restaurant Director / Manager"},{"job_title_id":"39","job_title":"Restaurant General Manager"},{"job_title_id":"40","job_title":"Director / Manager in fast food"},{"job_title_id":"41","job_title":"Fish preparer"},{"job_title_id":"42","job_title":"Inventory Manager"},{"job_title_id":"43","job_title":"Room Service worker"},{"job_title_id":"44","job_title":"Coffee / Bar worker"},{"job_title_id":"45","job_title":"Food Service worker"},{"job_title_id":"46","job_title":"Food Service worker - Kitchen area"},{"job_title_id":"47","job_title":"Food Service worker - Service area"},{"job_title_id":"48","job_title":"Collective catering worker"},{"job_title_id":"49","job_title":"Fast food worker"},{"job_title_id":"50","job_title":"Coffee or Bar Manager / Owner"},{"job_title_id":"51","job_title":"Restaurant Manager / Owner"},{"job_title_id":"52","job_title":"Chambermaid"},{"job_title_id":"53","job_title":"Cheese maker"},{"job_title_id":"54","job_title":"Coffee Waiter"},{"job_title_id":"55","job_title":"Manager in collective catering"},{"job_title_id":"56","job_title":"Housekeeper"},{"job_title_id":"57","job_title":"Executive Housekeeper / Housekeeping Manager"},{"job_title_id":"58","job_title":"Bellhop"},{"job_title_id":"59","job_title":"Guest relation manager"},{"job_title_id":"60","job_title":"Host / Hostess"},{"job_title_id":"61","job_title":"Lift attendant"},{"job_title_id":"62","job_title":"Beverage waiter"},{"job_title_id":"63","job_title":"Hotel Linen Manager"},{"job_title_id":"64","job_title":"Hotel Butler"},{"job_title_id":"65","job_title":"Butler"},{"job_title_id":"66","job_title":"Manager in restauration"},{"job_title_id":"67","job_title":"Manager in fast food restaurant"},{"job_title_id":"68","job_title":"Manager in upscale hotels and restaurants"},{"job_title_id":"69","job_title":"Night Auditor"},{"job_title_id":"70","job_title":"Pastry cook"},{"job_title_id":"71","job_title":"Pizzaiolo"},{"job_title_id":"72","job_title":"Dishwasher worker"},{"job_title_id":"73","job_title":"Fishmonger"},{"job_title_id":"74","job_title":"Porter / Doorman"},{"job_title_id":"75","job_title":"Preparator / Sales worker in fast food"},{"job_title_id":"76","job_title":"Receptionist / Front Office Associate"},{"job_title_id":"77","job_title":"Night receptionist / Night Clerk"},{"job_title_id":"78","job_title":"Dining Room / Service Manager"},{"job_title_id":"79","job_title":"Room Service Manager"},{"job_title_id":"80","job_title":"Revenue or Yield Manager"},{"job_title_id":"81","job_title":"Second cook"},{"job_title_id":"82","job_title":"Waiter"},{"job_title_id":"83","job_title":"Wine Steward / Sommelier"},{"job_title_id":"84","job_title":"Sous-chef"},{"job_title_id":"85","job_title":"Spa Manager"},{"job_title_id":"86","job_title":"Maintenance Technician"},{"job_title_id":"87","job_title":"Caterer"},{"job_title_id":"88","job_title":"Caterer / Reception Organizer"},{"job_title_id":"89","job_title":"Valet (room service)"},{"job_title_id":"90","job_title":"Night watchman"},{"job_title_id":"91","job_title":"Sales Coordinator / Sales worker"},{"job_title_id":"92","job_title":"Valet (car service)"}]
     * contractTypes : [{"contract_id":"1","contract_title":"Permanent contract"},{"contract_id":"2","contract_title":"Interim"},{"contract_id":"3","contract_title":"Apprenticeship"},{"contract_id":"4","contract_title":"Internship"},{"contract_id":"5","contract_title":"Franchise/Independent"},{"contract_id":"6","contract_title":"Fixed-term contract"}]
     * numberOfHours : [{"hours_id":"1","hours_title":"Full-time"},{"hours_id":"2","hours_title":"<10h / week"},{"hours_id":"3","hours_title":"10h / week"},{"hours_id":"4","hours_title":"<15h / week"},{"hours_id":"5","hours_title":"<20h / week"},{"hours_id":"6","hours_title":"<25h / week"},{"hours_id":"7","hours_title":"<30h / week"}]
     * salaries : [{"salary_id":"1","salary_title":"Open to discussion"},{"salary_id":"2","salary_title":"Minimum wage"},{"salary_id":"3","salary_title":"10 \u20ac/hour"},{"salary_id":"4","salary_title":"12 \u20ac/hour"},{"salary_id":"5","salary_title":"14 \u20ac/hour"},{"salary_id":"6","salary_title":"16 \u20ac/hour"},{"salary_id":"7","salary_title":"18 \u20ac/hour"},{"salary_id":"8","salary_title":"20 \u20ac/hour"},{"salary_id":"9","salary_title":"22 \u20ac/hour"},{"salary_id":"10","salary_title":"25 \u20ac/hour"},{"salary_id":"11","salary_title":"30 \u20ac/hour"},{"salary_id":"12","salary_title":"+30 \u20ac/hour"}]
     * msg : Data found
     */

    private boolean success;
    private boolean error;
    private String msg;
    private List<IndustryTypesBean> industryTypes;
    private List<ExperiencesBean> experiences;
    private List<EducationLevelsBean> educationLevels;
    private List<CandidateSeeksBean> candidateSeeks;
    private List<JobLanguagesBean> jobLanguages;
    private List<LanguageProficienciesBean> languageProficiencies;
    private List<CurrentStatusesBean> currentStatuses;
    private List<SkillsBean> skills;
    private List<MobilitiesBean> mobilities;
    private List<CompanyCategoriesBean> companyCategories;
    private List<NumberOfEmployeesBean> numberOfEmployees;
    private List<JobTitlesBean> jobTitles;
    private List<ContractTypesBean> contractTypes;
    private List<NumberOfHoursBean> numberOfHours;
    private List<SalariesBean> salaries;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<IndustryTypesBean> getIndustryTypes() {
        return industryTypes;
    }

    public void setIndustryTypes(List<IndustryTypesBean> industryTypes) {
        this.industryTypes = industryTypes;
    }

    public List<ExperiencesBean> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<ExperiencesBean> experiences) {
        this.experiences = experiences;
    }

    public List<EducationLevelsBean> getEducationLevels() {
        return educationLevels;
    }

    public void setEducationLevels(List<EducationLevelsBean> educationLevels) {
        this.educationLevels = educationLevels;
    }

    public List<CandidateSeeksBean> getCandidateSeeks() {
        return candidateSeeks;
    }

    public void setCandidateSeeks(List<CandidateSeeksBean> candidateSeeks) {
        this.candidateSeeks = candidateSeeks;
    }

    public List<JobLanguagesBean> getJobLanguages() {
        return jobLanguages;
    }

    public void setJobLanguages(List<JobLanguagesBean> jobLanguages) {
        this.jobLanguages = jobLanguages;
    }

    public List<LanguageProficienciesBean> getLanguageProficiencies() {
        return languageProficiencies;
    }

    public void setLanguageProficiencies(List<LanguageProficienciesBean> languageProficiencies) {
        this.languageProficiencies = languageProficiencies;
    }

    public List<CurrentStatusesBean> getCurrentStatuses() {
        return currentStatuses;
    }

    public void setCurrentStatuses(List<CurrentStatusesBean> currentStatuses) {
        this.currentStatuses = currentStatuses;
    }

    public List<SkillsBean> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsBean> skills) {
        this.skills = skills;
    }

    public List<MobilitiesBean> getMobilities() {
        return mobilities;
    }

    public void setMobilities(List<MobilitiesBean> mobilities) {
        this.mobilities = mobilities;
    }

    public List<CompanyCategoriesBean> getCompanyCategories() {
        return companyCategories;
    }

    public void setCompanyCategories(List<CompanyCategoriesBean> companyCategories) {
        this.companyCategories = companyCategories;
    }

    public List<NumberOfEmployeesBean> getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(List<NumberOfEmployeesBean> numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public List<JobTitlesBean> getJobTitles() {
        return jobTitles;
    }

    public void setJobTitles(List<JobTitlesBean> jobTitles) {
        this.jobTitles = jobTitles;
    }

    public List<ContractTypesBean> getContractTypes() {
        return contractTypes;
    }

    public void setContractTypes(List<ContractTypesBean> contractTypes) {
        this.contractTypes = contractTypes;
    }

    public List<NumberOfHoursBean> getNumberOfHours() {
        return numberOfHours;
    }

    public void setNumberOfHours(List<NumberOfHoursBean> numberOfHours) {
        this.numberOfHours = numberOfHours;
    }

    public List<SalariesBean> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<SalariesBean> salaries) {
        this.salaries = salaries;
    }

    public static class IndustryTypesBean {
        /**
         * industry_type_id : 1
         * industry_type_name : Catering
         * positions : [{"position_id":"4","position_name":"Butcher"},{"position_id":"5","position_name":"Baker"},{"position_id":"6","position_name":"Pork Butcher"},{"position_id":"7","position_name":"Event Planner"},{"position_id":"8","position_name":"Chef"},{"position_id":"9","position_name":"Chef in collective catering"},{"position_id":"10","position_name":"Manager in collective catering"},{"position_id":"11","position_name":"Manager in fast food"},{"position_id":"12","position_name":"Kitchen Chef"},{"position_id":"13","position_name":"Station Chef"},{"position_id":"14","position_name":"Production Manager in collective catering"},{"position_id":"15","position_name":"Chef Restaurant Manager / Owner"},{"position_id":"16","position_name":"Chocolatier / Candy maker"},{"position_id":"17","position_name":"Kitchen Clerk"},{"position_id":"18","position_name":"Crêpier"},{"position_id":"19","position_name":"Cook"},{"position_id":"20","position_name":"Cook in the public administration"},{"position_id":"21","position_name":"Dietitian"},{"position_id":"22","position_name":"Restaurant Dietitian"},{"position_id":"23","position_name":"Assistant Director in fast food"},{"position_id":"24","position_name":"Restaurant Director / Manager"},{"position_id":"25","position_name":"Restaurant General Manager"},{"position_id":"26","position_name":"Director / Manager in fast food"},{"position_id":"27","position_name":"Fish preparer"},{"position_id":"28","position_name":"Inventory Manager"},{"position_id":"29","position_name":"Food Service worker"},{"position_id":"30","position_name":"Food Service worker - Kitchen area"},{"position_id":"31","position_name":"Collective catering worker"},{"position_id":"32","position_name":"Restaurant Manager / Owner"},{"position_id":"33","position_name":"Cheese maker"},{"position_id":"34","position_name":"Manager in collective catering"},{"position_id":"35","position_name":"Manager in restauration"},{"position_id":"36","position_name":"Manager in fast food restaurant"},{"position_id":"37","position_name":"Pastry cook"},{"position_id":"38","position_name":"Pizzaiolo"},{"position_id":"39","position_name":"Dishwasher worker"},{"position_id":"40","position_name":"Fishmonger"},{"position_id":"41","position_name":"Preparator / Sales worker in fast food"},{"position_id":"42","position_name":"Revenue or Yield Manager"},{"position_id":"43","position_name":"Second cook"},{"position_id":"44","position_name":"Sous-chef"},{"position_id":"45","position_name":"Caterer"},{"position_id":"46","position_name":"Caterer / Reception Organizer"}]
         */

        private String industry_type_id;
        private String industry_type_name;
        private List<PositionsBean> positions;

        public String getIndustry_type_id() {
            return industry_type_id;
        }

        public void setIndustry_type_id(String industry_type_id) {
            this.industry_type_id = industry_type_id;
        }

        public String getIndustry_type_name() {
            return industry_type_name;
        }

        public void setIndustry_type_name(String industry_type_name) {
            this.industry_type_name = industry_type_name;
        }

        public List<PositionsBean> getPositions() {
            return positions;
        }

        public void setPositions(List<PositionsBean> positions) {
            this.positions = positions;
        }

        public static class PositionsBean {
            /**
             * position_id : 4
             * position_name : Butcher
             */

            private String position_id;
            private String position_name;

            public String getPosition_id() {
                return position_id;
            }

            public void setPosition_id(String position_id) {
                this.position_id = position_id;
            }

            public String getPosition_name() {
                return position_name;
            }

            public void setPosition_name(String position_name) {
                this.position_name = position_name;
            }
        }
    }

    public static class ExperiencesBean {
        /**
         * experience_id : 1
         * experience : No experience
         */

        private String experience_id;
        private String experience;

        public String getExperience_id() {
            return experience_id;
        }

        public void setExperience_id(String experience_id) {
            this.experience_id = experience_id;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }
    }

    public static class EducationLevelsBean {
        /**
         * education_id : 1
         * education_title : No diploma
         */

        private String education_id;
        private String education_title;

        public String getEducation_id() {
            return education_id;
        }

        public void setEducation_id(String education_id) {
            this.education_id = education_id;
        }

        public String getEducation_title() {
            return education_title;
        }

        public void setEducation_title(String education_title) {
            this.education_title = education_title;
        }
    }

    public static class CandidateSeeksBean {
        /**
         * candidate_seek_id : 1
         * candidate_seek_title : Apprenticeship
         */

        private String candidate_seek_id;
        private String candidate_seek_title;

        public String getCandidate_seek_id() {
            return candidate_seek_id;
        }

        public void setCandidate_seek_id(String candidate_seek_id) {
            this.candidate_seek_id = candidate_seek_id;
        }

        public String getCandidate_seek_title() {
            return candidate_seek_title;
        }

        public void setCandidate_seek_title(String candidate_seek_title) {
            this.candidate_seek_title = candidate_seek_title;
        }
    }

    public static class JobLanguagesBean {
        /**
         * job_language_id : 1
         * job_language_title : French
         */

        private String job_language_id;
        private String job_language_title;

        public String getJob_language_id() {
            return job_language_id;
        }

        public void setJob_language_id(String job_language_id) {
            this.job_language_id = job_language_id;
        }

        public String getJob_language_title() {
            return job_language_title;
        }

        public void setJob_language_title(String job_language_title) {
            this.job_language_title = job_language_title;
        }
    }

    public static class LanguageProficienciesBean {
        /**
         * language_proficiency_id : 1
         * language_proficiency_title : Beginner
         */

        private String language_proficiency_id;
        private String language_proficiency_title;

        public String getLanguage_proficiency_id() {
            return language_proficiency_id;
        }

        public void setLanguage_proficiency_id(String language_proficiency_id) {
            this.language_proficiency_id = language_proficiency_id;
        }

        public String getLanguage_proficiency_title() {
            return language_proficiency_title;
        }

        public void setLanguage_proficiency_title(String language_proficiency_title) {
            this.language_proficiency_title = language_proficiency_title;
        }
    }

    public static class CurrentStatusesBean {
        /**
         * seeker_current_status_id : 1
         * seeker_current_status_title : Student
         */

        private String seeker_current_status_id;
        private String seeker_current_status_title;

        public String getSeeker_current_status_id() {
            return seeker_current_status_id;
        }

        public void setSeeker_current_status_id(String seeker_current_status_id) {
            this.seeker_current_status_id = seeker_current_status_id;
        }

        public String getSeeker_current_status_title() {
            return seeker_current_status_title;
        }

        public void setSeeker_current_status_title(String seeker_current_status_title) {
            this.seeker_current_status_title = seeker_current_status_title;
        }
    }

    public static class SkillsBean {
        /**
         * skill_id : 1
         * skill_title : Catering
         */

        private String skill_id;
        private String skill_title;

        public String getSkill_id() {
            return skill_id;
        }

        public void setSkill_id(String skill_id) {
            this.skill_id = skill_id;
        }

        public String getSkill_title() {
            return skill_title;
        }

        public void setSkill_title(String skill_title) {
            this.skill_title = skill_title;
        }
    }

    public static class MobilitiesBean {
        /**
         * mobility_id : 1
         * mobility_title : Yes
         */

        private String mobility_id;
        private String mobility_title;

        public String getMobility_id() {
            return mobility_id;
        }

        public void setMobility_id(String mobility_id) {
            this.mobility_id = mobility_id;
        }

        public String getMobility_title() {
            return mobility_title;
        }

        public void setMobility_title(String mobility_title) {
            this.mobility_title = mobility_title;
        }
    }

    public static class CompanyCategoriesBean {
        /**
         * company_category_id : 1
         * company_category_title : Restaurant
         */

        private String company_category_id;
        private String company_category_title;

        public String getCompany_category_id() {
            return company_category_id;
        }

        public void setCompany_category_id(String company_category_id) {
            this.company_category_id = company_category_id;
        }

        public String getCompany_category_title() {
            return company_category_title;
        }

        public void setCompany_category_title(String company_category_title) {
            this.company_category_title = company_category_title;
        }
    }

    public static class NumberOfEmployeesBean {
        /**
         * number_of_employee_id : 1
         * number_of_employee_title : 1-10 employees
         */

        private String number_of_employee_id;
        private String number_of_employee_title;

        public String getNumber_of_employee_id() {
            return number_of_employee_id;
        }

        public void setNumber_of_employee_id(String number_of_employee_id) {
            this.number_of_employee_id = number_of_employee_id;
        }

        public String getNumber_of_employee_title() {
            return number_of_employee_title;
        }

        public void setNumber_of_employee_title(String number_of_employee_title) {
            this.number_of_employee_title = number_of_employee_title;
        }
    }

    public static class JobTitlesBean {
        /**
         * job_title_id : 1
         * job_title : Assistant General Manager
         */

        private String job_title_id;
        private String job_title;

        public String getJob_title_id() {
            return job_title_id;
        }

        public void setJob_title_id(String job_title_id) {
            this.job_title_id = job_title_id;
        }

        public String getJob_title() {
            return job_title;
        }

        public void setJob_title(String job_title) {
            this.job_title = job_title;
        }
    }

    public static class ContractTypesBean {
        /**
         * contract_id : 1
         * contract_title : Permanent contract
         */

        private String contract_id;
        private String contract_title;

        public String getContract_id() {
            return contract_id;
        }

        public void setContract_id(String contract_id) {
            this.contract_id = contract_id;
        }

        public String getContract_title() {
            return contract_title;
        }

        public void setContract_title(String contract_title) {
            this.contract_title = contract_title;
        }
    }

    public static class NumberOfHoursBean {
        /**
         * hours_id : 1
         * hours_title : Full-time
         */

        private String hours_id;
        private String hours_title;

        public String getHours_id() {
            return hours_id;
        }

        public void setHours_id(String hours_id) {
            this.hours_id = hours_id;
        }

        public String getHours_title() {
            return hours_title;
        }

        public void setHours_title(String hours_title) {
            this.hours_title = hours_title;
        }
    }

    public static class SalariesBean {
        /**
         * salary_id : 1
         * salary_title : Open to discussion
         */

        private String salary_id;
        private String salary_title;

        public String getSalary_id() {
            return salary_id;
        }

        public void setSalary_id(String salary_id) {
            this.salary_id = salary_id;
        }

        public String getSalary_title() {
            return salary_title;
        }

        public void setSalary_title(String salary_title) {
            this.salary_title = salary_title;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.success ? (byte) 1 : (byte) 0);
        dest.writeByte(this.error ? (byte) 1 : (byte) 0);
        dest.writeString(this.msg);
        dest.writeList(this.industryTypes);
        dest.writeList(this.experiences);
        dest.writeList(this.educationLevels);
        dest.writeList(this.candidateSeeks);
        dest.writeList(this.jobLanguages);
        dest.writeList(this.languageProficiencies);
        dest.writeList(this.currentStatuses);
        dest.writeList(this.skills);
        dest.writeList(this.mobilities);
        dest.writeList(this.companyCategories);
        dest.writeList(this.numberOfEmployees);
        dest.writeList(this.jobTitles);
        dest.writeList(this.contractTypes);
        dest.writeList(this.numberOfHours);
        dest.writeList(this.salaries);
    }

    public GetAllDropDownResponce() {
    }

    protected GetAllDropDownResponce(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.industryTypes = new ArrayList<IndustryTypesBean>();
        in.readList(this.industryTypes, IndustryTypesBean.class.getClassLoader());
        this.experiences = new ArrayList<ExperiencesBean>();
        in.readList(this.experiences, ExperiencesBean.class.getClassLoader());
        this.educationLevels = new ArrayList<EducationLevelsBean>();
        in.readList(this.educationLevels, EducationLevelsBean.class.getClassLoader());
        this.candidateSeeks = new ArrayList<CandidateSeeksBean>();
        in.readList(this.candidateSeeks, CandidateSeeksBean.class.getClassLoader());
        this.jobLanguages = new ArrayList<JobLanguagesBean>();
        in.readList(this.jobLanguages, JobLanguagesBean.class.getClassLoader());
        this.languageProficiencies = new ArrayList<LanguageProficienciesBean>();
        in.readList(this.languageProficiencies, LanguageProficienciesBean.class.getClassLoader());
        this.currentStatuses = new ArrayList<CurrentStatusesBean>();
        in.readList(this.currentStatuses, CurrentStatusesBean.class.getClassLoader());
        this.skills = new ArrayList<SkillsBean>();
        in.readList(this.skills, SkillsBean.class.getClassLoader());
        this.mobilities = new ArrayList<MobilitiesBean>();
        in.readList(this.mobilities, MobilitiesBean.class.getClassLoader());
        this.companyCategories = new ArrayList<CompanyCategoriesBean>();
        in.readList(this.companyCategories, CompanyCategoriesBean.class.getClassLoader());
        this.numberOfEmployees = new ArrayList<NumberOfEmployeesBean>();
        in.readList(this.numberOfEmployees, NumberOfEmployeesBean.class.getClassLoader());
        this.jobTitles = new ArrayList<JobTitlesBean>();
        in.readList(this.jobTitles, JobTitlesBean.class.getClassLoader());
        this.contractTypes = new ArrayList<ContractTypesBean>();
        in.readList(this.contractTypes, ContractTypesBean.class.getClassLoader());
        this.numberOfHours = new ArrayList<NumberOfHoursBean>();
        in.readList(this.numberOfHours, NumberOfHoursBean.class.getClassLoader());
        this.salaries = new ArrayList<SalariesBean>();
        in.readList(this.salaries, SalariesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetAllDropDownResponce> CREATOR = new Parcelable.Creator<GetAllDropDownResponce>() {
        @Override
        public GetAllDropDownResponce createFromParcel(Parcel source) {
            return new GetAllDropDownResponce(source);
        }

        @Override
        public GetAllDropDownResponce[] newArray(int size) {
            return new GetAllDropDownResponce[size];
        }
    };
}
