
/**
 * SealServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package serv;

    /**
     *  SealServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SealServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SealServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SealServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for sealAutoPdf method
            * override this method for handling normal response from sealAutoPdf operation
            */
           public void receiveResultsealAutoPdf(
                    serv.SealServiceStub.SealAutoPdfResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from sealAutoPdf operation
           */
            public void receiveErrorsealAutoPdf(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for pdfVarify method
            * override this method for handling normal response from pdfVarify operation
            */
           public void receiveResultpdfVarify(
                    serv.SealServiceStub.PdfVarifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from pdfVarify operation
           */
            public void receiveErrorpdfVarify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getClientIp method
            * override this method for handling normal response from getClientIp operation
            */
           public void receiveResultgetClientIp(
                    serv.SealServiceStub.GetClientIpResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getClientIp operation
           */
            public void receiveErrorgetClientIp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for modelAutoMerger method
            * override this method for handling normal response from modelAutoMerger operation
            */
           public void receiveResultmodelAutoMerger(
                    serv.SealServiceStub.ModelAutoMergerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from modelAutoMerger operation
           */
            public void receiveErrormodelAutoMerger(java.lang.Exception e) {
            }
                


    }
    