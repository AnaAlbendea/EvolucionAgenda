package agenda.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class Config {

	private static DataSource ds; //metodo singlestone
	private static Properties prop;
	
	private Config() { //constructor
		
	}
	//Un Datasource en Java es un objeto que representa una conexión a una base de datos o cualquier otro origen de datos.
	//Proporciona una interfaz para establecer y obtener conexiones a la base de datos de forma independiente.
	public static DataSource getDataSource() { //Este código es una implementación del patrón de diseño Factory Method. 
		//El método getDataSource() es un método que fábrica y retorna un objeto DataSource.
		//Este código verifica si el objeto ds es nulo. Si lo es, crea un nuevo objeto BasicDataSource y configura su driver, URL, nombre de usuario y contraseña utilizando propiedades obtenidas de un archivo de configuración. 
		//Luego, asigna el objeto bds a la variable ds.
		//Finalmente, retorna el objeto ds. Si ds ya fue creado previamente, simplemente lo retorna en lugar de crear uno nuevo.
		if(ds==null) {
			BasicDataSource bds=new BasicDataSource();
			bds.setDriverClassName(getProp().getProperty("bbdd.driver"));
			bds.setUrl(getProp().getProperty("bbdd.url"));
			bds.setUsername(getProp().getProperty("bbdd.user"));
			bds.setPassword(getProp().getProperty("bbdd.pass"));
			ds=bds;
		}
		return ds;
	}
	public static Properties getProp()  { //creamos otro singletone para  el fichero properties
		if(prop==null) { //la clase properties es un map
			prop=new Properties();
			try (FileReader fr=new FileReader("agenda.properties")) { //abre el fichero modo lectura
				prop.load(fr); //metemos el archivo properties				
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Problema con el fichero properties"); //si no hay fichero se lanza el mensaje con la exception uncatche
			}
		}
		return prop;
		
	}
}
