package com.example.rotibanksystem;

public class donation {

   private  String food;
   private String foodintro;
   private String quantity;
   private String address;
   private String coordinates;
   private String date;
   private String userid;
   private String status;
    public donation() {

    }



    public donation(String food,String foodintro, String quantity, String coordinates, String address,String date,String userid,String status){
        this.food=food;
        this.foodintro=foodintro;
        this.quantity=quantity;
        this.date=date;
        this.userid=userid;

this.status=status;

        this.coordinates=coordinates;
        this.address=address;
    }
    public String getStatus(){return status;}
    public String getUserid(){return userid;}
    public String getfood() {
        return food;
    }
    public String getFoodintro(){
        return foodintro;
    }
    public String getDate(){return date;}

    public String getQuantity() {
        return quantity;
    }
    public String getCoordinates() {
        return coordinates;
    }
    public String getAddress() {
        return address;
    }


    @Override
    public String  toString(){

        return "Food Description: "  + this.foodintro + "\n" +quantity+ " People will be served by this food   " ;
    }
}
