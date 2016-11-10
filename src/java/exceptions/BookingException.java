/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import javax.xml.ws.WebFault;

/**
 *
 * @author Daniel
 */

@WebFault(name="BookingException")
public class BookingException extends Exception {
    
    private CheckBeanFault faultInfo;
    
    public BookingException() {
        
    }
    
    public BookingException(String message, CheckBeanFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }
    
    public BookingException(String message, CheckBeanFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
    
    public CheckBeanFault getFaultInfo() {
        return faultInfo;
    }
    
    
    
}
