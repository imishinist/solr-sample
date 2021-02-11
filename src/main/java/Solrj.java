import model.Recipe;
import config.Config;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Solrj {

    private static List<Recipe> getRecipe() {
        Recipe r = new Recipe();
        r.id = "hogehoge1";
        r.name = "回鍋肉";
        r.ingredients.add("きゃべつ");
        r.ingredients_amount.add("半分");
        r.steps.add("hogehoge");
        r.genre = "Chinese";
        r.external_info_url = null;
        r.external_info_title = null;

        return Collections.singletonList(r);
    }

    public static void main(String[] args) throws IOException, SolrServerException {
        Config config = Config.init(System.getenv());

        String solrUrl = String.format("%s/solr/recipe", config.solrServer);
        final ConcurrentUpdateSolrClient solrClient = new ConcurrentUpdateSolrClient.Builder(solrUrl).build();
        try {
            List<Recipe> rs = Solrj.getRecipe();
            UpdateResponse updateResponse = solrClient.addBeans(rs);

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