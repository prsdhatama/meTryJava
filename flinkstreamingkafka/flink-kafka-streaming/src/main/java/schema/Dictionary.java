package schema;

import com.ds_inovasi.flink.annotation.JacksonSerializable;
import com.fasterxml.jackson.annotation.JsonProperty;


@JacksonSerializable
public class Dictionary {
    @JsonProperty private String name;
    @JsonProperty private String company;

    public Dictionary(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}