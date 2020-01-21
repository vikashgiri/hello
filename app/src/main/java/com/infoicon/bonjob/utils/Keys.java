package com.infoicon.bonjob.utils;

import android.os.Environment;

import com.infoicon.bonjob.retrofit.ServiceUrls;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by pramod  on 26/5/17.
 */

public class Keys {
    public static final int LOCATION_ENABLE_CODE = 5;
    public static final int FACEBOOK_REQUEST_CODE = 64206;
    public static final int FACEBOOK_CODE = 10;
    public static final int LOCATION_CODE = 102;
    public static final int RESULT_LOAD_IMG = 1;
    public static final int RESULT_LOAD_VID = 2;
    public static final int VERIFY_PHONE = 11;

    public static final int POST_CONFIRM_CODE = 12;
    public static final int CANDIDATE_SELECT_CODE = 13;
    public static final int CANDIDATE_HIRED_CODE = 14;
    public static final int SALARY_REQUEST_CODE = 15;
    public static final int COMPANY_CATEGORY_REQUEST_CODE = 16;
    public static final int DIPLOMA_REQUEST_CODE = 16;
    public static final int COMPETENCES_REQUEST_CODE = 17;
    public static final int EXPERIENCE_REQUEST_CODE = 18;
    public static final int CURRENT_STATUS_REQUEST_CODE = 19;
    public static final int MOBILITY_REQUEST_CODE = 20;
    public static final int LANGUAGE_REQUEST_CODE = 21;
    public static final int CONTRACT_TYPE_REQUEST_CODE = 22;
    public static final int NUM_HOUR_REQUEST_CODE = 23;
    public static final int FULL_PART_TIME_REQUEST_CODE = 24;
    public static final int VERIFY_PHONE2 = 25;
    public static final int REQUEST_CODE_EMAIL = 26;
    public static final int REQUEST_CODE_SEND_EMAIL = 27;
    public static final int REQUEST_CODE_SUBSCRIBE_PLAN = 28;
    public static final int REQUEST_CODE_SUBSCRIPTION_FORM = 29;
    public static final String LOCATION_NAME = "location_name";
    public static final String LOCATION_CITY = "location_city";
    public static final String LOCATION_LAT = "location_lat";
    public static final String CURRENT_LAT = "current_lat";
    public static final String LOCATION_LONG = "location_long";
    public static final String distance_radius = "distance_radius";
    public static final String CURRENT_LONG = "current_long";
    public static final String job_postal_code = "job_postal_code";
    public static final String job_country = "job_country";

    public static final long DURATION = 500;
    public static final String MAIL_ID = "contact@bonjob.co";
    //public static final String MAIL_ID = "pramod@infoicon.co.in";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String FB_ID = "fb_id";
    public static final String FULLNAME = "full_name";
    public static final String CLICKED_EVENT = "clicked_event";
    public static final String CLICKED_EVENT_SIGNUP = "clicked_event_signup";
    public static final String CLICKED_EVENT_LOGIN = "clicked_event_login";
    public static final String LOOK_FOR = "look_for";
    public static final String PROFILE = "profile";
    public static final String MY_OFFERS = "my_offers";
    public static final String MY_OFFERS_DEFAULT = "my_offers_default";
    public static final String CHAT = "chat";
    public static final String ONLINE_STATUS = "online_status";
    public static final String UNREAD_STATUS = "unread_status";
    public static final String UNREAD_STATUS_HOME = "unread_status_home";
    public static final String HIRE = "hire";
    public static final String FILTER_SEARCH = "filter_search";
    public static final String LANGUAGE = "language";
    public static final String LANGUAGEID = "language_id";
    public static final String DEFAULT_CHAT = "language_id";
    public static final String CHAT_LIST = "chat_LIST";
    public static final String ACTIVITY = "activity";
    public static final String EDIT_PROFILE = "edit_profile";
    public static final String VIEW_ONLINE_CANDIDATE = "view_online_candidate";
    public static final String VIEW_FRAGMENT = "view_fragment";
    public static final String SETTINGS = "settings";
    public static final String EXPERIENCE = "experience";
    public static final String EXPERIENCEID= "experience_id";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String ENTERPRISE_NAME = "enterprise_name";
    public static final String DROP_DOWN = "drop_down";
    public static final String ADDRESS = "address";
    public static final String USERNAME = "username";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String DEVICE_ID = "device_id";
    public static final String USER_TYPE = "user_type";
    public static final String GENDER = "gender";
    public static final String SEEKER = "seeker";
    public static final String EMPLOYER = "employer";
    public static final String SEARCH_JOB = "search_job";
    public static final String POST_JOB = "post_job";
    public static final String POSITION = "position";

    public static final String TYPE = "type";


    public static final String LATITUDE = "lattitude";
    public static final String LATTITUDE = "latitude";
    public static final String LONGITUDE = "longitude";


    public static final String MORE_FILTER = "more_filter";
    public static final String FAQ = "faq";
    public static final String JOB_DETAILS = "job_details";
    public static final String COMPANY_DETAILS = "company_details";
    public static final String COMPANY_JOBS = "company_jobs";

    public static final String CANDIDATE_SEEK_NAME = "candidate_seek_name";

    public static final String CANDIDATE_SEEK_ID = "candidate_seek_id";

    public static final String EDUCATION_LEVEL = "education_level";
    public static final String FROM = "from";
    public static final String FROM_FIND_JOB = "from_findJob";
    public static final String FROM_RECRUITER = "from_recruiter";
    public static final String FROM_CHAT = "from_chat";
    public static final String FROM_SEARCH_JOB = "from_search_job";
    public static final String FROM_SEARCH_CANDIDATE = "from_search_candidate";
    public static final String FROM_My_OFFER = "from_my_offer";
    public static final String DOB = "dob";
    public static final String CITY = "city";
    public static final String MOBILE = "mobile";
    public static final String job_city = "job_city";

    public static final String MOBILITY = "mobility";
    public static final String MOBILITYId = "mobilityId";
    public static final String ABOUT = "about";
    public static final String ACTIVE_USER = "active_user";
    public static final String TRAINING = "training";
    public static final String CURRENT_STATUS = "current_status";
    public static final String CURRENT_STATUS_ID = "current_status_id";
    public static final String ENTERPRISE_PIC = "enterprise_pic";
    public static final String USER_PIC = "user_pic";
    public static final String PATCH_VIDEO = "patch_video";
    public static final String PATCH_VIDEO_THUMBNAIL = "patch_video_thumbnail";
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    public static final String MESSAGE = "msg";
    public static final String DATA = "data";
    public static final String NOTIFICATION_JSON = "notification_json";
    public static final String NOTIFICATION = "notification";
    public static final String FROM_GALLERY_ADAPTER = "from_gallery_adapter";
    public static final String SEEKER_LANG_NAME = "seeker_lang_name";
    public static final String LANG_PROFICIENCY = "lang_proficiency";
    public static final String GALLERY_ID = "gallery_id";
    public static final String DESCRIPTION = "description";
    public static final String GALLERY = "gallery";
    public static final String IMAGE = "image";
    public static final String CREATED_ON = "createdOn";
    public static final String JOB_OFFERED = "job_offered";
    public static final String SKILL = "skills";
    public static final String INDUSTRY_TYPE = "industry_type";
    public static final String FOR = "for";
    public static final String SHOW_CHAT_HIRE_OPTION = "show_chat_hire_option";
    public static final String SELECTED_POSITION = "selected_position";
    public static final String TAG_SALARY = "TAG_salary";
    public static final String TAG_COMPETENCES = "TAG_competences";
    public static final String TAG_DIPLOMA = "TAG_diploma";
    public static final String TAG_COMPANY_CATEGORY = "TAG_company_category";
    public static final String TAG_EXPERIENCE = "TAG_experience";
    public static final String TAG_SUBSCRIBE = "TAG_subscribe";
    public static final String TAG_SUBSCRIPTION_FORM = "TAG_subscribtion_form";
    public static final String TAG_CURRENT_STATUS = "TAG_current_status";
    public static final String TAG_MOBILITY = "TAG_mobility";
    public static final String TAG_LANGUAGE = "TAG_language";
    public static final String TAG_TYPE_OF_CONTRACT = "TAG_type_of_contract";
    public static final String TAG_NUM_HOUR = "TAG_num_hour";
    public static final String TAG_FULL_PART_TIME = "TAG_full_part_time";
    public static final String SALARY = "salary";
    public static final String SALARY_ID = "salary_id";
    public static final String COMPETENCES = "competences";
    public static final String COMPETENCES_ID = "competences_id";
    public static final String DIPLOMA = "diploma";
    public static final String DIPLOMA_ID = "diploma_id";
    public static final String COMPANY_CAEGORY = "company_category";
    public static final String COMPANY_CAEGORY_Id = "company_category_id";
    public static final String TYPE_OF_CONTRACT = "type_of_contract";
    public static final String TYPE_OF_CONTRACT_ID = "type_of_contract_id";
    public static final String NUM_HOUR = "num_hour";
    public static final String NUM_HOUR_ID = "num_hour_id";
    public static final String JOB_TITLE = "job_title";
    public static final String employer_name = "employer_name";
    public static final String JOB_TITLE_ID = "job_title_id";
    public static final String START = "start";
    public static final String SEARCH_KEY = "search_key";
    public static final String JOB_DESC = "job_description";
    public static final String JOB_IMAGE = "job_image";
    public static final String JOB_LOCATION = "job_location";
    public static final String CONTRACT_TYPE = "contract_type";
    public static final String JOB_DURATION = "duration";
    public static final String NUM_HOURS = "num_of_hours";
    public static final String fetchedJobIds = "fetchedJobIds";
    public static final String SALALRY = "salarie";
    public static final String START_DATE = "start_date";
    public static final String CONTACT_MODE = "contact_mode";
    public static final String VALUE = "value";
    public static final String WEBSITE = "website";
    public static final String COUNTRY_NAME = "country_name";
    public static final String COUNTRY_CODE = "country_code";
    public static final String POST_LANGUAGE = "lang";
    public static final String FULL_PART_TIME = "full_part_time";
    public static final String COMPANY_LAT = "company_lat";
    public static final String COMPANY_LONG = "company_long";
    public static final String TERMS_OF_USE = "terms_of_use";
    public static final String PRIVACY_POLICY = "privacy_policy";
    public static final String FROM_APPLIED_CANDIDATE = "from_applied_candidate";
    public static final String FROM_HIRED_CANDIDATE = "from_hired_candidate";
    public static final String IS_MINE = "is_mine";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String CONTENT = "content";
    public static final String SENDER = "sender";
    public static final String RECEIVER = "receiver";
    public static final String MSG_ID = "msgid";
    public static final String CHAT_ID = "chat_id";
    public static final String SELECTED_CANDIDATE = "selected_candidate";
    public static final String APPLIED_CANDIDATE = "applied_candidate";
    public static final String EMAIL_NOTIFICATION = "email_notification";
    public static final String MOBILE_NOTIFICATION = "mobile_notification";
    public static final String COMPANY_ADDRESS = "company_address";
    public static final String APPLY_CANDIDATE_NOTIFY_TO_RECRUITER = "apply_candidate_notify_to_recruiter";
    public static final String user_pic = "user_pic";

    public static final int REQ_CODE_APPLY = 2;
    public static final String RECEIVER_ID = "receiver_id";
    public static final String RECONNECTION = "reconnection";
    public static final String ADD_TABLE = "add_table";


    ///preferencs
    public static final String MY_PREF = "my_pref";
    public static final String PREF_MANAGE_LOGIN_POPUP = "manage_login_popup";
    public static final String PITCH_VIDEO_PREF = "pitch_vid_pref";

    public static final String FROM_CANDIDATE = "from_candidate";

    public static final String USER_ID = "user_id";
    public static final String JOB_ID = "job_id";
    public static final String AUTH_KEY = "authKey";
    public static final String LANGUAGE_ID = "language_id";
    public static final String VERSION_ID = "version_id";
    public static final String EXPERIENCE_ID = "experience_id";
    public static final String IS_LOGIN = "isLogin";
    public static final String IS_LOGIN_RECRUITER = "isLoginRecruiter";
    public static final String EMPLOYER_ID = "employer_id";
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String LANG_ID = "lang_id";
    public static final String CANDIDATE_ID = "candidate_id";
    public static final String APPLIED_ID = "aplied_id";
    public static final String NOTIFICATION_TYPE = "type";
    public static final String OTP = "otp";
    public static final String USER = "user";
    public static final String ROSTER = "roster";
    public static final String UNAVAILABLE = "unavailable";
    public static final String AVAILABLE = "available";
    public static final String BONJOB_ = "bonjob_";
    public static final String ACTIVITY_COUNT = "activity_count";
    public static final String CHAT_COUNT = "CHAT_count";
    public static final String FIRST_TIME = "first_time";
    public static final String FIRST_TIME_SEEKER = "first_time_seeker";
    public static final String FIRST_TIME_RECRUITER = "first_time_recruiter";
    public static final String LOGIN_USER_ID = "login_user_id";
    public static final String FIELD_NAME = "field_name";
    public static final String IS_PITCH_VIDEO_VIEW = "is_pitch_video_view";
    public static final String SELECTED_ID = "selected_id";
    public static final String ACTIVITY_ID = "activity_id";
    public static final String REPORT_ID = "report_id";
    public static final String MY_OFFER_COUNT = "my_offer_count";
    public static final String SEARCH_CANDIDATE_COUNT = "search_candidate_count";
    public static final String PAGE = "page";
    public static final String SEARCH_NAME = "search_name";
    public static final String appType = "appType";


    public static final int TAKE_PHOTO_CODE = 132;
    public static final int TAKE_VIDEO_CODE = 133;
    public static final int TAB_CADIDATE = 0;
    public static final int TAB_SELECTED_CADIDATE = 1;
    public static final int TABE_HIRED = 2;
    public static final int TABE_OFFERS = 3;
    public static final String AUTH_CODE = "0";

    public static final String PLAN = "plan";
    public static final String PLAN_MONTH = "month";
    public static final String PLAN_YEAR = "year";
    public static final String SUBSCRIPTION = "subscription";
    public static final String URL = "url";


    private static final String APP_DIR = "ImageCompressor/";
    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + APP_DIR;
    public static final String PREF_FCM_TOKEN = "pref_fcm";
    public static final String FCM_TOKEN = "fcm_token";

    public static int count = 1;
    public static SimpleDateFormat SDF = new SimpleDateFormat("ddMMyyyy-hhmmss", Locale.US);


    public static final String TERM_OF_USE_URL = ServiceUrls.TERM_OF_USE_URL;
    public static final String PRIVACY_POLICY_URL = ServiceUrls.PRIVACY_POLICY_URL;

    public static final String IS_LOGIN_ADMIN = "isLoginAdmin";

}

