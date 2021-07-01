package com.example.rotibanksystem;

public class Needy {

    private String fullname;
    private String phone;
    private String email;
    private String aadharno;

    private String age;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private  String gender;
    private  String coordinates;

    public Needy(String fullname, String phone, String email, String aadharno, String age, String gender,String coordinates,String address) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.address=address;
        this.aadharno = aadharno;
        this.age = age;

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


    public String getAadharno() {
        return aadharno;
    }


    public String getAge() {
        return age;
    }



    public String getGender() {
        return gender;
    }


    public String getCoordinates() {
        return coordinates;
    }


    @Override
    public String  toString(){

        return "Full Name: "  + this.fullname;
    }
}


