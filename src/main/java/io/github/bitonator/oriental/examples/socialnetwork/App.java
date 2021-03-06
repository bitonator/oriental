package io.github.bitonator.oriental.examples.socialnetwork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import io.github.bitonator.oriental.EdgeEntity;
import io.github.bitonator.oriental.ThreadSafeGraph;
import io.github.bitonator.oriental.VertexEntity;

public class App {
    public static void main(String[] args) throws Exception {
        OrientGraphFactory factory=new OrientGraphFactory("memory:testdb").setupPool(1,10);
    
        UserManager userManager=new UserManager(factory);
        FriendManager friendManager=new FriendManager(factory);
        
        ThreadSafeGraph tsg=new ThreadSafeGraph(factory.getTx());

        userManager.setTsg(tsg);
        friendManager.setTsg(tsg);
    
        User u1=userManager.createUser("tom", "tom@gmail.com");
        User u2=userManager.createUser("harry", "harry@gmail.com");
        
        Friend f=friendManager.createFriendship(u1, u2);
        
        ObjectMapper mapper=new ObjectMapper();
        System.out.println(mapper.writeValueAsString(f));
        
        System.out.println(mapper.writeValueAsString(friendManager.getFriends(u1)));
        
        tsg.shutdown();
    }
}
