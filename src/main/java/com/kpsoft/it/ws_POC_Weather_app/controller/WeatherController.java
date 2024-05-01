package com.kpsoft.it.ws_POC_Weather_app.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kpsoft.it.ws_POC_Weather_app.dto.CityId;
import com.kpsoft.it.ws_POC_Weather_app.service.WeatherService;

@RestController
public class WeatherController {

   @Autowired
   WeatherService weatherService;

   @GetMapping("/country/cities")
   public CompletableFuture<String> countryAndCities() {
      return weatherService.countryAndCities();
   }

   @PostMapping("/cities/highest/temperature")
   public CompletableFuture<Integer> cityIdAndCityNames(@RequestBody CityId idsOfList)
         throws InterruptedException, ExecutionException {
      List<String> list = idsOfList.getIds();
      return weatherService.gettingDetails(list);
   }
}