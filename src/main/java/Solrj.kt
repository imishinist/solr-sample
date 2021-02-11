import config.Config
import model.Recipe
import kotlin.Throws
import java.io.IOException
import org.apache.solr.client.solrj.SolrServerException
import kotlin.jvm.JvmStatic
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient
import java.util.ArrayList

object Solrj {
    private val recipe: List<Recipe?>
        private get() {
            return listOf(
                    Recipe(
                            "hogehoge1",
                            "回鍋肉",
                            "Chinese",
                            listOf("きゃべつ"),
                            listOf("半分"),
                            listOf("きゃべつを切る。"),
                    ),
                    Recipe(
                            "hogehoge2",
                            "カレー",
                            "Japanese",
                            listOf("たまねぎ", "豚肉"),
                            listOf("半分", "200g"),
                            listOf("たまねぎを炒める"),
                            "https://www.youtube.com/watch?v=BcKNwo7bzJ4",
                            "我が家のカレーがこれになってしまったと何度も言われたほどウマい、市販のルーで作る【至高のカレー】"
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
            val rs = recipe
            var updateResponse = solrClient.addBeans(rs)
            if (updateResponse.status == 0) {
                updateResponse = solrClient.commit()
                System.out.printf("%s %s", updateResponse.status, updateResponse.response)
            }
        } catch (e: SolrServerException) {
            e.printStackTrace()
            solrClient.rollback()
        } finally {
            solrClient.close()
        }
    }
}