/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlets;

import com.google.gson.Gson;
import db.Tiempo;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import logic.Log;
import logic.Logic;
/**
 *
 * @author 34629
 */
@WebServlet("/GetTiempos")
public class GetTiempos extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public GetTiempos()
    {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Tiempos information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try 
		{

			ArrayList<Tiempo> values =Logic.getTiemposFromDB();
                        Date d = new Date();
                        /*Tiempo t = new Tiempo(d, 8.0, 100.0, 89, 99.0, "Soleado", 1, 1);
                        values.add(t);*/
			String jsonStations = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonStations);
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

/**
 * SERVLET THAR SEARCH ALL THE CITIES STORED IN THE DATABASE
 */

