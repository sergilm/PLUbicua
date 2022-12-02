package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import db.Measurement;
import db.SensorType;
import logic.Log;
import logic.Logic;

/**
 * SERVLET THAR SEARCH THE WEATHER FORECAST
 */
@WebServlet("/GetWeatherForecast")
public class GetWeatherForecast extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public GetWeatherForecast() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get the Weather Forecast --");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{
			int stationId = Integer.parseInt(request.getParameter("stationId"));	
			Log.log.info("stationId= "+stationId);
			
			String values =Logic.getWeatherForecast(stationId);
			out.println(values);
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
