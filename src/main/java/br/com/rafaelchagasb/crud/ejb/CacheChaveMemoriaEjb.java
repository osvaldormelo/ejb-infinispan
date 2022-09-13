package br.com.rafaelchagasb.crud.ejb;


import br.com.rafaelchagasb.crud.client.DataGridClient;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Stateless
public class CacheChaveMemoriaEjb {

    RemoteCache<Object, Object> cache;

    @Inject
    private DataGridClient dataGridClient;

    private static final Logger log = Logger.getLogger(DataGridClient.class);

    @PostConstruct
    public void init() {
        this.cache = build();
    }

    public void set(String chave, String conteudo, Long duracao) {
        if(cache != null) {
            Long inicio = System.currentTimeMillis();

            cache.put(chave, conteudo, duracao, TimeUnit.SECONDS);

            Long duracaoLog = System.currentTimeMillis() - inicio;

            log.info("Demorou " + duracaoLog+ " ms para gravar dados no cache");
        }
    }

    public Optional<String> get(String chave) {
        if(cache == null) {
            return Optional.empty();
        }

        Long inicio = System.currentTimeMillis();

        Optional<String> chaveDoCache = Optional.ofNullable((String) cache.get(chave));

        Long duracao = System.currentTimeMillis() - inicio;

        log.info("Demorou "+ duracao + " ms para recuperar dados do cache");

        return chaveDoCache;
    }

    private RemoteCache<Object, Object> build() {
        try {
            log.debug("Iniciando build do cliente do cache em memoria");

            RemoteCacheManager rmc = dataGridClient.getClientCache();
            return rmc.getCache("keys");
        } catch (Exception e){
            log.error("Falha ao recuperar o acesso ao cache no DataGrid", e);
        }
        return null;
    }
}
