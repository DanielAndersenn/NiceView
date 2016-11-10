package impl;

import dto.HotelDTO;
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
        System.out.println(hotels.get(2).toString());
    }
    
    @Override
    public HotelDTO[] getHotels(String city, Date arrDate, Date depDate) {
        Set<Integer> ids = hotels.keySet();
        HotelDTO[] h = new HotelDTO[ids.size()];
        
        int i = 0;
        for(Integer id : ids) {
            h[i] = hotels.get(id);
            i++;
        }
        
        return h;
        
    }

    @Override
    public String bookHotel(int HotelNr, String ccInfo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
    
}
    
    private long getNights(Date arrDate, Date depDate){
       long diff = depDate.getTime() - arrDate.getTime();
       return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}
