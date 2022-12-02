package mqtt;

public class MQTTBroker 
{		
	private static int qos = 2;
	private static String broker = "tcp://192.168.1.131:1883";
	private static String clientId = "WheatherStationUAH";
		
	public MQTTBroker()
	{
	}

	public static int getQos() {
		return qos;
	}

	public static String getBroker() {
		return broker;
	}

	public static String getClientId() {
		return clientId;
	}			
}


