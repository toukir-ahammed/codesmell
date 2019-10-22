package smell;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MyMain {
	public static void main(String[] args) {

		String csvFile = "F:\\Program Files\\eclipse\\workspaces\\Code Smell\\files\\result.csv";

		String line = "";

		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

			while ((line = br.readLine()) != null) {

				// use comma as separator
				String[] data = line.split(",");
				records.add(Arrays.asList(data));

			}
			
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		CodeSmell codeSmell = new CodeSmell();
		
		List<List<String>> rows = new ArrayList<List<String>>();

		for (List<String> row : records) {

			if (row.get(0).equals("Project Name"))
				continue;
			
			double CBO = Double.parseDouble(row.get(12));
			double LCOM = Double.parseDouble(row.get(10));
			
			List<String> data = new ArrayList<String>();
			data.add(row.get(0));
			
			if(codeSmell.calculateFeatureEnvy(CBO, LCOM)) {
				data.add("TRUE");
			}else {
				data.add("FALSE");
			}
			
			rows.add(data);	
			
			System.out.println(row.get(0) + " " + CBO + " " + LCOM  + " " + codeSmell.calculateFeatureEnvy(CBO, LCOM));
		}
		
		try {
			FileWriter csvWriter = new FileWriter("F:\\Program Files\\eclipse\\workspaces\\Code Smell\\files\\codesmell.csv");
			csvWriter.append("Project Name");
			csvWriter.append(",");
			csvWriter.append("Feature Envy");
			csvWriter.append("\n");
			
			for (List<String> rowData : rows) {
			    csvWriter.append(String.join(",", rowData));
			    csvWriter.append("\n");
			}
			
			csvWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
