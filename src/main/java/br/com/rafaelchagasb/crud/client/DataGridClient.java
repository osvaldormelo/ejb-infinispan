package br.com.rafaelchagasb.crud.client;

import org.infinispan.client.hotrod.RemoteCacheManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.jboss.logging.Logger;

@Stateless
public class DataGridClient {

    private static final int PORTA_DEFAULT = 11222;

    RemoteCacheManager rmc;

    private static final Logger log = Logger.getLogger(DataGridClient.class);

    @PostConstruct
    public void init()  {


        try {
            org.infinispan.client.hotrod.configuration.ConfigurationBuilder cb
                    = new org.infinispan.client.hotrod.configuration.ConfigurationBuilder();
            cb.marshaller(new org.infinispan.commons.marshall.ProtoStreamMarshaller())
                    .statistics()
                    .enable()
                    .addServer()
                    .host("localhost")
                    .port(11222) //11222
                    .security()
                    .authentication()
                    .username("admin")
                    .password("changeme");
            this.rmc = new RemoteCacheManager(cb.build(), true);
        } catch(Exception e) {
            log.error("Falha ao estabelecer conexão com o DataGrid", e);
        }
    }

    @PreDestroy
    public void destroy() {
        if(this.rmc != null) {
            this.rmc.stop();
        }
    }

    private Integer getPorta() {
        try{
            return 11222;
        } catch(Exception e){
            return PORTA_DEFAULT;
        }
    }

    public RemoteCacheManager getClientCache() {
        return this.rmc;
    }
}
