package com.info6205.webcrawler.util.Graph;
import org.springframework.stereotype.Component;

import com.info6205.webcrawler.pojo.Url;
import com.info6205.webcrawler.util.ArrayBag.BagInterface;
import com.info6205.webcrawler.util.HashMap.*;

@Component
public class UrlGraph implements UrlGraphInterface{
	 private UrlMap<String, Node> map;
	 private int size;
	 
	  public UrlGraph() {
	        this.map = new UrlMap<>();
			this.size = 0;
	    }
	  
	  @Override
	  public boolean addVertex(Url key) {
	        if (!map.containsKey(key.getPath())) {
	            Node newNode = new Node(key);
	            map.put(key.getPath(), newNode);
				size++;
	            return true;
				
	        }
	        else return false;
	    }
		@Override
		public boolean containsVertex(Url vertex) {
			return map.containsKey(vertex.getPath());
			
	   }
	   @Override
	   public int getCurrentSize(){
		return this.size;
	   }
	   @Override
	    public boolean addEdge(Url source, Url destination) {
	    	 Node sourceNode = map.get(source.getPath());
	    	    if (sourceNode != null) {
	    	        sourceNode.getEdges().addUrl(destination);
	    	        return true;
	    	    }
	    	    return false;
	    }
		@Override
		public boolean containsEdge(Url source, Url destination) {
			Node sourceNode = map.get(source.getPath());
			   if (sourceNode != null && sourceNode.getEdges().contains(destination)) {				   
				   return true;
			   }
			   return false;
	   }
	   @Override
	    public Url[] getEdges(Url vertex) {
			System.out.println("inside get edges");
			System.out.println("method param "+ vertex.getPath());
	        Node n = map.get(vertex.getPath());
			System.out.println("Node n "+ n);
			if(n==null) return null;
			Url[] edges = n.getEdges().toArray();
			
			return edges;
	    }

		
		@Override
		public Url getVertex(Url vertex) {
	        return map.get(vertex.getPath()).getVertex();
	    }
	    
	    @Override
	    public String toString() {
	        StringBuilder sb = new StringBuilder();
			BagInterface<Node> values= map.getValues();
						
			sb.append(values.toString()).append("\n");
	       
	        return sb.toString();
	    }
		@Override
		public void clear(){
			map.clear();
			this.size=0;
		}
    
    
}
