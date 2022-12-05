package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import db.ConectionDDBB;
import db.Measurement;
import db.Tiempo;
import static java.lang.Thread.sleep;

/**
 * ES:Hilo que obtiene la previsión del tiempo de wunderground.com/api para insertar la previsión en la base de datos
 * EN:This thread will obtain the weather forecast from wunderground.com/api to insert data obtained in the data base
 */
public class ThreadWeatherForecast extends Thread
{
	// Period at which the Thread will read data from the JSON is 60 minutes
	private int PERIOD = 1000 * 60 * 60;	
	private static String APIkey = "a63d2ad593904626c30f67f8d3f9a630";

	
	public ThreadWeatherForecast()
	{
		start();
	}
	
	@Override
	public void run()
	{
		while (true)
		{
			ArrayList<Tiempo> weatherValues = obtainPrediction();
			try
			{
				ConectionDDBB conectiondb = new ConectionDDBB();
				Connection con = conectiondb.obtainConnection(true);
				Log.log.info("Search forecast");
				for (int i = 0; i < weatherValues.size(); i++)
				{
                                    //*********************************************************************************
					PreparedStatement ps = ConectionDDBB.InsertWeatherForecast(con);
					Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
					ps.setTimestamp(1, ts);
					ps.setInt(2, weatherValues.get(i).getIdZona());
					ps.setDouble(3, weatherValues.get(i).getTemperatura());
					ps.setDouble(4, weatherValues.get(i).getVelViento());
					ps.setInt(5, weatherValues.get(i).getDirViento());
					ps.setDouble(6, weatherValues.get(i).getPorcPrecipitacion());
					ps.setString(7,weatherValues.get(i).getEstado());
					ps.setInt(8, weatherValues.get(i).getIdPlaca());
                                        ps.setTimestamp(9, ts);
					ps.setInt(10, weatherValues.get(i).getIdZona());
					ps.setDouble(11, weatherValues.get(i).getTemperatura());
					ps.setDouble(12, weatherValues.get(i).getVelViento());
					ps.setInt(13, weatherValues.get(i).getDirViento());
					ps.setDouble(14, weatherValues.get(i).getPorcPrecipitacion());
					ps.setString(15,weatherValues.get(i).getEstado());
					ps.setInt(16, weatherValues.get(i).getIdPlaca());
					Log.log.debug("Inserting weather value: {}", ps.toString());
					ps.executeUpdate();
				}

				conectiondb.closeConnection(con);
			} catch (SQLException e)
			{
				Log.log.error("Error SQL request {}", e);
			} catch (NullPointerException e)
			{
				Log.log.error("Nullpointer error {}", e);
			} catch (Exception e)
			{
				Log.log.error("Generic error {}", e);
			}
			try
			{
				sleep(PERIOD);
			} catch (InterruptedException e)
			{
				Log.log.error("WeatherObtainer Sleep Error: {}", e);
			}
		}

	}
	
	/**
	 * Gets the API's URL page
	 * 
	 * @return URL
	 * @throws IOException
	 */
	private static URL getURLGPS(String d, String e) throws IOException
	{
		String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" +d + "&lon=" + e + "&appid=" + APIkey;
		Log.log.info("URL={}", url);
		return new URL(url);
	}

	/**
	 * ES: Crea un nuevo WeatherObtainer 
	 * EN: Creates a new WeatherObtainer
	 */	
	public static String getCity()
	{
		String city = "Guadalajara"; 
		
		try
		{
			//ES:Recorrer todas las ciudades de la base de datos
			//EN:Get all the cities stored inthe database
			ConectionDDBB conectiondb = new ConectionDDBB();
			Connection con = conectiondb.obtainConnection(true);
			PreparedStatement ps = ConectionDDBB.GetCities(con);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				city = rs.getString("NAME");
			}
			Log.log.debug("Closing data base connection");
			conectiondb.closeConnection(con);
		} catch (SQLException e)
		{
			Log.log.error("SQL error when evaluating the generic collection sequence: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Nullpointer error when evaluatinf the generic collection sequence: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error when evaluating the generic collection sequence: {}", e);
		}
		return city;
	}

	
//TODO cambiar
        //**********************************************
	public static ArrayList<Tiempo> obtainPrediction()
	{
		ArrayList<Tiempo> weather = new ArrayList<>();
		try
		{
			//EN: Obtain data from API in a StringBuffer
			//ES:Obtener información del API en el StringBuffer
			InputStream is = getURLGPS("40.513493","-3.349011").openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			StringBuffer json = new StringBuffer();
			String inputLine;
			while ((inputLine = br.readLine()) != null)
			{
				json.append(inputLine);
			}

			Gson gson = new Gson();
			JsonElement element = gson.fromJson(json.toString(), JsonElement.class);
			com.google.gson.JsonObject o = element.getAsJsonObject();	
			
			int cont = 0;

			// ES: recorremos las horas que hay hasta las 12 de la mañana del dia siguiente desde este momento.
			// EN: we go through the hours until 12 in the morning of tomorrow from this moment.
			for (int i = 0; i < (36 - Calendar.getInstance().get(Calendar.HOUR_OF_DAY)); i++)
			{
				if (i % 3 == 0)
				{
                                    //**************************************************************************
					Date fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("dt_txt")).toString().split("\"")[1]);
					
                                        int idZona = 1;
                                        
					double temp = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("main").getAsJsonObject().get("temp").toString())) - 272.15f;
					//weather.add(new Measurement(1,currentDate, temp));
					
					//double humidity = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("main").getAsJsonObject().get("humidity").toString()));
					//weather.add(new Measurement(2,currentDate, humidity));
					
                                        String estado = o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("weather").getAsJsonObject().get("main").toString();
                                        
                                        int dirViento = Integer.parseInt((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("wind").getAsJsonObject().get("deg").toString()));
                                        
					double velViento = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("wind").getAsJsonObject().get("speed").toString()));
					//weather.add(new Measurement(6,currentDate, wind));
                                        
                                        double porPrep = Double.parseDouble((o.get("list").getAsJsonArray().get(cont).getAsJsonObject().get("pop").toString()));
					
                                        weather.add(new Tiempo(fecha, temp, velViento, dirViento, porPrep, estado, 1, 1));
                                        //*********************************************************
					cont++;
				}
			}

			is.close(); // Close connection, as it is not used anymore

		} catch (IOException e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		} catch (Exception e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		}
		return weather;
	}
	
	public static String obtainWeatherString(String lat, String lon)
	{
		String weather = "";
		try
		{
			//EN: Obtain data from API in a StringBuffer
			//ES:Obtener información del API en el StringBuffer
			InputStream is = getURLGPS(lat,lon).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			//StringBuffer json = new StringBuffer();
			String inputLine;
			while ((inputLine = br.readLine()) != null)
			{
				weather += inputLine;
			}
			is.close(); // Close connection, as it is not used anymore
		} catch (IOException e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		} catch (Exception e)
		{
			Log.log.error("WeatherObtainer Error: {}", e.toString());
		}
		return weather;
	}
}