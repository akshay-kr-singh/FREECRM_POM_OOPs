package com.crm.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.crm.qa.exceptions.DataFileNotFoundException;
import com.crm.qa.exceptions.DataParameterNotFoundException;
import com.crm.qa.exceptions.EnvironmentParametersNotFoundException;
import com.crm.qa.exceptions.NoSuchDataException;

public class DataTable {
	private static final Logger LOGGER = LogManager.getLogger(DataTable.class);

	private LinkedHashMap<String, String> dataSet;

	public DataTable(LinkedHashMap<String, String> dataSet) {
		this.dataSet = dataSet;
	}

	public DataTable() {
		dataSet = new LinkedHashMap<String, String>();
	}

	/**
	 * Get the data set Hash Map
	 * 
	 * @return DataSet
	 */
	public LinkedHashMap<String, String> getDataSet() {
		return dataSet;
	}

	/**
	 * Used to generate the time for backing up values in DataTable when it is going
	 * to be overwritten
	 * 
	 * @return Time as String in hhmmsss format
	 */
	private String generateTimeStampOfChange() {
		return new SimpleDateFormat("hhmmsss").format(new Date());
	}

	/**
	 * This method returns the value of the parameter specified in the particular
	 * dataTable instance
	 * 
	 * @param parameter The name of the value to be fetched
	 * @return value for the dataTable parameter
	 */
	public String getData(String parameter) {
		parameter = parameter.toUpperCase();
		if (dataSet.get(parameter) == null) {
			LOGGER.fatal("No DataTable found");
			throw new NoSuchDataException(parameter);
		}
		return dataSet.get(parameter);
	}

	/**
	 * This method sets the value of the parameter specified to the dataTable
	 * instance
	 * 
	 * @param parameter The Parameter whose value is to be set
	 * @param value     The value of the parameter to be set
	 */
	public void setData(String parameter, String value) {
		parameter = parameter.toUpperCase();
		if (dataSet.containsKey(parameter)) {
			String newParameter = parameter + "_" + generateTimeStampOfChange();
			dataSet.put(newParameter, dataSet.get(parameter));
			LOGGER.info("DataTable Value Backed up --> Old Parameter : " + parameter + " | New Parameter: "
					+ newParameter + " | Value: " + value);
			dataSet.put(parameter, value);
			LOGGER.info("DataTable Value Overwritten -- > Paremeter: " + parameter + " | Value: " + value);
		} else {
			LOGGER.fatal("Data Parameter, " + parameter + " not found");
			throw new DataParameterNotFoundException(parameter);
		}
	}

	/**
	 * This method adds a new parameter to the dataTable instance with an empty
	 * value
	 * 
	 * @param parameter Name of the parameter to be added to the dataTable
	 */
	public void addRuntimeParameter(String parameter) {
		parameter = parameter.toUpperCase();
		if (!dataSet.containsKey(parameter.toUpperCase())) {
			dataSet.put(parameter, "");
			LOGGER.info("New run time parameter set - " + parameter);
		}
	}

	/**
	 * This method reads the credentials XML file for a particular environment and
	 * adds it to the dataTable. This is similar to
	 * <code>public void readEnvironmentParameters(String environment, String location)</code>
	 * but a default location is looked up for the environment parameter file.<br/>
	 * Default location :
	 * <i>src/main/Resources/EnvironmentParameters/envParameters.xml</i>
	 * 
	 * 
	 * @param environment Name of the environment whose credentials needs to be
	 *                    fetched
	 */
	public void readEnvironmentParameters(String environment) {
		readEnvironmentParameters(environment, "src/main/Resources/EnvironmentParameters/envParameters.xml");
	}

	/**
	 * This method reads the credentials XML file for a particular environment and
	 * adds it to the dataTable
	 * 
	 * @param environment Name of the environment whose credentials needs to be
	 *                    fetched
	 * @param location    Location of the environment parameter file
	 */
	public void readEnvironmentParameters(String environment, String location) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = null;

			try {
				document = dBuilder.parse(new File(location));
				LOGGER.info("Environment Parameter XML read at " + location);
			} catch (IOException e) {
				location = "src/test/resources/EnvironmentParameters/envParameters.xml";
				document = dBuilder.parse(new File(location));
				LOGGER.info("Environment Parameter XML read at " + location);
			}

			document.getDocumentElement().normalize();
			NodeList environmentNodeList = document.getElementsByTagName("environment");

			boolean isFound = false;
			for (int count = 0; count < environmentNodeList.getLength(); count++) {
				Node environmentNode = environmentNodeList.item(count);

				if (environmentNode.getNodeType() == Node.ELEMENT_NODE) {
					Element environmentElement = (Element) environmentNode;
					if (environmentElement.getAttribute("id").equalsIgnoreCase(environment)) {
						NodeList parameterNodeList = environmentElement.getChildNodes();
						for (int parameterCount = 0; parameterCount < parameterNodeList.getLength(); parameterCount++) {
							Node parameterNode = parameterNodeList.item(parameterCount);
							if (parameterNode.getNodeType() == Node.ELEMENT_NODE) {
								dataSet.put(parameterNode.getNodeName().toUpperCase(), parameterNode.getTextContent());
							}
						}
						isFound = true;
						break;
					}
				}
			}

			if (!isFound) {
				LOGGER.fatal("Environment parameters not found for Environment, " + environment);
				throw new EnvironmentParametersNotFoundException(environment);
			}
		} catch (ParserConfigurationException e) {
			LOGGER.fatal("Error Reading Environment Parameter Sheet at " + location, e);
			throw new EnvironmentParametersNotFoundException(environment);
		} catch (SAXException e) {
			LOGGER.fatal("Error Reading Environment Parameter Sheet at " + location, e);
			throw new EnvironmentParametersNotFoundException(environment);
		} catch (IOException e) {
			LOGGER.fatal("Error Reading Environment Parameter Sheet at " + location, e);
			throw new DataFileNotFoundException(location);
		}
	}

}
