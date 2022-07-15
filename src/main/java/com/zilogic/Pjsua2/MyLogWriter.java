/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zilogic.Pjsua2;

/**
 *
 * @author user
 */
import org.pjsip.pjsua2.LogEntry;
import org.pjsip.pjsua2.LogWriter;

class MyLogWriter extends LogWriter {
  public void write(LogEntry paramLogEntry) {
    System.out.println(paramLogEntry.getMsg());
  }
}
