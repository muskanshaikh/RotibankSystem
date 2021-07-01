package com.example.rotibanksystem;

public class Ngouser {
    private String orgName;
    private String headName;
    private String email;
    private String userId;

    private String contact;
    private  String coordinates;
    private  String address;



    public void setAddress(String address) {
        this.address = address;
    }

    public Ngouser() {

    }

    public Ngouser(String orgName, String headName, String contact,String coordinates,String email,String address,String userId) {
        this.orgName = orgName;
        this.headName=headName;
        this.address=address;

        this.coordinates=coordinates;
        this.contact = contact;

        this.email=email;
        this.userId=userId;
    }

    public String getAddress() {
        return address;
    }
    public String getOrgName() {
        return orgName;
    }

    public String getHeadName() {
        return headName;
    }


    public String getCoordinates(){return  coordinates;}
    public String getContact() {
        return contact;
    }







    public String getEmail() {
        return email;
    }
    public String getUserId(){return userId; }


    @Override
    public String  toString(){

        return "Organization Name: "  + this.getOrgName() + "\n" + "Head Name: " + this.getHeadName() +"\n" +"Address:" + this.getAddress() +"\n" + "Contact:" + this.getContact();
    }

}