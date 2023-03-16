package modelsandspecs.lesson19.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ResponseModel {

    String name, job, page;
}
