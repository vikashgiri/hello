package com.infoicon.bonjob.seeker.profile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by info on 30/8/18.
 */

public class GetEmpProfileDropdownsResponce implements Parcelable {

    /**
     * success : true
     * error : false
     * companyCategories : [{"company_category_content_id":"1","company_category_id":"1","company_category_title":"Restaurant","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:32:24"},{"company_category_content_id":"3","company_category_id":"2","company_category_title":"Hotel","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:32:33"},{"company_category_content_id":"5","company_category_id":"3","company_category_title":"Hotel / Restaurant","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:32:48"},{"company_category_content_id":"7","company_category_id":"4","company_category_title":"Cafe, Bar, Brasserie","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:33:02"},{"company_category_content_id":"9","company_category_id":"5","company_category_title":"Nightclub, Casino","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:33:31"},{"company_category_content_id":"11","company_category_id":"6","company_category_title":"Thalassotherapy","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:33:43"},{"company_category_content_id":"13","company_category_id":"7","company_category_title":"Hotels - Accommodation","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:34:17"},{"company_category_content_id":"15","company_category_id":"8","company_category_title":"Caterer","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-16 05:34:31"}]
     * numberOfEmployees : [{"number_of_employee_content_id":"1","number_of_employee_id":"1","number_of_employee_title":"1-10 employees","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-13 06:45:32"},{"number_of_employee_content_id":"3","number_of_employee_id":"2","number_of_employee_title":"10-50 employees","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-13 06:44:02"},{"number_of_employee_content_id":"5","number_of_employee_id":"3","number_of_employee_title":"50-100 employees","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-13 06:44:02"},{"number_of_employee_content_id":"7","number_of_employee_id":"4","number_of_employee_title":"100+ employees","language_id":"1","status":"1","addedBy":"1","updatedOn":"2018-07-13 06:44:02"}]
     * msg : Data found
     * active_user : 1
     */

    private boolean success;
    private boolean error;
    private String msg;
    private String active_user;
    private List<CompanyCategoriesBean> companyCategories;
    private List<NumberOfEmployeesBean> numberOfEmployees;

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

    public String getActive_user() {
        return active_user;
    }

    public void setActive_user(String active_user) {
        this.active_user = active_user;
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

    public static class CompanyCategoriesBean {
        /**
         * company_category_content_id : 1
         * company_category_id : 1
         * company_category_title : Restaurant
         * language_id : 1
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-16 05:32:24
         */

        private String company_category_content_id;
        private String company_category_id;
        private String company_category_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getCompany_category_content_id() {
            return company_category_content_id;
        }

        public void setCompany_category_content_id(String company_category_content_id) {
            this.company_category_content_id = company_category_content_id;
        }

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

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
        }
    }

    public static class NumberOfEmployeesBean {
        /**
         * number_of_employee_content_id : 1
         * number_of_employee_id : 1
         * number_of_employee_title : 1-10 employees
         * language_id : 1
         * status : 1
         * addedBy : 1
         * updatedOn : 2018-07-13 06:45:32
         */

        private String number_of_employee_content_id;
        private String number_of_employee_id;
        private String number_of_employee_title;
        private String language_id;
        private String status;
        private String addedBy;
        private String updatedOn;

        public String getNumber_of_employee_content_id() {
            return number_of_employee_content_id;
        }

        public void setNumber_of_employee_content_id(String number_of_employee_content_id) {
            this.number_of_employee_content_id = number_of_employee_content_id;
        }

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

        public String getLanguage_id() {
            return language_id;
        }

        public void setLanguage_id(String language_id) {
            this.language_id = language_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAddedBy() {
            return addedBy;
        }

        public void setAddedBy(String addedBy) {
            this.addedBy = addedBy;
        }

        public String getUpdatedOn() {
            return updatedOn;
        }

        public void setUpdatedOn(String updatedOn) {
            this.updatedOn = updatedOn;
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
        dest.writeString(this.active_user);
        dest.writeList(this.companyCategories);
        dest.writeList(this.numberOfEmployees);
    }

    public GetEmpProfileDropdownsResponce() {
    }

    protected GetEmpProfileDropdownsResponce(Parcel in) {
        this.success = in.readByte() != 0;
        this.error = in.readByte() != 0;
        this.msg = in.readString();
        this.active_user = in.readString();
        this.companyCategories = new ArrayList<CompanyCategoriesBean>();
        in.readList(this.companyCategories, CompanyCategoriesBean.class.getClassLoader());
        this.numberOfEmployees = new ArrayList<NumberOfEmployeesBean>();
        in.readList(this.numberOfEmployees, NumberOfEmployeesBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<GetEmpProfileDropdownsResponce> CREATOR = new Parcelable.Creator<GetEmpProfileDropdownsResponce>() {
        @Override
        public GetEmpProfileDropdownsResponce createFromParcel(Parcel source) {
            return new GetEmpProfileDropdownsResponce(source);
        }

        @Override
        public GetEmpProfileDropdownsResponce[] newArray(int size) {
            return new GetEmpProfileDropdownsResponce[size];
        }
    };
}
