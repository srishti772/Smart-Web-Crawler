package com.info6205.webcrawler.controller;

import org.springframework.web.bind.annotation.RestController;

import com.info6205.webcrawler.pojo.Url;
import com.info6205.webcrawler.service.WebCrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class WebCrawlerController {

    @Autowired
    WebCrawlerService webCrawlerService;
    
    @PostMapping("/crawl")
    public ResponseEntity<String> getUrl(@RequestParam(value="selectedOption") String option, @RequestParam(value="inputValue") String urlpath) {
    
         webCrawlerService.crawl(new Url(urlpath),option);
       // webCrawlerService.crawl2(new Url("https://www.northeastern.edu/about/about-northeastern/"));
       //  System.out.println(option + urlpath);
        // Return the result with HttpStatus.OK https://www.northeastern.edu/about/about-northeastern/
        return new ResponseEntity<>(webCrawlerService.convertToJson(), HttpStatus.OK);
    }

    @PostMapping("/sort")
    public ResponseEntity<String> sortUrls(@RequestParam(value = "sortOption") String option) {
        webCrawlerService.sort(option);
        String searchHistoryJson = webCrawlerService.getSearchHistoryJson();
        System.out.println(option );
        return new ResponseEntity<>(searchHistoryJson, HttpStatus.OK);
    }
    
}
    

