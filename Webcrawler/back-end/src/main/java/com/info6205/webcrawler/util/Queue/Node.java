package com.info6205.webcrawler.util.Queue;

import com.info6205.webcrawler.pojo.Url;

public class Node {

  private Url url; // Url in Queue
  private Node nextUrl; // Link to next Url

	public Node(Url url)
	{
		this(url, null);	
	} // end constructor
	
	private Node(Url url, Node nextUrl)
	{
		this.url = url;
		this.nextUrl = nextUrl;	
	} // end constructor
    
    public Url getUrl(){
        return this.url;
    }

    
    public void setUrl(Url entry){
        this.url=entry;
    }

    public Node getNextUrl(){
        return this.nextUrl;
    }

    
    public void setNextUrl(Node entry){
        this.nextUrl=entry;
    }


   
    
} // end Node