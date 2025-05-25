import Distributed.NodeDistributed;
import Distributed.NodePrinterService;
import Network.NodeInfo;
import Uteis.Uteis;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Tente: java <id_node> <ip_node> <port_node> <is_service_printer>");
            throw  new RuntimeException();
        }

        NodeInfo currentNodeInfo = new NodeInfo(args[0], args[1], Integer.parseInt(args[2]));
        boolean isServer = Boolean.getBoolean(args[3]);

//        Boolean isServer = true;
//        NodeInfo currentNodeInfo = new NodeInfo("Service", "192.168.0.2", 5000);


        List<NodeInfo> nodesList = Uteis.readNodes("configNodes.txt");
        NodeInfo service = Uteis.readService("serviceNode.txt");

        if(service != null) {
            if (isServer) {

                NodePrinterService newService = new NodePrinterService(service);
                newService.start();

            } else {

                NodeDistributed newNode = new NodeDistributed(currentNodeInfo, service, nodesList);
                newNode.start();

            }
        }

    }
}