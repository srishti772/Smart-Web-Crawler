package com.info6205.webcrawler.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.info6205.webcrawler.pojo.Url;
import com.info6205.webcrawler.util.Graph.UrlGraph;
import com.info6205.webcrawler.util.Graph.UrlGraphInterface;
import com.info6205.webcrawler.util.Queue.UrlQueueInterface;
import com.info6205.webcrawler.util.Set.UrlSetInterface;
import com.info6205.webcrawler.util.HashMap.UrlMapInterface;
import com.info6205.webcrawler.util.Sorting.MergeSort;

import java.util.*;

/**
 * Service class for web crawling operations.
 */
@Service
public class WebCrawlerService {

    private UrlQueueInterface urlQueue;
    private UrlSetInterface nSearch;
    private UrlMapInterface<String, Url> visited;
    private UrlGraphInterface graph;
    private int depth = 4;
    private int max_urls = 200;
    private String searchCriteria;

    @Autowired
    public WebCrawlerService(UrlQueueInterface urlQueue, UrlSetInterface nSearch, UrlGraphInterface graph,
            UrlMapInterface<String, Url> visited) {
        this.urlQueue = urlQueue;
        this.nSearch = nSearch;
        this.graph = graph;
        this.visited = visited;
    }

    /**
     * Get the current set of URLs being searched.
     *
     * @return The UrlSetInterface containing the URLs being searched.
     */
    public UrlSetInterface getnSearch() {
        return this.nSearch;
    }

    /**
     * Get the graph representing the URLs and their connections.
     *
     * @return The UrlGraph representing the URLs and their connections.
     */
    public UrlGraphInterface getGraph() {
        return this.graph;
    }

    /**
     * Get the depth limit for crawling.
     *
     * @return The depth limit for crawling.
     */
    public int getDepth() {
        return this.depth;
    }

    /**
     * Set the depth limit for crawling.
     *
     * @param depth The new depth limit to set.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
     * Initiates the web crawling process with a seed URL.
     *
     * @param seedUrl The seed URL to start crawling from.
     * @param option The type of search algorithm to use
     */
    public void crawl(Url seedUrl,String option) {
        searchCriteria=option;
        seedUrl.setLevel(1);
         //empty objects
         nSearch.clear();
         graph.clear();
         visited.clear();
         urlQueue.clear();
         Url.resetCounter();
          System.out.println(nSearch.getCurrentSize());

          System.out.println(graph.getCurrentSize());
          System.out.println(visited.size());
          System.out.println(urlQueue.size());


        
         
        

        switch(option){
            case "DFS" :{               
                
                 crawlDFS(seedUrl);
                break;

            }
            case "BFS" :{
                urlQueue.enqueue(seedUrl);
                visited.put(seedUrl.getPath(), seedUrl);

               crawlBFS();
                break;
            }

         }
       
        
        
       
    }



    /**
     * Performs a depth-first search (DFS) crawling algorithm.
     *
     * @param url The URL to crawl from.
     */
    public void crawlDFS(Url url) {
        if (nSearch.getCurrentSize() > max_urls) {
            return;
        }
        System.out.println(url);

        Document doc = retrieveDocument(url);
        visited.put(url.getPath().toLowerCase(), url);
        nSearch.addUrl(url);
        graph.addVertex(url);

        if (doc != null && url.getStatus() == 200) {
            for (Element link : doc.select("a[href]")) {
                String next_link = link.absUrl("href");

                if (next_link.contains("#") || visited.containsKey(next_link.toLowerCase()) || next_link.startsWith("mailto:")) {//skip anchor links and avoid cycles in graph
                     continue;
                }

                Url next_url = null;

                    if (url.getLevel() + 1 <= depth) {
                        next_url = new Url();
                        next_url.setLevel(url.getLevel() + 1);
                        next_url.setPath(next_link);
                        crawlDFS(next_url);
                    }

                
                if (next_url != null && !url.getPath().equalsIgnoreCase(next_url.getPath())) {
                    graph.addEdge(url, next_url);
                }

            }
        }

    } //end CrawlDFS

    /**
     * Performs a breadth-first search (BFS) crawling algorithm.
     */
    public void crawlBFS() {
        while (!urlQueue.isEmpty()) {
            if (nSearch.getCurrentSize() > max_urls) break;

            Url process = urlQueue.dequeue();
            if (process.getLevel() > depth) {

                continue;
            }

            nSearch.addUrl(process);
            graph.addVertex(process);

            Document doc = retrieveDocument(process);

            if (doc != null && process.getStatus() == 200) {
                for (Element link : doc.select("a[href]")) {
                    String nextLink = link.absUrl("href");

                    if (nextLink.contains("#") || visited.containsKey(nextLink.toLowerCase()) || nextLink.startsWith("mailto:")) {//skip anchor links and avoid cycles in graph
                        continue;
                   }
                    Url nextUrl = null;

                   
                        nextUrl = new Url();
                        nextUrl.setPath(nextLink);
                        nextUrl.setLevel(process.getLevel() + 1);
                        urlQueue.enqueue(nextUrl);
                        visited.put(nextLink.toLowerCase(), nextUrl);
                    

                    if( nextUrl.getLevel()<=depth) graph.addEdge(process, nextUrl); 
                }
            }
        }
    } //endCrawlBFS

    /**
     * Retrieves the HTML document of a given URL.
     *
     * @param url The URL whose document is to be retrieved.
     * @return The Document object representing the HTML document.
     */
    private Document retrieveDocument(Url url) {
        try {
            Connection con = Jsoup.connect(url.getPath());
            Document doc = con.get();
            if (con.response().statusCode() == 200 && doc.title() != null) {
                url.setName(doc.title());
                url.setStatus(con.response().statusCode());
                url.setMessage("success");
            } else {
                url.setStatus(404);
                url.setMessage("Unknown Error");
            }

            return doc;

        } catch (Exception e) {
            url.setMessage(e.getClass().getSimpleName() + "");
            url.setStatus(404);
        }
        return null;
    }

    /**
     * Sorts the URLs in the search set based on path, name, and level.
     */
    public void sort(String option) {

        switch(option){
            case "depth" : {
                Comparator<Url> level = Comparator.comparing(Url::getLevel);
                nSearch = MergeSort.mergeSort(nSearch, level);
        System.out.println("sorted on level");
        System.out.println(nSearch);
        break;

            }
            case "url" : {
                Comparator<Url> path = Comparator.comparing((Url url) -> url.getPath() != null ? url.getPath() : "");
                nSearch = MergeSort.mergeSort(nSearch, path);
                 System.out.println("sorted on path");
                 System.out.println(nSearch);
                 break;
            }

            case "name" : {
                Comparator<Url> name = Comparator.comparing((Url url) -> url.getName() != null ? url.getName() : "");
                nSearch = MergeSort.mergeSort(nSearch, name);
                 System.out.println("sorted on name");
                 System.out.println(nSearch);
                 break;
            }
        }
              
    }

   
    /**
     * Converts the crawled data to JSON format.
     *
     * @return The JSON string representing the crawled data.
     */
    public String convertToJson() {
        JSONObject responseObject = new JSONObject();
        JSONArray edgeArray = new JSONArray();
        JSONArray vertexArray = new JSONArray();
        JSONArray searchArray = getSearchList();
        System.out.println(graph);
        Url[] vertex = nSearch.toArray();
        for (Url v : vertex) {
            JSONObject nodeObject = new JSONObject();
            nodeObject.put("id", v.getId());
            nodeObject.put("label", v.getId());
            nodeObject.put("title", v.getPath());
            vertexArray.put(nodeObject);

            Url[] edges = graph.getEdges(v);

            for (Url e : edges) {
                JSONObject edgeObject = new JSONObject();
                edgeObject.put("from", v.getId());
                edgeObject.put("to", e.getId());
                edgeArray.put(edgeObject);
            }
        }

       

        responseObject.put("nodes", vertexArray);
        responseObject.put("edges", edgeArray);
        responseObject.put("searchHistory", searchArray);
        responseObject.put("searchCriteria", searchCriteria);

       

        return responseObject.toString();
    }

    public String getSearchHistoryJson() {
        return getSearchList().toString();
    }

    public JSONArray getSearchList(){

        JSONArray searchArray = new JSONArray();
        for (Url u : nSearch.toArray()) {
            JSONObject urlObject = new JSONObject();
            urlObject.put("id", u.getId());
            urlObject.put("title", u.getName());
            urlObject.put("path", u.getPath());
            urlObject.put("status", u.getStatus());
            urlObject.put("level", u.getLevel());
            urlObject.put("message", u.getMessage());

            searchArray.put(urlObject);
        }
        return searchArray;
    }


}
