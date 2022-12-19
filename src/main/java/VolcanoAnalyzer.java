import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){
        return volcanos.size();
    }

    //add methods here to meet the requirements in README.md

    //1. Return the volcanoes that erupted in the 1980s.
    public List<Volcano> eruptedInEighties(){
        List<Volcano> vols = volcanos.stream()
        .filter(volcano -> volcano.getYear() >= 1980)
        .filter(volcano -> volcano.getYear() < 1990)
        .toList();
        return vols;
    }

    //1. Return an array of the names of volcanoes that had an eruption with a Volcanic Explosivity Index (VEI) of 6 or higher.
    public String[] highVEI(){
        String[] vols = volcanos.stream()
        .filter(volcano -> volcano.getVEI() >= 6)
        .map(volcano -> volcano.getName())
        .collect(Collectors.toList())
        .toArray(new String[0]);
        return vols;
    }



}
