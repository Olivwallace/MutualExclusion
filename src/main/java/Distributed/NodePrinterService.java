package Distributed;

import Enums.MessageType;
import Network.Message;
import Network.NodeInfo;
import Network.NodeTCP;
import Uteis.Uteis;

import java.util.ArrayList;
import java.util.List;

public class NodePrinterService extends NodeTCP {

    public NodeInfo currentClient;

    public NodePrinterService(NodeInfo currentNode){
        super(currentNode);
    }

    @Override
    public void start() {
        startServer();
    }

    @Override
    protected void handleMessage(Message message, String senderIp) {
        MessageType messageType = MessageType.valueOf(message.getType());

        switch (messageType){
            case PRINT:
                showCriticalSection(message);
        }
    }

    public void showCriticalSection(Message message){
        String nodeId = message.getSender().id();
        List<Integer> sequence = message.getSequencePrint();

        try {
            for (Integer i : sequence) {
                System.out.println("[" + nodeId + "] - " + i);
                Uteis.delay(5000);
            }

            Message printAck = new Message(MessageType.PRINT_ACK.name(),nodeInfo);
            sendMessage(message.getSender().nodeIP(), message.getSender().nodePort(), printAck);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
