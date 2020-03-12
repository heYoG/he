
/**
 * WebSignServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package serv;

    /**
     *  WebSignServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WebSignServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WebSignServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WebSignServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for SaveFile method
            * override this method for handling normal response from SaveFile operation
            */
           public void receiveResultSaveFile(
                    serv.WebSignServiceStub.SaveFileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SaveFile operation
           */
            public void receiveErrorSaveFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for UploadAndSynFile method
            * override this method for handling normal response from UploadAndSynFile operation
            */
           public void receiveResultUploadAndSynFile(
                    serv.WebSignServiceStub.UploadAndSynFileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from UploadAndSynFile operation
           */
            public void receiveErrorUploadAndSynFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for verifyDoc method
            * override this method for handling normal response from verifyDoc operation
            */
           public void receiveResultverifyDoc(
                    serv.WebSignServiceStub.VerifyDocResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from verifyDoc operation
           */
            public void receiveErrorverifyDoc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSealList method
            * override this method for handling normal response from getSealList operation
            */
           public void receiveResultgetSealList(
                    serv.WebSignServiceStub.GetSealListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSealList operation
           */
            public void receiveErrorgetSealList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for addSeal method
            * override this method for handling normal response from addSeal operation
            */
           public void receiveResultaddSeal(
                    serv.WebSignServiceStub.AddSealResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from addSeal operation
           */
            public void receiveErroraddSeal(java.lang.Exception e) {
            }
                


    }
    