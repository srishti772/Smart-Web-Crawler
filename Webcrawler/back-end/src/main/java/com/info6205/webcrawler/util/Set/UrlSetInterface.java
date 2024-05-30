package com.info6205.webcrawler.util.Set;

import com.info6205.webcrawler.pojo.Url;

public interface UrlSetInterface {
    
    /**
     * Gets the current number of URLs in this set.
     *
     * @return The integer number of URLs currently in the set.
     */
    public int getCurrentSize();

    /**
     * Checks whether this set is empty.
     *
     * @return True if the set is empty, or false if not.
     */
    public boolean isEmpty();

    /**
     * Adds a new URL to this set, avoiding duplicates.
     *
     * @param newUrl The URL to be added as a new entry.
     * @return True if the addition is successful, or false if not.
     */
    public boolean addUrl(Url newUrl);

    /**
     * Removes one unspecified URL from this set, if possible.
     *
     * @return Either the removed URL, if the removal was successful, or null.
     */
    public Url remove();

    /**
     * Removes one occurrence of a given URL from this set, if possible.
     *
     * @param urlToRemove The URL to be removed.
     * @return True if the removal was successful, or false if not.
     */
    public boolean removeUrl(Url urlToRemove);

    /**
     * Removes all URLs from this set.
     */
    public void clear();

    /**
     * Checks whether this set contains a given URL.
     *
     * @param urlToFind The URL to locate.
     * @return True if the set contains the URL, or false if not.
     */
    public boolean contains(Url urlToFind);

    /**
     * Retrieves all URLs that are in this set.
     *
     * @return A newly allocated array of all the URLs in the set.
     * Note: If the set is empty, the returned array is empty.
     */
    public Url[] toArray();
} //end UrlSetInterface
