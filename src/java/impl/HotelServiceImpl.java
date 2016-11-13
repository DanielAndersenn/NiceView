package impl;

import dto.HotelDTO;
import exceptions.BookingException;
import exceptions.CheckBeanFault;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.jws.WebService;
import services.HotelService;
/**
 *
 * @author Daniel
 */
@WebService(endpointInterface = "services.HotelService")
public class HotelServiceImpl implements HotelService{
    
    private static Map<Integer, HotelDTO> hotels = new HashMap<Integer, HotelDTO>();
    
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
        HotelDTO[] h = new HotelDTO[arraySize];
        
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
    public String bookHotel(int bookNr, String ccInfo) throws BookingException{
        if(bookNr == 1) {
            CheckBeanFault fault = new CheckBeanFault(); 
            fault.setFaultCode("54321");
            fault.setFaultString("Hey homie ######");
            throw new BookingException("12345", fault);    
        } else {
            return "lol";
        }
        
        
    }

    @Override
    public String cancelHotel(int bookNr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
