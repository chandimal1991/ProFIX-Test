package com.nss.messenger;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import simplefix.Application;
import simplefix.Engine;
import simplefix.EngineFactory;
import simplefix.Message;
import simplefix.Session;

@Path("/engine")
public class EngineResource {
	
	private static EngineFactory _engineFact;
    private Engine _engine;

	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String startEngine() {
		
		try {
		    
		    Class<?> classobj = Class.forName("simplefix.quickfix.EngineFactory");
		    Object engineobj = classobj.newInstance();
		    
		    if ( engineobj instanceof EngineFactory ) {
			
			_engineFact = (EngineFactory) engineobj;
			_engine = _engineFact.createEngine();
			_engine.initEngine("banzai.cfg");
			
			Application application = new _Application();
			
			_engine.startInProcess(application);
			
			
		    }
		} catch ( Exception e ) {
		    e.printStackTrace();
		}
		
        return "Engine Started!";
    }
	
	private static class _Application implements Application {
		
		public _Application() {
		}
		
		@Override
		public void onAppMessage(final Message arg0, final Session arg1) {
		    // TODO Auto-generated method stub
		    
		}
		
		@Override
		public void onLogon(final Session session) {
			
			System.out.println("LoggedOn==>" + session.getSenderCompID() + "<-->" + session.getTargetCompID());
	         
		}
		
		@Override
		public void onLogout(final Session session) {
		    // TODO Auto-generated method stub
			System.out.println("logout==>" + session.getSenderCompID() + "<-->" + session.getTargetCompID());
		    
		}
	    };
	
}
