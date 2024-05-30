package com.info6205.webcrawler.util.Set;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.info6205.webcrawler.pojo.Url;

@Component
public class UrlSet implements UrlSetInterface{
    private Url[] bag;
    private int numberOfEntries;
    private static final int DEFAULT_CAPACITY = 50;    
    private boolean initialized = false;
    private static final int MAX_CAPACITY = 10000;

    /** Creates an empty bag whose initial capacity is 25. */
    public UrlSet() {
        this(DEFAULT_CAPACITY);
    } // end default constructor

    /**
     * Creates an empty set having a given initial capacity.
     * @param desiredCapacity The integer capacity desired.
     */
    public UrlSet(int desiredCapacity) {
        if (desiredCapacity <= MAX_CAPACITY) {

            // The cast is safe because the new array contains null entries.
            
            bag = new Url[desiredCapacity]; // Unchecked cast
            numberOfEntries = 0;
            initialized = true;
        }
        else
            throw new IllegalStateException("Attempt to create a bag " +
                                            "whose capacity exceeds " +
                                            "allowed maximum.");
    } // end constructor

    @Override
    public int getCurrentSize() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean addUrl(Url newUrl) {
        checkInitialization();
       
       if (getIndexOf(newUrl)==-1){
        if(isFull()){            
			doubleCapacity();		
        }
        bag[numberOfEntries]=newUrl;
        numberOfEntries++;
        return true;
       }
       return false;
    }

    private void doubleCapacity() {
		int newLength = 2 * bag.length;
		checkSize(newLength);
		bag = Arrays.copyOf(bag, newLength);
	}

    
	private void checkSize(int size) {
		if (size > MAX_CAPACITY)
			throw new IllegalStateException(
					"Attempt to create a pile whose " + "capacity exeeds allowed maximum of " + MAX_CAPACITY);
	}


    @Override
    public Url remove() {
        checkInitialization();
        Url result = delete(numberOfEntries - 1);

        return result;
    }

    @Override
    public boolean removeUrl(Url urlToRemove) {
        checkInitialization();
        int index = getIndexOf(urlToRemove);
        Url result = delete(index);
        return urlToRemove.equals(result);
    }

    private Url delete(int givenIndex) {
        Url result = null;
        if (!isEmpty() && (givenIndex >= 0)) {
            result = bag[givenIndex];                   // entry to remove
            bag[givenIndex] = bag[numberOfEntries - 1]; // Replace entry with last entry
            bag[numberOfEntries - 1] = null;            // remove last entry
           numberOfEntries--;
         } // end if
        return result;
    } // end removeEntry

    private int getIndexOf(Url url) {
        int index = -1;
        for(int i=0;i<numberOfEntries;i++){
            Url current= bag[i];
            if(current.getPath().equalsIgnoreCase(url.getPath())){
                index=i;
                break;
            };
        }
        return index;
        
    } // end getIndexOf

    @Override
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    }

    @Override
    public boolean contains(Url urlToFind) {
        checkInitialization();
        return getIndexOf(urlToFind) > -1;
    }

    @Override
    public Url[] toArray() {
       
		Url[] result = new Url[numberOfEntries]; // unchecked cast
		for (int index = 0; index < numberOfEntries; index++) {
			result[index] = bag[index]; // return a copied array of pile
		}
		return result;
    }

    public String toString() {
        String result = "Set[ \n";
        for (int index = 0; index < numberOfEntries; index++) {
            result += bag[index] + "\n ";
        } // end for
        result += "\n]";
        return result;
    } // end toString
    
    private void checkInitialization()
    {
        if (!initialized)
             throw new SecurityException("Set object is not initialized properly.");
    }

    private boolean isFull() {
        return numberOfEntries >= bag.length;
    } // end isArrayFull
}
