/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Daniel
 */
public class CheckBeanFault {
    
    private String faultString;
    private String faultCode;
    
    public CheckBeanFault() {}
    
    public String getFaultCode() {
        return faultCode;
    }
    
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }
    
    public String getFaultString() {
        return faultString;
    }
    
    public void setFaultString(String faultString) {
        this.faultString = faultString;
    }
    
}
