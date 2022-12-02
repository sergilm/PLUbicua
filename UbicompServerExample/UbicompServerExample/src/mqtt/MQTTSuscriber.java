package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import db.ConectionDDBB;
import db.Topics;
import logic.Log;
import logic.Logic;

public class MQTTSuscriber implements MqttCallback
{
	public void searchTopicsToSuscribe(MQTTBroker broker){
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		ArrayList<String> topics = new ArrayList<>();
		try{
			con = conector.obtainConnection(true);
			Log.logmqtt.debug("Database Connected");
			
			//Get Cities to search the main topic
			PreparedStatement psZona = ConectionDDBB.GetCities(con);
			Log.logmqtt.debug("Query to search zonas=> {}", psZona.toString());
			ResultSet rsZona = psZona.executeQuery();
			while (rsZona.next()){
				topics.add("Zona" + rsZona.getInt("")+"/#");
			}
			topics.add("test");
			suscribeTopic(broker, topics);			
		} 
		catch (NullPointerException e){Log.logmqtt.error("Error: {}", e);} 
		catch (Exception e){Log.logmqtt.error("Error:{}", e);} 
		finally{conector.closeConnection(con);}
	}
	
	public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics)
	{
		Log.logmqtt.debug("Suscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try
        {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            for (int i = 0; i <topics.size(); i++) 
            {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to {}", topics.get(i));
			}
            
        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscribing topic: {}", e);
        }
	}
	
	@Override
	public void connectionLost(Throwable cause) 
	{	
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception 
	{
       Log.logmqtt.info("{}: {}", topic, message.toString());
       String[] topics = topic.split("/");
       Topics newTopic = new Topics();
       newTopic.setValue(message.toString());
       if(topic.contains("Sensor"))
       {
		   newTopic.setIdZona(topics[0].replace("Zona", ""));
		   newTopic.setIdPlaca(topics[1].replace("Placa", ""));
		   newTopic.setIdSensor(topics[2].replace("Sensor", ""));
    	   Log.logmqtt.info("Mensaje from zona{}, placa{} sensor{}: {}", 
    			   newTopic.getIdZona(), newTopic.getIdPlaca(), newTopic.getIdSensor(), message.toString());
    	   
    	   //Store the information of the sensor
    	   Logic.storeNewMeasurement(newTopic);
       }else
       {
    	   if(topic.contains("Placa"))
    	   {
    		   newTopic.setIdZona(topics[0].replace("Zona", ""));
    		   newTopic.setIdPlaca(topics[1].replace("Placa", ""));
        	   Log.logmqtt.info("Mensaje from zona{}, placa{}: {}", 
        			   newTopic.getIdZona(), newTopic.getIdPlaca(), message.toString());
    	   }else
    	   {
    		   if(topic.contains("Zona"))
        	   {
    			   newTopic.setIdZona(topics[0].replace("Zona", ""));
    	    	   Log.logmqtt.info("Mensaje from zona{}: {}", 
    	    			   newTopic.getIdZona(), message.toString());
        	   }else
        	   {
        		   
        	   }
    	   }
       }
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) 
	{		
	}
}

