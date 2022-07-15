
package com.zilogic.Pjsua2;


import org.pjsip.pjsua2.AccountConfig;
import org.pjsip.pjsua2.AuthCredInfo;
import org.pjsip.pjsua2.CallOpParam;

public class MainApp {

    private static MyApp app = new MyApp();

    private static MyObserver observer = new MyObserver();

    private static MyAccount account = null;

    private static AccountConfig accCfg = null;

    private static void runWorker() throws Exception {
        try {
            app.init((MyAppObserver) observer, ".", true);
        } catch (Exception exception) {
            System.out.println(exception);
            app.deinit();
            System.exit(-1);
        }

        MyAccountConfig myaccConf = new MyAccountConfig();
        AccountConfig accConf = new AccountConfig();
        accConf.setIdUri("sip:6001@192.168.0.132");
        accConf.getRegConfig().setRegistrarUri("sip:192.168.0.132");
        myaccConf.accCfg.getNatConfig().setIceEnabled(true);
            myaccConf.accCfg.getVideoConfig().setAutoTransmitOutgoing(true);
            myaccConf.accCfg.getVideoConfig().setAutoShowIncoming(true);
            myaccConf.accCfg.getMediaConfig().setSrtpUse(1);
            myaccConf.accCfg.getMediaConfig().setSrtpSecureSignaling(0);
        AuthCredInfo auth = new AuthCredInfo("digest", "*", "6001", 0, "1234");
        accConf.getSipConfig().getAuthCreds().add(auth);
         account = app.addAcc(accConf);
        MyCall call = new MyCall(account, 0);
        call.makeCall("sip:6002@192.168.0.132", new CallOpParam(true));
      
        
        while (!Thread.currentThread().isInterrupted()) {
            MyApp.ep.libHandleEvents(10L);
            observer.check_call_deletion();
            try {
                Thread.currentThread();
                Thread.sleep(50L);
            } catch (InterruptedException interruptedException) {
                break;
            }
        }
        app.deinit();
    }

    public static void main(String[] paramArrayOfString) throws Exception {
        Runtime.getRuntime().addShutdownHook((Thread) new MyShutdownHook(Thread.currentThread()));
        runWorker();

    }
}
