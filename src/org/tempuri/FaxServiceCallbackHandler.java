
/**
 * FaxServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4  Built on : Apr 26, 2008 (06:24:30 EDT)
 */

    package org.tempuri;

    /**
     *  FaxServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class FaxServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public FaxServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public FaxServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for GetUserInfo method
            * override this method for handling normal response from GetUserInfo operation
            */
           public void receiveResultGetUserInfo(
                    org.tempuri.FaxServiceStub.GetUserInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetUserInfo operation
           */
            public void receiveErrorGetUserInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetSendStatus method
            * override this method for handling normal response from GetSendStatus operation
            */
           public void receiveResultGetSendStatus(
                    org.tempuri.FaxServiceStub.GetSendStatusResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetSendStatus operation
           */
            public void receiveErrorGetSendStatus(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetFaxNotify method
            * override this method for handling normal response from GetFaxNotify operation
            */
           public void receiveResultGetFaxNotify(
                    org.tempuri.FaxServiceStub.GetFaxNotifyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetFaxNotify operation
           */
            public void receiveErrorGetFaxNotify(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetReceiveNewFaxCount method
            * override this method for handling normal response from GetReceiveNewFaxCount operation
            */
           public void receiveResultGetReceiveNewFaxCount(
                    org.tempuri.FaxServiceStub.GetReceiveNewFaxCountResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetReceiveNewFaxCount operation
           */
            public void receiveErrorGetReceiveNewFaxCount(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for DeleteSendFax method
            * override this method for handling normal response from DeleteSendFax operation
            */
           public void receiveResultDeleteSendFax(
                    org.tempuri.FaxServiceStub.DeleteSendFaxResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from DeleteSendFax operation
           */
            public void receiveErrorDeleteSendFax(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for DeleteReceiveFax method
            * override this method for handling normal response from DeleteReceiveFax operation
            */
           public void receiveResultDeleteReceiveFax(
                    org.tempuri.FaxServiceStub.DeleteReceiveFaxResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from DeleteReceiveFax operation
           */
            public void receiveErrorDeleteReceiveFax(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SignOut method
            * override this method for handling normal response from SignOut operation
            */
           public void receiveResultSignOut(
                    org.tempuri.FaxServiceStub.SignOutResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SignOut operation
           */
            public void receiveErrorSignOut(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ReceiveFaxBySymbol method
            * override this method for handling normal response from ReceiveFaxBySymbol operation
            */
           public void receiveResultReceiveFaxBySymbol(
                    org.tempuri.FaxServiceStub.ReceiveFaxBySymbolResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ReceiveFaxBySymbol operation
           */
            public void receiveErrorReceiveFaxBySymbol(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for Send method
            * override this method for handling normal response from Send operation
            */
           public void receiveResultSend(
                    org.tempuri.FaxServiceStub.SendResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from Send operation
           */
            public void receiveErrorSend(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for UploadFaxFile method
            * override this method for handling normal response from UploadFaxFile operation
            */
           public void receiveResultUploadFaxFile(
                    org.tempuri.FaxServiceStub.UploadFaxFileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from UploadFaxFile operation
           */
            public void receiveErrorUploadFaxFile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for GetReceiveFaxSymbol method
            * override this method for handling normal response from GetReceiveFaxSymbol operation
            */
           public void receiveResultGetReceiveFaxSymbol(
                    org.tempuri.FaxServiceStub.GetReceiveFaxSymbolResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from GetReceiveFaxSymbol operation
           */
            public void receiveErrorGetReceiveFaxSymbol(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for SignIn method
            * override this method for handling normal response from SignIn operation
            */
           public void receiveResultSignIn(
                    org.tempuri.FaxServiceStub.SignInResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from SignIn operation
           */
            public void receiveErrorSignIn(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for ModifyUser method
            * override this method for handling normal response from ModifyUser operation
            */
           public void receiveResultModifyUser(
                    org.tempuri.FaxServiceStub.ModifyUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from ModifyUser operation
           */
            public void receiveErrorModifyUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for Receive method
            * override this method for handling normal response from Receive operation
            */
           public void receiveResultReceive(
                    org.tempuri.FaxServiceStub.ReceiveResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from Receive operation
           */
            public void receiveErrorReceive(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for UploadAddress method
            * override this method for handling normal response from UploadAddress operation
            */
           public void receiveResultUploadAddress(
                    org.tempuri.FaxServiceStub.UploadAddressResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from UploadAddress operation
           */
            public void receiveErrorUploadAddress(java.lang.Exception e) {
            }
                


    }
    