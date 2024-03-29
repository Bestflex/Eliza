package fr.univ_lyon1.info.m1.elizagpt.model;

import java.util.List;
// import fr.univ_lyon1.info.m1.elizagpt.model.Message;
// import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;



/**
 * Interface defining a search strategy for message retrieval.
 * Implementations of this interface define specific search logic
 * and provide a way to identify the search option.
 */
public interface SearchStrategy {


    /**
     * Executes the search based on the given search query and
     * the message storage.
     *
     * @param searchQuery The query string for the search.
     * @param messageStorage The storage containing messages.
     * @return A list of messages matching the search criteria.
     */
    List<Message> executeSearch(
                String searchQuery,
                MessageStorage messageStorage);


    /**
     * Returns a string representation of the search option.
     * By default, it is the same as the option name.
     *
     * @return A string representation of the search option.
     */
    @Override
    String toString();
}


