/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.Arrays;

/**
 *
 * @author mathi
 */
public class HotelDTO implements Serializable {
    
    private String name;
    private String city;
    private String address;
    private int hotelnr;
    private boolean guarantee;
    private int price;
    private boolean BookedRooms[] = new boolean[10];
    
    public HotelDTO(String name, String city, String address, int hotelnr, boolean guarantee, int price) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.hotelnr = hotelnr;
        this.guarantee = guarantee;
        this.price = price;
        Arrays.fill(BookedRooms, false);
    }
    
    @Override
    public String toString() {
        return "HotelDTO{" + "name=" + name + ", city=" + city + ", address=" + address + ", hotelnr=" + hotelnr + ", guarantee=" + guarantee + ", Price=" + price + '}';
    }

     public HotelDTO(HotelDTO hotel) {
        this(hotel.getName(), hotel.getCity(), hotel.getAddress(), hotel.getHotelnr(), hotel.isGuarantee(), hotel.getPrice());
        this.BookedRooms = hotel.getBookedRooms();
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

    public int getHotelnr() {
        return hotelnr;
    }

    public void setHotelnr(int hotelnr) {
        this.hotelnr = hotelnr;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean[] getBookedRooms() {
        return BookedRooms;
    }

    public boolean setBookedRooms(boolean book, int room) {
       
        if(BookedRooms[room] == book){
            return false;
        } else {
        BookedRooms[room] = book;
        return true;
        }
        
    }
    
    
}
