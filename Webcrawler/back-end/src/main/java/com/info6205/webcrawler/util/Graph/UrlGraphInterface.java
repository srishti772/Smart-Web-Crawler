package com.info6205.webcrawler.util.Graph;

import com.info6205.webcrawler.pojo.Url;

public interface UrlGraphInterface {
    /**
     * Adds a new vertex to the graph.
     *
     * @param key The URL representing the vertex to be added.
     * @return True if the addition is successful, false otherwise.
     */
    boolean addVertex(Url key);

    /**
     * Checks if the graph contains a specified vertex.
     *
     * @param vertex The URL representing the vertex to be checked.
     * @return True if the graph contains the vertex, false otherwise.
     */
    boolean containsVertex(Url vertex);

    /**
     * Gets the current number of vertices in the graph.
     *
     * @return The integer number of vertices in the graph.
     */
    int getCurrentSize();

    /**
     * Adds an edge between two vertices in the graph.
     *
     * @param source      The URL representing the source vertex of the edge.
     * @param destination The URL representing the destination vertex of the edge.
     * @return True if the edge addition is successful, false otherwise.
     */
    boolean addEdge(Url source, Url destination);

    /**
     * Checks if the graph contains an edge between two specified vertices.
     *
     * @param source      The URL representing the source vertex of the edge.
     * @param destination The URL representing the destination vertex of the edge.
     * @return True if the graph contains the edge, false otherwise.
     */
    boolean containsEdge(Url source, Url destination);

    /**
     * Gets the edges of a specified vertex in the graph.
     *
     * @param vertex The URL representing the vertex to get the edges for.
     * @return An array of URLs representing the edges of the specified vertex.
     */
    Url[] getEdges(Url vertex);

    /**
     * Gets the specified vertex from the graph.
     *
     * @param vertex The URL representing the vertex to be retrieved.
     * @return The URL vertex if found, null otherwise.
     */
    Url getVertex(Url vertex);

    /**
     * Clears the graph by removing all vertices and edges.
     */
    void clear();
}
