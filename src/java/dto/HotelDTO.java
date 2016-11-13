/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author Daniel
 */
public class HotelDTO implements Serializable{
    
    private static final long serialVersionUID = -5577579081118070434L;
    
    String name;
    String city;
    String address;
    int booknr;
    boolean guarantee;
    int price;
    
    public HotelDTO(String name, String city, String address, int booknr, boolean guarantee, int price) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.booknr = booknr;
        this.guarantee = guarantee;
        this.price = price;
      
    }
    
    public HotelDTO(HotelDTO hotel) {
        this(hotel.getName(), hotel.getCity(), hotel.getAddress(), hotel.getBooknr(), hotel.isGuarantee(), hotel.getPrice());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getBooknr() {
        return booknr;
    }

    public void setBooknr(int booknr) {
        this.booknr = booknr;
    }

    public boolean isGuarantee() {
        return guarantee;
    }

    public void setGuarantee(boolean guarantee) {
        this.guarantee = guarantee;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int dayPrice) {
        this.price = dayPrice;
    }
    
    public String toString() {
        return "HotelDTO{" + "name=" + name + ", city=" + city + ", address=" + address + ", booknr=" + booknr + ", guarantee=" + guarantee + ", Price=" + price + '}';
    }
    
}
