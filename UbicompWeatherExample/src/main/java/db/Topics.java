package db;

public class Topics 
{
    private String idTopic;
    private String idZona;
    private String idPlaca;
    private String idSensor;
    private String value;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idZona = null;
    	this.idPlaca = null;
    	this.idSensor = null;
    	this.setValue(null);
    }
    public Topics(String idTopic, String idZona, String idPlaca, String idSensor, String value) 
    {
    	this.idTopic = idTopic;
    	this.idZona = idZona;
    	this.idPlaca = idPlaca;
    	this.idSensor = idSensor;
    	this.setValue(value);
    }

	public String getIdTopic() {
		return idTopic;
	}

	public void setIdTopic(String idTopic) {
		this.idTopic = idTopic;
	}

	public String getIdZona() {
		return idZona;
	}

	public void setIdZona(String idZona) {
		this.idZona = idZona;
	}

	public String getIdPlaca() {
		return idPlaca;
	}

	public void setIdPlaca(String idPlaca) {
		this.idPlaca = idPlaca;
	}

	public String getIdSensor() {
		return idSensor;
	}

	public void setIdSensor(String idSensor) {
		this.idSensor = idSensor;
	}

	public String getValue() {
		return value;
	}
        
	public void setValue(String value) {
		this.value = value;
	}
    
 }
