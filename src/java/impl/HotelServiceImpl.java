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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;
import services.HotelService;
/**
 *
 * @author Daniel
 */
@WebService(endpointInterface = "services.HotelService")
public class HotelServiceImpl implements HotelService{
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/fastmoney.imm.dtu.dk_8080/BankService.wsdl")
    private BankService service;
    
    private static Map<Integer, HotelDTO> hotels = new HashMap<>();
    HotelDTO[] h;
    
    public HotelServiceImpl () {
        GenerateHotels();
    }
    
    @Override
    public HotelDTO[] getHotels(String city, Date arrDate, Date depDate) {
        
        Set<Integer> ids = hotels.keySet();
        
        int price;
           
        int arraySize = 0;
        for(Integer id : ids) {
           if(hotels.get(id).getCity().equals(city)) arraySize++; 
        }
        //System.out.println("Arraysize: " + arraySize);
        h = new HotelDTO[arraySize];
        
        int i = 0;
        for(Integer id : ids) {
            //System.out.println("Byer: " + hotels.get(id).getCity() + "Input param city: " + city);
            //System.out.println("Hvad så mand?" + hotels.get(id).getCity().equals(city));
            if(hotels.get(id).getCity().equals(city)) 
            {
                
                h[i] = new HotelDTO(hotels.get(id));
                price = getNights(arrDate, depDate) * hotels.get(id).getPrice();
                h[i].setPrice(price);
                i++;
            }
            
        }
        
        return h;
        
    }

    @Override
    public boolean bookHotel(int bookNr, CreditCardInfoType ccInfo) throws BookingException{
        
        int amount = 0;
        if(ccInfo == null) {
            return true;
        } else {
            System.out.println("Value of ccInfo | Name: " + ccInfo.getName() + " | Nr: " + ccInfo.getNumber() + " | xpDate: " + ccInfo.getExpirationDate().getMonth() + ccInfo.getExpirationDate().getYear());
            for(int i = 0; i < h.length; i++) {
                if(h[i].getBooknr() == bookNr) {
                    amount = h[i].getPrice();
                    System.out.println("Value of amount: " + amount);
                    break;
                }
            }
            
            try {
                    validateCreditCard(15, ccInfo, 1001);
                    System.out.println("Value of validateCC() " + validateCreditCard(15, ccInfo, amount));
                    return true;
            } catch (CreditCardFaultMessage ex) {
                CheckBeanFault fault = new CheckBeanFault();
                fault.setFaultCode("");
                fault.setFaultString(ex.getFaultInfo().getMessage());
                throw new BookingException(ex.getMessage(), fault);
            }    
   
        } 
        
    }

    @Override
    public void cancelHotel(int bookNr) throws BookingException {
        if(bookNr == 1) return;
        if(bookNr != 1) throw new BookingException("Cancellation failed because reasons", new CheckBeanFault());
    }
    
    
    public void GenerateHotels() {
    
    HotelDTO h1 = new HotelDTO( "Danhostel", 
                                "Copenhagen", 
                                "H.C. Andersen Boulevard",
                                1,
                                false,
                                200);
    
    hotels.put(h1.getBooknr(), h1);
    
    HotelDTO h2 = new HotelDTO( "Cyka Blyat",
                                "Moscow",
                                 "???????????????? 25",
                                2,
                                true,
                                100);
    
    hotels.put(h2.getBooknr(), h2);
    
    HotelDTO h3 = new HotelDTO( "Marriott",
                                "Copenhagen",
                                 "Bryggen 37",
                                3,
                                true,
                                500);
    
    hotels.put(h3.getBooknr(), h3);
    
}
    
    private int getNights(Date arrDate, Date depDate){
       int toReturn;
       long diff = depDate.getTime() - arrDate.getTime();
       toReturn = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        System.out.println("Value of int toReturn: " + toReturn);
       return toReturn;

    }

    private boolean validateCreditCard(int group, CreditCardInfoType creditCardInfo, int amount) throws CreditCardFaultMessage {
    
        FastMoney.BankPortType port = service.getBankPort();
        return port.validateCreditCard(group, creditCardInfo, amount);
    }

}
