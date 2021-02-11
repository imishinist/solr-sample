import config.Config
import model.Genre
import model.Recipe
import kotlin.Throws
import java.io.IOException
import org.apache.solr.client.solrj.SolrServerException
import kotlin.jvm.JvmStatic
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient
import java.util.ArrayList

object Solrj {
    private fun getRecipe(): List<Recipe> {

        return listOf(
                Recipe(
                        id = "hogehoge1",
                        name = "回鍋肉",
                        Genre = Genre.Chinese,
                        ingredients = listOf("きゃべつ"),
                        ingredients_amount = listOf("半分"),
                        steps = listOf("きゃべつを切る。"),
                ),
                Recipe(
                        id = "hogehoge2",
                        name = "カレー",
                        Genre = Genre.Japanese,
                        ingredients = listOf("たまねぎ", "豚肉"),
                        ingredients_amount = listOf("半分", "200g"),
                        steps = listOf("たまねぎを炒める"),
                        external_info_url = "https://www.youtube.com/watch?v=BcKNwo7bzJ4",
                        external_info_title = "我が家のカレーがこれになってしまったと何度も言われたほどウマい、市販のルーで作る【至高のカレー】"
                )
        )
    }

    @Throws(IOException::class, SolrServerException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val config = Config(System.getenv())
        val solrUrl = String.format("%s/solr/recipe", config.solrServer)
        val solrClient = ConcurrentUpdateSolrClient.Builder(solrUrl).build()
        try {
            var updateResponse = solrClient.addBeans(getRecipe())
            if (updateResponse.status == 0) {
                updateResponse = solrClient.commit()
                System.out.printf("%s %s", updateResponse.status, updateResponse.response)
            }
            solrClient.commit()
        } catch (e: SolrServerException) {
            e.printStackTrace()
            solrClient.rollback()
        } finally {
            solrClient.close()
        }
    }
}