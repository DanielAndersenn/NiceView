/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package impl;

import FastMoney.BankService;
import FastMoney.CreditCardFaultMessage;
import FastMoney.CreditCardInfoType;
import dto.HotelDTO;
import exceptions.BookingException;
import exceptions.CheckBeanFault;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;
import services.HotelService;

/**
 *
 * @author mathi
 */
@WebService(endpointInterface = "services.HotelService")
public class HotelServiceImpl implements HotelService{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;

    
    private static Map<Integer, HotelDTO> hotels = new HashMap<>();
    HotelDTO[] h;
    
    public HotelServiceImpl(){
        GenerateHotels();
    }
    
    
    @Override
    public HotelDTO[] getHotels(String city, Date arrDate, Date depDate) {
        
        //Setup ...
        Set<Integer> ids = hotels.keySet();
        int price;
        //Setup ... Done
        
        //Finding arraysize for our return array
        int arraySize = 0;
        for(Integer id : ids) {
           if(hotels.get(id).getCity().equals(city)) arraySize++; 
        }
        h = new HotelDTO[arraySize];
        //h-array initialized ........ 
        
        //Fills the h-array
        //With hotels that matches the asked city, and has rooms available.
        int i = 0;
        for(Integer id : ids) {
            //Checks if the hotel in DB equals the searched City
            if(hotels.get(id).getCity().equals(city)) { 
                //Checks if there's any available rooms.
               if(!areAllTrue(hotels.get(id).getBookedRooms())){
                    //If the City matches, and a room is available
                    //It adds the hotel to the return array
                    h[i] = new HotelDTO(hotels.get(id));
                    price = getNights(arrDate, depDate) * hotels.get(id).getPrice();
                    h[i].setPrice(price);
                    i++;
                }
            }
        }
        //h-array with Hotels available done.....
        
    return h; //Returns the h-array with set prices
    }


    @Override
    //In order to book a hotel, the "bookNr" Needs to equal HotelNr + roomnr
    //Example: bookNr = 3-9
    //Hotel nr 3, room 9.
    public boolean bookHotel(String bookNr, CreditCardInfoType ccInfo) throws BookingException {
        //Setup....
        int amount = 0;
        String input[] = bookNr.split("-");
        int hotelnr = Integer.parseInt(input[0]);
        int roomnr = Integer.parseInt(input[1]);
        //Setup done...
        
        //If no deposit is required. Book the room and return true.
        if(ccInfo == null && !hotels.get(hotelnr).isGuarantee()){
            hotels.get(hotelnr).setBookedRooms(true, roomnr);
            return true;
        } 
        //Checks if client is trying to input a null ccInfo when a hotel requires a quarantee.
        else if(ccInfo == null && hotels.get(hotelnr).isGuarantee()){
                CheckBeanFault fault = new CheckBeanFault();
                fault.setFaultCode("300");
                fault.setFaultString("Hotel requires guarantee");
                throw new BookingException("Hotel requires guarantee", fault);
        
        }
        //Did we recieve creditcard info and does the hotel need a quarantee
        else if(ccInfo != null && hotels.get(hotelnr).isGuarantee()) { 
            //Prints the recieved info
            System.out.println("Value of ccInfo | Name: " + ccInfo.getName() + " | Nr: " + ccInfo.getNumber() + " | xpDate: " + ccInfo.getExpirationDate().getMonth() + ccInfo.getExpirationDate().getYear());
            //To find the proper amount to pay, 
            //we have to find the stored amount in the array returned from getHotels
            //compared to the Hotelnr
            for(int i = 0; i < h.length; i++) {
                if(h[i].getHotelnr() == hotelnr) {
                    amount = h[i].getPrice();
                    System.out.println("Value of amount: " + amount);
                    break;
                }
            }   
            try {
                //validate the creditcard with the recieved info, and previous amount to be paid.
                validateCreditCard(15, ccInfo, amount);
                hotels.get(hotelnr).setBookedRooms(true, roomnr);
                return true;
            } catch (CreditCardFaultMessage ex) {
                CheckBeanFault fault = new CheckBeanFault();
                fault.setFaultCode("");
                fault.setFaultString(ex.getFaultInfo().getMessage());
                throw new BookingException(ex.getMessage(), fault);
            } 
        } 
    return false;
    }//End of bookHotel

    //Cancels the room, returns true if it succeded.
    //False on failure.
    @Override
    public boolean cancelHotel(String bookNr) {
        String input[] = bookNr.split("-");
        int hotelnr = Integer.parseInt(input[0]);
        int roomnr = Integer.parseInt(input[1]);
        return hotels.get(hotelnr).setBookedRooms(false,roomnr);
    }
    
    //Calculates the numbers of days.
    private int getNights(Date arrDate, Date depDate){
       long diff = depDate.getTime() - arrDate.getTime();
       int toReturn = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
       System.out.println("Value of int toReturn: " + toReturn);
       return toReturn;
    }
    
    //Generates data
    private void GenerateHotels() {
    
    HotelDTO h1 = new HotelDTO( "Danhostel", 
                                "Copenhagen", 
                                "H.C. Andersen Boulevard",
                                1,
                                false,
                                200);
    
    hotels.put(h1.getHotelnr(), h1);
    
    HotelDTO h2 = new HotelDTO( "Cyka Blyat",
                                "Moscow",
                                 "???????????????? 25",
                                2,
                                true,
                                100);
    
    hotels.put(h2.getHotelnr(), h2);
    
    HotelDTO h3 = new HotelDTO( "Marriott",
                                "Copenhagen",
                                 "Bryggen 37",
                                3,
                                true,
                                500);
    
    hotels.put(h3.getHotelnr(), h3);
    
}

    //Imported method
    private boolean validateCreditCard(int group, FastMoney.CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        FastMoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }
    
    //Returns false if atleast 1 object is false
    //Returns true if all objects equals true
    public static boolean areAllTrue(boolean[] array){
        
        
    for(boolean b : array) if(!b) return false;
    return true;
}
    
}
