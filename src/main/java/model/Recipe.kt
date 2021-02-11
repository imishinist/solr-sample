package model

import org.apache.solr.client.solrj.beans.Field
import java.util.ArrayList

class Recipe(@Field val id: String,
             @Field val name: String,
             val Genre: Genre,
             @Field val ingredients: List<String>,
             @Field val ingredients_amount: List<String>,
             @Field val steps: List<String>,
             @Field var external_info_url: String? = null,
             @Field var external_info_title: String? = null,
) {
    @Field
    val genre: String = this.Genre.toString()
}