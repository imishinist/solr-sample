import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import config.Config;

class Recipe {
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

public class Solrj {
    public static void main(String[] args) throws IOException, SolrServerException {
        Config config = Config.init(System.getenv());

        Recipe r = new Recipe();
        r.id = "hogehoge1";
        r.name = "回鍋肉";
        r.ingredients.add("きゃべつ");
        r.ingredients_amount.add("半分");
        r.steps.add("hogehoge");
        r.genre = "Chinese";
        r.external_info_url = null;
        r.external_info_title = null;

        String solrUrl = String.format("%s/solr/recipe", config.solrServer);
        final SolrClient solrClient = new HttpSolrClient.Builder(solrUrl).build();
        try {
            UpdateResponse updateResponse = solrClient.addBean(r);

            if (updateResponse.getStatus() == 0) {
                updateResponse = solrClient.commit();
                System.out.printf("%s %s", updateResponse.getStatus(), updateResponse.getResponse());
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
            solrClient.rollback();
        } finally {
            solrClient.close();
        }
    }
}