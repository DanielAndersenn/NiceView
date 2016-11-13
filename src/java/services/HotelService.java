/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import FastMoney.CreditCardInfoType;
import exceptions.BookingException;
import javax.jws.WebService;
import javax.jws.WebMethod;
import dto.HotelDTO;
import java.util.Date;
import javax.jws.WebParam;

/**
 *
 * @author Daniel
 */
@WebService
public interface HotelService {
    
    @WebMethod
    public HotelDTO[] getHotels(@WebParam(name="city") String city, @WebParam(name="arrDate")Date arrDate, @WebParam(name="depDate")Date depDate);
    
    @WebMethod
    public boolean bookHotel(@WebParam(name="bookNr") int bookNr, @WebParam(name="ccInfo")CreditCardInfoType ccInfo) throws BookingException;
            
    @WebMethod 
    public void cancelHotel(int bookNr) throws BookingException;

    
}
