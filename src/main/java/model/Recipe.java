package model;

import org.apache.solr.client.solrj.beans.Field;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    @Field public String id;
    @Field public String name;
    @Field public List<String> ingredients;
    @Field public List<String> ingredients_amount;
    @Field public List<String> steps;
    @Field public String genre;
    @Field public String external_info_url;
    @Field public String external_info_title;

    public Recipe() {
        this.ingredients = new ArrayList<>();
        this.ingredients_amount = new ArrayList<>();
        this.steps = new ArrayList<>();
    }
}
