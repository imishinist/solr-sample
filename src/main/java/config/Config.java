package config;

import java.util.Map;

public class Config {
    private final String SOLR_SERVER = "SOLR_SERVER";
    
    public String solrServer;
    private final Map<String, String> envs;

    private Config(Map<String, String> envs) {
        this.envs = envs;

        this.solrServer = envs.get(this.SOLR_SERVER);
    }

    public static Config init(Map<String, String> envs) {
        return new Config(envs);
    }
}
