package com.jack.orion;

import com.jack.netty.Server;
import com.jack.netty.conf.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Jack on 2017/6/16.
 */
public class App {
    private static Logger log = LoggerFactory.getLogger("Orion-Namespace-Server");

    private static Server server;

    /**
    * @Description:
    * <p> 启动服务 </p>
    *
    * @Methods main
    * @param
    * @throws
    * @return
    * @Create In 2017/6/16 By Jack
    **/
    public static void main( String[] args ) {
        if (args != null && args.length != 0 && (Constant.SYSTEM_SEETING_SERVER_DEFAULT_COMMAND_STOP.equals(args[0].toLowerCase()))) {
            int ctrlPort = Integer.parseInt(args[1]);
            try {
                Socket s = new Socket("localhost", ctrlPort);
                PrintWriter w = new PrintWriter(s.getOutputStream());
                w.println(Constant.SYSTEM_SEETING_SERVER_DEFAULT_COMMAND_STOP);
                w.flush();
                w.close();
                s.close();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                server.shutdown(-1);
            }
        } else if (args != null && args.length >= 3 && Constant.SYSTEM_SEETING_SERVER_DEFAULT_COMMAND_STARTUP.equals(args[0].toLowerCase())) {
            try {
                int serverPort = Integer.parseInt(args[1]);
                int conrtrolPort = Integer.parseInt(args[2]);

                if (args.length > 3) {
                    server = new Server(serverPort, conrtrolPort, args[3]);
                } else {
                    server = new Server(serverPort, conrtrolPort);
                }

                server.run();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                server.shutdown(-1);
            }

        }
    }
}
