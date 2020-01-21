package com.infoicon.bonjob.retrofit;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.infoicon.bonjob.chat.DisconnectCallResponce;
import com.infoicon.bonjob.chat.Responce;
import com.infoicon.bonjob.chat.TulipResponce;
import com.infoicon.bonjob.dialogs.EductionLabelResponce;
import com.infoicon.bonjob.recruiters.post.GetJobTitleResponce;
import com.infoicon.bonjob.recruiters.post.GetJobpostDropDown;
import com.infoicon.bonjob.recruiters.search.GetTargetSearchResponce;
import com.infoicon.bonjob.retrofit.response.GetAcceptJobResponse;
import com.infoicon.bonjob.retrofit.response.GetAddToChatListResponse;
import com.infoicon.bonjob.retrofit.response.GetAdvertisementResponce;
import com.infoicon.bonjob.retrofit.response.GetApplyJobResponse;
import com.infoicon.bonjob.retrofit.response.GetCandidateListResponse;
import com.infoicon.bonjob.retrofit.response.GetCandidateProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetCandidateSearchResponse;
import com.infoicon.bonjob.retrofit.response.GetChatListResponse;
import com.infoicon.bonjob.retrofit.response.GetCloseOffersResponse;
import com.infoicon.bonjob.retrofit.response.GetCompanyDetailsResponse;
import com.infoicon.bonjob.retrofit.response.GetDeleteExperience;
import com.infoicon.bonjob.retrofit.response.GetDeleteGalleryResponse;
import com.infoicon.bonjob.retrofit.response.GetEditExperienceResponse;
import com.infoicon.bonjob.retrofit.response.GetFaqResponse;
import com.infoicon.bonjob.retrofit.response.GetForgotPasswordResponse;
import com.infoicon.bonjob.retrofit.response.GetGenerateReportResponse;
import com.infoicon.bonjob.retrofit.response.GetHireResponse;
import com.infoicon.bonjob.retrofit.response.GetHiredCandidateResponse;
import com.infoicon.bonjob.retrofit.response.GetInputCodeNumberResponse;
import com.infoicon.bonjob.retrofit.response.GetInputPhoneNumberResponse;
import com.infoicon.bonjob.retrofit.response.GetJobDetailsResponse;
import com.infoicon.bonjob.retrofit.response.GetLoginRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetLoginSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetLogoutResponse;
import com.infoicon.bonjob.retrofit.response.GetMakePaymentResponse;
import com.infoicon.bonjob.retrofit.response.GetMyOfferSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetMyOffersRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetNonSelectCandidateResponse;
import com.infoicon.bonjob.retrofit.response.GetNotiEmailResponse;
import com.infoicon.bonjob.retrofit.response.GetPhotoVideoRemoveResponse;
import com.infoicon.bonjob.retrofit.response.GetPrivacyPolicyResponse;
import com.infoicon.bonjob.retrofit.response.GetRecruiterProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetRenewOfferResponse;
import com.infoicon.bonjob.retrofit.response.GetSearchJobResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerActivityResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerProfileResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationRecruiterResponse;
import com.infoicon.bonjob.retrofit.response.GetRegistrationSeekerResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectCandidateBySearchResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectCandidateResponse;
import com.infoicon.bonjob.retrofit.response.GetSelectedCandidateResponse;
import com.infoicon.bonjob.retrofit.response.GetSocialLoginResponse;
import com.infoicon.bonjob.retrofit.response.GetSubscriptionListResponse;
import com.infoicon.bonjob.retrofit.response.GetTermsOfUseResponse;
import com.infoicon.bonjob.retrofit.response.GetUpdateDiscriptionResponse;
import com.infoicon.bonjob.seeker.profile.GetEducationDataResponce;
import com.infoicon.bonjob.seeker.profile.GetEmpProfileDropdownsResponce;
import com.infoicon.bonjob.seeker.profile.GetJobSoughtFragmentResponce;
import com.infoicon.bonjob.seeker.profile.GetLanguageResponce;
import com.infoicon.bonjob.seeker.profile.GetPositionDataResponce;
import com.infoicon.bonjob.seeker.searchJob.CompanyAllJobFragmentsResponce;
import com.infoicon.bonjob.seeker.searchJob.JobSearchDropDownResponce;
import com.infoicon.bonjob.splash.GetAllDropDownResponce;

import org.json.JSONObject;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;


public interface ApiService {

    @POST(ServiceUrls.LOGIN_SEEKER)
    Call<GetLoginSeekerResponse> getLoginSeekerResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.REGISTRATION_SEEKER)
    Call<GetRegistrationSeekerResponse> getRegistrationSeekerResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.FORGOT_PASSWORD)
    Call<GetForgotPasswordResponse> getForgotPasswordResponse(@Body JsonObject jsonObject);


    @POST(ServiceUrls.SEEKER_FORGOT_PASSWORD)
    Call<GetForgotPasswordResponse> getEmpForgotPasswordResponse(@Body JsonObject jsonObject);

   @POST(ServiceUrls.getTwilioToken)
    Call<TulipResponce> getTulipResponse(@Body JsonObject jsonObject);
   @POST(ServiceUrls.getTwilioDisconnect)
    Call<DisconnectCallResponce> getTulipDisconnectResponse(@Body JsonObject jsonObject);
 @POST(ServiceUrls.getTwiliorejectCall)
 Call<DisconnectCallResponce> getTulipDeclineResponse(@Body JsonObject jsonObject);


    @POST(ServiceUrls.SEEKER_PROFILE)
    Call<GetSeekerProfileResponse> getMyProfileResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.EDIT_EXPERIENCE)
    Call<GetEditExperienceResponse> getEditExperienceResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.DELETE_EXPERIENCE)
    Call<GetDeleteExperience> getDeleteExperienceResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.DELETE_GALLERY)
    Call<GetDeleteGalleryResponse> getDeleteGalleryResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.UPDATE_GALLERY_DESCRIPTION)
    Call<GetUpdateDiscriptionResponse> getUpdateDescriptionResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.REGISTRATION_RECRUITER)
    Call<GetRegistrationRecruiterResponse> getRegistrationRecruiterResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.LOGIN_RECRUITER)
    Call<GetLoginRecruiterResponse> getLoginRecruiterResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SOCIAL_LOGIN)
    Call<GetSocialLoginResponse> getSocialLoginResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.ADD_SOCIAL_LOGIN)
    Call<GetSocialLoginResponse> getAddSocialLoginResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.RECRUITER_PROFILE)
    Call<GetRecruiterProfileResponse> getRecruirerProfileResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.INPUT_PHONE_NUMBER)
    Call<GetInputPhoneNumberResponse> getInputPhoneNumberResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.INPUT_CODE_NUMBER)
    Call<GetInputCodeNumberResponse> getInputCodeNumberResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.MY_OFFERS_RECRUITER)
    Call<GetMyOffersRecruiterResponse> getMyOffersResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CLOSE_OFFER)
    Call<GetCloseOffersResponse> getCloseOfferResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.HIRED_CANDIDATE)
    Call<GetHiredCandidateResponse> getHiredCandidateResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CANDIDATE_LIST)
    Call<GetCandidateListResponse> getCandidateListResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SELECTED_CANDIDATE)
    Call<GetSelectedCandidateResponse> getSelectedCandidateResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.RENEW_OFFER)
    Call<GetRenewOfferResponse> getRenewOfferResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CANDIDATE_PROFILE)
    Call<GetCandidateProfileResponse> getCandidateProfileResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SELECT_CANDIDATE)
    Call<GetSelectCandidateResponse> getSelectCandidateResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.HIRE_CANDIDATE)
    Call<GetHireResponse> getHireResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SELECT_CANDIDATE_BY_SEARCH)
    Call<GetSelectCandidateBySearchResponse> getSelectCandidateBySearchResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.MY_OFFER_SEEKER)
    Call<GetMyOfferSeekerResponse> getMyOfferSeekerResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.getAllDropdowns)
    Call<GetAllDropDownResponce> getAllDropdowns(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SEEKER_ACTIVITY)
    Call<GetSeekerActivityResponse> getSeekerActivityResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.ACCEPT_JOB)
    Call<GetAcceptJobResponse> getAcceptJobResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.REJECT_JOB)
    Call<GetAcceptJobResponse> getRejectJobResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SEARCH_JOB)
    Call<GetSearchJobResponse> getSearchJobResponse(@Body JsonObject jsonObject);


    @POST(ServiceUrls.ADVERTISEMENT)
    Call<GetAdvertisementResponce> getAdvertisementResponse();


    @POST(ServiceUrls.DEFAULT_JOB)
    Call<GetSearchJobResponse> getDefaultJobResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.COMPANY_DETAILS)
    Call<GetCompanyDetailsResponse> getCompanyDetailsResponse(@Body JsonObject jsonObject);
    @POST(ServiceUrls.COMPANY_ALL_JOBS)
    Call<CompanyAllJobFragmentsResponce> getCompanyJobResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.JOB_DETAILS)
    Call<GetJobDetailsResponse> getJobDetailsResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.APPLY_JOB)
    Call<GetApplyJobResponse> getApplyJobResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.TERMS_OF_USE)
    Call<GetTermsOfUseResponse> getTermsOfUseResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.PRIVACY_POLICY)
    Call<GetPrivacyPolicyResponse> getPrivacyPolicyResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.NON_SELECT_CANDIDATE)
    Call<GetNonSelectCandidateResponse> getNonSelectResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.FAQ)
    Call<GetFaqResponse> getFaqResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CANDIDATE_SEARCH)
    Call<GetCandidateSearchResponse> getCandidateSearchResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.DEFAULT_CANDIDATE)
    Call<GetCandidateSearchResponse> getDefaultCandidateResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.NOTIFICATION_EMAIL_ENABLE_DISABLE)
    Call<GetNotiEmailResponse> getNotificationResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CHAT_LIST)
    Call<GetChatListResponse> getChatListResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.ADD_TO_CHAT)
    Call<GetAddToChatListResponse> getAddToChatResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.REMOVE_PHOTO_VIDEO)
    Call<GetPhotoVideoRemoveResponse> getRemovePhotoVideoResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.LOGOUT)
    Call<GetLogoutResponse> getLogoutResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.GENERATE_REPORT)
    Call<GetGenerateReportResponse> getGenerateReportResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.SUBSCRIPTION_LIST)
    Call<GetSubscriptionListResponse> getSubscriptionListResponse(@Body JsonObject jsonObject);

    @POST(ServiceUrls.MAKE_PAYMENT)
    Call<GetMakePaymentResponse> getMakePaymentResponse(@Body JsonObject jsonObject);


   @POST(ServiceUrls.MAKE_PAYMENT_makePayment)
   Call<Responce> MakePaymentResponse(@Body JsonObject jsonObject);


   @POST(ServiceUrls.SEARCH_CHAT_USER)
    Call<GetChatListResponse> getSearchChatUserResponse(@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_SEARCH_DROP_DOWN)
    Call<JobSearchDropDownResponce> getJobSearchDropDownResponce(@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_SEARCH_CANDIDATE_DROP_DOWN)
    Call<GetTargetSearchResponce> getGetTargetSearchResponce
            (@Body JsonObject jsonObject);



    @POST(ServiceUrls.JOB_SEARCH_EDUCTION)
    Call<EductionLabelResponce> getGetEductionResponce
            (@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_TITLE_GetJob_post)
    Call<GetJobpostDropDown> getGetJobpostDropDown
            (@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_TITLE_DROP_DOWN)
    Call<GetJobTitleResponce> getGetJobTitleResponce
            (@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_POSITION)
    Call<GetPositionDataResponce> getGetPositionDataResponce(@Body JsonObject jsonObject);



    @POST(ServiceUrls.JOB_EDUCTION)
    Call<GetEducationDataResponce> getGetEducationDataResponce(@Body JsonObject jsonObject);


    @POST(ServiceUrls.JOB_LANGUAGE)
    Call<GetLanguageResponce> getGetGetLanguageResponce(@Body JsonObject jsonObject);

    @POST(ServiceUrls.JOB_SOUGHT)
    Call<GetJobSoughtFragmentResponce> getGetJobSoughtFragmentResponce(@Body JsonObject jsonObject);


    @POST(ServiceUrls.GET_EMP_PROFILE)
    Call<GetEmpProfileDropdownsResponce> getGetEmpProfileDropdownsResponce(@Body JsonObject jsonObject);


}
