package config

class Config(envs: Map<String, String>) {
    val solrServer: String = envs[Companion.SOLR_SERVER].toString()

    companion object {
        private const val SOLR_SERVER = "SOLR_SERVER"
    }
}