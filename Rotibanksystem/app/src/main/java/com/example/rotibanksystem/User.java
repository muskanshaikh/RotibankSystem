package com.example.rotibanksystem;

public class User {
    private String firstName;
    private String lastName;
    private String email;

    private String contact;
    private String coordinates;
    private  String address;
    private String userId;



    public User() {

    }

    public User(String firstName, String lastName, String contact,  String coordinates,String address,String email,String userId) {
        this.firstName = firstName;
        this.lastName=lastName;

this.address=address;
        this.contact = contact;
       this.coordinates=coordinates;
        this.email=email;
        this.userId=userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

public String getUserId(){
        return  userId;
}
public  String getAddress(){
        return address;
}

    public String getContact() {
        return contact;
    }
    public String getCoordinates(){
        return coordinates;
    }



    public String  getEmail() {
        return email;
    }

    @Override
    public String  toString(){
        return firstName + " " + email;
    }

}
