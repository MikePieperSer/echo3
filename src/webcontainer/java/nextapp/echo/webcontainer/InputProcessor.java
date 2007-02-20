package nextapp.echo.webcontainer;

import java.io.IOException;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import nextapp.echo.app.Component;
import nextapp.echo.app.update.ClientUpdateManager;
import nextapp.echo.app.update.UpdateManager;
import nextapp.echo.app.util.Context;
import nextapp.echo.app.xml.XmlContext;
import nextapp.echo.app.xml.XmlPropertyPeer;

public class InputProcessor {

    private class InputContext implements Context {
        
        private XmlContext xmlContext = new XmlContext(){
        
            public XmlPropertyPeer getPropertyPeer(Class propertyClass) {
                // TODO Auto-generated method stub
                return null;
            }
        
            public ClassLoader getClassLoader() {
                //FIXME. temporary, not what we want.
                return Thread.currentThread().getContextClassLoader();
            }
        
            public Document getDocument() {
                return clientMessage.getDocument();
            }
        };
        
        /**
         * @see nextapp.echo.app.util.Context#get(java.lang.Class)
         */
        public Object get(Class specificContextClass) {
            if (specificContextClass == XmlContext.class) {
                return xmlContext;
            } else if (specificContextClass == Connection.class) {
                return conn;
            } else if (specificContextClass == ClientMessage.class) {
                return clientMessage;
            } else if (specificContextClass == UserInstance.class) {
                return conn.getUserInstance();
            } else if (specificContextClass == ClientUpdateManager.class) {
                return conn.getUserInstance().getApplicationInstance().getUpdateManager().getClientUpdateManager();
            } else {
                return null;
            }
        }
    }
    
    private Connection conn;
    private ClientMessage clientMessage;

    public InputProcessor(Connection conn) {
        super();
        this.conn = conn;
    }
    
    public void process() 
    throws IOException {
        clientMessage = new ClientMessage(conn);

        Context context = new InputContext();
        UserInstance userInstance = conn.getUserInstance();
        UpdateManager updateManager = userInstance.getUpdateManager();
        ClientUpdateManager clientUpdateManager = updateManager.getClientUpdateManager();
        
        if (ClientMessage.TYPE_INITIALIZE.equals(clientMessage.getType())) {
            // Flag full refresh if initializing.
            updateManager.getServerUpdateManager().processFullRefresh();
        }
        
        Iterator updatedComponentIdIt  = clientMessage.getUpdatedComponentIds();
        while (updatedComponentIdIt.hasNext()) {
            String componentId = (String) updatedComponentIdIt.next();
            Component component = userInstance.getComponentByElementId(componentId);
            ComponentSynchronizePeer componentPeer = SynchronizePeerFactory.getPeerForComponent(component.getClass());
            
            Iterator updatedPropertyIt = clientMessage.getUpdatedPropertyNames(componentId);
            while (updatedPropertyIt.hasNext()) {
                String propertyName = (String) updatedPropertyIt.next();
                Element propertyElement = clientMessage.getUpdatedProperty(componentId, propertyName);

                Class propertyClass = componentPeer.getPropertyClass(propertyName);
                if (propertyClass == null) {
                    continue;
                }
                
                PropertySynchronizePeer propertyPeer = 
                        (PropertySynchronizePeer) SynchronizePeerFactory.getPeerForProperty(propertyClass);
                if (propertyPeer == null) {
                    continue;
                }
                
                Object propertyValue = propertyPeer.toProperty(context, component.getClass(), propertyElement);
                
                componentPeer.storeInputProperty(context, component, propertyName, propertyValue);
            }
        }
        
        if (clientMessage.getEventType() != null) {
            Component component = userInstance.getComponentByElementId(clientMessage.getEventComponentId());
            clientUpdateManager.setComponentAction(component, clientMessage.getEventType(), null);
        }

        updateManager.processClientUpdates();
    }
}
