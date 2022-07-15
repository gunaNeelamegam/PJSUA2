/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zilogic.Pjsua2;

/**
 *
 * @author user
 */
import org.pjsip.pjsua2.CallInfo;
import org.pjsip.pjsua2.CallOpParam;


class MyObserver implements MyAppObserver {
  private static MyCall currentCall = null;
  
  private boolean del_call_scheduled = false;
  
  public void check_call_deletion() {
    if (this.del_call_scheduled && currentCall != null) {
      currentCall.delete();
      currentCall = null;
      this.del_call_scheduled = false;
    } 
  }
  
  public void notifyRegState(int paramInt, String paramString, long paramLong) {
  
  System.out.println("REg");
  }
  
  public void notifyIncomingCall(MyCall paramMyCall) {
    CallOpParam callOpParam = new CallOpParam();
    callOpParam.setStatusCode(200);
    try {
      currentCall = paramMyCall;
      currentCall.answer(callOpParam);
    } catch (Exception exception) {
      System.out.println(exception);
      return;
    } 
  }
  
  public void notifyCallMediaState(MyCall paramMyCall) {}
  
  public void notifyCallState(MyCall paramMyCall) {
    CallInfo callInfo;
    if (currentCall == null || paramMyCall.getId() != currentCall.getId())
      return; 
    try {
      callInfo = paramMyCall.getInfo();
    } catch (Exception exception) {
      callInfo = null;
    } 
    if (callInfo.getState() == 6)
      this.del_call_scheduled = true; 
  }
  
  public void notifyBuddyState(MyBuddy paramMyBuddy) {}
  
  public void notifyChangeNetwork() {}
}
