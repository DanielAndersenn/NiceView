/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.jws.WebService;
import javax.jws.WebMethod;
import dto.HotelDTO;
import java.util.Date;

/**
 *
 * @author Daniel
 */
@WebService
public interface HotelService {
    
    @WebMethod
    public HotelDTO[] getHotels(String city, Date arrDate, Date depDate);
    
    @WebMethod
    public String bookHotel(int bookNr, String ccInfo);
    
    @WebMethod 
    public String cancelHotel(int bookNr);

    
}
