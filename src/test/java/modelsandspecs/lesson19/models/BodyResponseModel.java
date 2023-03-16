package modelsandspecs.lesson19.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BodyResponseModel {

    private int total;
    private int page;

}
