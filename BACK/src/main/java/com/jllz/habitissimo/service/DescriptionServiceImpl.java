package com.jllz.habitissimo.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DescriptionServiceImpl implements DescriptionService {

    @Override
    public List<String> getCategoryDescription(String description) {
        List<String> category = new ArrayList<>();

        if(description.contains("gas") || description.contains("termo") || description.contains("calefaccion") ||
            description.contains("caldera") || description.contains("estufa"))
            category.add("Calefacción");
        if(description.contains("cocina") || description.contains("campana") || description.contains("encimera"))
            category.add("Reforma cocinas");
        if(description.contains("mampara") || description.contains("bañ") || description.contains("bano") || description.contains("ducha"))
            category.add("Reforma baños");
        if(description.contains("aire") || description.contains("calor") || description.contains("Aire"))
            category.add("Aire acondicionado");
        if(description.contains("constr") || description.contains("terreno") || description.contains("Cons"))
            category.add("Construcción casas");
        if(description.contains("piscina"))
            category.add("Construcción piscinas");

        return category;
    }
}
