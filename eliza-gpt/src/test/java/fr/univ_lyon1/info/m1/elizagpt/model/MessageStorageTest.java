import fr.univ_lyon1.info.m1.elizagpt.model.MessageObserver;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageStorage;
import fr.univ_lyon1.info.m1.elizagpt.model.RegexSearchStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.FindMessagesBySubstringStrategy;
import fr.univ_lyon1.info.m1.elizagpt.model.FindMessagesByWordStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import fr.univ_lyon1.info.m1.elizagpt.model.Message;


class MessageStorageTest implements MessageObserver {

    private boolean notified = false;

    @Test
    void testAddMessage() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Check if the "Bonjour" message is already there 
        assertEquals(1, messageStorage.getMessages().size());
        // Add a message
        messageStorage.addMessage("1", "Hello", true);

        // Check if the message is added
        assertEquals(2, messageStorage.getMessages().size());

        // Check if the observer is notified
        assertTrue(notified);
    }

    @Test
    void testRemoveMessageById() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Add a message
        messageStorage.addMessage("1", "Hello", true);

        // Remove the message by ID
        messageStorage.removeMessageById("1");

        // Check if the message is removed
        assertEquals(1, messageStorage.getMessages().size());


        // Remove the "Bonjour" message by ID
        messageStorage.removeMessageById("0");

        // Check if the message is removed
        assertEquals(0, messageStorage.getMessages().size());

        // Check if the observer is notified
        assertTrue(notified);
    }

    @Test
    void testRemoveMessagesByText() {
        MessageStorage messageStorage = new MessageStorage();
        messageStorage.registerObserver(this);

        // Add two messages with the same text
        messageStorage.addMessage("1", "Hello", true);
        messageStorage.addMessage("2", "Hello", true);

        // Remove messages by text
        messageStorage.removeMessagesByText("Hello");

        // Check if the messages are removed
        assertEquals(1, messageStorage.getMessages().size());


        // Remove the "Bonjour" message by ID
        messageStorage.removeMessageById("0");

        // Check if the message is removed
        assertEquals(0, messageStorage.getMessages().size());


        // Check if the observer is notified
        assertTrue(notified);
    }

    @Test
    void testFindMessagesByRegex() {
        MessageStorage messageStorage = new MessageStorage();
        RegexSearchStrategy strat = new RegexSearchStrategy();

        // Add messages with different texts
        messageStorage.addMessage("1", "Hello", true);
        messageStorage.addMessage("2", "Salut", true);
        messageStorage.addMessage("3", "Hi there", true);

        // Find messages by regex for "Hello"
        List<Message> helloMessages = strat.executeSearch("Hello", messageStorage);
        assertEquals(1, helloMessages.size());
        assertEquals("Hello", helloMessages.get(0).getMessageText());

        // Find messages by regex for "Bon.*"
        List<Message> bonjourMessages = strat.executeSearch("Sal.*", messageStorage);
        assertEquals(1, bonjourMessages.size());
        assertEquals("Salut", bonjourMessages.get(0).getMessageText());

        // Find messages by regex for ".*there.*"
        List<Message> thereMessages =
                strat.executeSearch(".*there.*", messageStorage);
        assertEquals(1, thereMessages.size());
        assertEquals("Hi there", thereMessages.get(0).getMessageText());

        // Find messages by regex for "Nonexistent"
        List<Message> nonexistentMessages =
                strat.executeSearch("Nonexistent", messageStorage);
        assertTrue(nonexistentMessages.isEmpty());
    }


    @Test
    void findMessagesBySubstring() {
        MessageStorage storage = new MessageStorage();
        FindMessagesBySubstringStrategy strat = new FindMessagesBySubstringStrategy();

        storage.addMessage("1", "Hello, how are you?", false);
        storage.addMessage("2", "I'm doing well, thank you!", true);

        List<Message> result = strat.executeSearch("well", storage);

        assertEquals(1, result.size());
        assertEquals("I'm doing well, thank you!", result.get(0).getMessageText());
    }


    @Test
    void findMessagesByWord() {
        MessageStorage storage = new MessageStorage();
        FindMessagesByWordStrategy strat = new FindMessagesByWordStrategy();


        storage.addMessage("1", "Hello, how are you?", false);
        storage.addMessage("2", "I'm doing well, thank you!", true);

        List<Message> result = strat.executeSearch("well", storage);

        assertEquals(1, result.size());
        assertEquals("I'm doing well, thank you!", result.get(0).getMessageText());
    }

    @Override
    public void update(final String notification) {
        notified = true;
    }
}
