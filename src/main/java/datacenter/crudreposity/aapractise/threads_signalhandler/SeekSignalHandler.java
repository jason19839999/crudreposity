package datacenter.crudreposity.aapractise.threads_signalhandler;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.thrift.server.TServer;

@SuppressWarnings("restriction")           //多线程处理的地方通过判断SeekSignalHandler.stop来决定是否继续执行   while(!SeekSignalHandler.stop){处理业务逻辑。。。}
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
			//执行业务逻辑
			startWorker();
			// bind signals
			new SeekSignalHandler(server).bind();
			logger.info("seek service for creeper is starting. Listening to port " + port);
			server.serve();
		}catch (Exception ex){
			ex.printStackTrace();
		}
	}

	private  static void startWorker() throws InterruptedException {
		//以下是多线程处理
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