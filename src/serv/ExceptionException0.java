
/**
 * ExceptionException0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

package serv;

public class ExceptionException0 extends java.lang.Exception{
    
    private serv.SealServiceStub.ExceptionE faultMessage;
    
    public ExceptionException0() {
        super("ExceptionException0");
    }
           
    public ExceptionException0(java.lang.String s) {
       super(s);
    }
    
    public ExceptionException0(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(serv.SealServiceStub.ExceptionE msg){
       faultMessage = msg;
    }
    
    public serv.SealServiceStub.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    