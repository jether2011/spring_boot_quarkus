package br.com.cassunde.amqp.producer.service;

import br.com.cassunde.amqp.producer.model.Sale;
import br.com.cassunde.amqp.producer.model.Tax;
import br.com.cassunde.amqp.producer.util.QueueManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.Dependent;

@Dependent
public class SaleShipper {

    public void send(Sale sale){

        try {

            Tax tax = new Tax(sale);

            ObjectMapper json = new ObjectMapper();
            String jsonSale = json.writeValueAsString( sale );
            String jsonTax  = json.writeValueAsString(tax);

            QueueManager queueManager = new QueueManager();

            queueManager.send("sales",jsonSale);
            queueManager.send("taxes",jsonTax);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
