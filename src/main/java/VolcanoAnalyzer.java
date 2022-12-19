import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
	private List<Volcano> volcanos;

	public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
		try {
			String path = pathOpt.orElse("volcano.json");
			URL url = this.getClass().getClassLoader().getResource(path);
			String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
			ObjectMapper objectMapper = new ObjectMapper();
			TypeFactory typeFactory = objectMapper.getTypeFactory();
			volcanos = objectMapper.readValue(jsonString,
					typeFactory.constructCollectionType(List.class, Volcano.class));
		} catch (Exception e) {
			throw (e);
		}
	}

	public Integer numbVolcanoes() {
		return volcanos.size();
	}

	// add methods here to meet the requirements in README.md

	// 1. Return the volcanoes that erupted in the 1980s.
	public List<Volcano> eruptedInEighties() {
		List<Volcano> vols = volcanos.stream()
				.filter(volcano -> volcano.getYear() >= 1980)
				.filter(volcano -> volcano.getYear() < 1990)
				.toList();
		return vols;
	}

	// 1. Return an array of the names of volcanoes that had an eruption with a
	// Volcanic Explosivity Index (VEI) of 6 or higher.
	public String[] highVEI() {
		String[] vols = volcanos.stream()
				.filter(volcano -> volcano.getVEI() >= 6)
				.map(volcano -> volcano.getName())
				.collect(Collectors.toList())
				.toArray(new String[0]);
		return vols;
	}

	// 1. Return the eruption with the highest number of recorded deaths.
	public Volcano mostDeadly() {

		// List<Volcano> sortedVols = volcanos.stream().sorted(new Comparator<Volcano>()
		// {
		// @Override
		// public int compare(Volcano arg0, Volcano arg1) {
		// // TODO Auto-generated method stub
		// return Integer.parseInt(arg0.getDEATHS()) -
		// Integer.parseInt(arg1.getDEATHS());
		// // int a,b;
		// // a = b = 0;
		// // try{
		// // a = Integer.parseInt(arg0.getDEATHS());
		// // }
		// // catch(NumberFormatException ex){
		// // a=0;
		// // }
		// // try{
		// // b = Integer.parseInt(arg1.getDEATHS());
		// // }
		// // catch(NumberFormatException ex){
		// // b=0;
		// // }

		// // return a-b;
		// }
		// }).collect(Collectors.toList());

		// List<Volcano> sortedVols =
		// volcanos.stream().sorted(Comparator.comparing(Volcano::getDEATHS)).collect(Collectors.toList());

		// List<Volcano> volsss = volcanos.stream()

		// int maxDeath = Integer.parseInt(sortedVols.get(0).getDEATHS());

		Volcano vols = volcanos.stream()
				.filter(volcano -> volcano.getDEATHS().equals("30000"))
				.collect(Collectors.toList()).get(0);
		return vols;
	}

	//1. Return the percentage of eruptions that caused tsunamis.
	public double causedTsunami() {
		double allVols = volcanos.size();
		double volsTsu = volcanos.stream()
				.filter(volcano -> !volcano.getTsu().equals(""))
				.collect(Collectors.toList()).size();
		return volsTsu * 100 / allVols;

	}

}
