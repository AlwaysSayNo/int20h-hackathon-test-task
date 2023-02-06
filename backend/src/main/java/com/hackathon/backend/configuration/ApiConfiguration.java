package com.hackathon.backend.configuration;

import com.hackathon.backend.enumeration.Dictionary;
import com.hackathon.backend.enumeration.ProductCategory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.hackathon.backend.enumeration.ProductCategory.*;

@Configuration
@ComponentScan("com.hackathon.backend")
public class ApiConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Map<ProductCategory, String> productCategoryData() {
        var productCategoryData = new HashMap<ProductCategory, String>();
        productCategoryData.put(BREAD, Dictionary.BREAD_PATH.getPath());
        productCategoryData.put(MEAT, Dictionary.MEAT_PATH.getPath());
        productCategoryData.put(FISH, Dictionary.FISH_PATH.getPath());
        productCategoryData.put(CHEESE, Dictionary.CHEESE_PATH.getPath());
        productCategoryData.put(DAIRY, Dictionary.DAIRY_PATH.getPath());
        productCategoryData.put(FRUIT, Dictionary.FRUIT_PATH.getPath());
        productCategoryData.put(GRAIN, Dictionary.GRAIN_PATH.getPath());
        productCategoryData.put(OIL, Dictionary.OIL_PATH.getPath());
        productCategoryData.put(SEA_FOOD, Dictionary.SEA_FOOD_PATH.getPath());
        productCategoryData.put(SEASONING, Dictionary.SEASONING_PATH.getPath());
        productCategoryData.put(VEGETABLE, Dictionary.VEGETABLE_PATH.getPath());
        productCategoryData.put(SAUCE, Dictionary.SAUCE_PATH.getPath());
        productCategoryData.put(ALCOHOL, Dictionary.ALCOHOL_PATH.getPath());

        return productCategoryData;
    }

}
