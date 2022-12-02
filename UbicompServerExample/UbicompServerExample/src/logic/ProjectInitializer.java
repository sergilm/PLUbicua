package logic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;
import db.ConectionDDBB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *	ES: Clase encargada de inicializar el sistema y de lanzar el hilo de previsión meteorológica
 *	EN: Class in charge of initializing the thread of weather forecast
 */
@WebListener
public class ProjectInitializer implements ServletContextListener 
{
	
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
	}
	
	@Override
	/**
	 *	ES: Metodo empleado para detectar la inicializacion del servidor	<br>
	 * 	EN: Method used to detect server initialization
	 * 	@param sce <br>
	 * 	ES: Evento de contexto creado durante el arranque del servidor	<br>
	 * 	EN: Context event created during server launch
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		Log.log.info("-->Suscribe Topics<--");
                ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
                        System.out.println("Conecto a la bbdd");
                }
                catch (Exception e){}
		MQTTBroker broker = new MQTTBroker();
		MQTTSuscriber suscriber = new MQTTSuscriber();
		suscriber.searchTopicsToSuscribe(broker);
                PreparedStatement ps = ConectionDDBB.GetUserInfo(con);
                JOptionPane.showMessageDialog(null, ps.toString());

		Log.log.info("-->Running weather Thread<--");
		new ThreadWeatherForecast();
		
	}	
}