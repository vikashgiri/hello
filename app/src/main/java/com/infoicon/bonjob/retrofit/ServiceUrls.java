package com.infoicon.bonjob.retrofit;

/**
 * Created by pramod  on 26/5/17.
 */

public class ServiceUrls
{
     public static final String BASE_URL = "https://bonjob.co/";

 //public static final String BASE_URL ="http://172.104.8.51/bonjob/";
   //public static final String LOGIN_SEEKER = "services_v4_v4/login";
    public static final String LOGIN_SEEKER = "services_v4/login";
    public static final String ADVERTISEMENT = "services_v4/appAdvertisements";
    public static final String REGISTRATION_SEEKER = "services_v4/registration/";
    public static final String FORGOT_PASSWORD = "services_v4/empForgotPassword";
    public static final String SEEKER_FORGOT_PASSWORD = "services_v4/forgotPassword";
    public static final String SEEKER_PROFILE = "services_v4/myProfile";
    public static final String EDIT_EXPERIENCE = BASE_URL + "services_v4/editExperience";
    public static final String DELETE_EXPERIENCE = "services_v4/deleteExperience";
    public static final String REGISTRATION_API_SEEKER = BASE_URL + "services_v4/registration/";
    public static final String EDIT_PROFILE = BASE_URL + "services_v4/editProfile";
    public static final String UPDATE_GALLERY = BASE_URL + "services_v4/updateGallery";
    public static final String DELETE_GALLERY = "services_v4/deleteGalleryImage";
    public static final String UPDATE_GALLERY_DESCRIPTION = "services_v4/updateDescriptionOnImage";
    public static final String REGISTRATION_RECRUITER = "services_v4/empRegistration";
    public static final String REGISTRATION_API_RECRUITER = BASE_URL + "services_v4/empRegistration";
    public static final String LOGIN_RECRUITER = "services_v4/empLogin";
    public static final String SOCIAL_LOGIN = "services_v4/facebookLogin";
    public static final String ADD_SOCIAL_LOGIN = "services_v4/addFacebookUser";
    public static final String POST_JOB = BASE_URL + "services_v4/jobPost";
    public static final String UPDATE_JOB = BASE_URL + "services_v4/updateJob";
    public static final String UPDATE_JOB_image = BASE_URL + "services_v4/uploadJobImage";
    public static final String RECRUITER_PROFILE = "services_v4/myEmpProfile";
    public static final String uploadUserPic =BASE_URL + "services_v4/uploadUserPic";
    public static final String uploadEnterprisePic =BASE_URL + "services_v4/uploadEnterprisePic";
    public static final String uploadPatchVideo =BASE_URL + "services_v4/uploadPatchVideo";
    public static final String RECRUITER_EDIT_PROFILE = BASE_URL + "services_v4/editEmpProfile";
    public static final String INPUT_PHONE_NUMBER = "services_v4/sendOtp";
    public static final String INPUT_CODE_NUMBER = "services_v4/verifyOtp";
    public static final String MY_OFFERS_RECRUITER = "services_v4/myPostedJob";
    public static final String CLOSE_OFFER = "services_v4/changeJobToClosed";
    public static final String RENEW_OFFER = "services_v4/changeJobToRenew";
    public static final String HIRED_CANDIDATE = "services_v4/hiredCandidateList";
    public static final String CANDIDATE_LIST = "services_v4/appliedCandidateList";
    public static final String SELECTED_CANDIDATE = "services_v4/selectedCandidateList";
    public static final String CANDIDATE_PROFILE = "";
    public static final String SELECT_CANDIDATE = "services_v4/selectCandidate";
    public static final String NON_SELECT_CANDIDATE = "services_v4/notSelectCandidate";
    public static final String HIRE_CANDIDATE = "services_v4/hireCandidate";
    public static final String MY_OFFER_SEEKER = "services_v4/myOffers";
    public static final String getAllDropdowns = "services_v4/getAllDropdowns";
    //public static final String SEEKER_ACTIVITY ="services_v4/activity";
    public static final String SEEKER_ACTIVITY = "services_v4/userActivities";
    public static final String SEARCH_JOB = "services_v4/jobSearch";
    public static final String DEFAULT_JOB = "services_v4/defaultJobList";
    public static final String COMPANY_DETAILS = "services_v4/employerDetails";
    public static final String COMPANY_ALL_JOBS= "services_v4/getAllJobs";
    public static final String JOB_DETAILS = "services_v4/jobDetails";
    public static final String APPLY_JOB = "services_v4/applyJob";
    public static final String TERMS_OF_USE = "services_v4/terms_of_use";
    public static final String PRIVACY_POLICY = "services_v4/privacy_policy";
    public static final String FAQ = "services_v4/faq";
    public static final String CANDIDATE_SEARCH = "services_v4/candidateSearch";
    public static final String DEFAULT_CANDIDATE = "services_v4/defaultCandidateList";
    public static final String NOTIFICATION_EMAIL_ENABLE_DISABLE = "services_v4/enableDisableNotification";
    public static final String CHAT_LIST = "services_v4/getContactList";
    public static final String ADD_TO_CHAT = "services_v4/getFirstMessage";
    public static final String REMOVE_PHOTO_VIDEO = "services_v4/deleteUserImages";
    public static final String LOGOUT = "services_v4/logout";
    public static final String SELECT_CANDIDATE_BY_SEARCH = "services_v4/selectSeekerByEmployer";
    public static final String ACCEPT_JOB = "services_v4/acceptRequestForJob";
    public static final String REJECT_JOB = "services_v4/rejectRequestForJob";
    public static final String GENERATE_REPORT = "services_v4/generateReport";
    public static final String SUBSCRIPTION_LIST = "services_v4/subscriptionList";
    public static final String MAKE_PAYMENT = "services_v4/makePayment";
    public static final String MAKE_PAYMENT_makePayment = "services_v4/stripePayment";
    public static final String TERM_OF_USE_URL = BASE_URL + "terms";
    public static final String PRIVACY_POLICY_URL = BASE_URL + "privacy";
    public static final String CHAT_HISTORY = BASE_URL + "services_v4/getHistoryMessages";
    public static final String SEARCH_CHAT_USER = BASE_URL + "services_v4/getContactList";
    //public static final String CHAT_HISTORY ="http://172.104.8.51/chatServer/api/messages";

   public static final String JOB_SEARCH_DROP_DOWN = "services_v4/jobSearchDropdowns";
   public static final String JOB_POSITION = "services_v4/getPositions";
   public static final String JOB_EDUCTION= "services_v4/getEducations";
   public static final String JOB_LANGUAGE= "services_v4/getJobLanguages";
   public static final String JOB_SOUGHT= "services_v4/getCandidateSeeks";

   public static final String JOB_SEARCH_CANDIDATE_DROP_DOWN = "services_v4/getCandidateSearchDropdowns";
   public static final String JOB_SEARCH_EDUCTION= "services_v4/getEducations";
   public static final String JOB_TITLE_DROP_DOWN = "services_v4/getJobTitles";
   public static final String JOB_TITLE_GetJob_post= "services_v4/jobPostDropdowns";
   public static final String GET_EMP_PROFILE= "services_v4/getEmpProfileDropdowns";
   public static final String getTwilioToken= "services_v4/getTwilioToken";
   public static final String getTwilioDisconnect= "services_v4/disconnectCall";
   public static final String getTwiliorejectCall= "services_v4/rejectCall";

}
