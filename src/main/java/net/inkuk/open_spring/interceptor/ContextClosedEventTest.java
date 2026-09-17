package net.inkuk.open_spring.interceptor;

import net.inkuk.open_spring.database.DataBaseClientPool;
import net.inkuk.open_spring.util.Log;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ContextClosedEventTest {

    @EventListener
    public void event(ContextClosedEvent event) {

        DataBaseClientPool.closeAll();
        Log.close();
    }
}
