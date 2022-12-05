package logic;

import java.util.ArrayList;

import db.ChartMeasurements;
import db.City;
import db.ConectionDDBB;
import db.SensorType;
import db.Station;
import db.Tiempo;
import db.Topics;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

public class Logic 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @return The list of all the stations stored in the db
	 */
	public static ArrayList<Station> getStationsFromDB()
	{
		ArrayList<Station> stations = new ArrayList<Station>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStations(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Station station = new Station();
				station.setId(rs.getInt("ID"));
				station.setName(rs.getString("NAME"));
				station.setLatitude(rs.getDouble("LATITUDE"));
				station.setLongitude(rs.getDouble("LONGITUDE"));
				stations.add(station);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			stations = new ArrayList<Station>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			stations = new ArrayList<Station>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			stations = new ArrayList<Station>();
		} finally
		{
			conector.closeConnection(con);
		}
		return stations;
	}
	
	/**
	 * 
	 * @return The list of all the stations stored in the db of a city
	 */
	public static ArrayList<Station> getStationsFromCity(int cityId)
	{
		ArrayList<Station> stations = new ArrayList<Station>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationsFromCity(con);
			ps.setInt(1, cityId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Station station = new Station();
				station.setId(rs.getInt("ID"));
				station.setName(rs.getString("NAME"));
				station.setLatitude(rs.getDouble("LATITUDE"));
				station.setLongitude(rs.getDouble("LONGITUDE"));
				stations.add(station);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			stations = new ArrayList<Station>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			stations = new ArrayList<Station>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			stations = new ArrayList<Station>();
		} finally
		{
			conector.closeConnection(con);
		}
		return stations;
	}
	
	/**
	 * 
	 * @return The list of all the cities stored in the db
	 */
	public static ArrayList<City> getCitiesFromDB()
	{
		ArrayList<City> cities = new ArrayList<City>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetCities(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				City city = new City();
				city.setId(rs.getInt("ID"));
				city.setName(rs.getString("NAME"));
				cities.add(city);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			cities = new ArrayList<City>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			cities = new ArrayList<City>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			cities = new ArrayList<City>();
		} finally
		{
			conector.closeConnection(con);
		}
		return cities;
	}
	
	/**
	 * 
	 * @param idStation ID of the station to search
	 * @return The list of sensors of a Installation
	 */
	public static ArrayList<SensorType> getStationSensorsFromDB(int idStation)
	{
		ArrayList<SensorType> sensors = new ArrayList<SensorType>();	
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensors(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				SensorType sensor = new SensorType();
				sensor.setId(rs.getInt("ID"));
				sensor.setName(rs.getString("NAME"));
				sensor.setUnits(rs.getString("UNITS"));
				if(rs.getInt("STATION_ID")>0)
				{
					sensor.setAvailable(1);
					//EN:Search the last value of the station
					PreparedStatement ps_value = ConectionDDBB.GetLastValueStationSensor(con);
					ps_value.setInt(1, idStation);
					ps_value.setInt(2, rs.getInt("ID"));
					Log.log.info("Query=> {}", ps_value.toString());
					ResultSet rs_value = ps_value.executeQuery();
					if (rs_value.next())
					{
						sensor.setValue(rs_value.getInt("VALUE"));
						if(sensor.getValue()<rs.getInt("MINVALUE"))
						{
							sensor.setLabel(0); //Low value
						}else
						{
							if(sensor.getValue()>rs.getInt("MAXIVALUE"))
							{
								sensor.setLabel(2); //High value
							}else
							{
								sensor.setLabel(1);	 //Medium value
							}
						}
					}
				}else
				{
					sensor.setAvailable(0);
					//TODO buscar la media de la ciudad para dar un valor aproximado
				}
				sensors.add(sensor);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			sensors = new ArrayList<SensorType>();
		} finally
		{
			conector.closeConnection(con);
		}
		return sensors;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getLastWeekStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementLastDays(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			ps.setInt(3, 7); //ES:Number of Days to search //ES:Número de días a buscar
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("dayofweek")) {
					case 1:
						measurement.getLabels().add("Sunday " + rs.getString("date"));
					break;
					case 2:
						measurement.getLabels().add("Monday " + rs.getString("date"));
					break;
					case 3:
						measurement.getLabels().add("Tuesday " + rs.getString("date"));
					break;
					case 4:
						measurement.getLabels().add("Wednesday " + rs.getString("date"));
					break;
					case 5:
						measurement.getLabels().add("Thursday " + rs.getString("date"));
					break;
					case 6:
						measurement.getLabels().add("Friday " + rs.getString("date"));;
					break;
					case 7:
						measurement.getLabels().add("Saturday " + rs.getString("date"));
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getMonthStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementMonth(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("date")) {
					case 1:
						measurement.getLabels().add("Jan");
					break;
					case 2:
						measurement.getLabels().add("Feb");
					break;
					case 3:
						measurement.getLabels().add("Mar");
					break;
					case 4:
						measurement.getLabels().add("Apr");
					break;
					case 5:
						measurement.getLabels().add("May");
					break;
					case 6:
						measurement.getLabels().add("Jun");
					break;
					case 7:
						measurement.getLabels().add("Jul");
					break;
					case 8:
						measurement.getLabels().add("Aug");
					break;
					case 9:
						measurement.getLabels().add("Sep");
					break;
					case 10:
						measurement.getLabels().add("Oct");
					break;
					case 11:
						measurement.getLabels().add("Nov");
					break;
					case 12:
						measurement.getLabels().add("Dec");
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 	
	 * @param idStation
	 * @return Arraylist with the measurements
	 */
	public static String getWeatherForecast(int idStation)
	{
		String forecast  = "";
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetInfoFromStation(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				String lat=rs.getString("LATITUDE");
				String lon=rs.getString("LONGITUDE");
				
				forecast = ThreadWeatherForecast.obtainWeatherString(lat, lon); 
				
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			forecast  = "";
		} finally
		{
			conector.closeConnection(con);
		}
		return forecast;
	}
	
/*	
	public static void storeNewMeasurement(Topics newTopic)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertnewMeasurement(con);
			ps.setString(1, newTopic.getIdStation());
			ps.setString(2, newTopic.getIdSensor());
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ps.setString(3, sdf.format(timestamp));
			ps.setString(4, newTopic.getValue());
			ps.setString(5, newTopic.getIdStation());
			ps.setString(6, newTopic.getIdSensor());
			ps.setString(7, sdf.format(timestamp));
			ps.setString(8, newTopic.getValue());
			Log.log.info("Query to store Measurement=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
		} finally
		{
			conector.closeConnection(con);
		}
	}
*/	
        
        
        public static ArrayList<Tiempo> getTiemposFromDB()
	{
		ArrayList<Tiempo> tiempos = new ArrayList<Tiempo>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
                        //USER
                        PreparedStatement ps5 = ConectionDDBB.InsertUser(con);
                        //Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
                        ps5.setString(1, "hola@gmail.com");
                        ps5.setString(2, "contraseña");
                        ps5.setString(3, "Raul");
                        ps5.setInt(4, 1);
                        ps5.setString(5, "hola@gmail.com");
                        ps5.setString(6, "contraseña");
                        ps5.setString(7, "Raul");
                        ps5.setInt(8, 1);
                        ps5.executeUpdate();
                        
                        
                        //PLACA
                        PreparedStatement ps3 = ConectionDDBB.InsertPlaca(con);
                        //Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
                        ps3.setInt(1, 4);
                        ps3.setString(2, "Madrid");
                        ps3.setInt(3, 90);
                        ps3.setInt(4,1);
                        ps3.setInt(5, 4);
                        ps3.setString(6, "Madrid");
                        ps3.setInt(7, 90);
                        ps3.setInt(8,1);

                        ps3.executeUpdate();
                        
                        //ZONA
                        PreparedStatement ps4 = ConectionDDBB.InsertZona(con);
                        //Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
                        ps4.setInt(1, 4);
                        ps4.setString(2, "Madrid");
                        ps4.setInt(3, 4);
                        ps4.setString(4, "Madrid");
                        
                        ps4.executeUpdate();
                        
                        
                        //TIEMPO
                        String withTime = "2014-01-01 12:30";
                        DateTimeFormatter formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime january1st2014WithTime = LocalDateTime.parse(withTime, formatterWithTime);

                        //Tiempo t = new Tiempo(d, 8.0, 100.0, 89, 99.0, "Soleado", 1, 1);
                        
                        PreparedStatement ps2 = ConectionDDBB.InsertTiempo(con);
                        //Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
                        ps2.setDate(1, Date.valueOf(january1st2014WithTime.toLocalDate()));
                        ps2.setInt(2, 4);
                        ps2.setDouble(3, 90.0);
                        ps2.setDouble(4, 90.0);
                        ps2.setInt(5, 90);
                        ps2.setDouble(6, 90.0);
                        ps2.setString(7,"Lluvioso");
                        ps2.setInt(8, 4);
                        ps2.setDate(9, Date.valueOf(january1st2014WithTime.toLocalDate()));
                        ps2.setInt(10, 4);
                        ps2.setDouble(11, 90.0);
                        ps2.setDouble(12, 90.0);
                        ps2.setInt(13, 90);
                        ps2.setDouble(14,90.0);
                        ps2.setString(15,"Lluvioso");
                        ps2.setInt(16, 4);
                        ps2.executeUpdate();
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        
			PreparedStatement ps = ConectionDDBB.GetTiempos(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Tiempo tiempo = new Tiempo();
				tiempo.setIdPlaca(rs.getInt("IDPLACA"));
				tiempo.setEstado(rs.getString("ESTADO"));
                                tiempo.setPorcPrecipitacion(rs.getDouble("PORCPRECIPITACION"));
                                tiempo.setDirViento(rs.getInt("DIRVIENTO"));
                                tiempo.setVelViento(rs.getDouble("VELVIENTO"));
                                tiempo.setTemperatura(rs.getDouble("TEMPERATURA"));
                                tiempo.setFecha(rs.getDate("FECHA"));
                                tiempo.setIdZona(rs.getInt("IDZONA"));
				tiempos.add(tiempo);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			tiempos = new ArrayList<Tiempo>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			tiempos = new ArrayList<Tiempo>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			tiempos = new ArrayList<Tiempo>();
		} finally
		{
			conector.closeConnection(con);
		}
		return tiempos;
	}
        
        public static void insertTiemposFromDB()
	{
		ArrayList<Tiempo> tiempos = new ArrayList<Tiempo>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
                       String withTime = "2014-01-01 12:30";
                        DateTimeFormatter formatterWithTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime january1st2014WithTime = LocalDateTime.parse(withTime, formatterWithTime);

                        //Tiempo t = new Tiempo(d, 8.0, 100.0, 89, 99.0, "Soleado", 1, 1);
                        
                        PreparedStatement ps = ConectionDDBB.InsertTiempo(con);
                        //Timestamp ts = new Timestamp(weatherValues.get(i).getFecha().getTime());
                        ps.setDate(1, Date.valueOf(january1st2014WithTime.toLocalDate()));
                        ps.setInt(2, 8);
                        ps.setDouble(3, 8.0);
                        ps.setDouble(4, 99.0);
                        ps.setInt(5, 86);
                        ps.setDouble(6, 99.0);
                        ps.setString(7,"Soleado");
                        ps.setInt(8, 1);
                        ps.setDate(9, Date.valueOf(january1st2014WithTime.toLocalDate()));
                        ps.setInt(10, 8);
                        ps.setDouble(11, 8.0);
                        ps.setDouble(12, 99.0);
                        ps.setInt(13, 86);
                        ps.setDouble(14,99.0);
                        ps.setString(15,"Soleado");
                        ps.setInt(16, 1);
                        ps.executeUpdate();
                        
			PreparedStatement ps2 = ConectionDDBB.GetTiempos(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Tiempo tiempo = new Tiempo();
				tiempo.setIdPlaca(rs.getInt("IDPLACA"));
				tiempo.setEstado(rs.getString("ESTADO"));
                                tiempo.setPorcPrecipitacion(rs.getDouble("PORCPRECIPITACION"));
                                tiempo.setDirViento(rs.getInt("DIRVIENTO"));
                                tiempo.setVelViento(rs.getDouble("VELVIENTO"));
                                tiempo.setTemperatura(rs.getDouble("TEMPERATURA"));
                                tiempo.setFecha(rs.getDate("FECHA"));
                                tiempo.setIdZona(rs.getInt("IDZONA"));
				tiempos.add(tiempo);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			tiempos = new ArrayList<Tiempo>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			tiempos = new ArrayList<Tiempo>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			tiempos = new ArrayList<Tiempo>();
		} finally
		{
			conector.closeConnection(con);
		}
		
	}
	
	
}
