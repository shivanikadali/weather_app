package com.kpsoft.it.ws_POC_Weather_app.service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class WeatherService {

        int maxTemp;
        HashMap<String, Integer> idAndTemp = new HashMap<>();

        Logger logger = Logger.getLogger("log");

        // 1st endpoint
        @Async
        public CompletableFuture<String> countryAndCities() {
                String uri = "https://worldweather.wmo.int/en/json/full_city_list.txt";
                WebClient webClient = WebClient.create();
                Mono<String> countryDetails = webClient.get()
                                .uri(uri)
                                .retrieve()
                                .bodyToMono(String.class);
                log.info("Thread name   " + Thread.currentThread().getName() + "--------------------");
                // converting from mono<String> to string
                return CompletableFuture.completedFuture(countryDetails.block());
        }

        //2nd EndPoint for finding highest temperature
        @Async
        public CompletableFuture<Integer> gettingDetails(List<String> ids)
                        throws InterruptedException, ExecutionException {
                long start = System.currentTimeMillis();
                String uri = null;
                int tempFromCity = 0;
                for (String id : ids) {
                        uri = "https://worldweather.wmo.int/en/json/" + id + "_en.json";
                        CompletableFuture<Integer> highestTempOfCity = clientCall(uri,id);
                        tempFromCity = highestTempOfCity.get();
                        idAndTemp.put(id, tempFromCity);
                        if (maxTemp < idAndTemp.get(id)) {
                                maxTemp = idAndTemp.get(id);
                        }
                }
                long end = System.currentTimeMillis();
                long time = start - end;
                System.out.println("---------------time taken to complete the method--------------" + time);
                // return maxTemp;
                return CompletableFuture.completedFuture(maxTemp);
        }

        //getting temperature of each city
        @Async
        public CompletableFuture<Integer> clientCall(String uri,String id) {
                WebClient webClient = WebClient.create();
                Mono<String> countryDetails = webClient.get()
                                .uri(uri)
                                .retrieve()
                                .bodyToMono(String.class);

                int temperature = 0;
                // converting from mono<String> to string
                String result = countryDetails.block();
                if (result.contains("maxTemp")) {
                        int start = result.indexOf("maxTemp") + 10;
                        int last = start + 2;
                        String temp = result.substring(start, last);
                        temperature = Integer.parseInt(temp);
                        System.out.println("Temperature of the city of id "+id+ " is " + temperature);
                }
                log.info("---------Thread name---------" + Thread.currentThread().getName());
                return CompletableFuture.completedFuture(temperature);
        }
}