package com.meeting.checker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.lang.Runnable;

import org.apache.log4j.Logger;

/**
 * @author abhi
 * This class is used for loading CSV file
 */
public class LoadCSVSingleton {
	static final Logger LOGGER = Logger.getLogger(LoadCSVSingleton.class);
	
	private static final  String CSV_FILE = "/dataset.csv";
	
	private static final  String COMMA_SEPERATED = ",";

	private static LoadCSVSingleton instance = null;

	protected LoadCSVSingleton() {
	}

	public static LoadCSVSingleton getInstance() {
		if (instance == null) {
			instance = new LoadCSVSingleton();
		}
		return instance;
	}

	public static List<MeetingBean> loadCsv() throws FileNotFoundException, IOException {

		List<MeetingBean> inputList = new ArrayList<MeetingBean>();

		try {
			InputStream inputFS =  LoadCSVSingleton.getInstance().getClass().getResourceAsStream(CSV_FILE);
					
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

			//Skipping the 1st item of the file as it is a header
			inputList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());

			br.close();

		} catch (NullPointerException nullPointerException) {
			throw new FileNotFoundException("File is not there");
		} catch (IOException ioException) {
			throw new IOException();
		}
		return inputList;
	}

	
	private static Function<String, MeetingBean> mapToItem = (line) -> {

		String[] parts = line.split(COMMA_SEPERATED);

		MeetingBean item = new MeetingBean();

		item.setuId(parts[parts.length - 1]);
		item.setFloor(new Integer(parts[parts.length - 2]));
		item.setyCordinator(new Double(parts[parts.length - 3]));
		item.setxCoordinator(new Double(parts[parts.length - 4]));
		item.setTimeStamp(parts[parts.length - 5]);

		return item;
	};

}
