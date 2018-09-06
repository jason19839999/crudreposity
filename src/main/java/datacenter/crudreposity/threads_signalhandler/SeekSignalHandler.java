package datacenter.crudreposity.threads_signalhandler;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("restriction")
public class SeekSignalHandler implements SignalHandler {
	final static Logger logger = LoggerFactory
			.getLogger(SeekSignalHandler.class);
	public static boolean stop = false;
	public TServer server = null;

	public SeekSignalHandler() {
		super();
	}

	public SeekSignalHandler(TServer server) {
		super();
		this.server = server;
	}

	// band the kill signal for seek
	public void bind() {
		// install signals
		// in eclipse you can't see the log after Terminate (the red button)
		Signal.handle(new Signal("TERM"), this); // kill -15
		Signal.handle(new Signal("INT"), this); // kill -2

		logger.info("bind kill signal for seek.");
	}

	private void systemExit(Signal signal) throws Exception {

		logger.info(signal.getName() + " is recevied, system is existing...");
		stop = true;
		if (server != null) {
			server.stop();
		}

//		if (TTLHandler.timer != null)
//			TTLHandler.timer.cancel();
//		if(OFAgentSenderProxy.proxy != null)
//			OFAgentSenderProxy.getProxy().close(true);
//		if (TitleRuleHandler.timer != null)
//			TitleRuleHandler.timer.cancel();
//
//		//added on 2016-10-08
//		if (NewsProfileRules.timer != null)
//			NewsProfileRules.timer.cancel();
//
//		logger.info("starting wait ....");
//		Thread.sleep(120000);
//		logger.info("end wait ....");
//		SolrServerInstance.getInstance().ShutDownAllServer();
//		HBaseConnectionFactory.close();
		System.exit(0);
	}

	@Override
	public void handle(Signal signal) {
		try {
			if (signal.getName() == "TERM") {
				systemExit(signal);
			} else if (signal.getName() == "INT") {
				// TODO
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			int port = 9888;
			int minWorkerThreads = 5;
			int maxWorkerThreads = Integer.MAX_VALUE;
			CreeperService.Processor<CreeperServiceHandler> processor = new CreeperService.Processor<CreeperServiceHandler>(
					new CreeperServiceHandler());
			TServerTransport transport;
			transport = new TServerSocket(port);
			TBinaryProtocol.Factory portFactory = new TBinaryProtocol.Factory(true, true);
			TThreadPoolServer.Args argsPool = new TThreadPoolServer.Args(transport);
			//config thrift server Args
			argsPool.processor(processor);
			argsPool.protocolFactory(portFactory);
			argsPool.maxWorkerThreads(maxWorkerThreads);
			argsPool.minWorkerThreads(minWorkerThreads);

			TServer server = new TThreadPoolServer(argsPool);
			// bind signals
			new SeekSignalHandler(server).bind();
			//启动服务
			server.serve();
		}catch (Exception ex){
			ex.printStackTrace();
		}

		SeekSignalHandler testSignalHandler = new SeekSignalHandler();
		// install signals
		// in eclipse you can't see the log after Terminate (the red button)
		Signal.handle(new Signal("TERM"), testSignalHandler);
		Signal.handle(new Signal("INT"), testSignalHandler);
		// Signal.handle(new Signal("USR2"), testSignalHandler);

		int treadPoolSize = 3;
		ExecutorService execPush = Executors.newFixedThreadPool(treadPoolSize);
		// pusher
		for (int j = 0; j < treadPoolSize; j++) {
			// Thread.sleep(1000);
			//execPush.execute(new CreeperMQWorker());
		}
		execPush.shutdown();

		// wait for task complete
		while (!execPush.awaitTermination(1, TimeUnit.SECONDS)) {
			logger.info("wait to threads completed");
		}
		logger.info("threads stopped");

	}

}