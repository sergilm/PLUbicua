package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import db.ChartMeasurements;
import logic.Log;
import logic.Logic;

/**
 * SERVLET THAR SEARCH ALL THE STATIONS STORED IN THE DATABASE
 */
@WebServlet("/GetMonthStationSensor")
public class GetMonthStationSensor extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetMonthStationSensor() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("--Set new value into the DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
			int stationId = Integer.parseInt(request.getParameter("stationId"));
			int sensorId = Integer.parseInt(request.getParameter("sensorId"));	
			
			ChartMeasurements values =Logic.getMonthStationSensorFromDB(stationId, sensorId);
			
			String jsonStations = new Gson().toJson(values);
			Log.log.info("JSON Sensors Values=> {}", jsonStations);
			out.println(jsonStations);
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
