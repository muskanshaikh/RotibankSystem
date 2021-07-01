package com.example.rotibanksystem;

public class volunteer {

    private String fullname;
    private String phone;
    private String email;
    private String txtbefore;

    private String isdays;

    private String istime;
    private String address;


    private  String gender;

    private  String coordinates;

    public volunteer(String fullname, String phone, String email, String txtbefore, String isdays, String istime, String gender, String coordinates,String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.txtbefore = txtbefore;
        this.isdays = isdays;
        this.istime = istime;
        this.gender = gender;
        this.coordinates = coordinates;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }

    public String getTxtbefore() {
        return txtbefore;
    }




    public String getIsdays() {
        return isdays;
    }



    public String getIstime() {
        return istime;
    }


    public String getAddress() {
        return address;
    }


    public String getGender() {
        return gender;
    }


    public String getCoordinates() {
        return coordinates;
    }


    public String  toString(){
        return this.fullname + " " + email;

    }

}
