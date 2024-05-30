package com.info6205.webcrawler.util.Graph;

import com.info6205.webcrawler.pojo.Url;
import com.info6205.webcrawler.util.Set.UrlSet;

public class Node {
    private Url vertex;
    private UrlSet edges;

    public Node() {
        this.vertex = null;
        this.edges = null;
    }

    public Node(Url vertex) {
        this.vertex = vertex;
        this.edges = new UrlSet();
    }

    public Node(Url vertex, UrlSet edges) {
        this.vertex = vertex;
        this.edges = edges;
    }

    public Url getVertex() {
        return this.vertex;
    }

    public void setVertex(Url vertex) {
        this.vertex = vertex;
    }

    public UrlSet getEdges() {
        return this.edges;
    }

    public void setEdges(UrlSet edges) {
        this.edges = edges;
    }


    @Override
    public String toString() {
        return " vertex= " + getVertex() + "\n" +
            ", edges= "+ getEdges()  ;
    }

}
